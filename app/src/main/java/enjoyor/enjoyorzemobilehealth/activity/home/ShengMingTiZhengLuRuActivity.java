package enjoyor.enjoyorzemobilehealth.activity.home;

import android.app.AlertDialog;
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
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.adapter.ShengMingTiZhengLuRuAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.CanShu;
import enjoyor.enjoyorzemobilehealth.entities.ContentBean;
import enjoyor.enjoyorzemobilehealth.entities.KongJian;
import enjoyor.enjoyorzemobilehealth.entities.MoKuaiFenLei;
import enjoyor.enjoyorzemobilehealth.entities.ShengMingTiZhengLuRuBean;
import enjoyor.enjoyorzemobilehealth.entities.ValueSMTZKongJian;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.CreateXmlUtil;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.SPUtil;
import enjoyor.enjoyorzemobilehealth.views.ConfirmAndCancelDialog;
import enjoyor.enjoyorzemobilehealth.views.DateTimeDialogOnlyYMD;
import enjoyor.enjoyorzemobilehealth.views.MyTimePicker;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/2/4.
 */

public class ShengMingTiZhengLuRuActivity extends BaseActivity implements View.OnClickListener, DateTimeDialogOnlyYMD.MyOnDateSetListener, MyTimePicker.MyOnTimeSetListener, ScanFactory.FragScan {
    @BindView(R.id.lv_smtz_content)
    ListView mContentLV;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_smtzlr_look)
    TextView tvSmtzlrLook;

    //public KeyboardView keyboardView;
    private Context context;
    private ImageView mIvBack;
    private ImageView mIvTouXiang;
    private TextView mTvPatientName;
    private TextView mTvChuangHao;
    private TextView mTvYMD;
    private TextView mTvHM;

    private String yhid;
    private String bqdm;
    private String brzyid;
    private String brname;

//    private String name;
//    private String bedNum;

    private static final int REQUEST_CODE = 1; // 请求码
    private int selectPos = 0;
    private MyApplication myApplication;

    private static final String SMTZ_KONGJIAN_DATA = "smtz_kongjian_data";
    private DateTimeDialogOnlyYMD mDateTimeDialogOnlyYMD;
    private MyTimePicker myTimePicker;
    // 日期格式化工具
    //private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    private String[] selectTime = new String[]{"03:00", "07:00", "11:00", "15:00", "19:00", "23:00"};
    private List<ShengMingTiZhengLuRuBean> mLVDatas;
    private List<ContentBean> contentChangLuXiang;
    private List<ContentBean> contentQiTaXiang;
    private List<ContentBean> totalXiang;
    private List<ValueSMTZKongJian> getValueList;

    private ShengMingTiZhengLuRuAdapter mAdatper;


    List<MoKuaiFenLei> moKuaiFenLeiList = new ArrayList<>();
    List<KongJian> kongJianList = new ArrayList<>();
    List<CanShu> canShuList = new ArrayList<>();

    private List<ContentBean> tempContentChangLuXiang = new ArrayList<>();
    private List<ContentBean> tempContentQiTaXiang = new ArrayList<>();

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


    MyXmlHandler getKongJianDataHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    //解析好后的操作
                    Log.i("Data", "moKuaiFenLeiList大小" + moKuaiFenLeiList.size());
                    Log.i("Data", "kongJianList大小" + kongJianList.size());
                    Log.i("Data", "canShuList大小" + canShuList.size());
                    String str = "canShu内容：";
                    for (int i = 0; i < canShuList.size(); i++) {
                        CanShu canShu = canShuList.get(i);
                        str = str + canShu.getJiChuXiangMuID() + "---" + canShu.getCanShuZhi();
                    }
                    Log.i("Data", str);
                    for (int i = 0; i < kongJianList.size(); i++) {
                        KongJian kongJian = kongJianList.get(i);
                        Log.i("Data", kongJian.getKongJianMC() + "------" + kongJian.getKongJianLX());
                        //totalXiang.add(new ContentBean(kongJian.getKongJianMC(), kongJian.getZhiDanWei(), kongJian.getJiChuXiangMuID(),kongJian.getMoKuaiFLID()));
                        //Log.i("Data", totalXiang.size() + "");
                        if (kongJian.getMoKuaiFLID().equals("2")) {
                            //常录项
                            contentChangLuXiang.add(new ContentBean(kongJian.getKongJianMC(), kongJian.getKongJianLX(), kongJian.getZhiDanWei(), kongJian.getJiChuXiangMuID(), kongJian.getMoKuaiFLID()));
                        } else if (kongJian.getMoKuaiFLID().equals("3")) {
                            //其它项
                            contentQiTaXiang.add(new ContentBean(kongJian.getKongJianMC(), kongJian.getKongJianLX(), kongJian.getZhiDanWei(), kongJian.getJiChuXiangMuID(), kongJian.getMoKuaiFLID()));
                        }
                    }
                    //Log.i("Data", contentChangLuXiang.size() + "");
                    //Log.i("Data", contentQiTaXiang.size() + "");
                    for (int i = 0; i < moKuaiFenLeiList.size(); i++) {
                        if (moKuaiFenLeiList.get(i).getMoKuaiFLID().equals("2")) {
                            mLVDatas.add(new ShengMingTiZhengLuRuBean(moKuaiFenLeiList.get(i).getMoKuaiFLMC(), contentChangLuXiang));
                        } else if (moKuaiFenLeiList.get(i).getMoKuaiFLID().equals("3")) {
                            mLVDatas.add(new ShengMingTiZhengLuRuBean(moKuaiFenLeiList.get(i).getMoKuaiFLMC(), contentQiTaXiang));
                        }
                    }
                    //Log.i("Data", mLVDatas.size() + "");
                    //给ListView设置适配器
                    mAdatper = new ShengMingTiZhengLuRuAdapter(ShengMingTiZhengLuRuActivity.this, mLVDatas, canShuList);
                    mContentLV.setAdapter(mAdatper);

                    //初始化控件的初始值
                    initValue();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            moKuaiFenLeiList.add((MoKuaiFenLei) msg.obj);
                            //Log.i("Data",moKuaiFenLeiList.size()+"");
                            break;
                        case 1:
                            kongJianList.add((KongJian) msg.obj);
                            //Log.i("Data",kongJianList.size()+"");
                            break;
                        case 2:
                            canShuList.add((CanShu) msg.obj);
                            //Log.i("Data",canShuList.size()+"");
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

    MyXmlHandler getKongJianValueHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    Log.i("Data", "getValueList大小" + getValueList.size());
                    //contentChangLuXiang.clear();
                    // contentQiTaXiang.clear();
                    //mLVDatas.clear();
                    for (int i = 0; i < getValueList.size(); i++) {
                        ValueSMTZKongJian valueBean = getValueList.get(i);
                        Log.i("Data", "名称" + valueBean.getKongJianMC());
                        String id = "";
                        String smtzId = "";
                        for (int j = 0; j < kongJianList.size(); j++) {
                            KongJian kongJian = kongJianList.get(j);
                            Log.i("Data", "名称" + kongJian.getKongJianMC());
                            if (valueBean.getKongJianMC().equals(kongJian.getKongJianMC())) {
                                id = kongJian.getMoKuaiFLID();
                                smtzId = valueBean.getShengMingTZID();
                                Log.i("Data", "id" + id);
                                break;
                            }
                        }
                        //ContentBean addBean=new ContentBean(valueBean.getKongJianMC(), valueBean.getShuZhiDW(), valueBean.getJiChuXiangMuID(),id,valueBean.getShuZhi1());
                        if (TextUtils.equals(id, "2")) {
                            //contentChangLuXiang.add(addBean);
                            for (int k = 0; k < contentChangLuXiang.size(); k++) {
                                ContentBean bean = contentChangLuXiang.get(k);
                                if (valueBean.getKongJianMC().equals(bean.getKongJianMC())) {
                                    bean.setContentValue(valueBean.getShuZhi1());
                                    bean.setShengMingTZID(smtzId);
                                    Log.i("Data", "数值" + bean.getContentValue());
                                }
                            }
                        } else if (TextUtils.equals(id, "3")) {
                            //contentQiTaXiang.add(addBean);
                            for (int k = 0; k < contentQiTaXiang.size(); k++) {
                                ContentBean bean = contentQiTaXiang.get(k);
                                if (valueBean.getKongJianMC().equals(bean.getKongJianMC())) {
                                    bean.setContentValue(valueBean.getShuZhi1());
                                    bean.setShengMingTZID(smtzId);
                                    Log.i("Data", "数值" + bean.getContentValue());
                                }
                            }
                        }
                    }
                    mAdatper.notifyDataSetChanged();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            ValueSMTZKongJian bean = (ValueSMTZKongJian) msg.obj;
                            if (!TextUtils.isEmpty(bean.getShengMingTZID())) {
                                getValueList.add(bean);
                            }
//                            Log.i("Data", "getValueList大小" + getValueList.size());
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

    MyXmlHandler getIfSaveSuccessHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {

        }
    };
    private TextView tv_ohter_time;//出院手术开始等时间 控件展示
    private DateUtil dateUtil;
    private String hour_minute = "";//出院手术开始等时间
    private Intent intent;
    private TextView tv_entry_age;

    private List<BRLB> listBRLB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shengmingtizhengluru);
        ButterKnife.bind(this);
        initHandler(this);

//        new Handler(Looper.getMainLooper()).post(new Runnable(){
//            @Override
//            public void run() {
//                //主线程异常拦截
//                while (true) {
//                    try {
//                        Looper.loop();//主线程异常会从这里抛出
//                    } catch (Throwable e) {
//                        log.e(context.getClass().getName() + "信息" + e.getMessage() + "--字符" + e.toString());
//                        ShowDialog.setShowErro(context,context.getClass().getName()+"信息"+e.getMessage()+"--字符"+e.toString());
////                        Toast.makeText(ShengMingTiZhengLuRuActivity.this, "主线程：" + e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
//
////拦截所有子线程
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                ShowDialog.setShowErro(context, "信息" + e.getMessage() + "--字符" + e.toString());
////                Toast.makeText(ShengMingTiZhengLuRuActivity.this, "重新操作", Toast.LENGTH_LONG).show();
//            }
//        });
        initView();
        init();


        initData();
        inittime();
        initKongJianData();
        //setRvData();
        setListener();
        initBeep();
    }

    private void inittime() {
        String finalTime = shaixuantime();
        mTvHM.setText(finalTime);

        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(this, this, true, true, true);
        myTimePicker = new MyTimePicker(this, this);
    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        dateUtil = DateUtil.getInstance();
        myApplication = MyApplication.getInstance();
        listBRLB = myApplication.getListBRLB();

        yhid = myApplication.getYhgh();
        bqdm = myApplication.getBqdm();
        selectPos = myApplication.getChoosebr();

        hour_minute = dateUtil.getHour_Minute();
        tv_ohter_time.setText(hour_minute);
        log.e(hour_minute + "位置" + selectPos);

        mLVDatas = new ArrayList<>();
        contentChangLuXiang = new ArrayList<>();
        contentQiTaXiang = new ArrayList<>();
        totalXiang = new ArrayList<>();
        getValueList = new ArrayList<>();
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
//
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
            log.e(e.getMessage() + "--" + e.toString());
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

    private void initView() {
        context = this;

        mIvBack = (ImageView) findViewById(R.id.iv_back);
        //View topBgView= LayoutInflater.from(context).inflate(RcyMoreAdapter.layout.top_bg,null);
        View topBgView = LayoutInflater.from(context).inflate(R.layout.top_bg, mContentLV, false);
        //topBgView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mIvTouXiang = (ImageView) topBgView.findViewById(R.id.iv_touxiang);
        mTvPatientName = (TextView) topBgView.findViewById(R.id.tv_patient_name);
        mTvChuangHao = (TextView) topBgView.findViewById(R.id.tv_chuanghao);
        tv_ohter_time = (TextView) topBgView.findViewById(R.id.tv_ohter_time);
        mTvYMD = (TextView) topBgView.findViewById(R.id.tv_time_ymd);
        tv_entry_age = (TextView) topBgView.findViewById(R.id.tv_entry_age);//年龄
        mTvHM = (TextView) topBgView.findViewById(R.id.tv_time_hm);
        mContentLV.addHeaderView(topBgView);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
        mIvTouXiang.setOnClickListener(this);
        mTvPatientName.setOnClickListener(this);
        tvSmtzlrLook.setOnClickListener(this);
        mTvYMD.setOnClickListener(this);
        mTvHM.setOnClickListener(this);
        tv_ohter_time.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void initData() {
        String xingBie = listBRLB.get(selectPos).getXINGBIE();
        if (TextUtils.equals(xingBie, "男")) {
            mIvTouXiang.setImageResource(R.drawable.icon_men);
        } else {
            mIvTouXiang.setImageResource(R.drawable.icon_women);
        }
        brname = listBRLB.get(selectPos).getXINGMING();
        mTvPatientName.setText(brname);
        tv_entry_age.setText(listBRLB.get(selectPos).getNIANLING());
        mTvChuangHao.setText(listBRLB.get(selectPos).getCHUANGWEIHAO() + "床");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //int minute = calendar.get(Calendar.MINUTE);
        mTvYMD.setText(year + "-" + month + "-" + day);
        //mTvHM.setText(hour + ":" + minute);


    }

    /*
     * 筛选时间
     */
    private String shaixuantime() {
        long temp = 0l;
        String finalTime = "";
        //找出最接近当前的时间点
        for (int i = 0; i < selectTime.length; i++) {
            String time = mTvYMD.getText() + " " + selectTime[i];
            try {
                Date now = new Date();
                Date select = format.parse(time);
                if (i == 0) {
                    temp = Math.abs(now.getTime() - select.getTime());
                    finalTime = selectTime[i];
                } else {
                    if (temp > Math.abs(now.getTime() - select.getTime())) {
                        temp = Math.abs(now.getTime() - select.getTime());
                        finalTime = selectTime[i];
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
                log.e("ParseException日期格式转换异常" + e.toString());
            }
        }
        return finalTime;
    }


    /**
     * 初始化控件属性
     */
    private void initKongJianData() {
        //清空数据避免重复
        //moKuaiFenLeiList kongJianList canShuList contentChangLuXiang contentQiTaXiang mLVDatas
        moKuaiFenLeiList.clear();
        kongJianList.clear();
        canShuList.clear();
        contentChangLuXiang.clear();
        contentQiTaXiang.clear();
        mLVDatas.clear();

        String getSPData = myApplication.getStrKJ();//SPUtil.getStringVal(ShengMingTiZhengLuRuActivity.this, SMTZ_KONGJIAN_DATA, "");
        //初始化控件
        if (TextUtils.isEmpty(getSPData)) {
            Log.i("Data", "从服务端获取控件数据");
            initKongJian();
        } else {
            Log.i("Data", "从本地SP获取控件数据");
            Log.i("Data", "从本地SP获取控件数据" + getSPData);
            //解析并初始化
            if (getSPData.length() < 1763) {
                initKongJian();
            } else {
                parseData(getKongJianDataHandler, new Class[]{MoKuaiFenLei.class, KongJian.class,
                        CanShu.class}, getSPData);
            }
        }

        //注意在这里初始化控件的录入值时可能导致程序崩掉，因为初始化控件数据有可能还没完成,故放到解析完成后
        //initValue();
    }

    /**
     * 解析xml并发消息
     *
     * @param handler 消息handler
     * @param cla     bean数组
     * @param data    从服务端获得的数据
     */
    private void parseData(MyXmlHandler handler, Class[] cla, String data) {
        StringXmlParser parser = new StringXmlParser(handler, cla);
        parser.parse(data);
    }

    public String GetNowDate() {
        String temp_str = "";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        temp_str = sdf.format(dt);
        return temp_str;
    }

    /**
     * 初始化控件的值
     */
    private void initValue() {
        //清空数据源
        getValueList.clear();
        //控件的值置空操作
        for (int i = 0; i < contentChangLuXiang.size(); i++) {
            ContentBean bean = contentChangLuXiang.get(i);
            log.e("常录项bean名称" + bean.getKongJianMC());
            bean.setContentValue("");
        }
        for (int i = 0; i < contentQiTaXiang.size(); i++) {
            ContentBean bean = contentQiTaXiang.get(i);
            log.e("其它项bean名称" + bean.getKongJianMC());
            bean.setContentValue("");
        }


        brzyid = myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
        //String bingQuDM=myApplication.getListBRLB().get(selectPos).getBINGQUDM();
        mTvPatientName.setText(myApplication.getListBRLB().get(selectPos).getXINGMING());
        tv_entry_age.setText(myApplication.getListBRLB().get(selectPos).getNIANLING());
        //20111201
//            String canShu = "20111201" + NetWork.SEPARATE
//                    + mTvYMD.getText().toString() + NetWork.SEPARATE
//                    + mTvHM.getText().toString() + NetWork.SEPARATE
//                    + "0404";
        String canShu = brzyid + NetWork.SEPARATE
                + mTvYMD.getText().toString() + NetWork.SEPARATE
                + mTvHM.getText().toString() + NetWork.SEPARATE
                + bqdm;

        ZhierCall call = new ZhierCall()
                .setId(yhid)
                .setNumber("0600301")
                .setMessage(NetWork.SMTZ)
                .setCanshu(canShu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("请求中...")
                .build();
        log.e(canShu);
        //Log.i("Data", "执行了");
        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                parseData(getKongJianValueHandler, new Class[]{ValueSMTZKongJian.class}, data);
//                StringXmlParser parser = new StringXmlParser(getKongJianValueHandler,
//                        new Class[]{ValueSMTZKongJian.class});
//                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initKongJian() {
        ZhierCall call = new ZhierCall()
                .setId(yhid)
                .setNumber("0600202")
                .setMessage(NetWork.SMTZ)
                .setCanshu(bqdm)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e("初始化控件" + yhid + "---" + bqdm);
        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                //Log.i("Data", "初始化控件+++++++" + data);
                //存储控件信息到SP
                SPUtil.putStringVal(ShengMingTiZhengLuRuActivity.this, SMTZ_KONGJIAN_DATA, data);
                myApplication.setStrKJ(data);
                //解析并初始化
                parseData(getKongJianDataHandler, new Class[]{MoKuaiFenLei.class, KongJian.class,
                        CanShu.class}, data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
//                Toast.makeText(ShengMingTiZhengLuRuActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(ShengMingTiZhengLuRuActivity.this, HomePageActivity.class));
                finish();
                break;
            case R.id.tv_smtzlr_look:
                intent = new Intent(ShengMingTiZhengLuRuActivity.this, TemperatureChartActivity.class);
                intent.putExtra("position", selectPos);
                startActivity(intent);
                log.e(selectPos + "生体位置");

                break;
            case R.id.iv_touxiang:
            case R.id.tv_patient_name:
                intent = new Intent(ShengMingTiZhengLuRuActivity.this, BrlbActivity.class);
                intent.putExtra("which", "SMTZ");
//                startActivity(intent);
//                finish();
                startActivityForResult(intent, REQUEST_CODE);
//                finish();
                break;
            case R.id.tv_ohter_time:
                showDialogOhterTime();
//                mDateTimeDialogOnlyYMD.hideOrShow();
                break;
            case R.id.tv_time_ymd:
                mDateTimeDialogOnlyYMD.hideOrShow();
                break;
            case R.id.tv_time_hm:
                myTimePicker.hideOrShow();
                break;
            case R.id.btn_save:
                //弹出确定取消对话框
                final ConfirmAndCancelDialog dialog = new ConfirmAndCancelDialog(context);
                dialog.setOnConfirmOrCancelClickListener(new ConfirmAndCancelDialog.OnConfirmOrCancelClickListener() {
                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        //保存数据
                        saveData();
                    }
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
//                saveData();
                break;
        }
    }

    /*
     * 选择出院及手术开始类似的时间*/
    private void showDialogOhterTime() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(ShengMingTiZhengLuRuActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                hour_minute = format.format(date);
                tv_ohter_time.setText(hour_minute);
                //commitData();
            }
        }).setDate(dateUtil.getCalendar(tv_ohter_time.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                .setTitleText("40-42文字时间")//标题文字
                .setType(TimePickerView.Type.HOURS_MINS)//默认全部显示
                .gravity(Gravity.CENTER)
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .build();
        pvTime.show();
    }

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();
            mDoDecodeHandler = new Handler();
            Looper.loop();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new DoDecodeThread();
            mDoDecodeThread.start();
            log.e("onStart");
            //initSaoMiao();
        }
//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            mDoDecodeThread = new DoDecodeThread();
//            mDoDecodeThread.start();
//            initSaoMiao();
//        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(ShengMingTiZhengLuRuActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        String[] s1 = data.split("\\¤");
        Pattern pattern = Pattern.compile("^[-+]?[0-9]");

        if (s1[0].equals("st72")) {
            zxBR(s[1]);
        } else {

            Toast.makeText(ShengMingTiZhengLuRuActivity.this, "条码不正确：" + s.length, Toast.LENGTH_LONG).show();


            //Toast.makeText(BryzLianjianActivity.this,data,Toast.LENGTH_LONG).show();
        }
//            //核对病人列表并更新数据
//            checkBRLBAndUpdate(zyid);
    }

    private void zxBR(String brid) {
        try {
            List<BRLB> listBRLB = MyApplication.getInstance().getListBRLB();
            int i = 0;
            for (int j = 0; j < listBRLB.size(); j++) {
                if (brid.equals(listBRLB.get(j).getBINGRENZYID())) {
                    selectPos = j;
                    i = 1;
                    break;
                }
            }

            if (i == 0) {
                Toast.makeText(ShengMingTiZhengLuRuActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            } else {
                initData();
                initValue();
                playSuccessBeepSound();
                Toast.makeText(ShengMingTiZhengLuRuActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ShengMingTiZhengLuRuActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        totalXiang.clear();
        totalXiang.addAll(tempContentChangLuXiang);
        totalXiang.addAll(tempContentQiTaXiang);
        log.e("totalXiang的大小" + totalXiang.size());
        String tag = "";
        for (int i = 0; i < totalXiang.size(); i++) {
            tag = tag + totalXiang.get(i).getPanDuanBZ() + "---";
        }
        log.e("totalXiang判断标志" + tag);
//        for(int i=0;i<totalXiang.size();i++){
//            if(totalXiang.get(i).getPanDuanBZ().equals("2")){
//                totalXiang.remove(totalXiang.get(i));
//            }
//        }
        if (totalXiang != null && totalXiang.size() > 0) {
            Iterator iterator = totalXiang.iterator();
            while (iterator.hasNext()) {
                ContentBean bean = (ContentBean) iterator.next();
                if (bean.getPanDuanBZ().equals("2")) {
                    //移除对象
                    iterator.remove();
                }
            }
        }
        log.e("totalXiang的大小" + totalXiang.size());
        String lastTag = "";
        for (int i = 0; i < totalXiang.size(); i++) {
            lastTag = lastTag + totalXiang.get(i).getPanDuanBZ() + "---";
        }
        log.e("totalXiang判断标志" + lastTag);

        //判断是否有添加或更改
        if (totalXiang.size() > 0) {
            String curTime = dateUtil.getYear_Day();
            String result = CreateXmlUtil.createSMTZXml(totalXiang, "tab_CaoZuo", 0);
            Log.i("SaveData", result);
            String canShu = yhid + NetWork.SEPARATE
                    + mTvYMD.getText().toString() + NetWork.SEPARATE
                    + mTvHM.getText().toString() + NetWork.SEPARATE
                    + bqdm + NetWork.SEPARATE
                    + brzyid + NetWork.SEPARATE
                    + brname + NetWork.SEPARATE
                    + result
                    + NetWork.SEPARATE + hour_minute;
//            hour_minute
            log.e("保存参数" + canShu);

            ZhierCall call = new ZhierCall()
                    .setId(yhid)
                    .setNumber("0600306")
                    .setMessage(NetWork.SMTZ)
                    .setCanshu(canShu)
                    .setContext(this)
                    .setPort(5000)
                    .setDialogmes("保存中...")
                    .build();
            //Log.i("Data", "执行了");
            call.start(new NetWork.SocketResult() {
                @Override
                public void success(String data) {

                    //Toast.makeText(ShengMingTiZhengLuRuActivityBak.this, data, Toast.LENGTH_SHORT).show();
                    Toast.makeText(ShengMingTiZhengLuRuActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
//                    //页面数据置空操作
//                    for(int i=0;i<contentChangLuXiang.size();i++){
//                        ContentBean bean=contentChangLuXiang.get(i);
//                        Log.i("常录项bean名称",bean.getKongJianMC());
//                        bean.setContentValue("");
//                    }
//                    for(int i=0;i<contentQiTaXiang.size();i++){
//                        ContentBean bean=contentQiTaXiang.get(i);
//                        Log.i("其它项bean名称",bean.getKongJianMC());
//                        bean.setContentValue("");
//                    }
//                    //刷新数据
//                    mAdatper.notifyDataSetChanged();

                    //刷新页面
                    initKongJianData();
                    //清空适配器传递过来的变化的数据
                    tempContentChangLuXiang.clear();
                    tempContentQiTaXiang.clear();
                }

                @Override
                public void fail(String info) {

                    Toast.makeText(ShengMingTiZhengLuRuActivity.this, info, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ShengMingTiZhengLuRuActivity.this, "数据未更改!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(Calendar calendar) {
        Date date = calendar.getTime();
        mTvYMD.setText(mDateFormat.format(date) + "");
        //重新获取控件值
        initValue();
    }

    @Override
    public void onTimeSet(String time) {
        mTvHM.setText(time);
        //重新获取控件值
        initValue();
    }

    public void getChangeData(List<ContentBean> changeData) {
        if (changeData.get(0).getMoKuaiFenLeiID().equals("2")) {
            //this.contentChangLuXiang = changeData;
            tempContentChangLuXiang = changeData;
            String tag = "";
            for (int i = 0; i < tempContentChangLuXiang.size(); i++) {
                tag = tag + tempContentChangLuXiang.get(i).getPanDuanBZ() + "---";
            }
            Log.i("Data", "常录项判断标志" + tag);
        } else if (changeData.get(0).getMoKuaiFenLeiID().equals("3")) {
            //this.contentQiTaXiang = changeData;
            tempContentQiTaXiang = changeData;
            String tag = "";
            for (int i = 0; i < tempContentQiTaXiang.size(); i++) {
                tag = tag + tempContentQiTaXiang.get(i).getPanDuanBZ() + "---";
            }
            Log.i("Data", "其它项判断标志" + tag);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int pos = data.getIntExtra("position", 0);
                log.e("------" + pos);
                selectPos = pos;
                brzyid = myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
                brname = myApplication.getListBRLB().get(pos).getXINGMING();
                mTvPatientName.setText(brname);
                tv_entry_age.setText(myApplication.getListBRLB().get(selectPos).getNIANLING());
                mTvChuangHao.setText(myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO() + "床");
                //重新获取控件值
                initValue();
            }
        }
    }

    //m80s
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
//                playScanSuccessBeepSound();
                data = data.trim().replace("��", "¤").replace("?", "¤");
                data = data.replaceAll("陇", "¤");
                String[] s = data.split("\\*");
//                Toast.makeText(ShengMingTiZhengLuRuActivity.this, data, Toast.LENGTH_SHORT).show();
//                System.out.print(data);
                log.e("扫码数据--" + data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if (s1[0].equals("st72")) {
                    zxBR(s1[1]);
                } else {
                    playFailBeepSound();
                    Toast.makeText(ShengMingTiZhengLuRuActivity.this, "条码错误", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShengMingTiZhengLuRuActivity.this);
                builder.setMessage("无法得到扫描结果");
                builder.show();
            }
        }


    };
}
