package enjoyor.enjoyorzemobilehealth.activity.xxcx;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bben.date.NowDate;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.SsjlxqActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.SscxAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.ShouShuXX;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/6/27.
 */

public class SsjlcxActivity extends BaseActivity {
    ListView listView;
    SscxAdapter adapter;
    @BindView(R.id.tv_empty_content)
    TextView tvEmptyContent;
    @BindView(R.id.ll_empty_data)
    LinearLayout llEmptyData;
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private ImageView back;
    private ZhierCall zhierCall;
    private String time1 = NowDate.get() + " 00:00:00";
    private String time2 = NowDate.get() + " 23:59:59";
    List<ShouShuXX> listBRLB = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssjlcx);
        ButterKnife.bind(this);
        init();

        listView = (ListView) findViewById(R.id.sslb);
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SsjlcxActivity.this, XxcxActivity.class));
                finish();
            }
        });
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");
        String canshu = preferences2.getString("bqdm", "");
        String s = MyApplication.getInstance().getBqdm();
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        month++;
        /*time1="2017"+"-"+month+"-"+day+" 00:00:00";
        time2="2017"+"-"+month+"-"+day+" 23:59:59";*/
        String t1 = time1;
        String t2 = time2;

        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0401513")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(s + "¤" + time1 + "¤" + "1")
                .setContext(SsjlcxActivity.this)
                .setPort(5000).setDialogmes("请求中...")
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{ShouShuXX.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void init() {
        tvTitleInclude.setText("手术记录查询");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }
    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listBRLB.size() == 0 || listBRLB.get(0).getCHUANGWEIHAO().equals("")) {
                        llEmptyData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        tvEmptyContent.setText("查询不到任何手术记录");
                        log.e("查询到空表");
                    } else {
                        llEmptyData.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        adapter = new SscxAdapter(SsjlcxActivity.this, listBRLB);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                                Intent intent = new Intent(SsjlcxActivity.this, SsjlxqActivity.class);
                                intent.putExtra("xm", listBRLB.get(arg2).getXINGMING());//getBingRenXM());
                                intent.putExtra("sj", listBRLB.get(arg2).getAPRQ());//.getKaiShiSJ());
                                intent.putExtra("ssmc", listBRLB.get(arg2).getSSMC());//.getShouSuMC());
//                                intent.putExtra("sss",listBRLB.get(arg2).getShouShuShi());
//                                intent.putExtra("zd",listBRLB.get(arg2).getShouShuRY());
//                                intent.putExtra("mzry",listBRLB.get(arg2).getMaZuiRY());
//                                intent.putExtra("sqzd",listBRLB.get(arg2).getShuQianZD());
//                                intent.putExtra("shzd",listBRLB.get(arg2).getShuHouZD());
//                                intent.putExtra("ssmx",listBRLB.get(arg2).getShouShuMX());
//                                intent.putExtra("xb",listBRLB.get(arg2).getXingBie());
                                intent.putExtra("ch", listBRLB.get(arg2).getCHUANGWEIHAO());

                               /* intent5.putExtra("RUYUANSJ",listBRLB.get(arg2).getRUYUANSJ());
                                intent5.putExtra("CHUANGHAO",listBRLB.get(arg2).getCHUANGWEIHAO());
                                intent5.putExtra("XINGMING",listBRLB.get(arg2).getXINGMING());
                                intent5.putExtra("XINGBIE",listBRLB.get(arg2).getXINGBIE());*/

                                startActivity(intent);
                                finish();


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
                            listBRLB.add((ShouShuXX) msg.obj);
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

            startActivity(new Intent(SsjlcxActivity.this, XxcxActivity.class));
            finish();
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

}
