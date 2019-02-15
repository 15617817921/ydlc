package enjoyor.enjoyorzemobilehealth.activity.infosearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.adapter.BqxhjlAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/7/19.
 */

public class BqxhcxActivity extends BaseActivity {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private DateUtil instance;
    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private ListView list_bqxh;
    private ZhierCall zhierCall;
    private TextView cxsj;
    private View line;
    private LinearLayout sjxz;
    private LinearLayout nodata;
    private String sj;
    private int flag = 0;
    private List<XunHuiJL> listXunHuiJL = new ArrayList<>();

    private String yhid;

    public void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_bqxhcx);
        ButterKnife.bind(this);
        init();

        defineData();
        clickData();
        initData();
    }

    private void init() {
        yhid = MyApplication.getInstance().getYhgh();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvTitleInclude.setText("病区巡回查询");
    }

    private void defineData() {
        list_bqxh = (ListView) findViewById(R.id.list_bqxh);
        cxsj = (TextView) findViewById(R.id.cxsj);
        sjxz = (LinearLayout) findViewById(R.id.sjxz);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        line = (View) findViewById(R.id.line);
    }

    private void clickData() {
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BqxhcxActivity.this,XhcxActivity.class));
                finish();
            }
        });
        sjxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/MM/dd");
                //时间选择器
                pvTime = new TimePickerView.Builder(BqxhcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        sj = format.format(date);
                        flag = 1;
                        initData();
                    }
                }).setDate(instance.getCalendar(cxsj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();

            }
        });
    }

    private void initData() {

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String BingQuDM = preferences2.getString("bqdm", "");
        if (flag == 0) {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyy/MM/dd");
            sj = formatter.format(currentTime);
        } else {
        }
        cxsj.setText(sj);
        String kaishiSJ = sj + " 0:00:00";
        String jieshuSJ = sj + " 23:59:59";
        listXunHuiJL.clear();

        String canshu = BingQuDM + "¤" + kaishiSJ + "¤" + jieshuSJ + "¤" + "BQ";
        zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("03043001")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000) .setDialogmes("查询中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                StringXmlParser parser = new StringXmlParser(xmlHandler, new Class[]{XunHuiJL.class});
                log.e(data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
            }

        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listXunHuiJL.get(0).getBingRenZYID() == "") {
                        list_bqxh.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                    } else {
                        line.setVisibility(View.GONE);
                        list_bqxh.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        BqxhjlAdapter adapter = new BqxhjlAdapter(BqxhcxActivity.this, listXunHuiJL);
                        list_bqxh.setAdapter(adapter);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listXunHuiJL.add((XunHuiJL) msg.obj);
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
}
