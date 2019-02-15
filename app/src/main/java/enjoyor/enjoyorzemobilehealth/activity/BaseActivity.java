package enjoyor.enjoyorzemobilehealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.receiver.MyActivityManager;
import enjoyor.enjoyorzemobilehealth.views.ShowDialog;
import my_network.MyLogger;

/**
 * 基础 Activity
 */
public class BaseActivity extends AppCompatActivity {
    protected ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private Handler handler = new Handler();
    private long time = 1000 * 120;
//    private long time = 1000 * 7200;
//    protected ProgressDialog progressDialog;
    /**
     * 所有已存在的Activity
     */
    protected static ConcurrentLinkedQueue<Activity> allActivity = new ConcurrentLinkedQueue<Activity>();
    /**
     * 同时有效的界面数量
     */
    protected static final int validActivityCount = 15;
    protected MyLogger log;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        log = MyLogger.kLog();
        mContext=this;
//        if (MyApplication.APP_STATUS != MyApplication.APP_STATUS_NORMAL) { // 非正常启动流程，直接重新初始化应用界面
//            log.e("liunianprint:  reInitApp");
//            MyApplication.reInitApp();
//            finish();
//            return;
//        } else {    // 正常启动流程
//            activity();
//            startAD();   // 子Activity初始化界面
//        }


        if (MyApplication.flag == -1) { //flag为-1说明程序被杀掉
//            protectApp();
            MyApplication.reInitApp();
            finish();
            return;
        }
        activity();
//        startAD();

    }

    protected void protectApp() {
        log.e("程序被杀死");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//清空栈里MainActivity之上的所有activty
        startActivity(intent);
        finish();
    }

    protected void initHandler(final Context context) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //主线程异常拦截
                while (true) {
                    try {
                        Looper.loop();//主线程异常会从这里抛出
                    } catch (Throwable e) {
                        try {
                            dumpExceptionToSDCard(e);
                            ShowDialog.setShowErro(context, context.getClass().getName() + "信息" + e.getMessage() + "--字符" + e.toString());
                            log.e(mContext.getClass().getName()+"--" + e.getMessage() + "--" + e.toString());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    /**
     * 文件名
     */
    public static final String FILE_NAME = "crash";
    /**
     * 异常日志 存储位置为根目录下的 Crash文件夹
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() +
            "/Crash/log/";
    /**
     * 文件名后缀
     */
    private static final String FILE_NAME_SUFFIX = ".trace";

    /**
     * 将异常信息写入SD卡
     *
     * @param e
     */
    private void dumpExceptionToSDCard(Throwable e) throws IOException {
        //如果SD卡不存在或无法使用，则无法将异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            if (DEBUG) {
//                Log.w(TAG, "sdcard unmounted,skip dump exception");
//                return;
//            }
        }
        File dir = new File(PATH);
        //如果目录下没有文件夹，就创建文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //得到当前年月日时分秒
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        //在定义的Crash文件夹下创建文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //写入时间
            pw.println(time);
            pw.println(e.getMessage());
            pw.println(e.toString());
            //写入手机信息
            dumpPhoneInfo(pw);
            pw.println();//换行
            e.printStackTrace(pw);
            pw.close();//关闭输入流
        } catch (Exception e1) {
            log.e("dump crash info failed");
        }

    }

    /**
     * 获取手机各项信息
     *
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //得到包管理器
        PackageManager pm = mContext.getPackageManager();
        //得到包对象
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //写入APP版本号
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);
        //写入 Android 版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        //CPU架构
        pw.print("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw.println(Build.SUPPORTED_ABIS);
        } else {
            pw.println(Build.CPU_ABI);
        }
    }

    /**
     * 将错误信息上传至服务器
     */
    private void uploadExceptionToServer() {

    }


    private void activity() {
        MyActivityManager.addActivity(this);
        //Activity队列管理，如果超出指定个数，获取并移除此队列的头（队列中时间最长的）。
        if (allActivity.size() >= validActivityCount) {
            Activity act = allActivity.poll();
            act.finish();// 结束
        }
        allActivity.add(this);
    }


//    public void showLoading(Activity activity, String content) {
////        DialogUtil.showProgressDialog((Activity) getApplicationContext(),content);
//        if (progressDialog != null && progressDialog.isShowing()) return;
//        progressDialog = new ProgressDialog(this);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage(content);
//        progressDialog.show();
//    }


//    public void dismissLoading() {
////        DialogUtil.closeProgressDialog();
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                handler.removeCallbacks(runnable);
//                break;
//            case MotionEvent.ACTION_UP:
//                startAD();
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }

//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            //PrefUtils.setBoolean(BaseActivity.this, "isLogin", false);
//            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
//            builder.setTitle("温馨提示")
//                    .setCancelable(false)
//                    .setMessage("当前登录已失效，请重新登录")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            startActivity(new Intent(BaseActivity.this, LoginActivity.class));
//                            finish();
//                        }
//                    });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        }
//    };

//    public void startAD() {
//        handler.removeCallbacks(runnable);
//        handler.postDelayed(runnable, time);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        dismissLoading();
    }

}
