package enjoyor.enjoyorzemobilehealth.activity.home;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.TemperatureDetailActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.TiWen;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.ScreenUtils;
import enjoyor.enjoyorzemobilehealth.views.AutoTemperatureLineChart;
import enjoyor.enjoyorzemobilehealth.views.DateTimeDialogOnlyYMD;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * 体温单
 * Created by Administrator on 2017/5/10.
 */

public class TemperatureChartActivity extends BaseActivity implements DateTimeDialogOnlyYMD.MyOnDateSetListener, View.OnClickListener, ScanFactory.FragScan {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Context context;

    private static final int REQUEST_CODE = 1; // 请求码
    private int selectPos = 0;
    private MyApplication myApplication;


    private ImageView mIvTouXiang;
    private TextView mTvTemperPatientName;
    private TextView mTvChuangHao;
    private TextView mTvTimeYMD;
    private TextView mTvChartTemper;
    private TextView mTvChartMsgNormal;
    private TextView mTvChartMsgUp;
    private ImageView mIvChartMsgNormal;
    private ImageView mIvChartMsgUp;
    private LinearLayout llHsvContent;

    private DateTimeDialogOnlyYMD mDateTimeDialogOnlyYMD;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String selectTime;
    private String threeDayAgoTime;

    private List<TiWen> temperatureList = new ArrayList<>();
    //详情页面的list集合
    private List<TiWen> temperatureDetailList = new ArrayList<>();

    private String[] xLineData;
    private String[] yLineData = new String[]{"35", "36", "37", "38", "39", "40", "41", "42"};
    private String[] value;
    private static final int PER_PAGE_COUNT = 4;
    private int pageCount;
    private String tiaoMa;
    //扫描提示音
    private boolean playBeep = true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;
    private DoDecodeThread mDoDecodeThread;
    private Handler mDoDecodeHandler;
    ScanInterface scanInf;
    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_temperature_chart);
        ButterKnife.bind(this);
        init();
        initHandler(this);
        initBeep();
        intent = getIntent();
        selectPos = intent.getIntExtra("position", 0);

        initView();

        initData();
    }

    private void init() {
        context = this;
        myApplication = MyApplication.getInstance();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvRight.setText("明细");
        tvTitleInclude.setText("体温单");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_temperature_touxiang:
            case R.id.tv_temperature_patient_name:
                Intent intent = new Intent(context, BrlbActivity.class);
                intent.putExtra("which", "TWD");
                startActivity(intent);
                finish();
                //startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.tv_time_ymd:
                mDateTimeDialogOnlyYMD.hideOrShow();
                break;
            default:
                break;
        }
    }
    @OnClick({R.id.iv_back_include, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_include:
                startActivity(new Intent(TemperatureChartActivity.this, HomePageActivity.class));
                finish();
                break;
            case R.id.tv_right:
                Intent temperatureDetailIntent = new Intent(context, TemperatureDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("temperatureList", (Serializable) temperatureDetailList);
                temperatureDetailIntent.putExtras(bundle);
                startActivity(temperatureDetailIntent);
                break;
        }
    }

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }


    private void initBeep() {
        try {
            //注册默认音频通道
            this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            successAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            failAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            scanAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            successMediaPlayer = new MediaPlayer();
            failMediaPlayer = new MediaPlayer();
            scanMediaPlayer = new MediaPlayer();
            //指定播放的声音通道
            successMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            failMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            scanMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //当播放完毕一次后，重新指向流文件的开头，以准备下次播放
            successMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer player) {
                    // TODO Auto-generated method stub
                    player.seekTo(0);
                }
            });
            failMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer player) {
                    // TODO Auto-generated method stub
                    player.seekTo(0);
                }
            });
            scanMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer player) {
                    // TODO Auto-generated method stub
                    player.seekTo(0);
                }
            });
            //设定数据源，并准备播放
            AssetFileDescriptor successfile = getResources().openRawResourceFd(R.raw.success);
            AssetFileDescriptor failfile = getResources().openRawResourceFd(R.raw.failure);
            AssetFileDescriptor scanfile = getResources().openRawResourceFd(R.raw.scansuccessbeep);

            successMediaPlayer.setDataSource(successfile.getFileDescriptor(), successfile.getStartOffset(), successfile.getLength());
            failMediaPlayer.setDataSource(failfile.getFileDescriptor(), failfile.getStartOffset(), failfile.getLength());
            scanMediaPlayer.setDataSource(scanfile.getFileDescriptor(), scanfile.getStartOffset(), scanfile.getLength());
            successfile.close();
            failfile.close();
            scanfile.close();
            //设置音量大小
            successMediaPlayer.setVolume(0.5f, 0.5f);
            failMediaPlayer.setVolume(0.5f, 0.5f);
            scanMediaPlayer.setVolume(0.5f, 0.5f);
            successMediaPlayer.prepare();
            failMediaPlayer.prepare();
            scanMediaPlayer.prepare();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 播放beep声
     */
    private void playSuccessBeepSound() {

        if (successAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        if (playBeep && successMediaPlayer != null) {
            successMediaPlayer.start();
        }
    }

    /**
     * 播放beep声
     */
    private void playFailBeepSound() {
        //检查铃音模式
        if (failAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }

        if (playBeep && failMediaPlayer != null) {
            failMediaPlayer.start();
        }
    }

    /**
     * 播放beep声
     */
    private void playScanSuccessBeepSound() {
        //检查铃音模式
        if (scanAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        if (playBeep && scanMediaPlayer != null) {
            scanMediaPlayer.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new DoDecodeThread();
            mDoDecodeThread.start();
            //initSaoMiao();
        }

    }

    //    public void initSaoMiao() {
//        scanInf = new ScanFactory() {
//            public void handleData(String data, int keycode) {
//                super.handleData(data, keycode);
//
//            }
//
//            public ScanInterface getInstance(int flag, BryzActivity context) {
//
//                return null;
//            }
//
//            public Activity getFragment() {
//                return TemperatureChartActivity.this;
//            }
//        }.getInstance(4, this, 0);
//    }
//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(TemperatureChartActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyUp(keyCode, event)) {
//                return true;
//            }
//        }
//
//        return super.onKeyUp(keyCode, event);
//    }
    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        data = data.trim().replace("��", "¤").replace("?", "¤");
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        String[] s1 = data.split("\\¤");
        Pattern pattern = Pattern.compile("^[-+]?[0-9]");

        if (s[0].equals("st72")) {
            zxBR(s[1]);
        }
    }

    //去病人列表中核实是否有这条id，如果有去切换医嘱
    private void zxBR(String brid) {
        try {
            List<BRLB> listBRLB = new ArrayList<>();
            listBRLB = MyApplication.getInstance().getListBRLB();
            int i = 0;
            for (int j = 0; j < listBRLB.size(); j++) {
                if (brid.equals(listBRLB.get(j).getBINGRENZYID())) {
                    selectPos = j;
                    i = 1;
                    break;
                }
            }

            if (i == 0) {
                Toast.makeText(TemperatureChartActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            } else {
                initData();
                playSuccessBeepSound();
                Toast.makeText(TemperatureChartActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(TemperatureChartActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    private  List<TiWen> max_tiwen_list = new ArrayList<>();
    MyXmlHandler getTemperatureHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (temperatureList.size() > 0) {
                        float max = 0;
                        for (int i = 0; i < max_tiwen_list.size(); i++) {
                            try {
                                float cur = Float.parseFloat(max_tiwen_list.get(i).getTiWen());
                                if (cur > max) {
                                    max = cur;
                                }
                            } catch (Exception e) {
                                log.e(e.getMessage() + "--" + e.toString());
                            }
                        }
//                        //Textview字符串拼接
//                        String text=String.format(getResources().getString(RcyMoreAdapter.string.max_temperature),max+"");
//                        SpannableString spanString = new SpannableString(text);
//                        int index=text.indexOf(max+"");
//                        int length=(max+"").length();
//                        //设置数值部分文本大小
//                        AbsoluteSizeSpan span = new AbsoluteSizeSpan(60);
//                        spanString.setSpan(span, index, index+length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                        mTvChartTemper.setText(spanString);

                        mTvChartTemper.setText(max + "");
                        if (max > 37.5) {
                            mTvChartMsgUp.setVisibility(View.VISIBLE);
                            mIvChartMsgUp.setVisibility(View.VISIBLE);
                            mTvChartMsgNormal.setVisibility(View.GONE);
                            mIvChartMsgNormal.setVisibility(View.GONE);
                        } else {
                            mTvChartMsgUp.setVisibility(View.GONE);
                            mIvChartMsgUp.setVisibility(View.GONE);
                            mTvChartMsgNormal.setVisibility(View.VISIBLE);
                            mIvChartMsgNormal.setVisibility(View.VISIBLE);
                        }

                        int res = temperatureList.size() % PER_PAGE_COUNT;
                        if (res != 0) {
                            pageCount = temperatureList.size() / PER_PAGE_COUNT + 1;
                        } else {
                            pageCount = temperatureList.size() / PER_PAGE_COUNT;
                        }
                        int perWidth = ScreenUtils.getScreenWidth(context) / (PER_PAGE_COUNT - 1);
                        AutoTemperatureLineChart lineChart = new AutoTemperatureLineChart(context, temperatureList);
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(perWidth * (temperatureList.size() - 1), LinearLayout.LayoutParams.MATCH_PARENT);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(perWidth * (temperatureList.size()), LinearLayout.LayoutParams.MATCH_PARENT);
                        lineChart.setLayoutParams(params);
                        llHsvContent.removeAllViews();
                        llHsvContent.addView(lineChart);
                    } else {
                        int perWidth = ScreenUtils.getScreenWidth(context) / (PER_PAGE_COUNT - 1);
                        AutoTemperatureLineChart lineChart = new AutoTemperatureLineChart(context, temperatureList);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(context), LinearLayout.LayoutParams.MATCH_PARENT);
                        lineChart.setLayoutParams(params);
                        llHsvContent.removeAllViews();
                        llHsvContent.addView(lineChart);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            TiWen tiWen = (TiWen) msg.obj;
                            TiWen tiWenDetail = (TiWen) msg.obj;
                            String rqTiWen = tiWen.getCaiJiRQ();
                            String sjTiWen = tiWen.getCaiJiSJ();
                            //有效值判断
                            if (!TextUtils.isEmpty(sjTiWen)) {
                                //字符串截取
                                tiWen.setCaiJiRQ(rqTiWen.substring(5));
                                tiWen.setCaiJiSJ(sjTiWen.substring(0, 5));

                                if (tiWenDetail.getLeiXing().equals("1")) {
                                    max_tiwen_list.add(tiWen);
                                }
                                    temperatureList.add(tiWen);
//                                }

                                tiWenDetail.setCaiJiRQ(rqTiWen);
                                tiWenDetail.setCaiJiSJ(sjTiWen.substring(0, 5));

                                temperatureDetailList.add(tiWenDetail);

                            }
                            log.e("temperatureList的大小---" + temperatureList.size());
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };


    private void getintent() {

    }

    private void initData() {
//        if(intent.hasExtra("tag")){    //生体传过来的 信息
//            intent.getIntExtra("position", 0);
//        }

        String xingBie = myApplication.getListBRLB().get(selectPos).getXINGBIE();
        if (TextUtils.equals(xingBie, "男")) {
            mIvTouXiang.setImageResource(R.drawable.icon_men);
        } else {
            mIvTouXiang.setImageResource(R.drawable.icon_women);
        }
        mTvTemperPatientName.setText(myApplication.getListBRLB().get(selectPos).getXINGMING());
        mTvChuangHao.setText(myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO() + "床");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //mTvTimeYMD.setText(year + "-" + month + "-" + day);
        selectTime = mDateFormat.format(calendar.getTime()) + "";
        mTvTimeYMD.setText(selectTime);
        //三天前时间
        calendar.set(Calendar.DAY_OF_MONTH, day - 3);
        threeDayAgoTime = mDateFormat.format(calendar.getTime()) + "";

        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(this, this, true, true, true);
        //获取数据
        initValue();
    }

    private void initValue() {

        String yhid = myApplication.getYhgh();
        String id = myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
        String bingQuDM = myApplication.getListBRLB().get(selectPos).getBINGQUDM();
        String canShu = id + NetWork.SEPARATE + bingQuDM + NetWork.SEPARATE + threeDayAgoTime + NetWork.SEPARATE
                + selectTime;

        ZhierCall call = new ZhierCall()
                .setId("1000")
                .setNumber("0600701")
                .setMessage(NetWork.SMTZ)
                .setCanshu(canShu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("请求中...")
                .build();
        log.e(canShu);
        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                StringXmlParser parser = new StringXmlParser(getTemperatureHandler, new Class[]{TiWen.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
            }
        });
    }

    private void initView() {
        mIvTouXiang = (ImageView) findViewById(R.id.iv_temperature_touxiang);
        mTvChuangHao = (TextView) findViewById(R.id.tv_chuanghao);
        mTvTimeYMD = (TextView) findViewById(R.id.tv_time_ymd);
        mTvTemperPatientName = (TextView) findViewById(R.id.tv_temperature_patient_name);
        mTvChartTemper = (TextView) findViewById(R.id.tv_chart_temperature);
        mTvChartMsgNormal = (TextView) findViewById(R.id.tv_chart_msg_normal);
        mTvChartMsgUp = (TextView) findViewById(R.id.tv_chart_msg_up);
        mIvChartMsgNormal = (ImageView) findViewById(R.id.iv_chart_msg_normal);
        mIvChartMsgUp = (ImageView) findViewById(R.id.iv_chart_msg_up);
        llHsvContent = (LinearLayout) findViewById(R.id.ll_hsv_content);


        mIvTouXiang.setOnClickListener(this);
        mTvTemperPatientName.setOnClickListener(this);
        mTvTimeYMD.setOnClickListener(this);
    }

    @Override
    public void onDateSet(Calendar calendar) {
        selectTime = mDateFormat.format(calendar.getTime()) + "";
        mTvTimeYMD.setText(selectTime);
        //三天前时间
        int day = calendar.get(Calendar.DAY_OF_MONTH) - 3;
        calendar.set(Calendar.DAY_OF_MONTH, day);
        threeDayAgoTime = mDateFormat.format(calendar.getTime()) + "";
        //重新获取值
        initValue();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int pos = data.getIntExtra("position", 0);
                Log.i("data", "------" + pos);
                selectPos = pos;
                String name = myApplication.getListBRLB().get(pos).getXINGMING();
                mTvTemperPatientName.setText(name);
                //重新获取控件值
                initValue();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
       /* IntentFilter filter = new IntentFilter();
        filter.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        this.registerReceiver(myReceiver, filter);*/
        //千岛湖广播注册
        //initSaoMiao();
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("shmaker.android.intent.action.SCANER_DECODE_DATA");
        this.registerReceiver(myReceiver2, filter2);
        //联新科技
        IntentFilter filter = new IntentFilter();
        filter.addAction("lachesis_barcode_value_notice_broadcast");
        filter.addAction("com.mobilead.tools.action.scan_result");
        filter.addAction("android.provider.sdlMessage");
        registerReceiver(myReceiver2, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //this.unregisterReceiver(myReceiver);
        this.unregisterReceiver(myReceiver2);
    }

    //千岛湖的广播接收器
    private final BroadcastReceiver myReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();
                String data = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
                if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
                    data = intent.getStringExtra("decode_rslt");
                }
                if (Build.MODEL.equals("N1")) {
                    data = intent.getStringExtra("msg");
                }
                data = data.trim().replace("��", "¤").replace("?", "¤");
                data = data.replaceAll("陇", "¤");
                String[] s = data.split("\\*");
                Toast.makeText(TemperatureChartActivity.this, data, Toast.LENGTH_SHORT).show();
                System.out.print(data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if (s1[0].equals("st72")) {
                    zxBR(s1[1]);
                } else {

                    Toast.makeText(TemperatureChartActivity.this, "条码不正确!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TemperatureChartActivity.this);
                builder.setMessage("无法得到扫描结果");
                builder.show();
            }
        }


    };

}
