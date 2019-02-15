package enjoyor.enjoyorzemobilehealth.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.example.my_xml.entities.BRLB;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.activity.LoginActivity;
import enjoyor.enjoyorzemobilehealth.entities.HuLiJiLuWH;
import enjoyor.enjoyorzemobilehealth.entities.KongJian;
import enjoyor.enjoyorzemobilehealth.receiver.NetStateReceiver;


/**
 * Created by youxi on 2016-9-12.
 */
public class MyApplication extends Application {
    protected static final String SEPARATE = "¤";
    private static MyApplication singleton;
    public static  int i=0;
    List<BRLB> listBRLB=new ArrayList<>();
    int choosebr=0;
    String yhxm=null;//用户姓名  评估护士人
    String yhgh=null;//登录ID，用户工号
    String listZXYZ=null;
    String changeYIZHU=null;
    String bqdm;//病区代码，ID
    String bryz_no="1";//病人医嘱代码
    String bryz_bj="false";//病人医嘱标记
    String yzmc="";
    String smtz_hm="";
    String smtz_hm2="";
    String hedui_tiaoMa="";
    private String strKJ="";

    public String getStrKJ() {
        return strKJ == null ? "" : strKJ;
    }

    public void setStrKJ(String strKJ) {
        this.strKJ = strKJ;
    }

    public String getHedui_tiaoMa() {
        return hedui_tiaoMa == null ? "" : hedui_tiaoMa;
    }

    public void setHedui_tiaoMa(String hedui_tiaoMa) {
        this.hedui_tiaoMa = hedui_tiaoMa;
    }

    List<KongJian> kongJianList = new ArrayList<>();
    List<HuLiJiLuWH> huLiJiLuWHList;
    //网络监听广播
    private NetStateReceiver mNetWorkReceiver;
    //表示是否连接
    public static boolean isConnected;
    //表示网络类型（移动数据或者wifi）移动：Moblie Wifi:Wifi
    public static String netWorkState;

    public List<HuLiJiLuWH> getHuLiJiLuWHList() {
        return huLiJiLuWHList;
    }

    public void setHuLiJiLuWHList(List<HuLiJiLuWH> huLiJiLuWHList) {
        this.huLiJiLuWHList = huLiJiLuWHList;
    }

    public List<KongJian> getKongJianList() {
        return kongJianList;
    }

    public void setKongJianList(List<KongJian> kongJianList) {
        this.kongJianList = kongJianList;
    }

    public String getSmtz_hm2() {
        return smtz_hm2;
    }

    public void setSmtz_hm2(String smtz_hm2) {
        this.smtz_hm2 = smtz_hm2;
    }

    public String getSmtz_hm() {
        return smtz_hm;
    }

    public void setSmtz_hm(String smtz_hm) {
        this.smtz_hm = smtz_hm;
    }

    public String getYzmc() {
        return yzmc;
    }

    public void setYzmc(String yzmc) {
        this.yzmc = yzmc;
    }

    public String getBryz_bj() {
        return bryz_bj;
    }

    public void setBryz_bj(String bryz_bj) {
        this.bryz_bj = bryz_bj;
    }

    public String getBryz_no() {
        return bryz_no;
    }

    public void setBryz_no(String bryz_no) {
        this.bryz_no = bryz_no;
    }

    private BRLB other_brlb=null;

    public BRLB getOther_brlb() {
        return other_brlb;
    }

    public void setOther_brlb(BRLB other_brlb) {
        this.other_brlb = other_brlb;
    }

    public String getBqdm() {
        return bqdm;
    }

    public void setBqdm(String bqdm) {
        this.bqdm = bqdm;
    }

    public String getChangeYIZHU() {
        return changeYIZHU;
    }

    public void setChangeYIZHU(String changeYIZHU) {
        this.changeYIZHU = changeYIZHU;
    }

    public String getListZXYZ() {
        return listZXYZ;
    }

    public void setListZXYZ(String listZXYZ) {
        this.listZXYZ = listZXYZ;
    }

    public String getYhgh() {
        return yhgh;
    }

    public void setYhgh(String yhgh) {
        this.yhgh = yhgh;
    }

    public String getYhxm() {
        return yhxm;
    }

    public void setYhxm(String yhxm) {
        this.yhxm = yhxm;
    }

    public int getChoosebr() {
        return choosebr;
    }

    public void setChoosebr(int choosebr) {
        this.choosebr = choosebr;
    }

    /**
     *  XML解析标记
     */
    public static final int END = 1;
    public static final int NODE = 2;
    public static final int TUPIAN = 3;
    public static Context mContext;
    //判断是否被回收
    public static int flag = -1;

    public final static int APP_STATUS_KILLED = 0; // 表示应用是被杀死后在启动的
    public final static int APP_STATUS_NORMAL = 1; // 表示应用时正常的启动流程
    public static int APP_STATUS = APP_STATUS_KILLED; // 记录App的启动状态


    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;

        mContext = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
//        setDate();
    }
    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };
    public static Context getAppContext() {
        return mContext;
    }

    /**
     * 重新初始化应用界面，清空当前Activity棧，并启动欢迎页面
     */
    public static void reInitApp() {
        Intent intent = new Intent(getAppContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getAppContext().startActivity(intent);
    }

    public void restartApp() {
//        MyLogger.kLog().e("捕获异常");
//        Intent intent = new Intent(singleton, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        singleton.startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前

    }


    public static MyApplication getInstance(){
        return singleton;
    }
    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }

    public List<BRLB> getListBRLB() {
        return listBRLB;
    }

    public void setListBRLB(List<BRLB> listBRLB) {
        this.listBRLB = listBRLB;
    }

    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

//    private void setDate(){
//        try {
//            java.lang.Process process=Runtime.getRuntime().exec("su");
//            DataOutputStream os=new DataOutputStream(process.getOutputStream());
//            os.writeBytes("date -s 20120419.024012; \n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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


}
