package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.TemperatureDetailAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.SMTZSJ;
import enjoyor.enjoyorzemobilehealth.entities.TiWen;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/6/7.
 */

public class TemperatureDetailActivity extends BaseActivity {
    private Context context;
    private List<TiWen> mTiWenList;
    private ImageView mIvBack;
    private ListView mLvTemperatureDetail;
    private RelativeLayout emptyView;
    private MyApplication myApplication;
    private int selectPos = 0;
    private String selectTime;
    private String threeDayAgoTime;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<SMTZSJ> listBRLB = new ArrayList<>();
    TemperatureDetailAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_temperature_detail);
        init();
        initView();
        initValue();
    }

    private void init() {
        context = this;
        myApplication = MyApplication.getInstance();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);

        Intent intent = getIntent();
        selectPos = intent.getIntExtra("position", 0);
    }

    private void initValue() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //mTvTimeYMD.setText(year + "-" + month + "-" + day);
        selectTime = mDateFormat.format(calendar.getTime()) + "";

        String yhid = myApplication.getYhgh();
        String id = myApplication.getListBRLB().get(myApplication.getChoosebr()).getBINGRENZYID();
        String bingQuDM = myApplication.getListBRLB().get(selectPos).getBINGQUDM();
        Log.i("Data", "id++++++bingQuDM" + id + "---" + bingQuDM);
        String canShu = id + NetWork.SEPARATE + bingQuDM + NetWork.SEPARATE + selectTime;

        ZhierCall call = new ZhierCall()
                .setId("1000")
                .setNumber("0500402")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(canShu)
                .setContext(this)
                .setPort(5000).setDialogmes("请求中...")
                .build();
        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                StringXmlParser parser = new StringXmlParser(xmlHandler, new Class[]{SMTZSJ.class});
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
                    Log.d("zzzz5", "hello");


                    if (listBRLB.size() == 0||listBRLB.get(0).getSHIJIAN().equals("")) {

                    } else {
                        adapter = new TemperatureDetailAdapter(TemperatureDetailActivity.this, listBRLB);

                        mLvTemperatureDetail.setAdapter(adapter);
                        mLvTemperatureDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
//                                Intent intent=new Intent(TemperatureDetailActivity.this,SsjlxqActivity.class);
//                                intent.putExtra("xm",listBRLB.get(arg2).getXINGMING());//getBingRenXM());
//                                intent.putExtra("sj",listBRLB.get(arg2).getAPRQ());//.getKaiShiSJ());
//                                intent.putExtra("ssmc",listBRLB.get(arg2).getSSMC());//.getShouSuMC());
////                                intent.putExtra("sss",listBRLB.get(arg2).getShouShuShi());
////                                intent.putExtra("zd",listBRLB.get(arg2).getShouShuRY());
////                                intent.putExtra("mzry",listBRLB.get(arg2).getMaZuiRY());
////                                intent.putExtra("sqzd",listBRLB.get(arg2).getShuQianZD());
////                                intent.putExtra("shzd",listBRLB.get(arg2).getShuHouZD());
////                                intent.putExtra("ssmx",listBRLB.get(arg2).getShouShuMX());
////                                intent.putExtra("xb",listBRLB.get(arg2).getXingBie());
//                                intent.putExtra("ch",listBRLB.get(arg2).getCHUANGWEIHAO());
//
//                               /* intent5.putExtra("RUYUANSJ",listBRLB.get(arg2).getRUYUANSJ());
//                                intent5.putExtra("CHUANGHAO",listBRLB.get(arg2).getCHUANGWEIHAO());
//                                intent5.putExtra("XINGMING",listBRLB.get(arg2).getXINGMING());
//                                intent5.putExtra("XINGBIE",listBRLB.get(arg2).getXINGBIE());*/
//
//                                startActivity(intent);
//                                finish();


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
                            listBRLB.add((SMTZSJ) msg.obj);
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

    private void getData() {
//        mTiWenList=new ArrayList<>();
//        Intent intent=getIntent();
//        mTiWenList= (List<TiWen>) intent.getSerializableExtra("temperatureList");
//        mLvTemperatureDetail.setAdapter(new TemperatureDetailAdapter(context,mTiWenList));
//        mLvTemperatureDetail.setEmptyView(emptyView);
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mLvTemperatureDetail = (ListView) findViewById(R.id.lv_temperature_detail);
        emptyView = (RelativeLayout) findViewById(R.id.rl_empty_view);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
