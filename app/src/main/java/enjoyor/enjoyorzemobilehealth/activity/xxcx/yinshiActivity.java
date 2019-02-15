package enjoyor.enjoyorzemobilehealth.activity.xxcx;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.yinshiAdapter;
import enjoyor.enjoyorzemobilehealth.entities.YinShi;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/6/27.
 */

public class yinshiActivity extends BaseActivity {
    ListView listView;
    yinshiAdapter adapter;
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
    List<YinShi> listBRLB = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yinshi);
        ButterKnife.bind(this);
        init();
        listView = (ListView) findViewById(R.id.yslb);

        initData();
        initListener();
    }

    private void init() {
        tvTitleInclude.setText("饮食查询");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }

    private void initData() {

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");
        String canshu = preferences2.getString("bqdm", "");
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0500405")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(canshu + "¤" + "")
                .setContext(yinshiActivity.this)
                .setPort(5000).setDialogmes("加载中...")
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {


                Log.d("zzzz3", data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{YinShi.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initListener() {
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(yinshiActivity.this, XxcxActivity.class));
                finish();
            }
        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listBRLB.size() == 0 || listBRLB.get(0).getCHUANGWEIHAO().equals("")) {
                        llEmptyData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        tvEmptyContent.setText("查询不到任何饮食记录");
                        log.e("查询到空表");
                    } else {
                        llEmptyData.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        adapter = new yinshiAdapter(yinshiActivity.this, listBRLB);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                            }
                        });
                    }


                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBRLB.add((YinShi) msg.obj);
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

            startActivity(new Intent(yinshiActivity.this, XxcxActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
