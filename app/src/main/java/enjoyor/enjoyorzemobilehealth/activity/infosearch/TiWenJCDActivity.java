package enjoyor.enjoyorzemobilehealth.activity.infosearch;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.TiWenJCDAdapter;
import enjoyor.enjoyorzemobilehealth.entities.TiWenJCD;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/6/27.
 */

public class TiWenJCDActivity extends BaseActivity {
    ListView listView;
    TiWenJCDAdapter adapter;
    private ImageView back;
    private ZhierCall zhierCall;
    private ProgressDialog progressDialog;
    List<TiWenJCD> listBRLB=new ArrayList<>();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiwenjcd);
        listView=(ListView)findViewById(R.id.yslb);
        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(TiWenJCDActivity.this,XxcxActivity.class));
                finish();
            }
        });
        showProgress();
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name=preferences2.getString("id","");
        String canshu=preferences2.getString("bqdm","");
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0601006")
                .setMessage(NetWork.SMTZ)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("zzzz3",data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{TiWenJCD.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(TiWenJCDActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在获取数据...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    Log.d("zzzz5","hello");

                    progressDialog.dismiss();
                    if (listBRLB.size() == 0) {
                        Log.d("zzzz1","1111");
                    }
                    else{
                        adapter=new TiWenJCDAdapter(TiWenJCDActivity.this,listBRLB);
                        Log.d("login32","准备执行适配器");
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                            }
                        });
                        /*
                        List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
                        for(int i=0;i<listBRLB.size();i++){
                            HashMap<String,Object>  item=new HashMap<String,Object>();
                            item.put("xm",listBRLB.get(i).getBingRenXM());
                            item.put("xb",listBRLB.get(i).getXingBie());
                            data.add(item);

                        }
                        Log.d("zzzz1",data.toString());
                        SimpleAdapter simplead = new SimpleAdapter(SsjlcxActivity.this, data,
                            R.layout.activity_ssjlcx_item, new String[] { "xm", "xb" },
                            new int[] {R.id.xm,R.id.xb});


                    listView.setAdapter(simplead);*/

                    }


                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBRLB.add((TiWenJCD) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(TiWenJCDActivity.this, XxcxActivity.class));
            finish();
            return true;
        }



        return super.onKeyDown(keyCode, event);
    }

}
