package enjoyor.enjoyorzemobilehealth.activity.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.imscs.barcodemanager.BarcodeManager;
import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.BqxhcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.XHuiAdapter;
import enjoyor.enjoyorzemobilehealth.adapter.XunHuiJLAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiCZ;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;
import enjoyor.enjoyorzemobilehealth.entities.ZhiXingJL;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import enjoyor.enjoyorzemobilehealth.utlis.YiHuUtlis;
import enjoyor.enjoyorzemobilehealth.views.MyInterface;
import enjoyor.enjoyorzemobilehealth.views.XunHuiAddDialog;
import enjoyor.enjoyorzemobilehealth.views.XunHuiCZDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/7/17.
 * //巡回查询
 */

public class XhcxActivity extends BaseActivity implements ScanFactory.FragScan {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_xhjl_beizhu)
    TextView tvXhjlBeizhu;
    @BindView(R.id.tv_xhjl_num)
    TextView tvXhjlNum;

    private TimePickerView pvTime;
    private ZhierCall zhierCall;
    //    private LinearLayout ll_choose_xhlx;
    private TextView xhlx;
    private ListView lv_xhjl;
    private LinearLayout top;
    private List<XunHuiCZ> listXunHui = new ArrayList<>();
    private List<XunHuiJL> listXunHuiJL = new ArrayList<>();
    private XunHuiCZDialog dialog;
    private XunHuiAddDialog dialog1;
    private ImageView xhjl_add;


    private TextView tv_xhcx_start;
    private TextView tv_xhcx_end;
    private LinearLayout sjxz;

    private TextView xm;
    private TextView ch;
    private ImageView iv_image;
    private LinearLayout nodata;

    private String startTime = "";//无时分秒
    private String endTime = "";
    private String zyid;
    private String bingRenXM;

    //    private String bingRenXB;
//    private String chuangHao;
//    private String BingQuDM;
//    private int flag = 0;
    //    private BRLB brlb = null;
    private MyInterface myListener = new MyInterface() {
        @Override
        public void method(String text) {
            tvXhjlBeizhu.setText(text);
            commitToServer(text);
        }

        @Override
        public void updateData(String zy) {
//            zyid = zy;
//            tvXhjlBeizhu

//            int i;
//            for (i = 0; i < listBRLB.size(); i++) {
//                if (zyid.equals(listBRLB.get(i).getBINGRENZYID())) {
//                    bingRenXM = listBRLB.get(i).getXINGMING();
//                    bingRenXB = listBRLB.get(i).getXINGBIE();
//                    chuangHao = listBRLB.get(i).getCHUANGWEIHAO();
//                    break;
//                }
//            }
//            Date currentTime = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyy/MM/dd");
//            startTime = formatter.format(currentTime);
            commitData();
        }
    };


    //扫码相关部分
    private final int ID_SCANSETTING = 0x12;
    private final int ID_CLEAR_SCREEN = 0x13;
    private final int ID_SCANTOUCH = 0x14;

    private BarcodeManager mBarcodeManager = null;
    private final int SCANKEY_LEFT = 301;
    private final int SCANKEY_RIGHT = 300;
    private final int SCANKEY_CENTER = 302;
    private final int SCANTIMEOUT = 3000;
    long mScanAccount = 0;
    private boolean mbKeyDown = true;
    private boolean scanTouch_flag = true;

    ScanInterface scanInf;
    private Handler mDoDecodeHandler;
    private DateUtil dateUtil;
    private SimpleDateFormat format;
    private MyApplication app;

    private List<BRLB> listBRLB = null;//全局病人列表
    private String yhgh = "";//全局用户工号
    private String bqdm = "";//病区代码
    private BRLB curBrlb;


    @OnClick({R.id.iv_back_include, R.id.tv_right, R.id.tv_xhjl_beizhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_include:
//                startActivity(new Intent(XhcxActivity.this, HomePageActivity.class));
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(XhcxActivity.this, BqxhcxActivity.class));
                break;
            case R.id.tv_xhjl_beizhu:
                chaxunmsg();
                break;
        }
    }

    /*
     * 查询备注信息
     * */
    private void chaxunmsg() {
        listXunHui.clear();
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("03043003")
                .setMessage(NetWork.GongYong)
                .setCanshu("1")
                .setContext(XhcxActivity.this)
                .setPort(5000)
                .setDialogmes("查询中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{XunHuiCZ.class});
                log.e(data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e(info);
            }
        });
    }

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();
            mDoDecodeHandler = new Handler();
            Looper.loop();
        }
    }

    private DoDecodeThread mDoDecodeThread;

//    private int selectPosition=0;
//    private boolean isScan=false;

    //扫描提示音
    private boolean playBeep = true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;

    public void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_xhcx);
        ButterKnife.bind(this);
        init();
        initHandler(this);
        initBeep();
        defineData();

        clickData();
//        initData();
//        commitData();

        //扫码相关
        mDoDecodeThread = new DoDecodeThread();
        mDoDecodeThread.start();
    }

    private void showTouxiang() {
        zyid = curBrlb.getBINGRENZYID();
        bingRenXM = curBrlb.getXINGMING();
        if (curBrlb.getXINGBIE().equals("男")) {
            iv_image.setImageResource(R.drawable.icon_men);
        } else {
            iv_image.setImageResource(R.drawable.icon_women);
        }
        xm.setText(curBrlb.getXINGMING());
        ch.setText(curBrlb.getCHUANGWEIHAO() + "床");
    }

    private void commitToServer(String hulimsg) {
//        String[] split = dengji.split("×");
        String canShu = bqdm + "¤" + zyid + "¤" + bingRenXM + "¤" + yhgh
                + "¤" + hulimsg + "¤" + "" + "¤" + app.getYhxm() + "¤" + Constant.ZERO;
        ;
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("03043006")
                .setMessage(NetWork.GongYong)
                .setCanshu(canShu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("请求中...")
                .build();
        log.e(canShu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                Toast.makeText(XhcxActivity.this, "巡视成功" + data, Toast.LENGTH_SHORT).show();
                //刷新页面
                commitData();
            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(XhcxActivity.this, "巡视失败" + info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initNet(String time1, String time2) {
        showTouxiang();
        String startTime = time1.trim() + " 00:00:00";
        String endTime = time2.trim() + " 23:59:59";
        String canshu = bqdm + Constant.FUHAO + startTime + Constant.FUHAO + endTime + Constant.FUHAO + "4" + Constant.FUHAO + curBrlb.getBINGRENZYID();
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("0401520")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e("护理查询单个病人--" + data);
                list_hljb_yizhu.clear();
                StringXmlParser parser = new StringXmlParser(hljbHandler,
                        new Class[]{Yizhu.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Yizhu> list_hljb_yizhu = new ArrayList();//查询到的护理级别
    MyXmlHandler hljbHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    Yizhu yizhu = list_hljb_yizhu.get(0);
                    if (yizhu.getBingRenZYID().equals("")) {
                        xhlx.setText("暂无护理等级");
                    } else {
                        String beiZhu = yizhu.getBeiZhu();
                        String yiZhuMC = yizhu.getYiZhuMC();
                        xhlx.setText(yiZhuMC);

//                        if (beiZhu.equals("")) {
//                            xhlx.setText(yiZhuMC);
//                        } else {
//                            xhlx.setText(yiZhuMC + Constant.CHENGYI + beiZhu);
//                        }
                        if (isSaoma) {
                            String dengji = xhlx.getText().toString().trim();
                            commitToServer(dengji);
//                            xunShi(yizhu);
                        } else {
                            commitData();
//                            zhiXjilu(yizhu.getTiaoMaID());
                        }
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            list_hljb_yizhu.add((Yizhu) msg.obj);
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

    private void zhiXjilu(String tiaoMaID) {
        list_zxjl.clear();
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("0401101")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(tiaoMaID)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e("" + data);
                StringXmlParser parser = new StringXmlParser(handler_zxjl,
                        new Class[]{ZhiXingJL.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e("" + info);
            }
        });
    }

    private List<ZhiXingJL> list_zxjl = new ArrayList<>();

    MyXmlHandler handler_zxjl = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (list_zxjl.get(0).getYiZhuZXMXID().equals("")) {
                        nodata.setVisibility(View.VISIBLE);
                        lv_xhjl.setVisibility(View.GONE);
                    } else {
                        nodata.setVisibility(View.GONE);
                        lv_xhjl.setVisibility(View.VISIBLE);
                        lv_xhjl.setAdapter(new XHuiAdapter(XhcxActivity.this, dateUtil.invertOrderList(false, list_zxjl)));
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            ZhiXingJL bean = (ZhiXingJL) msg.obj;
                            list_zxjl.add(bean);
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    };


    private void init() {
        app = MyApplication.getInstance();
        yhgh = app.getYhgh();
        listBRLB = app.getListBRLB();
        bqdm = app.getBqdm();
        curBrlb = listBRLB.get(app.getChoosebr());
        format = new SimpleDateFormat("yyy/MM/dd");

        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        dateUtil = DateUtil.getInstance();

        tvTitleInclude.setText("巡回查询");
        tvRight.setText("记录");
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
//            Toast.makeText(BryzBbenActivity.this,"不能播放",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 播放beep声
     */
    private void playSuccessBeepSound() {
//		//注册默认音频通道
//		getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
//		audioManager=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        //检查铃音模式
        if (successAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
//		mediaPlayer=new MediaPlayer();
//		//指定播放的声音通道
//		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//		//当播放完毕一次后，重新指向流文件的开头，以准备下次播放
//		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

//			@Override
//			public void onCompletion(MediaPlayer player) {
//				// TODO Auto-generated method stub
//				player.seekTo(0);
//			}
//		});
//		//设定数据源，并准备播放
//		AssetFileDescriptor file=getResources().openRawResourceFd(R.raw.beep);
//		try {
//			mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
//			file.close();
//			//设置音量大小
//			mediaPlayer.setVolume(0.5f, 0.5f);
//			mediaPlayer.prepare();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
//        ll_choose_xhlx = (LinearLayout) findViewById(R.id.ll_choose_xhlx);
        xhlx = (TextView) findViewById(R.id.xhlx);
        //xhjl_add = (ImageView) findViewById(R.id.xhjl_add);
        lv_xhjl = (ListView) findViewById(R.id.lv_xhjl);

        top = (LinearLayout) findViewById(R.id.top);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);
        iv_image = (ImageView) findViewById(R.id.tx);
        tv_xhcx_start = (TextView) findViewById(R.id.tv_xhcx_kssj);
        tv_xhcx_end = (TextView) findViewById(R.id.tv_xhcx_jssj);
        sjxz = (LinearLayout) findViewById(R.id.sjxz);
        nodata = (LinearLayout) findViewById(R.id.nodata);

        String date = dateUtil.getYear_Day();
        startTime = date;
        endTime = date;
        tv_xhcx_end.setText(date);
        tv_xhcx_start.setText(date);
        initNet(date, date);
    }

    private void clickData() {
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "XunHuiCX");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(XhcxActivity.this, BrlbActivity.class);
                intent.putExtra("which", "XunHuiCX");
                startActivity(intent);
                finish();
            }
        });
        //开始时间选择,不需要请求数据
        tv_xhcx_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                pvTime = new TimePickerView.Builder(XhcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        startTime = format.format(date);
                        tv_xhcx_start.setText(startTime);
                        //commitData();
                    }
                }).setDate(dateUtil.getCalendar(tv_xhcx_start.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });
        //结束时间选择，请求数据
        tv_xhcx_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //时间选择器
                pvTime = new TimePickerView.Builder(XhcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        endTime = format.format(date);
                        tv_xhcx_end.setText(endTime);
                        initNet(startTime, endTime);
//                        commitData();
                    }
                }).setDate(dateUtil.getCalendar(tv_xhcx_end.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });

//        ll_choose_xhlx.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    private void initData() {
//        病区代码¤2018/11/15 0:00:00¤2018/11/15 23:59:59¤1  0 特级，1一级 2二级 3 三级    个人的话
//        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
//
//        BingQuDM = preferences2.getString("bqdm", "");
//        if (brlb == null) {
//            MyApplication instance = MyApplication.getInstance();
//            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
//            bingRenXM = instance.getListBRLB().get(0).getXINGMING();
//            bingRenXB = instance.getListBRLB().get(0).getXINGBIE();
//            chuangHao = instance.getListBRLB().get(0).getCHUANGWEIHAO();
//        } else {
//            Intent intent = getIntent();
//            zyid = intent.getStringExtra("BINGRENZYID");
//            chuangHao = intent.getStringExtra("CHUANGHAO");
//            bingRenXM = intent.getStringExtra("XINGMING");
//            bingRenXB = intent.getStringExtra("XINGBIE");
//            MyApplication.getInstance().setOther_brlb(null);
//        }
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyy/MM/dd");
//        startTime = formatter.format(currentTime);
    }

    private void commitData() {
        String kaishiSJ = startTime + " 0:00:00";
        String jieshuSJ = endTime + " 23:59:59";
        listXunHuiJL.clear();
        String canshu = zyid + "¤" + kaishiSJ + "¤" + jieshuSJ + "¤" + "BR";
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("03043001")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler2, new Class[]{XunHuiJL.class});
                log.e(data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e(info);
            }
        });
    }

    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listXunHuiJL.get(0).getBingRenZYID() == "") {
                        tvXhjlNum.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    } else {
                        tvXhjlNum.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        tvXhjlNum.setText(Constant.CHENGYI + listXunHuiJL.size());
                        XunHuiJLAdapter adapter = new XunHuiJLAdapter(XhcxActivity.this, listXunHuiJL);
                        lv_xhjl.setAdapter(adapter);
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
    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    String lx = xhlx.getText().toString().trim();
                    if (listXunHui.get(0).getXunHuiCZID().equals("")) {
                        listXunHui.clear();
                    }
                    dialog = new XunHuiCZDialog(XhcxActivity.this, listXunHui, lx, myListener);
                    dialog.show();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listXunHui.add((XunHuiCZ) msg.obj);
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


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new DoDecodeThread();
            mDoDecodeThread.start();
            //initSaoMiao();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(XhcxActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 扫描后调用的返回数据的方法
     *
     * @param data
     * @param keycode
     */
    @Override
    public void putDataToFrag(String data, int keycode) {
//        playScanSuccessBeepSound();
        log.e("M80扫码返回--" + data);
        String[] s = data.split("\\*");
        if (s[0].equals("st72")) {
            //核对病人列表并更新数据
            checkBRLBAndUpdate(s[1]);
        } else if (s.length == 1) {
            Toast.makeText(XhcxActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        filter.addAction("lachesis_barcode_value_notice_broadcast");
        filter.addAction("com.mobilead.tools.action.scan_result");
        filter.addAction("android.provider.sdlMessage");
        this.registerReceiver(myReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(myReceiver);

    }

    private boolean isSaoma = false; //true扫腕带切换病人  直接执行护理等级   false手动打开页面或手动切换病人列表
    /*
     * m80
     * */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            playScanSuccessBeepSound();
            String data = "";
            data = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
            if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
                data = intent.getStringExtra("decode_rslt");
            }
            if (Build.MODEL.equals("N1")) {
                data = intent.getStringExtra("msg");
            }
            data = data.trim().replace("��", "¤").replace("?", "¤");
            data = data.replaceAll("陇", "¤");
            String[] s = data.split("\\*");
//            Toast.makeText(XhcxActivity.this, data, Toast.LENGTH_SHORT).show();
//            System.out.print(data);
            String[] s1 = data.split("\\¤");
            //st72是腕带，切换病人
            if (s1[0].equals("st72")) {
                playSuccessBeepSound();
                checkBRLBAndUpdate(s1[1]);
            } else if (s.length > 2) {
                playFailBeepSound();
                Toast.makeText(XhcxActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(BryzBbenActivity.this,data,Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * 核对病人列表并更新数据
     *
     * @param brzyid
     */
    private void checkBRLBAndUpdate(String brzyid) {
        //病人列表是否包含这个扫出来的ID
        boolean containsZYID = false;
        for (BRLB brlb : listBRLB) {
            if (brzyid.equals(brlb.getBINGRENZYID())) {
                if (!curBrlb.getXINGMING().equals(brlb.getXINGMING())) {//判断是否
                    curBrlb = brlb;
                    isSaoma = true;
                    ToastUtil.showLong("切换病人成功");
                } else {
                    isSaoma = true;
                }
                curBrlb = brlb;
                containsZYID = true;
                break;
            }
        }

        if (containsZYID) {
            tvXhjlBeizhu.setText("病人去向＞");
            initNet(dateUtil.getYear_Day(), dateUtil.getYear_Day());
        } else {
            playFailBeepSound();
            ToastUtil.showLong("请查看病人是否在此病区");
        }
    }

    /*
     * 巡视病人
     * */
    private void xunShi(Yizhu yizhu) {
        String bzhu = yizhu.getBeiZhu();
        if (bzhu.trim().equals("")) {
            bzhu = "0";
        }
        int beizhu = Integer.parseInt(bzhu) + 1;
        yizhu.setCiShu(beizhu + "");
        yizhu.setBeiZhu(beizhu + "");
        String tou = "开始";
        List<Yizhu> list_xml = new ArrayList<>();
        list_xml.add(yizhu);
        String xml = null;
        try {
            xml = YiHuUtlis.createXml(list_xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder s = new StringBuilder();
        //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
        s.append(tou + "¤");
        s.append(xml + "¤");
        s.append("" + "¤");
        s.append(app.getYhxm() + "¤");
        s.append(yhgh + "¤");
        s.append(bqdm + "¤");
        s.append(1 + "¤");
        s.append(0 + "¤");


        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(s.toString())
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        log.e(s.toString());
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                playSuccessBeepSound();
                isSaoma = false;
                initNet(dateUtil.getYear_Day(), dateUtil.getYear_Day());
                ToastUtil.showLong("巡视成功");
            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                ToastUtil.showLong("巡视失败");
            }
        });
    }


}