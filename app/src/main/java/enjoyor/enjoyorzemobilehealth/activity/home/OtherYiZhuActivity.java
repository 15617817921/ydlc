package enjoyor.enjoyorzemobilehealth.activity.home;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.bben.view.TabItem;
import com.bben.view.TabLayout;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.PingTieXinXiActivity;
import enjoyor.enjoyorzemobilehealth.activity.ZXYZActivity;
import enjoyor.enjoyorzemobilehealth.activity.ZhiXingJiLuActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentFive;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentFour;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentOne;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentSix;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentThree;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentTwo;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.ListDataSave;
import enjoyor.enjoyorzemobilehealth.utlis.StringUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import enjoyor.enjoyorzemobilehealth.utlis.ZhuanHuanTool;
import enjoyor.enjoyorzemobilehealth.views.BottomMenu;
import enjoyor.enjoyorzemobilehealth.views.DateTimeDialogOnlyYMD;
import enjoyor.enjoyorzemobilehealth.views.PopUpWindowWithNoItemClick;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.TUPIAN;

//import org.jdom.Document;

/**
 * Created by dantevsyou on 2017/8/3.
 * 1. 新版病人医嘱
 * 2. 增加时间动态选择
 */

public class BryzBbenActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, ScanFactory.FragScan, TabLayout.OnTabClickListener, DateTimeDialogOnlyYMD.MyOnDateSetListener {
    @BindView(R.id.no4)
    ViewPager viewPager;
    @BindView(R.id.tv_zuotian)
    TextView tvZuotian;
    @BindView(R.id.tv_jintian)
    TextView tvJintian;
    @BindView(R.id.tv_mingtian)
    TextView tvMingtian;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    private ArrayList<Fragment> fragments;
    //fragments
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private FragmentFour fragmentFour;
    private FragmentFive fragmentFive;
    private FragmentSix fragmentSix;
    private int indexFL = 1;
    //view,很重要
    List<View> list = new ArrayList<>();
    private BottomMenu menuWindow;
    ZhierCall zhierCall;
    List<Yizhu> list_yizhu = new ArrayList<>();
    ListDataSave dataSave;
    ImageView back;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout linearLayout;
    ArrayList<TabItem> tabs;

    public ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
    public ArrayList<TextView> textViewArrayList = new ArrayList<>();
    int image = 0;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private TabLayout mTabLayout;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ImageView imageView;//男女头像
    private TextView tv_name;//病人姓名
    private TextView tv_chuangweihao;//病人床位号


    private MyApplication app;
    private List<BRLB> listBRLB;
    private DateUtil dateUtil;


    @Override
    public void onDateSet(Calendar calendar) {
        //时间选择函数
        Date date = calendar.getTime();
        String str = mDateFormat.format(date) + "";
        time_choose.setText(str);
        time1 = str + " 00:00:00";
        time2 = str + " 23:59:59";
        getYZ(gg_id);
    }


    //    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    Date curDate = new Date(System.currentTimeMillis());
//    String str = formatter.format(curDate);
//    private String time1 = str + " 00:00:00";
//    private String time2 = str + " 23:59:59";
    private String time1 = "";
    private String time2 = "";
    //    private String ll;
    private DateTimeDialogOnlyYMD mDateTimeDialogOnlyYMD;
    private TextView time_choose;
    private LinearLayout linearLayout2;

    //保存当前扫描的条码ID
    private String selectTMID;
    private static final int REQUEST_CODE = 1; // 请求码
    //扫描提示音
    private boolean playBeep = true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;

    private String yhid;//用户工号
    private EditText saomiao_text;

    private TextView brid_textview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bryz_bben);
        ButterKnife.bind(this);
        init();
//        screenListene();//
//        initHandler();
        initBeep();
        getFragments();
        initView();
        initHandler(this);
        //扫描监听
        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(BryzBbenActivity.this, this, true, true, true);

        time_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateTimeDialogOnlyYMD.show();
                //Toast.makeText(BryzBbenActivity.this,"xx",Toast.LENGTH_LONG).show();
            }
        });

        BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("1")
                .setBackgroundColorResource(R.color.colorAccent)
                .setBorderWidth(0);
        dataSave = new ListDataSave(this, "yizhu");
        initData();
        initListiner();

//        Intent intent = getIntent();
//        String xm = intent.getStringExtra("xingming");
//        String xb = intent.getStringExtra("xingbie");
//        String cwh = intent.getStringExtra("chuanghao");
//        String brid = intent.getStringExtra("brid");


//        brlb_position = intent.getIntExtra("position", 0);
        brlb_position = app.getChoosebr();
        BRLB brlb = listBRLB.get(brlb_position);
        String xm = brlb.getXINGMING();
        String xb = brlb.getXINGBIE();
        String cwh = brlb.getCHUANGWEIHAO();
        String brid = brlb.getBINGRENID();

        log.e(xm + "--" + xb + "--" + cwh + "--" + brlb_position);

        netWork();  //请求网络查找医嘱


        if (xb.equals("男")) {
            imageView.setImageResource(R.drawable.icon_men);
        } else {
            imageView.setImageResource(R.drawable.icon_women);
        }
        tv_name.setText(xm);
        tv_chuangweihao.setText(cwh + "床");
        brid_textview.setText("住院号:" + brid);

        viewPager.setOffscreenPageLimit(1);


        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.my_bule);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                list_yizhu=null;
//                list_yizhu=new ArrayList<Yizhu>();
                //清空数据
                list_yizhu.clear();
                zhierCall = (new ZhierCall())
                        .setId(yhid)
                        .setNumber("0400101")
                        .setMessage(NetWork.YIZHU_ZHIXING)
                        .setCanshu(gg_id + "¤" + time1 + "¤" + time2)
                        .setContext(BryzBbenActivity.this)
                        .setPort(5000)
                        .setDialogmes("刷新中...")
                        .build();
                log.e(gg_id + "¤" + time1 + "¤" + time2);
                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {
                        log.e(data);
                        StringXmlParser parser = new StringXmlParser(xmlHandler,
                                new Class[]{Yizhu.class});
                        //Log.d("login5",data);
                        parser.parse(data);
                        swipeRefreshLayout.setRefreshing(false);
                        //Toast.makeText(BryzBbenActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String info) {
                        log.e(info);
                        Toast.makeText(BryzBbenActivity.this, info, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };

        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        final float[] mPosX = new float[1];
        final float[] mPosY = new float[1];
        final float[] mCurPosX = new float[1];
        final float[] mCurPosY = new float[1];

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX[0] = event.getX();
                        mPosY[0] = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX[0] = event.getX();
                        mCurPosY[0] = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY[0] - mPosY[0] > 0 && (Math.abs(mCurPosY[0] - mPosY[0]) > 25)) {
                            //向下滑動
                            linearLayout.setVisibility(View.INVISIBLE);
                        } else if (mCurPosY[0] - mPosY[0] < 0 && (Math.abs(mCurPosY[0] - mPosY[0]) > 25)) {
                            //向上滑动
                            linearLayout.setVisibility(View.GONE);
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    private void getCurTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        time1 = str + " 00:00:00";
        time2 = str + " 23:59:59";
        time_choose.setText(str);
    }

//    @Override
//    protected void protectApp() {
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
//    }

//    private void initHandler() {
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                //主线程异常拦截
//                while (true) {
//                    try {
//                        Looper.loop();//主线程异常会从这里抛出
//                    } catch (Throwable e) {
//                        ShowDialog.setShowErro(BryzBbenActivity.this, BryzBbenActivity.this.getClass().getName() + "信息" + e.getMessage() + "--字符" + e.toString());
//                        log.e("病人医嘱主线程Throwable--" + e.getMessage() + "--" + e.toString());
////                        Intent intent = new Intent(BryzBbenActivity.this, LoginActivity.class);
////                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //清空栈里MainActivity之上的所有activty
////                        startActivity(intent);
////                        finish();
//
////                        try {
////                            Thread.sleep(2000);
////                            //移除当前activity
////                            MyActivityManager.getInstance().removeCurrent();
////                            //结束当前的进程
////                            android.os.Process.killProcess(android.os.Process.myPid());
////                            //结束虚拟机
//////                            System.exit(0);
////                        } catch (InterruptedException ex) {
////                            ex.printStackTrace();
////                            log.e("病人医嘱主线程InterruptedException" + ex.getMessage() + "--" + ex.toString());
////                        }
//                    }
//                }
//            }
//        });
//
//        //拦截所有子线程
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                ShowDialog.setShowErro(BryzBbenActivity.this, BryzBbenActivity.this.getClass().getName() + "信息" + e.getMessage() + "--字符" + e.toString());
//                Toast.makeText(BryzBbenActivity.this, "重新操作", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void initListiner() {
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "2");
                app.setOther_brlb(null);
                Intent intent = new Intent(BryzBbenActivity.this, BrlbActivity.class);
                intent.putExtra("which", "2");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BryzBbenActivity.this, HomePageActivity.class));
                finish();
            }
        });
    }

    private void netWork() {
        gg_id = listBRLB.get(brlb_position).getBINGRENZYID();
        String canshu = gg_id + "¤" + time1 + "¤" + time2;
        zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("0400101")
                .setMessage(NetWork.YIZHU_ZHIXING)
//                .setCanshu(ll + "¤" + time1 + "¤" + time2)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{Yizhu.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e(info);
                //Toast.makeText(BryzBbenActivity.this, info+"请重新扫码！", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        app = MyApplication.getInstance();
        listBRLB = app.getListBRLB();
        yhid = app.getYhgh();
        dateUtil = DateUtil.getInstance();

//        布局文件顶部 文字赋值
        tvOne.setText("检验核对");
        tvTwo.setText("巡回记录");
//        tvThree.setText("其他医嘱");
    }


    /*
    相差多少分钟
     */
    private int getTime(String beforeTime) {
        int time = 0;
        TimeZone tz = TimeZone.getTimeZone("GMT+08:00");
        TimeZone.setDefault(tz);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        formatter.setTimeZone(tz);
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        try {
            Date parse = formatter.parse(beforeTime);
//            Date parse = formatter.parse("20170206 12:06:00");
//            long yy = parse.getTime() - curDate.getTime();
            long yy = curDate.getTime() - parse.getTime();
            time = new Long(yy / 1000 / 60).intValue();//相差多少分钟
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
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


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(BryzBbenActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void putDataToFrag(String data, int keycode) {
        //播放扫描成功提示音
        playScanSuccessBeepSound();
        data = data.trim().replace("��", "¤").replace("?", "¤");
        String[] s = data.split("\\*");
        Toast.makeText(BryzBbenActivity.this, data, Toast.LENGTH_SHORT).show();
        String[] s1 = data.split("\\¤");
        //st72是腕带，切换病人
        //口服药品的话只有一串数字
        if (s1[0].equals("st72")) {
            zxBR(s1[1]);
        } else if (s.length > 2) {
            ypZXWF(data);
            //获得条码ID
            selectTMID = s[0];
            //Toast.makeText(BryzBbenActivity.this,data,Toast.LENGTH_LONG).show();
        }

    }


    private void ypZXWF(String data) {
        try {
            List<Yizhu> list_shangchuan = new ArrayList<>();
            int tag = 0;

            String[] str_TM1 = data.split("\\*");
            String[] str_TM2 = new String[1];
            String[] TMID = new String[1];
            String yzlx = "";
            if (str_TM1.length > 1 && str_TM1[0].trim().equals("st99") && str_TM1[str_TM1.length - 1].trim().startsWith("end")) {
                ScanChuli scanChuli = new ScanChuli(list_shangchuan, tag, str_TM1, yzlx).invoke();
                tag = scanChuli.getTag();
                yzlx = scanChuli.getYzlx();
            }
            //Toast.makeText(BryzBbenActivity.this,"llk"+list_shangchuan.size(),Toast.LENGTH_SHORT).show();
            //获取医嘱状态
            //注意！！！！未执行-》开始-》结束
            //后续：复核 = 7,摆药 = 8,收药 = 9后面全部是开始
            //
            //pp是当前医嘱状态,sc_xml是要上传的xml
            Yizhu yizhu = list_shangchuan.get(0);
            String pp = yizhu.getYiZhuZT();

            app.setBryz_no(yizhu.getYongFaFL());
            //扫描到医嘱已是结束状态，直接返回
            if (pp.equals(Constant.STATE_ONE)) {
//                playFailBeepSound();
                app.setBryz_bj("true");
                ToastUtil.showLong("医嘱已是结束状态");
                //刷新
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                onRefreshListener.onRefresh();
//                tishiHhuituiDialog();
                return;
            }

            //扫描滴眼 等 可多次重复 条码
            String pinCi = yizhu.getPinCi();
            String yongFaMC = yizhu.getYongFaMC();
            String title_zuhe = yongFaMC + " | " + pinCi;
            if (title_zuhe.contains(Constant.DIYAN) || title_zuhe.contains(Constant.CSX) || title_zuhe.contains(Constant.PRN)) {
                Yizhu yz_diyan = yizhu;
                String beiZhu = yz_diyan.getBeiZhu();
                if (StringUtils.stringNull(beiZhu).equals("")) {  //空未执行---即0次
                    yz_diyan.setBeiZhu("1");
                } else {
                    int num = Integer.parseInt(beiZhu);
                    num += 1;
                    yz_diyan.setBeiZhu(num + "");
                }
                yz_diyan.setYiZhuZT("6");

                String sc_xml = createXml(list_shangchuan);
                StringBuilder s = new StringBuilder();
                s.append("开始¤");
                s.append(sc_xml + "¤");
                s.append(listBRLB.get(app.getChoosebr()).getBINGRENZYID() + "¤");
                s.append(app.getYhxm() + "¤");
                s.append(app.getYhgh() + "¤");
                s.append(app.getBqdm() + "¤");
                s.append(1 + "¤");
                s.append(0 + "¤" + Constant.ZERO);//追加0是扫码   1是手动执行

                app.setListZXYZ(s.toString());
                app.setChangeYIZHU(yizhu.getYiZhuZT());
                zhierCall = (new ZhierCall())
                        .setId(yhid)
                        .setNumber("0400902")
                        .setMessage(NetWork.YIZHU_ZHIXING)
                        .setCanshu(s.toString())
                        .setContext(this)
                        .setPort(5000)
                        .setDialogmes("加载中...")
                        .build();
                log.e(s.toString());
                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {
                        log.e(data);
                        playSuccessBeepSound();
                        app.setBryz_bj("true");
                        Toast.makeText(BryzBbenActivity.this, "执行医嘱成功", Toast.LENGTH_LONG).show();
                        //刷新
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(true);
                            }
                        });
                        onRefreshListener.onRefresh();
                    }

                    @Override
                    public void fail(String info) {
                        log.e(info);
                        playFailBeepSound();
                        Toast.makeText(BryzBbenActivity.this, info, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                String sc_xml = "";
                int nb = 0;//需要改成的状态的数字
                //执行的医嘱类别
                //用法分类
//            Toast.makeText(BryzBbenActivity.this, "op" + yzlx.trim(), Toast.LENGTH_SHORT).show();

                if (yzlx.equals("4")) {
                    //if(pp.equals(ZhuanHuanTool.toInt("开始")+"")){
                    try {
                        //首先把医嘱状态遍历完成
                        for (Yizhu s : list_shangchuan) {
                            s.setYiZhuZT(1 + "");
                        }
                        sc_xml = createXml(list_shangchuan);
                        tag = 1;
                        nb = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //}
                } else {
                    if (pp.equals(ZhuanHuanTool.toInt("未执行") + "") || pp.equals(ZhuanHuanTool.toInt("停用") + "")) {
                        try {
                            //首先把医嘱状态遍历完成
                            for (Yizhu s : list_shangchuan) {
                                s.setYiZhuZT(6 + "");
                            }
                            sc_xml = createXml(list_shangchuan);
                            tag = 1;
                            nb = 6;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (pp.equals(ZhuanHuanTool.toInt("开始") + "")) {
                        try {
                            //首先把医嘱状态遍历完成
                            for (Yizhu s : list_shangchuan) {
                                s.setYiZhuZT(1 + "");
                            }
                            sc_xml = createXml(list_shangchuan);
                            tag = 1;
                            nb = 1;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (pp.equals(ZhuanHuanTool.toInt("复合") + "")
                            || pp.equals(ZhuanHuanTool.toInt("摆药") + "")
                            || pp.equals(ZhuanHuanTool.toInt("收药") + "")) {
                        try {
                            //首先把医嘱状态遍历完成
                            for (Yizhu s : list_shangchuan) {
                                s.setYiZhuZT(6 + "");
                            }
                            sc_xml = createXml(list_shangchuan);
                            tag = 1;
                            nb = 6;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                if (tag == 1) {
                    StringBuilder s = new StringBuilder();
                    s.append(ZhuanHuanTool.toString1(nb) + "¤");
                    s.append(sc_xml + "¤");
                    s.append(listBRLB.get(app.getChoosebr()).getBINGRENZYID() + "¤");
                    s.append(app.getYhxm() + "¤");
                    s.append(app.getYhgh() + "¤");
                    s.append(app.getBqdm() + "¤");
                    s.append(1 + "¤");
                    s.append(0 + "¤" + Constant.ZERO);

                    app.setListZXYZ(s.toString());
                    app.setChangeYIZHU(yizhu.getYiZhuZT());
                    String ll = listBRLB.get(app.getChoosebr()).getBINGRENZYID();
//                if (!DateUtil.isFastClick()) {
//                    // 进行点击事件后的逻辑操作
//                    return;
//                }
                    zhierCall = (new ZhierCall())
                            .setId(yhid)
                            .setNumber("0400902")
                            .setMessage(NetWork.YIZHU_ZHIXING)
                            .setCanshu(s.toString())
                            .setContext(this)
                            .setPort(5000)
                            .setDialogmes("加载中...")
                            .build();
                    log.e(s.toString());
                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {
                            log.e(data);
                            playSuccessBeepSound();
                            app.setBryz_bj("true");
                            Toast.makeText(BryzBbenActivity.this, "执行医嘱成功", Toast.LENGTH_LONG).show();
                            //刷新
                            swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(true);
                                }
                            });
                            onRefreshListener.onRefresh();
                        }

                        @Override
                        public void fail(String info) {
                            log.e(info);
                            playFailBeepSound();
                            Toast.makeText(BryzBbenActivity.this, info, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }


        } catch (Exception e) {
            log.e(e.getMessage() + "--" + e.toString());
            playFailBeepSound();
            Toast.makeText(BryzBbenActivity.this, "匹配不成功", Toast.LENGTH_LONG).show();
        }


    }

    /*
     * 医嘱误操作结束  提示框*/
    private void tishiHhuituiDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("提示").setMessage("医嘱已是结束状态，如需回退可点击医嘱后省略号进行回退")
//                .setPositiveButton("回退", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
                .setNegativeButton("已了解",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }


    /**
     * .
     * 底部医嘱  切换
     */
    public void bottomChange(String tag) {
        switch (Integer.parseInt(tag)) {
            case 1:
                mTabLayout.setCurrentTab(1);
                viewPager.setCurrentItem(1);
                break;
            case 2:
                mTabLayout.setCurrentTab(3);
                viewPager.setCurrentItem(3);
                break;
            case 3:
                mTabLayout.setCurrentTab(4);
                viewPager.setCurrentItem(4);
                break;
            case 4:
                mTabLayout.setCurrentTab(0);
                viewPager.setCurrentItem(0);
                break;
            case 5:
                mTabLayout.setCurrentTab(2);
                viewPager.setCurrentItem(2);
                break;
            case 6:
                mTabLayout.setCurrentTab(5);
                viewPager.setCurrentItem(5);
                break;
            default:
                mTabLayout.setCurrentTab(1);
                viewPager.setCurrentItem(1);
                break;
        }
    }


    //==================上面是mobi扫描头驱动=============================

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        back = (ImageView) findViewById(R.id.back);
        time_choose = (TextView) findViewById(R.id.time_choose);
        brid_textview = (TextView) findViewById(R.id.brid);
        linearLayout2 = (LinearLayout) findViewById(R.id.bdf);
        linearLayout = (RelativeLayout) findViewById(R.id.no3);
        imageView = (ImageView) findViewById(R.id.imageView);
        tv_name = (TextView) findViewById(R.id.bingrenname);
        tv_chuangweihao = (TextView) findViewById(R.id.chuanghao);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        getCurTime();
    }

    /*
     * 底部文字展示
     -----------------*/
    private void initData() {
        tabs = new ArrayList<TabItem>();
        tabs.add(new TabItem(R.drawable.selector_tab_1, R.string.bben1, FragmentOne.class));
        tabs.add(new TabItem(R.drawable.selector_tab_2, R.string.bben2, FragmentTwo.class));
        tabs.add(new TabItem(R.drawable.selector_tab_3, R.string.bben3, FragmentThree.class));
        tabs.add(new TabItem(R.drawable.selector_tab_4, R.string.bben4, FragmentFive.class));
        tabs.add(new TabItem(R.drawable.selector_tab_5, R.string.bben5, FragmentFour.class));
        tabs.add(new TabItem(R.drawable.selector_tab_6, R.string.bben6, FragmentSix.class));
        mTabLayout.initData(tabs, this);

    }

    @Override
    public void onTabClick(TabItem tabItem) {
        viewPager.setCurrentItem(tabs.indexOf(tabItem));
        //ImageView imageView=tabItem.
    }


    private void getFragments() {
        list = new ArrayList<>();
//        LayoutInflater inflater = getLayoutInflater();
        LayoutInflater inflater = BryzBbenActivity.this.getLayoutInflater();
        View view1 = inflater.inflate(R.layout.fragment_one, null);
        View view2 = inflater.inflate(R.layout.fragment_two, null);
        View view3 = inflater.inflate(R.layout.fragment_three, null);
        View view4 = inflater.inflate(R.layout.fragment_four, null);
        View view5 = inflater.inflate(R.layout.fragment_five, null);
        View view6 = inflater.inflate(R.layout.fragment_six, null);

        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);
        list.add(view6);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                break;
            case 4:
                viewPager.setCurrentItem(4);
                break;
            case 5:
                viewPager.setCurrentItem(5);
                break;
            default:
                viewPager.setCurrentItem(6);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:


                    final List<Yizhu> list1 = new ArrayList<>();
                    final List<Yizhu> list2 = new ArrayList<>();
                    final List<Yizhu> list3 = new ArrayList<>();
                    final List<Yizhu> list4 = new ArrayList<>();
                    final List<Yizhu> list5 = new ArrayList<>();
                    final List<Yizhu> list6 = new ArrayList<>();

                    for (int i = 0; i < list_yizhu.size(); i++) {
                        Yizhu yizhu = list_yizhu.get(i);
                        switch (yizhu.getYongFaFL()) {
                            case "1":
                                log.e("输液类目:" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
//                                log.e("下面打印出医嘱分类:" + list_yizhu.get(i).getYongFaFL());
                                list1.add(yizhu);
                                break;
                            case "2":
                                log.e("注射类目:" + yizhu.getYiZhuZT() + "--" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
                                //System.out.print("治疗:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list2.add(yizhu);
                                break;
                            case "3":
                                log.e("口服类目:" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
                                //System.out.print("口服:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list3.add(yizhu)
                                ;
                                break;
                            case "4":
                                log.e("口服:" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
                                list4.add(yizhu);
                                break;
                            case "5":
                                log.e("其它医嘱5 新修改的内容:" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
                                /*//System.out.print("其它医嘱:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list5.add(list_yizhu.get(i));*/
                                //新修改的内容
                                list5.add(yizhu);
                                break;
                            case "6":
                                log.e("其它医嘱:" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
                                //System.out.print("其它医嘱:"+list_yizhu.get(i).getYongFaFL()+"\n");
//                                list5.add(list_yizhu.get(i));
                                list6.add(yizhu);
                                break;
                        }
                    }


                    PagerAdapter pagerAdapter = new PagerAdapter() {
                        @Override
                        public int getCount() {
                            return list.size();
                        }

                        @Override
                        public boolean isViewFromObject(View arg0, Object arg1) {
                            return arg0 == arg1;
                        }

                        @Override
                        public void destroyItem(ViewGroup container, int position, Object object) {
                            container.removeView(list.get(position));
                        }

                        @Override
                        public Object instantiateItem(View container, int position) {
                            ((ViewPager) container).addView(list.get(position), 0);
                            //navigationBar.selectTab(position);
                            return list.get(position);
                        }
                    };

                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                            //切换页面
                            //navigationBar.selectTab(position);
                            mTabLayout.setCurrentTab(position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                   /* try {
                        createXml(list2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    viewPager.setOffscreenPageLimit(6);

                    //给每一组list设置数据
                    setRecyclerView(0, list4);
                    setRecyclerView(1, list1);
                    //setRecyclerView(2,list5);
                    setRecyclerView(3, list2);
                    setRecyclerView(4, list3);
                    setRecyclerView(5, list6);
                    viewPager.setCurrentItem(indexFL);
                    mTabLayout.setCurrentTab(indexFL);
                    //Toast.makeText(BryzBbenActivity.this,MyApplication.getInstance().getBryz_no(),Toast.LENGTH_SHORT).show();
                    //刷新好后切换到指定的tab
                    try {
                        if (Integer.parseInt(app.getBryz_no()) != 1 && app.getBryz_bj().equals("true")) {
                            bottomChange(app.getBryz_no());//底部切换
                            app.setBryz_bj("false");
                        }
                    } catch (Exception e) {
                        log.e(e.getMessage() + "--" + e.toString());
                    }

                    try {
                        if (if_success == true) {
                            for (int i = 0; i < listBRLB.size(); i++) {
                                //病人住院ID
                                if (id2.equals(listBRLB.get(i).getBINGRENZYID())) {
                                    //重新设置病人姓名
                                    String xb = listBRLB.get(i).getXINGBIE();
                                    String xm = listBRLB.get(i).getXINGMING();
                                    String cwh = listBRLB.get(i).getCHUANGWEIHAO();
                                    if (xb.equals("男")) {
                                        imageView.setImageResource(R.drawable.icon_men);
                                    } else {
                                        imageView.setImageResource(R.drawable.icon_women);
                                    }
                                    tv_name.setText(xm);
                                    tv_chuangweihao.setText(cwh + "床");
                                    brid_textview.setText("住院号:" + listBRLB.get(i).getBINGRENID());
                                    break;
                                }
                            }
                        }
                        if_success = false;
                    } catch (Exception e) {
                        log.e(e.getMessage() + "--" + e.toString());
                    }


                    break;

                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            Yizhu yizhu = (Yizhu) msg.obj;
                            log.e("END:" + yizhu.getYongFaFL() + "--" + yizhu.getTiaoMaID() + "--" + yizhu.getYiZhuMC());
                            list_yizhu.add((Yizhu) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                case TUPIAN:
                    int i = msg.getData().getInt("what");
                    int j = msg.getData().getInt("what2");
                    ImageView imageView = (ImageView) msg.getData().getParcelable("object");
                    switch (i) {
                        case 0:
                            imageViewArrayList.get(j).setImageResource(R.drawable.wzx);
                            textViewArrayList.get(j).setTextColor(Color.rgb(151, 198, 52));
                            break;
                        case 1:
                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_jieshu3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                            break;
                        case 2:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_zhongduan3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                            break;
                        case 3:
                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_zanting3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(151, 198, 52));
                            break;
                        case 4:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_tingyong3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                            break;
                        case 5:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_jixu3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                            break;
                        case 6:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_kaishi3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));
                            break;
                        case 7:
                            //holder.setText(R.id.yizhu2, "复核");
                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_fuhe);
                            textViewArrayList.get(j).setTextColor(Color.rgb(249, 153, 29));
                            break;
                        case 8:
                            //holder.setText(R.id.yizhu2, "提药");
                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_baiyao);
                            textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));

                            break;
                        case 9:
                            //holder.setText(R.id.yizhu2, "收药");
                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_shouyao);
                            textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));
                            break;
                    }

                    break;
                default:
                    break;
            }
        }
    };

    public void dataSave(List<Yizhu> list1, SharedPreferences.Editor editor, String name) {
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(list1);
        System.out.print("Gson转化数据：" + strJson + "\n");

        editor.clear();
        editor.putString(name, strJson);
        editor.commit();
    }

    public void setRecyclerView(final int index, final List<Yizhu> list1) {
        View one = null;
        viewPager.setCurrentItem(0);
        switch (index) {
            case 0:
                one = list.get(index).findViewById(R.id.one);
                break;
            case 1:
                one = list.get(index).findViewById(R.id.two);
                break;
            case 2:
                one = list.get(index).findViewById(R.id.three);
                break;
            case 3:
                one = list.get(index).findViewById(R.id.four);
                break;
            case 4:
                one = list.get(index).findViewById(R.id.five);
                break;
            case 5:
                one = list.get(index).findViewById(R.id.six);
                break;

        }
        //list1.add(list1.get(0));
        RecyclerView recyclerView = (RecyclerView) one.findViewById(R.id.fragment_recyclerview);
//        final int[] i = {0};
        //排序  分组
        final ArrayList<ArrayList<Yizhu>> fenzhu_list = getArrayLists(index, list1);
        try {
            recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<Yizhu>>(BryzBbenActivity.this, R.layout.bryz_recycler_view_item, fenzhu_list) {
                String tmid = "";
                int nn = 1;
                int bb = 0;

                @Override
                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<Yizhu> yizhus, final int position) {

//                    LinearLayout linearLayout=holder.getView(R.id.real_back);
//                    ViewGroup.LayoutParams ip;
//                    ip=linearLayout.getLayoutParams();
//                    ip.height=dp2px(BryzBbenActivity.this,72)+fenzhu_list.get(position).size()*dp2px(BryzBbenActivity.this,50);
//                    linearLayout.setLayoutParams(ip);
//                    linearLayout.invalidate();
                    String mc = fenzhu_list.get(position).get(0).getYongFaMC();
                    log.e("标题--" + mc);
                    //总的状态
                    final String yiZhuZT = fenzhu_list.get(position).get(0).getYiZhuZT();

                    final int vv = position;
                    ArrayList<Yizhu> tempList = new ArrayList<Yizhu>();
//                    Button btnClickMore= holder.getView(R.id.btn_clickmore);
                    ImageView ivClickMore = holder.getView(R.id.iv_clickmore);
                    if (fenzhu_list.get(vv).size() > 5) {
                        tempList.add(fenzhu_list.get(vv).get(0));
                        tempList.add(fenzhu_list.get(vv).get(1));
                        tempList.add(fenzhu_list.get(vv).get(2));
                        tempList.add(fenzhu_list.get(vv).get(3));
                        tempList.add(fenzhu_list.get(vv).get(4));
//                        btnClickMore.setVisibility(View.VISIBLE);
//                        btnClickMore.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                PopUpWindowWithNoItemClick popWindow=new PopUpWindowWithNoItemClick(BryzBbenActivity.this,fenzhu_list.get(vv));
//                                popWindow.showAtLocation(BryzBbenActivity.this.findViewById(R.id.ll_parent), Gravity.CENTER,0,0);
//                            }
//                        });
                        ivClickMore.setVisibility(View.VISIBLE);
                        ivClickMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PopUpWindowWithNoItemClick popWindow = new PopUpWindowWithNoItemClick(BryzBbenActivity.this.getParent(), fenzhu_list.get(vv));
                                popWindow.showAtLocation(BryzBbenActivity.this.getParent().findViewById(R.id.ll_parent), Gravity.CENTER, 0, 0);

//                                PopUpWindowWithNoItemClick popWindow = new PopUpWindowWithNoItemClick(BryzBbenActivity.this, fenzhu_list.get(vv));
//                                popWindow.showAtLocation(BryzBbenActivity.this.findViewById(R.id.ll_parent), Gravity.CENTER, 0, 0);
                                backgroundAlpha(0.7f);
                                popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                    @Override
                                    public void onDismiss() {
                                        //设置背景透明度
                                        backgroundAlpha(1f);
                                    }
                                });
                            }
                        });
                    } else {
                        tempList = fenzhu_list.get(vv);
//                        holder.getView(R.id.btn_clickmore).setVisibility(View.GONE);
                        ivClickMore.setVisibility(View.GONE);
                    }

                    LinearLayout linearLayout = holder.getView(R.id.real_back);
                    ViewGroup.LayoutParams ip;
                    ip = linearLayout.getLayoutParams();
                    ip.height = dp2px(BryzBbenActivity.this, 72) + tempList.size() * dp2px(BryzBbenActivity.this, 50);
                    linearLayout.setLayoutParams(ip);
                    linearLayout.invalidate();

                    final ArrayList<Yizhu> finalList = tempList;

                    View view = holder.getView(R.id.my_list);
                    ListView listView = (ListView) view.findViewById(R.id.my_list);
                    setInAdapter(yiZhuZT, finalList, listView);//内层 展示

//                    listView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                    if (fenzhu_list.size() == 0) {

                    } else {
                        tmid = fenzhu_list.get(0).get(0).getTiaoMaID();
                    }

                    //省略号 弹窗
                    moreClick(yiZhuZT, holder, position, fenzhu_list);
                    //就是上面透明的那一栏
//                    holder.setVisible(R.id.pp, false);
//                    i[0]++;
                    log.e("位置：" + position);

//                    String yiZhuZT = fenzhu_list.get(position).get(0).getYiZhuZT();
                    switch (yiZhuZT) {
                        case "0":
                            holder.setText(R.id.yizhu2, "未执行");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 0);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "1":
                            holder.setText(R.id.yizhu2, "结束");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 1);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "2":
                            holder.setText(R.id.yizhu2, "异常中断");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 2);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "3":
                            holder.setText(R.id.yizhu2, "暂停");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 3);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(151, 198, 52));
                            break;
                        case "4":
                            holder.setText(R.id.yizhu2, "停用");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 4);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "5":
                            holder.setText(R.id.yizhu2, "继续");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 5);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "6":
                            holder.setText(R.id.yizhu2, "开始");
                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 6);
                            image++;
                            break;
                        case "7":
                            holder.setText(R.id.yizhu2, "复核");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 7);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "8":
                            holder.setText(R.id.yizhu2, "摆药");
                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 8);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "9":
                            holder.setText(R.id.yizhu2, "收药");
                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 9);
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            image++;
                    }

                    //holder.setText(R.id.yizhu3, list1.get(position).getYiZhuMC());


                    holder.setText(R.id.yizhu10, fenzhu_list.get(position).get(0).getJieShuSJ());
                    //holder.setText(R.id.yizhu5, list1.get(position).getJiLiang() + list1.get(position).getJiLiangDW());
//                    holder.setText(R.id.yizhu6, fenzhu_list.get(position).get(0).getPinCi());


                    TextView tv_name = holder.getView(R.id.yizhu7);//分类 如：静滴 csx 文字遗嘱
                    TextView tv_qd = holder.getView(R.id.yizhu6);// 频次  qd

                    int gb = position + 1;
                    TextView tv_zu = holder.getView(R.id.yizhu4);//第几组
//                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                    setTextColor(yiZhuZT, tv_name);
                    setTextColor(yiZhuZT, tv_qd);
                    setTextColor(yiZhuZT, tv_zu);

                    Yizhu yizhu = fenzhu_list.get(position).get(0);
                    String yongFaMC = yizhu.getYongFaMC();
                    String yanbie = "";
                    if (yongFaMC.contains(Constant.DIYAN)) {
                        yanbie = yizhu.getBz();
                    }
                    String yongFaFL = yizhu.getYongFaFL();
                    String pinCi = yizhu.getPinCi();
                    String title_zuhe = yizhu.getPinCi();
                    if (!yizhu.getBeiZhu().equals("") && yongFaFL.equals("6")) { //其他医嘱 显示 执行次数  备注
                        tv_name.setText(yongFaMC + " " + yanbie + Constant.CHENGYI + " " + yizhu.getBeiZhu());
                    } else {
                        tv_name.setText(yongFaMC + " " + yanbie);
                    }

                    tv_qd.setText(yizhu.getPinCi());
                    tv_zu.setText("第" + gb + "组");
//                    holder.setText(R.id.yizhu7, fenzhu_list.get(position).get(0).getYongFaMC());

                    TextView tv_zxr = holder.getView(R.id.nn4);//执行人
                    TextView tv_zxsj = holder.getView(R.id.nn5);//执行时间
                    TextView tv_iszx = holder.getView(R.id.nn6);//是否执行
                    setTextColor(yiZhuZT, tv_zxr);
                    setTextColor(yiZhuZT, tv_zxsj);
                    setTextColor(yiZhuZT, tv_iszx);
                    tv_zxr.setText(fenzhu_list.get(position).get(0).getCaoZuoRen());
                    tv_zxsj.setText(fenzhu_list.get(position).get(0).getCaoZuoSJ());


//                    holder.setText(R.id.nn4, fenzhu_list.get(position).get(0).getCaoZuoRen());
//                    holder.setText(R.id.nn5, fenzhu_list.get(position).get(0).getCaoZuoSJ());
                    if (!TextUtils.isEmpty(fenzhu_list.get(position).get(0).getCaoZuoSJ())) {
//                        holder.setText(R.id.nn6, "执行");
                        tv_iszx.setText("执行");
                    }
                    // holder.setTag(R.id.nn5,fenzhu_list.get(position).get(0).getCaoZuoSJ());
                }
            });
        } catch (Exception e) {
            log.e(e.getMessage() + "--" + e.toString());
        }
    }

    @NonNull
    private ArrayList<ArrayList<Yizhu>> getArrayLists(int index, List<Yizhu> list1) {
        //进行分组
        final ArrayList<ArrayList<Yizhu>> fenzhu_list = new ArrayList<>();
        ArrayList<Yizhu> jiahuan = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();
        String fenzhu1 = "";
        try {
            fenzhu1 = list1.get(0).getTiaoMaID();
            all.add(fenzhu1);
        } catch (Exception e) {

        }

        for (Yizhu yizhu : list1) {
            ArrayList<String> all2 = new ArrayList<>();
            int o = 0;
            for (String s : all) {
                if (yizhu.getTiaoMaID().equals(s)) {
                    o = 1;
                    break;
                }
            }

            if (o == 0) {
                all.add(yizhu.getTiaoMaID());
            }
        }

        //用分好的进行排序
        for (String fenzhu : all) {
            int ff = 0;
            for (Yizhu yizhu : list1) {
                if (fenzhu.equals(yizhu.getTiaoMaID())) {
                    jiahuan.add(yizhu);
                    ff = 1;
                }
            }
            if (ff == 1) {
                fenzhu_list.add(jiahuan);
                jiahuan = null;
                jiahuan = new ArrayList<>();
            }

        }

//        for (int p = 0; p < fenzhu_list.size(); p++) {
//            log.e("分组后的代码为:");
//            log.e("一共有：" + fenzhu_list.get(0).size());
//            log.e("个数：" + fenzhu_list.get(0).size());
//        }

        //将当前页面的扫描的条码ID所在的分组和第一组调换位置，显示在最前面
        try {
            switch (Integer.parseInt(app.getBryz_no())) {
                case 1:
                    if (index == 1 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 2:
                    if (index == 3 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 3:
                    if (index == 4 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 4:
                    if (index == 0 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 5:
                    if (index == 2 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 6:
                    if (index == 5 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.e(e.getMessage() + "--" + e.toString());
        }
        return fenzhu_list;
    }

    private void setInAdapter(final String yiZhuZT, final ArrayList<Yizhu> finalList, ListView listView) {
        listView.setAdapter(new CommonAdapter<Yizhu>(getBaseContext(), R.layout.bryz_singleitem, finalList) {
            @Override
            protected void convert(ViewHolder viewHolder, Yizhu item, int position) {
//                            log.e(finalList.get(position).getYiZhuFL() + "--" + finalList.get(position).getYiZhuFL() + "--" + finalList.get(position).getYiZhuZT());
                TextView textView = viewHolder.getView(R.id.nn1);
                TextView tv_jiliang = viewHolder.getView(R.id.nn2);
                setTextColor(yiZhuZT, textView);
                setTextColor(yiZhuZT, tv_jiliang);
                textView.setText(finalList.get(position).getYiZhuMC());
                tv_jiliang.setText("剂量  " + finalList.get(position).getJiLiang() + finalList.get(position).getJiLiangDW());
//                            viewHolder.setText(R.id.nn1, finalList.get(position).getYiZhuMC());
//                            viewHolder.setText(R.id.nn2, "剂量  " + finalList.get(position).getJiLiang() + finalList.get(position).getJiLiangDW());
            }
        });
    }

    /**
     * 省略号  弹出底部弹窗
     *
     * @param yiZhuZT
     * @param holder
     * @param position
     * @param fenzhu_list
     */
    private void moreClick(final String yiZhuZT, com.zhy.adapter.recyclerview.base.ViewHolder holder, final int position, final ArrayList<ArrayList<Yizhu>> fenzhu_list) {
        holder.setOnClickListener(R.id.more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行医嘱，底部弹框
                final View layout = findViewById(R.id.bottom);
                layout.setVisibility(View.VISIBLE);
                Button bt_ptXinXi = (Button) layout.findViewById(R.id.btn1);
                Button bt_zxyizhu = (Button) layout.findViewById(R.id.btn2);
                Button bt_zxJiLu = (Button) layout.findViewById(R.id.btn3);
                Button bt_back = (Button) layout.findViewById(R.id.bt_back);
                Button bt_cancle = (Button) layout.findViewById(R.id.btn_cancel);

                String[] s = new String[1];
                s[0] = fenzhu_list.get(position).get(0).getTiaoMaID();
                final String tiaomaID = s[0];
                //保存医嘱状态
                toZX(s);
                //如果不是未执行状态状态  则显示回退
                if (!yiZhuZT.equals(Constant.STATE_O)) {
                    bt_back.setVisibility(View.VISIBLE);
                    bt_back.setText("回退");
                    bt_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layout.setVisibility(View.GONE);
                            Yizhu yz = fenzhu_list.get(position).get(0);
                            //底部切换
                            app.setBryz_no(yz.getYongFaFL());
                            app.setBryz_bj("true");
                            backDialog(tiaomaID);
                        }
                    });
                } else {
                    bt_back.setVisibility(View.GONE);
                }


                bt_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setVisibility(View.GONE);
                    }
                });

                bt_zxyizhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                                    String yongFaMC = fenzhu_list.get(position).get(0).getYongFaMC();
                        Yizhu yz = fenzhu_list.get(position).get(0);
                        Intent intent = new Intent(BryzBbenActivity.this, ZXYZActivity.class);
//                                    Bundle bundle=new Bundle();
//                                    bundle.putString("no1",fenzhu_list.get(position).get(0).getYiZhuMC());
//                                    bundle.putString("no2",fenzhu_list.get(position).get(0).getYongLiang());
                        String yongFaMC = yz.getYongFaMC();// 标题
                        String pici = yz.getPinCi();// 频次
                        String title = yongFaMC + " | " + pici;
                        /*
                        区分滴眼
                        */
                        Yizhu yizhu = null;
                        if (title.contains(Constant.DIYAN) || title.contains(Constant.CSX) || title.contains(Constant.PRN)) {   //滴眼
                            yizhu = new Yizhu();
                            if (!StringUtils.stringNull(tiaomaID).equals("")) {
                                for (Yizhu x : list_yizhu) {
                                    if (tiaomaID.equals(x.getTiaoMaID())) {
                                        yizhu = x;
                                        break;
                                    }
                                }
                            }
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("yizhu", yizhu);
                            intent.putExtra("bundle", bundle);
                        } else {  //正常流程


                        }
                        intent.putExtra("no1", yz.getYiZhuMC());
                        intent.putExtra("no2", yz.getJiLiang());
                        intent.putExtra("no3", yz.getYiZhuLB());
                        intent.putExtra("state", yz.getYiZhuZT());
                        intent.putExtra("danwei", yz.getJiLiangDW());
                        intent.putExtra("tmid", yz.getTiaoMaID());
                        intent.putExtra("yfmc", title);//包括 用途路径  |   频次

                        intent.putExtra("leibie", "yizhu");//区分滴眼和医嘱返回码

                        app.setBryz_no(yz.getYongFaFL());
                        startActivityForResult(intent, REQUEST_CODE);
                        layout.setVisibility(View.GONE);
                    }
                });

                //瓶贴信息按钮的单击事件
                bt_ptXinXi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToPingTieXinXi = new Intent(BryzBbenActivity.this, PingTieXinXiActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", listBRLB.get(brlb_position).getXINGMING());
                        bundle.putString("bedNum", listBRLB.get(brlb_position).getCHUANGWEIHAO());
                        bundle.putSerializable("yizhu", fenzhu_list.get(position));
                        intentToPingTieXinXi.putExtras(bundle);
                        startActivity(intentToPingTieXinXi);
                        layout.setVisibility(View.GONE);
                        log.e(listBRLB.get(brlb_position).getXINGMING() + listBRLB.get(brlb_position).getCHUANGWEIHAO());
                    }
                });

                //执行记录的单击事件
                bt_zxJiLu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentZhiXingJiLu = new Intent(BryzBbenActivity.this, ZhiXingJiLuActivity.class);
                        intentZhiXingJiLu.putExtra("tmid", fenzhu_list.get(position).get(0).getTiaoMaID());
                        startActivity(intentZhiXingJiLu);
                        layout.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void toZX(String[] tmid) {
        try {
            List<Yizhu> list_shangchuan = new ArrayList<>();
            int i = 0;
            for (String s : tmid) {
                for (Yizhu x : list_yizhu) {
                    System.out.println(x.getTiaoMaID());
                    if (s.equals(x.getTiaoMaID())) {
                        list_shangchuan.add(x);
                        i = 1;
                        break;
                    }
                }
            }

            String all;
            String xml = null;
            String kk = list_shangchuan.get(0).getYiZhuZT();
            for (int l = 0; l < list_shangchuan.size(); l++) {
                //list_shangchuan.get(l).setYiZhuZT(ZhuanHuanTool.toString1(ZhuanHuanTool.toInt((Integer.parseInt(kk)+1)+"")));
            }
            try {
                xml = createXml(list_shangchuan);
            } catch (Exception e) {
                e.printStackTrace();
            }

            StringBuilder s = new StringBuilder();
            //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
            s.append("ThreeMills" + "¤");
            s.append(xml + "¤");
            s.append(listBRLB.get(app.getChoosebr()).getBINGRENZYID() + "¤");
            s.append(app.getYhxm() + "¤");
            s.append(app.getYhgh() + "¤");
            s.append(app.getBqdm() + "¤");
            s.append(1 + "¤");
            s.append(0 + "¤");

            app.setListZXYZ(s.toString());
            app.setChangeYIZHU(list_shangchuan.get(0).getYiZhuZT());
        } catch (Exception e) {
            Toast.makeText(BryzBbenActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 执行医嘱  回退
     *
     * @param tmid
     */
    public void backDialog(final String tmid) {
        AlertDialog alertDialog = new AlertDialog.Builder(BryzBbenActivity.this)
                .setCancelable(false)
                .setTitle("提示").setMessage("医嘱是否回退到未执行状态？")
                .setPositiveButton("回退", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startHuitui(tmid);
                    }
                }).setNegativeButton("稍后再说",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }

    /*
     *请求网络开始回退
     **/
    private void startHuitui(String tmid) {
        List<Yizhu> list_shangchuan = new ArrayList<>();
        //获得TMID
//        String tiaomaid = "";
//        for (Yizhu x : list_yizhu) {
//            if (tmid.equals(x.getYiZhuMXID())) {
//                tiaomaid = x.getTiaoMaID();
//                break;
//            }
//        }
        //通过条码ID去取医嘱
        if (!StringUtils.stringNull(tmid).equals("")) {
            for (Yizhu x : list_yizhu) {
                if (tmid.equals(x.getTiaoMaID())) {
                    x.setBeiZhu("0");
                    list_shangchuan.add(x);
                    break;
                }
            }
        }
        String sc_xml = null;
        try {
            sc_xml = createXml(list_shangchuan);
        } catch (Exception e) {
            log.e("创建xml文件出错" + e.getMessage() + "--" + e.toString());
            e.printStackTrace();
        }
        StringBuilder s = new StringBuilder();
        s.append("未执行¤");
        s.append(sc_xml + "¤");
        s.append(listBRLB.get(app.getChoosebr()).getBINGRENZYID() + "¤");
        s.append(app.getYhxm() + "¤");
        s.append(app.getYhgh() + "¤");
        s.append(app.getBqdm() + "¤");
        s.append(1 + "¤");
        s.append(0 + "¤" + Constant.ONE);

        zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(s.toString())
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e("回退参数--" + s.toString());
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e(data);
//                playSuccessBeepSound();
                app.setBryz_bj("true");
                ToastUtil.showLong("回退医嘱成功");
                //刷新
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                onRefreshListener.onRefresh();
            }

            @Override
            public void fail(String info) {
                log.e(info);
//                playFailBeepSound();
                ToastUtil.showLong("回退医嘱失败");
            }
        });
    }

    /*
     *根据状态来改变相应的字体
     */
    private void setTextColor(String yiZhuZT, TextView tv_name) {
        // 0--未执行 1--结束 2--异常中断 3--暂停 4--停用 5--继续 6--开始
        if (yiZhuZT.equals("0") || yiZhuZT.equals("3")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt0));
        } else if (yiZhuZT.equals("6") || yiZhuZT.equals("2")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt1));
        } else if (yiZhuZT.equals("1") || yiZhuZT.equals("5")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt3));
        } else if (yiZhuZT.equals("4")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt6));
        }
    }

    MediaPlayer player = new MediaPlayer();


    //去病人列表中核实是否有这条id，如果有去切换病人
    private void zxBR(String brid) {
        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(BryzBbenActivity.this, this, true, true, true);
        getCurTime();//先更新一下当前时间.
        try {
            int i = 0;
            for (int j = 0; j < listBRLB.size(); j++) {
                if (brid.equals(listBRLB.get(j).getBINGRENZYID())) {
//                    ll = brid;
                    gg_id = brid;
                    brlb_position = j;
                    getYZ(gg_id);
                    i = 1;
                    log.e(listBRLB.get(j).getBINGRENZYID() + "--" + listBRLB.get(j).getXINGMING());
                    break;
                }
            }
            if (i == 0) {
                ToastUtil.showLong("当前病人不在改病区");
                player.start();
                playFailBeepSound();
            } else {
                playSuccessBeepSound();
            }
        } catch (Exception e) {
            log.e(e.getMessage() + "--" + e.toString());
            ToastUtil.showLong("当前病人不在该病区");
            playFailBeepSound();
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
        //this.unregisterReceiver(myReceiver);
        if (myReceiver2 != null) {
            this.unregisterReceiver(myReceiver2);
            log.e("广播解绑");
        }
        super.onPause();
    }

    // m80 扫码广播
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

                log.e("条码" + data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if (s1[0].equals("st72")) {
                    zxBR(s1[1]);
                } else if (s.length > 2) {
                    ypZXWF(data);
                    //获得条码ID
                    selectTMID = s[0];
                } else {
                    ToastUtil.showLong(data);
                }
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BryzBbenActivity.this);
                builder.setMessage("无法得到扫描结果");
                builder.show();
            }
        }


    };

//    //扫描的广播接收器
//    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            playScanSuccessBeepSound();
//            String text = intent.getStringExtra("data");
//
//            //Toast.makeText(BryzBbenActivity.this,text,Toast.LENGTH_LONG).show();
//            String data = text;
//            String[] s = data.split("\\*");
//            //Toast.makeText(BryzBbenActivity.this,text+"大小："+s.length,Toast.LENGTH_LONG).show();
//            System.out.print(data);
//            //st72是腕带，切换病人
//            //药品的话只有一串数字
//            if (s[0].equals("st72")) {
//                zxBR(s[1]);
//            } else {
//                ypZX(s);
//                //获得条码ID
//                selectTMID = s[0];
//                //Toast.makeText(BryzBbenActivity.this,data,Toast.LENGTH_LONG).show();
//            }
//        }
//    };


    //公共的id
    String gg_id = "";
    //腕带扫描成功后切换到此方法，去更新界面。list_yizhu要清理一下
    //判断扫腕带是否成功
    boolean if_success = false;
    String id2;
    private int brlb_position = 0;//全局 病人列表的位置  默认第一个

    private void getYZ(String id) {
        for (int i = 0; i < listBRLB.size(); i++) {
            if (id.equals(listBRLB.get(i).getBINGRENZYID())) {
                brlb_position = i;
                log.e(brlb_position + "--" + listBRLB.get(i).getBINGRENZYID());
                app.setChoosebr(i);
                break;
            }
        }

        gg_id = id;
//        progressDialog.show();
        list_yizhu = null;
        list_yizhu = new ArrayList<>();
        zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("0400101")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(gg_id + "¤" + time1 + "¤" + time2)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("查询中...")
                .build();
        log.e("网络参数" + id + "¤" + time1 + "¤" + time2);
        id2 = id;
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e(data);
                if_success = true;
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{Yizhu.class});
                //Log.d("login5",data);
                parser.parse(data);
                ToastUtil.showLong("查询信息成功");
            }

            @Override
            public void fail(String info) {
                if (info.equals("请重新操作！"))//断网，超时返回的结果，重新请求
                {
                    getYZ(gg_id);
                }

//                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        list = null;
        listBRLB = null;
        tabs = null;
    }

    //chang
    public String createXml(List<Yizhu> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getYiZhuZT();
        }
        //1.头部
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>" + "\r\n");
        s.append("<DS Name=\"59408853\" Num=\"1\">" + "\r\n");
        s.append("<DT Name=\"my_YiZhu\" Num=\"" + list.size() + "\">" + "\r\n");
        //2.中部
        for (int j = 0; j < list.size(); j++) {
            Field[] fields = list.get(j).getClass().getDeclaredFields();
            s.append("<DR Name=\"56152722\" Num=\"35\">" + "\r\n");
            for (int i = 0; i < fields.length; i++) {
                s.append("<DC Name=\"" + fields[i].getName() + "\" Num=\"0\">" + fields[i].get(list.get(j)) + "</DC>" + "\r\n");
            }
            s.append("</DR>" + "\r\n");
        }
        //3.尾部
        s.append("</DT>" + "\r\n");
        s.append("</DS>");
        return s.toString();
    }


    //下面的代码都是无用的=======================================


    //像素转换
    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void sendObject(int j, int i) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("what", i);
        bundle.putInt("what2", j);
        message.setData(bundle);
        message.what = TUPIAN;
        xmlHandler.sendMessage(message);
    }

//    //刷新函数
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });
//        onRefreshListener.onRefresh();
//    }

    //手动执行
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log.e("requestCode" + requestCode + "--" + "requestCode" + requestCode);
        if (resultCode == REQUEST_CODE) {
            if (requestCode == REQUEST_CODE) {
                boolean back = data.getBooleanExtra("ok", false);
                selectTMID = data.getStringExtra("tmid");
                app.setBryz_bj("true");

                if (back) {
                    //刷新
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(true);
                        }
                    });
                    onRefreshListener.onRefresh();
                }
            }
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @OnClick({R.id.tv_zuotian, R.id.tv_jintian, R.id.tv_mingtian, R.id.tv_one, R.id.tv_two, R.id.tv_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zuotian:
                String zuoday = dateUtil.getzuoDay();
                time_choose.setText(zuoday);
                time1 = zuoday + " 00:00:00";
                time2 = zuoday + " 23:59:59";
                getYZ(gg_id);
                break;
            case R.id.tv_jintian:
                String today = dateUtil.getYear_Day();
                time_choose.setText(today);
                time1 = today + " 00:00:00";
                time2 = today + " 23:59:59";
                getYZ(gg_id);
                break;
            case R.id.tv_mingtian:
                String mingday = dateUtil.getmingDay();
                time_choose.setText(mingday);
                time1 = mingday + " 00:00:00";
                time2 = mingday + " 23:59:59";
                getYZ(gg_id);
                break;
            case R.id.tv_one:
                startactivity(HeDuiActivity.class);
                break;
            case R.id.tv_two:
                startactivity(XhcxActivity.class);
                break;
            case R.id.tv_three:
                startactivity(QitaYizhuActivity.class);
                break;
            default:
                break;
        }
    }

    private void startactivity(Class<?> cls) {
        for (int i = 0; i < listBRLB.size(); i++) {
            if (!gg_id.equals("") && listBRLB.get(i).getBINGRENZYID().equals(gg_id)) {
                app.setChoosebr(i);
                break;
            }
        }
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    private class ScanChuli {
        private List<Yizhu> list_shangchuan;
        private int tag;
        private String[] str_tm1;
        private String yzlx;

        public ScanChuli(List<Yizhu> list_shangchuan, int tag, String[] str_TM1, String yzlx) {
            this.list_shangchuan = list_shangchuan;
            this.tag = tag;
            str_tm1 = str_TM1;
            this.yzlx = yzlx;
        }

        public int getTag() {
            return tag;
        }

        public String getYzlx() {
            return yzlx == null ? "" : yzlx;
        }

        public ScanChuli invoke() {
            String[] str_TM2;
            String[] TMID;
            if (str_tm1[1].equals("4") && str_tm1[2].split(",").length > 1) {
                log.e("发药");
                // 重新组合条码
                // str_TM1[2] = "2013/12/24180001,22570893,22570894,91032";

                str_TM2 = str_tm1[2].split(",");
                String RQ = str_TM2[0].replace("/", "");
                RQ = RQ.substring(0, RQ.length() - 2);
                TMID = new String[str_TM2.length - 2];
                int j = 0;
                for (int k = 1; k < str_TM2.length - 1; k++) {
                    TMID[j] = RQ + str_TM2[k].toString();
                    j++;
                }
                if (TMID.length > 0) {
                    for (String s : TMID) {
                        log.e("扫描到的条码组合--" + s);
                        if (s == null) {
//                            Toast.makeText(BryzBbenActivity.this, "ojbk1" + s, Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(BryzBbenActivity.this,"ojbk2"+s,Toast.LENGTH_SHORT).show();
                            int po = 0;
                            for (Yizhu x : list_yizhu) {
//                                    System.out.println(x.getTiaoMaID());
                                log.e((po += 1) + "--" + s + "----" + x.getTiaoMaID() + "--" + x.getYiZhuMC());
                                if (s.equals(x.getTiaoMaID())) {
                                    //Toast.makeText(BryzBbenActivity.this, "abc" + x.getTiaoMaID(), Toast.LENGTH_SHORT).show();
                                    list_shangchuan.add(x);
                                    tag = 1;
                                    yzlx = "4";
                                    indexFL = 0;
                                    break;
                                }
                            }
                        }
                    }
                }

            } else {
                log.e("医嘱");
                for (Yizhu x : list_yizhu) {
                    if (str_tm1[2].trim().equals(x.getTiaoMaID())) {
                        list_shangchuan.add(x);
                        tag = 1;
                        indexFL = 1;
                        break;
                    }
                }
            }
            return this;
        }
    }
}
