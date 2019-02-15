package qdh.jyhd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HeDuiActivity;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by dantevsyou on 2017/11/15.
 */

public class HeDuiJLActivity extends BaseActivity {
    private DateUtil instance;
    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private ZhierCall zhierCall;
    private ListView list_heduijl;
    private TextView kssj;
    private TextView jssj;
    private ImageView back;
    private LinearLayout nodata;
    private View line;
    private String kaishiSJ;
    private String jieshuSJ;
    private String userID;
    private String bingQuDM;
    private List<JianYanJGMX> listHeDuiJL =new ArrayList<>();

    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_heduijl);
        defineData();
        clickData();
        initData();
    }
    private void defineData(){
        list_heduijl = (ListView) findViewById(R.id.list_heduijl);
        back = (ImageView) findViewById(R.id.iv_hdjl_back);
        kssj = (TextView) findViewById(R.id.tv_hdjl_kssj);
        jssj = (TextView) findViewById(R.id.tv_hdjl_jssj);
        nodata = (LinearLayout) findViewById(R.id.ll_hdjl_nodata);
        line = (View) findViewById(R.id.line_hdjl);
    }
    private void clickData(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HeDuiJLActivity.this,HeDuiActivity.class));
                finish();
            }
        });
        kssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance= DateUtil.getInstance();
                format=new SimpleDateFormat("yyyy/MM/dd");
                //时间选择器
                pvTime = new TimePickerView.Builder(HeDuiJLActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        kaishiSJ=format.format(date);
                        kssj.setText(kaishiSJ);
                        commitData();
                    }
                }).setDate(instance.getCalendar(kssj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });
        //结束时间选择，请求数据
        jssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance= DateUtil.getInstance();
                format=new SimpleDateFormat("yyyy/MM/dd");
                //时间选择器
                pvTime = new TimePickerView.Builder(HeDuiJLActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        jieshuSJ =format.format(date);
                        jssj.setText(jieshuSJ);
                        commitData();
                    }
                }).setDate(instance.getCalendar(jssj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });
    }
    private void initData(){
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        userID = preferences2.getString("id", "");
        bingQuDM=preferences2.getString("bqdm","");
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        jieshuSJ = formatter.format(c.getTime());
        c.add(Calendar.DATE, -7);
        kaishiSJ= formatter.format(c.getTime());
        kssj.setText(kaishiSJ);
        jssj.setText(jieshuSJ);
        commitData();

    }
    private void commitData(){
        String canshu=kaishiSJ+" 0:00:00¤"+jieshuSJ+" 23:59:59¤"+bingQuDM;
        listHeDuiJL.clear();
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401508")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JianYanJGMX.class});

                Log.v("login12", data);
                parser.parse(data);

            }
            @Override
            public void fail(String info) {
            }

        });
    }
    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if(listHeDuiJL.size()==0){
                        Log.d("fafdafasdf","fafafa");
                        list_heduijl.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                    }else {
                        Log.d("fafdafasdf","fafafa111111");
                        line.setVisibility(View.GONE);
                        list_heduijl.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        HeDuiJLAdapter adapter = new HeDuiJLAdapter(HeDuiJLActivity.this, listHeDuiJL);
                        list_heduijl.setAdapter(adapter);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            JianYanJGMX bean=(JianYanJGMX) msg.obj;
                            if(!TextUtils.isEmpty(bean.getBingRenZYID())){
                                listHeDuiJL.add((JianYanJGMX) msg.obj);
                            }
                            Log.d("fafdafasdf","fafafa111166");
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
