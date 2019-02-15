package enjoyor.enjoyorzemobilehealth.activity.home;

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
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.ScreenListener;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import my_network.NetWork;
import my_network.ZhierCall;
import qdh.jyhd.HeDuiAdapter;
import qdh.jyhd.HeDuiJLActivity;
import qdh.jyhd.JianYanJG;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by dantevsyou on 2017/11/15.
 */
//lang.RuntimeException: Unable to start activity ComponentInfo{enjoyor.enjoyorzemobilehealth/enjoyor.enjoyorzemobilehealth.activity.home.HeDuiActivity}: java.lang.NullPointerException
public class HeDuiActivity extends BaseActivity implements ScanFactory.FragScan {
    @BindView(R.id.tx)
    ImageView tx;
    @BindView(R.id.mz)
    TextView mz;
    @BindView(R.id.ch)
    TextView ch;
    @BindView(R.id.ll_check_top)
    LinearLayout llCheckTop;
    @BindView(R.id.ll_check_nodata)
    LinearLayout llCheckNodata;
    @BindView(R.id.lv_check)
    ListView lvCheck;
    @BindView(R.id.activity_hedui)
    LinearLayout activityHedui;
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private ZhierCall zhierCall;

    private List<JianYanJG> listJianYanJG = new ArrayList<>();
    private static final int REQUEST_CODE = 1; // 请求码
    private String userName;
    private String userID;
    //    private String bingQuDM;
    private String zyid;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;

    private int selectPos = 0;
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
    private HeDuiAdapter adapter;
    private MyApplication app;
    private List<BRLB> listBRLB = new ArrayList<>();//缓存的病人列表
    private DateUtil dateUtil;
    private ScreenListener screenListener;


    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();
            mDoDecodeHandler = new Handler();
            Looper.loop();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hedui);
        ButterKnife.bind(this);
        init();
        initHandler(this);
        initBeep();
        initShow(selectPos);
        initData();
        screenListene();//
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenListener.unregisterListener();
    }

    private void screenListene() {
        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onUserPresent() {
                log.e(" // 解锁  onUserPresent");
            }

            @Override
            public void onScreenOn() {
                log.e(" //  开屏  onScreenOn");
            }

            @Override
            public void onScreenOff() {
                log.e(" //  // 锁屏  onScreenOff");

            }
        });
    }


    private void init() {
        dateUtil = DateUtil.getInstance();
        app = MyApplication.getInstance();

        selectPos = app.getChoosebr();//病人列表的位数
        listBRLB = app.getListBRLB(); //病人列表
        userID = app.getYhgh();
        userName = app.getYhxm();

        tvTitleInclude.setText("检验核对");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
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


    private void initShow(int pos) {
        BRLB brlb = listBRLB.get(pos);//得到确定位置的病人信息

        zyid = brlb.getBINGRENZYID();
        bingRenXM = brlb.getXINGMING();
        bingRenXB = brlb.getXINGBIE();
        chuangHao = brlb.getCHUANGWEIHAO();

        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        mz.setText(bingRenXM);
        ch.setText(chuangHao + "床");
    }


    @OnClick({R.id.iv_back_include, R.id.tv_right, R.id.ll_check_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_include:
                startActivity(new Intent(HeDuiActivity.this, HomePageActivity.class));
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(HeDuiActivity.this, HeDuiJLActivity.class));
                finish();
                break;
            case R.id.ll_check_top:
                Intent intent = new Intent(HeDuiActivity.this, BrlbActivity.class);
                intent.putExtra("which", "HeDui");
                startActivityForResult(intent, REQUEST_CODE);
                break;
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
            // initSaoMiao();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(HeDuiActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        log.e("扫码内容-" + data);
        if (data.contains("¤")) {
            String[] s1 = data.split("\\¤");
            if (s1[0].equals("st72")) {
                log.e("st72");
                updateBrlb(s1[1]);
            } else {
                if (true) {
                    log.e("elsest72");
                    updateBrlb(data);
                } else {
                    Toast.makeText(HeDuiActivity.this, "条码不正确：" + data, Toast.LENGTH_LONG).show();
                }
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        this.registerReceiver(myReceiver, filter1);
        //湛江注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.jaway.action.DECODE_RESULT");
        this.registerReceiver(myReceiver, filter);
        //千岛湖广播注册
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("shmaker.android.intent.action.SCANER_DECODE_DATA");
        this.registerReceiver(myReceiver, filter2);
        //
        IntentFilter filter4 = new IntentFilter();
        filter4.addAction("lachesis_barcode_value_notice_broadcast");
        filter4.addAction("com.mobilead.tools.action.scan_result");
        filter4.addAction("android.provider.sdlMessage");
        this.registerReceiver(myReceiver, filter4);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(myReceiver);
    }

    //廉江扫描的广播接收器   m80扫码
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            log.e("时间开始");
            //代码处理
            String text = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
            //Toast.makeText(BryzLianjianActivity.this,text,Toast.LENGTH_LONG).show();
            String data = text;
            if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
                log.e("m80");
                data = intent.getStringExtra("decode_rslt");
            }
            if (Build.MODEL.equals("N1")) {
                log.e("MODEL");
                data = intent.getStringExtra("msg");
            }
            playScanSuccessBeepSound();
            data = data.trim().replace("��", "¤").replace("?", "¤");
            data = data.replaceAll("陇", "¤");
            log.e(data);
//            Log.i("data", "M80扫码返回" + data);
            String[] s = data.split("\\*");
            String[] s1 = data.split("\\¤");
            if (s1[0].equals("st72")) {
                updateBrlb(s1[1]);
            } else {
                if (true) {
                    ypZX(data);
                } else {
                    Toast.makeText(HeDuiActivity.this, "条码不正确：" + s.length, Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    /*
    扫腕带切换病人
  */
    private void updateBrlb(String brid) {
        try {
            int i = 0;
            for (int j = 0; j < listBRLB.size(); j++) {
                BRLB brlb = listBRLB.get(j);
                if (brid.equals(brlb.getBINGRENZYID())) {
                    selectPos = j;
                    initShow(selectPos);
                    i = 1;
                    break;
                }
            }
            if (i == 0) {
                Toast.makeText(HeDuiActivity.this, "匹配不成功,刷新病人列表重试", Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            } else {
                initData();
                playSuccessBeepSound();
                Toast.makeText(HeDuiActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(HeDuiActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void ypZX(String tm) {
        try {
            String time = dateUtil.getDate();
            String _str1 = (Integer.parseInt(tm) - 1000000000) + "";
            tiaoMa = _str1;
            app.setHedui_tiaoMa(tiaoMa);//adapter中获取
            log.e(time + userName + "--条码--" + tiaoMa);
            int flag = 0;
            JianYanJG jianYanJG = null;
            for (int i = 0; i < listJianYanJG.size(); i++) {
                jianYanJG = listJianYanJG.get(i);
                if (jianYanJG.getTiaoMaID().equals(tiaoMa)) {
                    jianYanJG.setBEIZHU("1");
                    jianYanJG.setZXSJ(time);
                    jianYanJG.setZXGH(userName);
                    flag = 1;
                }
            }
            if (flag == 0) {
                playFailBeepSound();
                Toast.makeText(HeDuiActivity.this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
            } else {
                checkData();
            }
        } catch (Exception ex) {
            log.e(ex.getMessage() + "--" + ex.toString());
            Toast.makeText(HeDuiActivity.this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                selectPos = data.getIntExtra("position", 0);
                initShow(selectPos);
                initData();
            }
        }
    }

    private void initData() {
        listJianYanJG.clear();

        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401503")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(zyid)
                .setContext(HeDuiActivity.this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JianYanJG.class});
                log.e(data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e("fail--" + info);
            }
        });
    }

    private void checkData() {
        String canshu = zyid + "¤" + tiaoMa + "¤" + userName + "¤" + userID;
        log.e(canshu);
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401506")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("核对中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                ToastUtil.showLong("核对成功");
//                Toast.makeText(HeDuiActivity.this, "核对成功", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                log.e("时间结束");
//                initData();
            }

            @Override
            public void fail(String info) {
                log.e(info);
                playFailBeepSound();
                Toast.makeText(HeDuiActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    try {
                        if (listJianYanJG.size() == 0 || listJianYanJG == null) {
                            lvCheck.setVisibility(View.GONE);
                            llCheckNodata.setVisibility(View.VISIBLE);
                        } else {
                            lvCheck.setVisibility(View.VISIBLE);
                            llCheckNodata.setVisibility(View.GONE);
                            listJianYanJG = dateUtil.jyhduiOrderList(false, listJianYanJG);
                            adapter = new HeDuiAdapter(HeDuiActivity.this, listJianYanJG, zyid, tiaoMa, userName, userID);
                            lvCheck.setAdapter(adapter);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(HeDuiActivity.this, "重新扫描执行！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            JianYanJG bean = (JianYanJG) msg.obj;
                            log.e(bean.getTiaoMaID()+"--"+bean.getJianYanSJ());
                            if (!TextUtils.isEmpty(bean.getBingRenZYID())) {
                                listJianYanJG.add((JianYanJG) msg.obj);
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
}
