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
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import my_network.NetWork;
import my_network.ZhierCall;
import qdh.jyhd.JianYanJG;
import qdh.wfsx.WFSXHeDuiAdapter;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * 输血
 * Created by dantevsyou on 2017/11/15.
 */

public class SXHeDuiActivity extends BaseActivity implements ScanFactory.FragScan {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;

    @BindView(R.id.iv_sxhedui_image)
    ImageView ivSxheduiImage;
    @BindView(R.id.tv_sxhedui_name)
    TextView tvSxheduiName;
    @BindView(R.id.tv_sxhedui_chuang)
    TextView tvSxheduiChuang;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.ll_sx)
    ListView llSx;
    private ZhierCall zhierCall;

    private List<JianYanJG> listJianYanJG = new ArrayList<>();
    private static final int REQUEST_CODE = 1; // 请求码
    private String userName;
    private String userID;
    private String bingQuDM;
    private String zyid;

    private int selectPos = 0;
    private String xuedaima = "";//血袋码
    private String qubiema = "";//区别码
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
    private String ztbz = "";
    ScanInterface scanInf;
    private MyApplication app;
    private List<BRLB> listBRLB;
    private BRLB brlb = null;

    @OnClick({R.id.iv_back_include, R.id.ll_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_include:
                startActivity(new Intent(SXHeDuiActivity.this, HomePageActivity.class));
                finish();
                break;
            case R.id.ll_top:
                Intent intent = new Intent(SXHeDuiActivity.this, BrlbActivity.class);
                intent.putExtra("which", "HeDui");
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wf_sx);
        ButterKnife.bind(this);
        init();
        initBeep();

        initHandler(this);
    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvTitleInclude.setText("输血核对");
        app = MyApplication.getInstance();
        listBRLB = app.getListBRLB();
        userID = app.getYhgh();
        userName = app.getYhxm();
        bingQuDM = app.getBqdm();
        selectPos = app.getChoosebr();
        initData(listBRLB.get(selectPos));
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(SXHeDuiActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        data = data.trim().replace("��", "¤").replace("?", "¤").replace("陇", "¤");
        String[] s = data.split("\\*");
        Toast.makeText(SXHeDuiActivity.this, data, Toast.LENGTH_SHORT).show();
        System.out.print(data);
        String[] s1 = data.split("\\¤");
        if (s1[0].equals("st72")) {
            zxBR(s1[1]);
        } else {
            if (true) {
                ypZX(data);
            } else {
                Toast.makeText(SXHeDuiActivity.this, "条码不正确：" + s.length, Toast.LENGTH_LONG).show();
            }
        }
//            //核对病人列表并更新数据
//            checkBRLBAndUpdate(zyid);
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

    //廉江扫描的广播接收器
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //代码处理
            String text = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
            //Toast.makeText(BryzLianjianActivity.this,text,Toast.LENGTH_LONG).show();
            String data = text;
            if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
                data = intent.getStringExtra("decode_rslt");
            }
            if (Build.MODEL.equals("N1")) {
                data = intent.getStringExtra("msg");
            }
            playScanSuccessBeepSound();
            data = data.trim().replace("��", "¤").replace("?", "¤");
            data = data.replaceAll("陇", "¤");
            String[] s = data.split("\\*");
            String[] s1 = data.split("\\¤");
            Pattern pattern = Pattern.compile("^[-+]?[0-9]");

            if (s1[0].equals("st72")) {
                zxBR(s1[1]);
            } else {
                if (true) {
                    ypZX(data);
                } else {
                    Toast.makeText(SXHeDuiActivity.this, "条码不正确：" + s.length, Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    //去病人列表中核实是否有这条id，如果有去切换医嘱
    private void zxBR(String brid) {
        try {
            int i = 0;
            BRLB brlb = new BRLB();
            for (int j = 0; j < listBRLB.size(); j++) {
                if (brid.equals(listBRLB.get(j).getBINGRENZYID())) {
                    selectPos = j;
                    brlb = listBRLB.get(j);
                    i = 1;
                    break;
                }
            }

            if (i == 0) {
                Toast.makeText(SXHeDuiActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            } else {
                initData(brlb);
                playSuccessBeepSound();
                Toast.makeText(SXHeDuiActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(SXHeDuiActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void ypZX(String tm) {
        try {
            String _str1 = (Integer.parseInt(tm) - 1000000000) + "";
//            tiaoMa = _str1;
            int flag = 0;
            for (int i = 0; i < listJianYanJG.size(); i++) {
//                if (listJianYanJG.get(i).getTiaoMaID().equals(tiaoMa)) {
                if (listJianYanJG.get(i).getTiaoMaID().equals("")) {
                    flag = 1;
                    ztbz = listJianYanJG.get(i).getBeiZhu();
                }
            }
            if (flag == 0) {
                playFailBeepSound();
                Toast.makeText(SXHeDuiActivity.this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
            } else {
                if (ztbz.equals("3")) {
                    playFailBeepSound();
                    Toast.makeText(SXHeDuiActivity.this, "已经输血结束！", Toast.LENGTH_LONG).show();
                    return;
                }
                checkData();
            }
        } catch (Exception ex) {
            Toast.makeText(SXHeDuiActivity.this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                selectPos = data.getIntExtra("position", 0);
                initData(brlb);
            }
        }
    }

    private void initData(BRLB brlb) {
        zyid = brlb.getBINGRENZYID();
        String bingRenXM = brlb.getXINGMING();
        String bingRenXB = brlb.getXINGBIE();
        String chuangHao = brlb.getCHUANGWEIHAO();
        if (bingRenXB.equals("男")) {
            ivSxheduiImage.setImageResource(R.drawable.icon_men);
        } else {
            ivSxheduiImage.setImageResource(R.drawable.icon_women);
        }
        tvSxheduiName.setText(bingRenXM);
        tvSxheduiChuang.setText(chuangHao + "床");

        listJianYanJG.clear();
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401502")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(zyid)//住院号 +血袋码 + 区别码
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(zyid);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler, new Class[]{JianYanJG.class});
                log.e(data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e("fail--" + info);
                Toast.makeText(SXHeDuiActivity.this, info, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void checkData() {
//        String canshu = zyid + "¤" + tiaoMa + "¤" + userName + "¤" + userID + "¤" + ztbz;
        String canshu = zyid + "¤" + "" + "¤" + userName + "¤" + userID + "¤" + ztbz;
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401505")//执行方法
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                Toast.makeText(SXHeDuiActivity.this, "核对成功", Toast.LENGTH_LONG).show();
                initData(brlb);

            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(SXHeDuiActivity.this, info, Toast.LENGTH_LONG).show();
            }

        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listJianYanJG.size() == 0) {
                        llSx.setVisibility(View.GONE);
                        llNodata.setVisibility(View.VISIBLE);
                    } else {
                        llSx.setVisibility(View.VISIBLE);
                        llNodata.setVisibility(View.GONE);
                        WFSXHeDuiAdapter adapter = new WFSXHeDuiAdapter(SXHeDuiActivity.this, listJianYanJG);
                        llSx.setAdapter(adapter);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            JianYanJG bean = (JianYanJG) msg.obj;
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
