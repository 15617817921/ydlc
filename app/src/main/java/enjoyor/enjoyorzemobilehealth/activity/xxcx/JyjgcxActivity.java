package enjoyor.enjoyorzemobilehealth.activity.xxcx;

/**
 * Created by Administrator on 2017/7/14.
 */

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.JyjgAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.JYJG;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class JyjgcxActivity extends BaseActivity implements ScanFactory.FragScan {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private DateUtil instance;
    private ZhierCall zhierCall;
    private LinearLayout layout;
    private ImageView back;
    private LinearLayout sjxzlayout;
    private SimpleDateFormat format;
    private TimePickerView pvTime;
    private ListView listBgd;
    private String chuanghao;
    private String xingming;
    private String xingbie;
    private String shijian;
    private String zyid;
    private List<JYJG> listJYJG = new ArrayList<>();
    private List<JYJG> listJYJG1 = new ArrayList<>();
    private ProgressDialog progressDialog;
    BRLB brlb = null;
    private TextView xm;
    private TextView ch;
    private TextView sj;
    private LinearLayout nodata;
    private ImageView tx;
    private int i;
    private int flag = 0;
    private JyjgAdapter adapter;
    private int position;
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
//                return JyjgcxActivity.this;
//            }
//        }.getInstance(4, this, 0);
//    }
//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(JyjgcxActivity.this, XxcxActivity.class));
            finish();
            return true;
        }

//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyDown(keyCode, event, JyjgcxActivity.this.getClass().getName())) {
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
                Toast.makeText(JyjgcxActivity.this, data, Toast.LENGTH_SHORT).show();
                System.out.print(data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if (s1[0].equals("st72")) {
                    zxBR(s1[1]);
                } else {

                    Toast.makeText(JyjgcxActivity.this, "条码不正确！", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JyjgcxActivity.this);
                builder.setMessage("无法得到扫描结果");
                builder.show();
            }
        }


    };


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
                Toast.makeText(JyjgcxActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            } else {
                initData();
                playSuccessBeepSound();
                Toast.makeText(JyjgcxActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(JyjgcxActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyjgcx);
        ButterKnife.bind(this);
    init();
        defineData();
        clickData();
        initData();
        initBeep();
//        sj.setText( format.format(shijian));
    }
    private void init() {
        tvTitleInclude.setText("检验结果查询");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }

    private void defineData() {
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        sj = (TextView) findViewById(R.id.sj);
//        sj.setText(format.format(System.currentTimeMillis()));

        ch = (TextView) findViewById(R.id.ch);
        nodata = (LinearLayout) findViewById((R.id.nodata));
        listBgd = (ListView) findViewById(R.id.bgd);
        layout = (LinearLayout) findViewById(R.id.top);
        sjxzlayout = (LinearLayout) findViewById((R.id.sjxz));

        brlb = MyApplication.getInstance().getOther_brlb();
    }

    private void clickData() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "5");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(JyjgcxActivity.this, BrlbActivity.class);
                intent.putExtra("which", "5");
                startActivity(intent);
                finish();
            }
        });
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JyjgcxActivity.this, XxcxActivity.class));
                finish();
            }
        });

        sjxzlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/M/d");
                //时间选择器
                pvTime = new TimePickerView.Builder(JyjgcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        shijian = format.format(date);
                        flag = 1;

                        initData();
                    }
                }).setDate(instance.getCalendar(sj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isDialog(true)
                        .build();
                pvTime.show();

            }
        });
    }

    private void initData() {

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Date d = null;
        if (flag == 1) {
            Intent intent = getIntent();
            zyid = intent.getStringExtra("BINGRENID");
        } else if (brlb != null && flag == 0) {
            Intent intent = getIntent();
            zyid = intent.getStringExtra("BINGRENID");
            shijian = intent.getStringExtra("RUYUANSJ").replace('-', '/');
            chuanghao = intent.getStringExtra("CHUANGHAO");
            xingming = intent.getStringExtra("XINGMING");
            xingbie = intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);
            try {
                d = format.parse(shijian);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            shijian = format.format(d);
        } else if (brlb == null && flag == 0) {
            MyApplication instance = MyApplication.getInstance();
            zyid = instance.getListBRLB().get(0).getBINGRENID();
            shijian = instance.getListBRLB().get(0).getRUYUANSJ().replace('-', '/');
            chuanghao = instance.getListBRLB().get(0).getCHUANGWEIHAO();
            xingming = instance.getListBRLB().get(0).getXINGMING();
            xingbie = instance.getListBRLB().get(0).getXINGBIE();
            try {
                d = format.parse(shijian);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            shijian = format.format(d);
        }
//        try {
//            sj.setText(format.format(shijian));
//        }catch (ParseException e) {
//            e.printStackTrace();
//        }

        Log.d("test121", zyid + "___" + shijian);
        String name = preferences2.getString("id", "");
        listJYJG.clear();
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0500301")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(zyid + "¤" + shijian)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JYJG.class});

                Log.v("login11", data);
                parser.parse(data);

            }

            @Override
            public void fail(String info) {
            }

        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:

                    int s = listJYJG.size();
                    log.e("list个数为：" + s);
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    if (xingbie.equals("男")) {
                        tx.setImageResource(R.drawable.icon_men);
                    } else {
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    xm.setText(xingming);
                    ch.setText(chuanghao + "床");
                    String shijian1 = shijian.replace("/", "-");
                    sj.setText(shijian);

                    try {
                        if (listJYJG.get(0).getYIZHUID().equals("")) {
                            listBgd.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        } else {
                            listBgd.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.GONE);
                            adapter = new JyjgAdapter(JyjgcxActivity.this, listJYJG);

                            listBgd.setAdapter(adapter);

                            listBgd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                    initItem(i);
                                }
                            });

                        }
                    } catch (Exception e) {

                    }

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJYJG.add((JYJG) msg.obj);
                            Log.d("test1000000", String.valueOf(listJYJG.size()));
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

    private void initItem(int i) {
        String canshu = "";
        try {
            canshu = listJYJG.get(i).getYIZHUID();
        } catch (Exception e) {

        }

        position = i;
        listJYJG1.clear();
        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0500306")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(canshu)
                .setContext(JyjgcxActivity.this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        Log.d("ssasddasdsadsad", "1.5");
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("ssasddasdsadsad", "2");
                StringXmlParser parser = new StringXmlParser(xmlHandler2,
                        new Class[]{JYJG.class});
                Log.d("10000001", data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
            }

        });
    }

    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:

                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    if (JyjgAdapter.mParentItem == position && JyjgAdapter.mbShowChild) {
                        JyjgAdapter.mbShowChild = false;
                    } else {
                        JyjgAdapter.mbShowChild = true;
                    }
                    JyjgAdapter.mParentItem = position;
                    JyjgAdapter.listItem = listJYJG1;
                    adapter.notifyDataSetChanged();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJYJG1.add((JYJG) msg.obj);
                            Log.d("test5555", String.valueOf(listJYJG1.size()));
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
