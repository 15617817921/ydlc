package enjoyor.enjoyorzemobilehealth.activity.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
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
import enjoyor.enjoyorzemobilehealth.adapter.ChuangWeiZHAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.ChuangWeiZH;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * 病人流转
 * Created by Administrator on 2017/9/14.
 */

public class ChuangWeiLiuZhuanActivity extends BaseActivity implements ScanFactory.FragScan {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.tv_zhuyuanhao)
    TextView tvZhuyuanhao;


    private DateUtil dateUtil;

    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private static final int REQUEST_CODE = 1; // 请求码
    private int selectPos = 0;
    private TextView tv_saveDatea;
    private TextView xm;
    private TextView ch;
    private TextView cxsj1;
    private TextView cxsj2;
    private ImageView tx;
    private LinearLayout nodata;

    private AppCompatEditText xch;
    private TextView tv_zhuanhuan;
    private String zyid;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private String bingQuDM;
    private String bingQuMC;
    private String userID;
    private String userName;
    private ZhierCall zhierCall;
    private ListView list_zhjl;
    private String kaiShiSJ;
    private String jieShuSJ;
    private String chuanghao1;
    private String chuanghao2;
    private List<ChuangWeiZH> listChuangWeiZH = new ArrayList<>();
    private List<BRLB> listBRLB = new ArrayList<>();
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
    //最新PDA
    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String ACTION_TEST = "shmaker.android.intent.action.TEST";
    public static final String ACTION_SYSTEM_READY = "shmaker.android.intent.action.SYSTEM_READY";
    public static final String ACTION_SCANER_KEYEVENT_LONG = "shmaker.android.intent.action.SCANER_KEYEVENT_LONG";
    public static final String ACTION_SCANER_KEYEVENT_DOWN = "shmaker.android.intent.action.SCANER_KEYEVENT_DOWN";
    public static final String ACTION_SCANER_KEYEVENT_UP = "shmaker.android.intent.action.SCANER_KEYEVENT_UP";
    public static final String ACTION_SCANER_DECODE_DATA = "shmaker.android.intent.action.SCANER_DECODE_DATA";
    public static final String ACTION_SCANER_SERVER_ACTIVE = "shmaker.android.intent.action.SCANER_SERVER_ACTIVE";
    public static final String ACTION_SCANER_SERVER_INACTIVE = "shmaker.android.intent.action.SCANER_SERVER_INACTIVE";
    public static final String ACTION_SCANER_SCANNING = "shmaker.android.intent.action.SCANER_SCANNING";
    public static final String ACTION_SHUTDOWN = Intent.ACTION_SHUTDOWN;
    public static final String ACTION_SHUTDOWN_IPO = "android.intent.action.ACTION_SHUTDOWN_IPO";

    public static final String EXTRA_DECODE = "extra_decode";
    public static final String EXTRA_LOOP = "extra_loop";
    public static final String EXTRA_SCANNING = "extra_scanning";
    public static final String EXTRA_DECODE_TYPE = "extra_decode_type";
    public static final String EXTRA_DECODE_DATA = "extra_decode_data";
    public static final String EXTRA_DECODE_DATA_ADDITIONAL = "extra_decode_data_additional";
    public static final String EXTRA_DECODE_DATA_CLEAR = "extra_decode_data_clear";
    private String zhuyuanhao;//病人住院号

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }


    public void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_chuangweiliuzhuan);
        ButterKnife.bind(this);
        init();
        initHandler(this);
        initBeep();
        defineData();
        clickData();
        initData();
        //commitData();
    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvTitleInclude.setText("病人流转");

        dateUtil = DateUtil.getInstance();
        userID = MyApplication.getInstance().getYhgh();
        userName = MyApplication.getInstance().getYhxm();
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

    private void defineData() {

        nodata = (LinearLayout) findViewById(R.id.nodata);
        list_zhjl = (ListView) findViewById(R.id.list_zhjl);
//        tv_zhuanhuan = (TextView) findViewById(R.id.tv_zhuanhuan);
        cxsj1 = (TextView) findViewById(R.id.cxsj1);
        cxsj2 = (TextView) findViewById(R.id.cxsj2);
        xch = (AppCompatEditText) findViewById(R.id.xch);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);

    }

    private void clickData() {
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChuangWeiLiuZhuanActivity.this, HomePageActivity.class));
                finish();
            }
        });
//        xm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(ChuangWeiLiuZhuanActivity.this,BrlbActivity.class);
//                intent.putExtra("which","CHuangWeiZhuanHuan");
//                startActivityForResult(intent,REQUEST_CODE);
//            }
//        });
        cxsj1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/MM/dd");
                //时间选择器
                pvTime = new TimePickerView.Builder(ChuangWeiLiuZhuanActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        kaiShiSJ = format.format(date);
                        cxsj1.setText(kaiShiSJ);
                        commitData();
                    }
                }).setDate(dateUtil.getCalendar(cxsj1.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });
        cxsj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                //时间选择器
                pvTime = new TimePickerView.Builder(ChuangWeiLiuZhuanActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) { //选中事件回调
                        jieShuSJ = format.format(date);
                        cxsj2.setText(jieShuSJ);
//                        commitData();
                    }
                }).setDate(dateUtil.getCalendarSfm(cxsj2.getText().toString()))
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.ALL)//默认全部显示
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });
//        tv_zhuanhuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chuanghao2 = xch.getText().toString().trim();
//                if(chuanghao2.equals("")){
//                    Toast.makeText(ChuangWeiLiuZhuanActivity.this,"请输入床位号", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                Log.d("fafdfads",chuanghao2);
//                MyApplication instance = MyApplication.getInstance();
//                for(int i=0;i<instance.getListBRLB().size();i++){
//                    if(instance.getListBRLB().get(i).getBINGRENZYID().equals(zyid)){
//                        chuanghao1=instance.getListBRLB().get(i).getCHUANGWEIHAO();
//                    }
//                }
//                zhData();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                selectPos = data.getIntExtra("position", 0);
                xch.setText("");
                initData();
            }
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
//                return ChuangWeiLiuZhuanActivity.this;
//            }
//        }.getInstance(4, this, 0);
//    }
//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(ChuangWeiLiuZhuanActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyDown(keyCode, event, ChuangWeiLiuZhuanActivity.this.getClass().getName())) {
//
//                return true;
//
//            }
//        }

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
        Log.i("data", "M80扫码返回" + data);
        data = data.trim().replace("��", "¤").replace("?", "¤");
        String[] s = data.split("\\¤");
        if (s[0].equals("st72")) {
            //核对病人列表并更新数据
            checkBRLBAndUpdate(s[1]);
        } else if (s.length == 1) {
            Toast.makeText(ChuangWeiLiuZhuanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
        }
//            //核对病人列表并更新数据
//            checkBRLBAndUpdate(zyid);
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  //注册广播
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        this.registerReceiver(myReceiver, filter1);
        //湛江注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.jaway.action.DECODE_RESULT");
        this.registerReceiver(myReceiver, filter);*/
        // 注册接收条码数据的receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction("shmaker.android.intent.action.SCANER_DECODE_DATA");
        registerReceiver(myReceiver, filter);
        //
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("lachesis_barcode_value_notice_broadcast");
        filter2.addAction("com.mobilead.tools.action.scan_result");
        filter2.addAction("android.provider.sdlMessage");
        registerReceiver(myReceiver, filter2);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(myReceiver);
    }

    //廉江扫描的广播接收器
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //代码处理

            String action = intent.getAction();
            String data = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
            playScanSuccessBeepSound();
            if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
                data = intent.getStringExtra("decode_rslt");
            }
            if (Build.MODEL.equals("N1")) {
                data = intent.getStringExtra("msg");
            }
            Log.i("data", "M80扫码返回" + data);
            data = data.trim().replace("��", "¤").replace("?", "¤");
            data = data.replaceAll("陇", "¤");
            String[] s = data.split("\\¤");
            if (s[0].equals("st72")) {
                //核对病人列表并更新数据
                checkBRLBAndUpdate(s[1]);
            } else if (s.length == 1) {
                Toast.makeText(ChuangWeiLiuZhuanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
            }

        }
    };

    /**
     * 核对病人列表并更新数据
     *
     * @param brzyid
     */
    private void checkBRLBAndUpdate(String brzyid) {
        List<BRLB> listBRLB = new ArrayList<>();
        listBRLB = MyApplication.getInstance().getListBRLB();

        //病人列表是否包含这个扫出来的ID
        boolean containsZYID = false;
        if (false)//TextUtils.equals(xch.getText().toString(),""))
        {
            Toast.makeText(ChuangWeiLiuZhuanActivity.this, "请输入床位号!", Toast.LENGTH_SHORT).show();
        } else {
            for (int j = 0; j < listBRLB.size(); j++) {
                if (brzyid.equals(listBRLB.get(j).getBINGRENZYID())) {
                    selectPos = j;
                    zyid = brzyid;
                    chuanghao1 = listBRLB.get(j).getCHUANGWEIHAO();
                    zhuyuanhao = listBRLB.get(j).getBINGRENID();
                    String zhuyuanhao = listBRLB.get(j).getBINGANHAO();
                    containsZYID = true;
                    break;
                }
            }
            log.e(containsZYID);
            if (containsZYID) {
                //xm.setText(bingRenXM);
                //上面页面刷新后，提交数据
//                chuanghao2 = xch.getText().toString().trim();
//                if(TextUtils.equals(xch.getText().toString(),"")){
//                    Toast.makeText(ChuangWeiLiuZhuanActivity.this, "请输入床位号!", Toast.LENGTH_SHORT).show();
//                }else {
//                    zhData();
//                }
                zhData();
            } else {
                getBingRenXX(brzyid);
                //playFailBeepSound();
                //Toast.makeText(ChuangWeiLiuZhuanActivity.this, "匹配不成功!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getBingRenXX(String brzyid) {
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0500106")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(brzyid)
                .setContext(ChuangWeiLiuZhuanActivity.this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(brzyid);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler1,
                        new Class[]{BRLB.class});
                log.e("床位流转" + data);
                parser.parse(data);

            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(ChuangWeiLiuZhuanActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initData() {
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        bingQuDM = preferences2.getString("bqdm", "");
        bingQuMC = preferences2.getString("bqmc", "");
        // userID=preferences2.getString("id","");
        MyApplication instance = MyApplication.getInstance();
        BRLB brlb = instance.getListBRLB().get(selectPos);

        settopmsg(brlb);


//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Calendar c = Calendar.getInstance();
//        jieShuSJ = formatter.format(c.getTime());
//
//        c.add(Calendar.DATE, -7);
//        kaiShiSJ = formatter.format(c.getTime());

        String curdate = dateUtil.getDate();
//        cxsj1.setText(kaiShiSJ);
        cxsj2.setText(curdate);

        commitData();

    }

    /*病人top信息
     * 设置*/
    private void settopmsg(BRLB brlb) {
        zyid = brlb.getBINGRENZYID();
        bingRenXM = brlb.getXINGMING();
        bingRenXB = brlb.getXINGBIE();
        chuangHao = brlb.getCHUANGWEIHAO();
        zhuyuanhao = brlb.getBINGRENID();
        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        xm.setText(bingRenXM);
        tvZhuyuanhao.setText("住院号：" + zhuyuanhao);

        ch.setText(chuangHao + "床");
    }

    private void commitData() {
        String sj1 = kaiShiSJ + " 0:00:00";
        String sj2 = jieShuSJ + " 23:59:59";
        String canshu = zyid + "¤" + bingQuDM + "¤" + sj1 + "¤" + sj2;
        listChuangWeiZH.clear();
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0306601")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu)
                .setContext(ChuangWeiLiuZhuanActivity.this)
                .setPort(5000)
                .setDialogmes("查询记录中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{ChuangWeiZH.class});
                log.e("床位流转" + data);
                parser.parse(data);
            }
            @Override
            public void fail(String info) {
                log.e("String" + info);
            }

        });
    }

    private void zhData() {
//        String date = dateUtil.getDate();
        String date = cxsj2.getText().toString().trim();
        String canshu = date + "¤" + userName + "¤" + userID + "¤" + bingRenXM + "¤" + chuanghao1 + "¤" + chuanghao2 + "¤" + bingQuDM + "¤" + bingQuMC + "¤" + zyid+  "¤"+zhuyuanhao ;
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0306602")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu)
                .setContext(ChuangWeiLiuZhuanActivity.this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                Toast.makeText(ChuangWeiLiuZhuanActivity.this, "转换成功", Toast.LENGTH_LONG).show();
                initData();
            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(ChuangWeiLiuZhuanActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listChuangWeiZH.size() == 0) {
                        list_zhjl.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    } else {
                        list_zhjl.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        ChuangWeiZHAdapter adapter = new ChuangWeiZHAdapter(ChuangWeiLiuZhuanActivity.this, listChuangWeiZH);
                        list_zhjl.setAdapter(adapter);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            ChuangWeiZH bean = (ChuangWeiZH) msg.obj;
                            if (!TextUtils.isEmpty(bean.getID())) {
                                listChuangWeiZH.add((ChuangWeiZH) msg.obj);
                            }

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


    private void initData1(List<BRLB> br) {
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        bingQuDM = preferences2.getString("bqdm", "");
        bingQuMC = preferences2.getString("bqmc", "");

        settopmsg(br.get(0));

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        Calendar c = Calendar.getInstance();
//        jieShuSJ = formatter.format(c.getTime());
//        c.add(Calendar.DATE, -7);
//        kaiShiSJ = formatter.format(c.getTime());
//        cxsj1.setText(kaiShiSJ);

        zhData1(br.get(0).getBINGRENID());
        //commitData();

    }

    private void zhData1(String bingrenzyid) {
        String date = cxsj2.getText().toString().trim();
        String canshu = date + "¤" + userName + "¤" + userID + "¤" + bingRenXM + "¤" + chuanghao1 + "¤" + chuanghao2 + "¤" + bingQuDM + "¤" + bingQuMC + "¤" + zyid + "¤" + bingrenzyid;
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0306602")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu)
                .setContext(ChuangWeiLiuZhuanActivity.this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                Toast.makeText(ChuangWeiLiuZhuanActivity.this, "记录成功", Toast.LENGTH_LONG).show();
                commitData();
            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(ChuangWeiLiuZhuanActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    MyXmlHandler xmlHandler1 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listBRLB.size() == 0) {

                    } else {
                        initData1(listBRLB);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            BRLB br = (BRLB) msg.obj;
                            listBRLB.add((BRLB) msg.obj);
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
