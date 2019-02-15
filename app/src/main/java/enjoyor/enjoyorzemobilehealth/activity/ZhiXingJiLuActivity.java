package enjoyor.enjoyorzemobilehealth.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import enjoyor.enjoyorzemobilehealth.adapter.ZhiXingJiLuAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.ZhiXingJL;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ZhiXingJiLuActivity extends BaseActivity {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private String tmID;

    private ListView lvZXJL;

    ZhierCall zhierCall;

    private List<ZhiXingJL> mData = new ArrayList<>();

    MyXmlHandler myXmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    lvZXJL.setAdapter(new ZhiXingJiLuAdapter(ZhiXingJiLuActivity.this, mData));
//                    TextView view= (TextView) findViewById(R.id.tv_empty);
//                    lvZXJL.setEmptyView(view);
                    LinearLayout noData = (LinearLayout) findViewById(R.id.nodata);
                    lvZXJL.setEmptyView(noData);
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            ZhiXingJL bean = (ZhiXingJL) msg.obj;
                            if (!TextUtils.isEmpty(bean.getYiZhuZXZT())) {
                                mData.add(bean);
                            }
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zhixingjilu);
        ButterKnife.bind(this);
        init();
        initView();
        getData();
    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvTitleInclude.setText("执行记录");
    }

    private void getData() {
        tmID = getIntent().getStringExtra("tmid");
        String yhid = MyApplication.getInstance().getYhgh();
        log.e("---data" + tmID);
        zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("0401101")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(tmID)
                .setContext(ZhiXingJiLuActivity.this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
              log.e("---data" + data);
                parseData(myXmlHandler, new Class[]{ZhiXingJL.class}, data);
            }

            @Override
            public void fail(String info) {
                Log.i("data", "---info" + info);
            }
        });
    }

    private void initView() {
        lvZXJL = (ListView) findViewById(R.id.lv_zxjl);
//        TextView view= (TextView) findViewById(R.id.tv_empty);
//        lvZXJL.setEmptyView(view);

        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 解析xml并发消息
     *
     * @param handler 消息handler
     * @param cla     bean数组
     * @param data    从服务端获得的数据
     */
    private void parseData(MyXmlHandler handler, Class[] cla, String data) {
        StringXmlParser parser = new StringXmlParser(handler, cla);
        parser.parse(data);
    }
}
