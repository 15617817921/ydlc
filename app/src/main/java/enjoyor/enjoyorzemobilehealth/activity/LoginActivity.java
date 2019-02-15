package enjoyor.enjoyorzemobilehealth.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.entities.CaiDan;
import com.example.my_xml.entities.MyserverTime;
import com.example.my_xml.entities.YongHuXX;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import app.update.UpdateManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.T_BingQuXX;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import enjoyor.enjoyorzemobilehealth.views.ShowDialog;
import my_network.MyLogger;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * 登录界面.
 */
public class LoginActivity extends LoginSildingBaseActivity {
    ZhierCall zhierCall;
    BRLB brlb;
    List<CaiDan> caidanList;
    YongHuXX yonghuXX;
    MyserverTime myserverTime;
    List<T_BingQuXX> t_bingQuXXList = new ArrayList<>();

    EditText editText1, editText2;
    LinearLayout view;
    String s1 = "";
    String s2 = "";
    int i_ChongLianJieCS = 0;
    private static final int REQUEST_CODE_CONTACT = 101;
    @BindView(R.id.tv_banben)
    TextView tvBanben;
    private boolean isAllGranted = false;
    private boolean isNewPermissionGranted = false;
    private MyLogger log;
    private NetWork.SocketResult socketResult;
    //科室种类弹窗
    private AlertDialog.Builder alertDialog = null;
    private int isshowing = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyApplication.flag = 0;
        log = MyLogger.kLog();
        initView();
        initData();
        initHandler();

        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String string : permissions) {
                if (this.checkSelfPermission(string) != PackageManager.PERMISSION_GRANTED) {
                    isNewPermissionGranted = false;
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    isNewPermissionGranted = true;
                }
            }

            //所有敏感权限都已授权
            if (isNewPermissionGranted) {
                UpdateManager updateManager = new UpdateManager(this, "", "");
                updateManager.pd();

                editText1 = (EditText) findViewById(R.id.zhanghao);
                editText2 = (EditText) findViewById(R.id.mima);
                view = (LinearLayout) findViewById(R.id.view);

                initUser();
            }
        } else {

            UpdateManager updateManager = new UpdateManager(this, "", "");
            updateManager.pd();

//            editText1 = (EditText) findViewById(R.id.zhanghao);
//            editText2 = (EditText) findViewById(R.id.mima);
//            view = (LinearLayout) findViewById(R.id.view);
//
//            initUser();

        }
//        UpdateManager updateManager=new UpdateManager(this,"","");
//        updateManager.pd();


      /* zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0301706")
                .setMessage(NetWork.SYSTEM_MESSAGE)
                .setCanshu("8")
                .setContext(LoginActivity.this)
                .setPort(5000)
                .build();*/
        getSlidingMenu().setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                View view = getSlidingMenu().getMenu();
                EditText editText = (EditText) view.findViewById(R.id.ip);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("ip", "192.168.0.12");//第二个参数为默认值
                editText.setText(name);
            }
        });

        getSlidingMenu().setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                View view = getSlidingMenu().getMenu();
                EditText editText = (EditText) view.findViewById(R.id.ip);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("ip", "192.168.0.12");//第二个参数为默认值
                editText.setText(name);
            }
        });

        getSlidingMenu().setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("ip", "192.168.0.12");//第二个参数为默认值

                View view = getSlidingMenu().getMenu();
                EditText editText = (EditText) view.findViewById(R.id.ip);
                String ip = editText.getText().toString();

                if ((name.trim()).equals(ip.trim()) || ip.trim().equals("")) {

                } else {
                    SharedPreferences pref2 = LoginActivity.this.getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref2.edit();
                    editor.putString("ip", ip.trim());
                    editor.commit();
                }

            }
        });

        editText1 = (EditText) findViewById(R.id.zhanghao);
        editText2 = (EditText) findViewById(R.id.mima);
        editText1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);

            }
        });
        view = (LinearLayout) findViewById(R.id.view);

        initUser();

    }

    private void initHandler() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //主线程异常拦截
                while (true) {
                    try {
                        Looper.loop();//主线程异常会从这里抛出
                    } catch (Throwable e) {
                        ShowDialog.setShowErro(LoginActivity.this, this.getClass().getName() + "信息" + e.getMessage() + "--字符" + e.toString());
                        log.e("病人医嘱主线程Throwable--" + e.getMessage() + "--" + e.toString());
//                        Intent intent = new Intent(BryzBbenActivity.this, LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //清空栈里MainActivity之上的所有activty
//                        startActivity(intent);
//                        finish();

//                        try {
//                            Thread.sleep(2000);
//                            //移除当前activity
//                            MyActivityManager.getInstance().removeCurrent();
//                            //结束当前的进程
//                            android.os.Process.killProcess(android.os.Process.myPid());
//                            //结束虚拟机
////                            System.exit(0);
//                        } catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                            log.e("病人医嘱主线程InterruptedException" + ex.getMessage() + "--" + ex.toString());
//                        }
                    }
                }
            }
        });


    }

    private void initView() {


        try {
            PackageManager manager = LoginActivity.this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(LoginActivity.this.getPackageName(), 0);
            tvBanben.setText("版本号:" + info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CONTACT) {
            // 判断是否所有的权限都已经授予了
            for (int granted : grantResults) {
                if (granted != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                } else {
                    isAllGranted = true;
                }
            }

            if (isAllGranted) {
                UpdateManager updateManager = new UpdateManager(this, "", "");
                updateManager.pd();
                editText1 = (EditText) findViewById(R.id.zhanghao);
                editText2 = (EditText) findViewById(R.id.mima);
                view = (LinearLayout) findViewById(R.id.view);
                initUser();
            } else {
                Toast.makeText(LoginActivity.this, "请重新打开APP进行授权!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void initUser() {
        if (SaveUtils.contains(this, Constant.USERID) && SaveUtils.contains(this, Constant.PASSWORD)) {
            String userid = (String) SaveUtils.get(this, Constant.USERID, "");
            String password = (String) SaveUtils.get(this, Constant.PASSWORD, "");

            editText1.setText(userid);
            editText2.setText(password);
        }

    }

    private void initData() {
        brlb = new BRLB();
        caidanList = new ArrayList<CaiDan>();
        yonghuXX = new YongHuXX();
        myserverTime = new MyserverTime();
    }

    private void showProgress() {

    }


    @OnClick(R.id.login)
    public void login() {

//        if(!NetUtils.isNetAvailable(LoginActivity.this)){
//            ToastUtils.makeToast(MyApplication.getContext(), "网络开小差儿了");
//            return;
//        }
        MyApplication.getInstance().setYhgh(editText1.getText().toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘


        s1 = editText1.getText().toString();
        s2 = editText2.getText().toString();
        //网络参数设置
        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0300801")
                .setMessage(NetWork.SYSTEM_MESSAGE)
                .setCanshu(s1 + "," + s2 + ",0,20")
                .setContext(this)
                .setPort(5000)
                .setDialogmes("登录中...")
                .build();

        //Log.d("login5",data);
//Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
// login2();
        socketResult = new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                SaveUtils.put(LoginActivity.this, Constant.USERID, s1);
                SaveUtils.put(LoginActivity.this, Constant.PASSWORD, s2);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{BRLB.class, CaiDan.class, YongHuXX.class,
                                MyserverTime.class});
                //Log.d("login5",data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e(info);
                //Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
                // login2();

            }
        };
        zhierCall.start(socketResult);
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    //保存用户的工号和姓名
                    MyApplication.getInstance().setYhgh(yonghuXX.getYongHuGH());
                    MyApplication.getInstance().setYhxm(yonghuXX.getYongHuXM());

//                    System.out.print("第二次请求病区信息。。。。。。" + yonghuXX.getBuMenID());
//                    progressDialog.setMessage("获取病区信息...");

                    String s1 = editText1.getText().toString();
                    String s2 = editText2.getText().toString();

                    String number = "";//0301710
                    String id = "";//0301710
                    String buMenID = yonghuXX.getBuMenID();
                    if (buMenID.equals("1000")) {
                        id = buMenID;
                        number = "0301706";
                    } else {
                        id = yonghuXX.getYongHuGH();
                        number = "0301710";
                    }
                    log.e(number + "--" + id);
                    zhierCall = (new ZhierCall())
                            .setId(s1)
                            .setNumber(number)
                            .setMessage(NetWork.SYSTEM_MESSAGE)
                            .setCanshu(id)
                            .setContext(LoginActivity.this)
                            .setPort(5000).setDialogmes("获取病区信息...")
                            .build();
                    t_bingQuXXList = null;
                    t_bingQuXXList = new ArrayList<>();
                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {

                            StringXmlParser parser = new StringXmlParser(xmlHandler2,
                                    new Class[]{T_BingQuXX.class});
                            //Log.d("login5",data);
                            parser.parse(data);
                        }

                        @Override
                        public void fail(String info) {

                            log.e(info);
                        }
                    });
                    //progressDialog.dismiss();
                    //startActivity(new Intent(LoginActivity.this,BrlbActivity.class));
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            brlb = (BRLB) msg.obj;
                            break;
                        case 1:
                            caidanList.add((CaiDan) msg.obj);
                            break;
                        case 2:
                            yonghuXX = (YongHuXX) msg.obj;
                            //saveToApp(yonghuXX);
                            break;
                        case 3:
                            myserverTime = (MyserverTime) msg.obj;
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

    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    System.out.print("第二次请求病区信息。。。。。。" + yonghuXX.getBuMenID());

                    if (t_bingQuXXList.get(0).getBuMenID().equals("1")) {
                        /*String s = t_bingQuXXList.get(0).getBingQuDM();
                        Intent intent = new Intent(LoginActivity.this, BrlbActivity.class);
                        intent.putExtra("id", editText1.getText().toString());
                        intent.putExtra("canshu", s);
                        MyApplication.getInstance().setYhxm(editText1.getText().toString());
                        MyApplication.getInstance().setYhxm(editText2.getText().toString());
                        MyApplication.getInstance().setBqdm(t_bingQuXXList.get(0).getBingQuDM());
                        startActivity(intent);*/
                        listDialogDemo();
                    } else {
                        listDialogDemo();
                    }
                    //startActivity(new Intent(LoginActivity.this,BrlbActivity.class));
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            t_bingQuXXList.add((T_BingQuXX) msg.obj);
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

    private void listDialogDemo() {
        final String[] names = new String[t_bingQuXXList.size()];
        int i = 0;
        for (T_BingQuXX x : t_bingQuXXList) {
            names[i] = x.getBingQuMC();
            i++;
        }

        if (t_bingQuXXList.get(0).getBuMenID().equals("1")) {
            String s = t_bingQuXXList.get(0).getBingQuDM();
            Intent intent = new Intent(LoginActivity.this, BrlbActivity.class);
            intent.putExtra("id", editText1.getText().toString());
            intent.putExtra("canshu", s);
            intent.putExtra("bingqu", names[0]);

            intent.putExtra("isback", true);//加了一个标识，用于病人列表页面返回结束
            MyApplication.getInstance().setBqdm(t_bingQuXXList.get(0).getBingQuDM());
            startActivity(intent);

            SaveUtils.put(LoginActivity.this, Constant.BQMC, names[0]);
            SharedPreferences preferences3 = getSharedPreferences("init", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor3 = preferences3.edit();
            editor3.putString("bqdm", s);
            editor3.putString("id", editText1.getText().toString());
            editor3.putString("bqmc", names[0]);
            MyApplication.getInstance().setYzmc(names[0]);
            editor3.commit();

//            Toast.makeText(LoginActivity.this, names[0], Toast.LENGTH_SHORT).show();
            finish();
        } else {

            // 创建构建器
            alertDialog = new AlertDialog.Builder(LoginActivity.this)
                    .setCancelable(false).setTitle("列表对话框")
                    .setItems(names, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String s = t_bingQuXXList.get(which).getBingQuDM();
                            Intent intent = new Intent(LoginActivity.this, BrlbActivity.class);
                            intent.putExtra("id", editText1.getText().toString());
                            intent.putExtra("canshu", s);
                            intent.putExtra("bingqu", names[which]);
                            MyApplication.getInstance().setBqdm(t_bingQuXXList.get(which).getBingQuDM());

                            intent.putExtra("isback", true);//加了一个标识，用于病人列表页面返回结束
                            startActivity(intent);

                            SaveUtils.put(LoginActivity.this, Constant.BQMC, names[which]);
                            SharedPreferences preferences3 = getSharedPreferences("init", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor3 = preferences3.edit();
                            editor3.putString("bqdm", s);
                            editor3.putString("id", editText1.getText().toString());
                            editor3.putString("bqmc", names[which]);
                            MyApplication.getInstance().setYzmc(names[which]);
                            editor3.commit();

//                            Toast.makeText(LoginActivity.this, names[which], Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            isshowing = 0;
                        }
                    });
            if (isshowing != 1) {
                alertDialog.show();
            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*Log.d("login", event.getRepeatCount() + "");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           *//* ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
            am.restartPackage(getPackageName());*//*

            return true;
        }*/
        return super.onKeyDown(keyCode, event);
    }
}

