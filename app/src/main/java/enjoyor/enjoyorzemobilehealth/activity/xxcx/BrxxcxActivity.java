package enjoyor.enjoyorzemobilehealth.activity.xxcx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.views.BarGraphView;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class BrxxcxActivity extends BaseActivity implements ScanFactory.FragScan {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private BarGraphView mBarGraphView;
    float yxf;
    float yjj;
    ZhierCall zhierCall;
    ImageView tx;
    TextView xm;
    TextView ll;
    TextView dh;
    TextView ch;
    TextView hl;
    TextView ks;
    TextView zz;
    TextView gm;
    TextView bq;
    TextView xf;
    TextView yj;
    TextView ye;
    TextView yb;
    List<BRLB> listBRLB = new ArrayList<>();
    ProgressDialog progressDialog;
    BRLB brlb = null;
    ImageView back;
    LinearLayout layout;
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
//                return BrxxcxActivity.this;
//            }
//        }.getInstance(4, this, 0);
//    }
//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(BrxxcxActivity.this, XxcxActivity.class));
            finish();
            return true;
        }

//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyDown(keyCode, event, BrxxcxActivity.this.getClass().getName())) {
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
        data = data.trim().replace("��", "¤").replace("?", "¤");
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        String[] s1 = data.split("\\¤");
        Pattern pattern = Pattern.compile("^[-+]?[0-9]");

        if (s1[0].equals("st72")) {
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
                Toast.makeText(BrxxcxActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            } else {
                initData();
                playSuccessBeepSound();
                Toast.makeText(BrxxcxActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(BrxxcxActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
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
                String[] s = data.split("\\*");
                Toast.makeText(BrxxcxActivity.this, data, Toast.LENGTH_SHORT).show();
                System.out.print(data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if (s1[0].equals("st72")) {
                    zxBR(s1[1]);
                } else {

                    Toast.makeText(BrxxcxActivity.this, "条码不正确!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BrxxcxActivity.this);
                builder.setMessage("无法得到扫描结果");
                builder.show();
            }
        }


    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brxxcx);
        ButterKnife.bind(this);
        init();


        layout = (LinearLayout) findViewById(R.id.top);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        //ll= (TextView) findViewById(R.id.nl);
        dh = (TextView) findViewById(R.id.dh);
        ch = (TextView) findViewById(R.id.ch);
        hl = (TextView) findViewById(R.id.hl);
        ks = (TextView) findViewById(R.id.ks);
        zz = (TextView) findViewById(R.id.zz);
        gm = (TextView) findViewById(R.id.gm);
        bq = (TextView) findViewById(R.id.bq);
        xf = (TextView) findViewById(R.id.xf);
        yj = (TextView) findViewById(R.id.yj);
        ye = (TextView) findViewById(R.id.ye);
        yb = (TextView) findViewById(R.id.yibao);
        selectPos = MyApplication.getInstance().getChoosebr();
        brlb = MyApplication.getInstance().getOther_brlb();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "4");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(BrxxcxActivity.this, BrlbActivity.class);
                intent.putExtra("which", "4");
                startActivity(intent);
                finish();
            }
        });
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrxxcxActivity.this, XxcxActivity.class));
                finish();
            }
        });
        initData();
        initBeep();
    }

    private void init() {
        tvTitleInclude.setText("病人信息查询");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }

    private void initData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        MyApplication instance = MyApplication.getInstance();
        String zyid = instance.getListBRLB().get(selectPos).getBINGRENZYID();
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");
        String canshu = preferences2.getString("bqdm", "");

        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0500106")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(zyid)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();

        zhierCall.start(new NetWork.SocketResult() {

            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{BRLB.class});
                Log.v("login10", data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.,this info, Toast.LENGTH_LONG).show();
            }
        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    progressDialog.dismiss();
                    final BRLB brlb = listBRLB.get(0);
                    if (brlb.getXINGBIE().equals("男")) {
                        tx.setImageResource(R.drawable.icon_men);
                        tx.setMaxHeight(20);
                    } else {
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    xm.setText(brlb.getXINGMING());
                    dh.setText(brlb.getLIANXIFS());
                    ch.setText(brlb.getCHUANGWEIHAO() + "床");
                    hl.setText(brlb.getHULIDJ());
                    ks.setText(brlb.getBINGQUMC());
                    zz.setText(brlb.getZHUZHIYS());
                    gm.setText(brlb.getYINSHI());
                    bq.setText(brlb.getZHENDUAN());
                    yb.setText(brlb.getFEIYONGXZ());
//                    xf.setText(brlb.getYIXIAOFEI());
                    if (TextUtils.isEmpty(brlb.getYIXIAOFEI())) {
                        xf.setText("0");
                    } else {
                        xf.setText(brlb.getYIXIAOFEI());
                    }
                    if (TextUtils.isEmpty(brlb.getYUJIAOJIN())) {
                        yj.setText("0");
                    } else {
                        yj.setText(brlb.getYUJIAOJIN());
                    }


                    String s = brlb.getYIXIAOFEI();
                    String v = brlb.getYUJIAOJIN();
                    //以缴费，预交金
                    if (s.equals("")) {
                        yxf = 0;
                    } else {
                        yxf = Float.valueOf(brlb.getYIXIAOFEI()).floatValue();
                    }
                    if (v.equals("")) {
//                        yjj = Float.valueOf(brlb.getYUJIAOJIN()).floatValue();
                        yjj = 0;
                    } else {
                        yjj = Float.valueOf(brlb.getYUJIAOJIN()).floatValue();
                    }

                    float xfye = yjj - yxf;
                    ye.setText(String.valueOf(xfye));
                    MyApplication.getInstance().setOther_brlb(null);
                    initView();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
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

    private void initView() {
        int a, b;
        mBarGraphView = (BarGraphView) findViewById(R.id.custom_view);
        mBarGraphView.setAxisX(90, 9);
        mBarGraphView.setAxisY(70, 7);
        if (yjj > yxf) {
            a = 60;
            b = (int) (60 * yxf / yjj);
        } else {
            b = 60;
            a = (int) (60 * yjj / yxf);
        }
        int columnInfo[][] = new int[][]{{a, Color.parseColor("#FFB90F")}, {0, Color.WHITE}, {b, Color.parseColor("#EE4000")}};
        mBarGraphView.setColumnInfo(columnInfo);
    }
}
