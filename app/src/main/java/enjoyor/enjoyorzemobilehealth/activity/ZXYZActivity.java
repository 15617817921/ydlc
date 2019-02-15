package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_xml.entities.BRLB;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.StringUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import enjoyor.enjoyorzemobilehealth.utlis.ZhuanHuanTool;
import my_network.NetWork;
import my_network.ZhierCall;

public class ZXYZActivity extends BaseActivity {
    ZhierCall zhierCall;
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.tv_zxyz_name)
    TextView tvZxyzName;
    @BindView(R.id.tv_zxyz_cishu)
    TextView tvZxyzCishu;

    private String tmid;
    private String yiZhuState;
    private Intent intent;
    private MyApplication app;
    private List<BRLB> listBRLB;
    private String yfmc = "";
    private int num = 0;
    private Yizhu yizhu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxyz);
        ButterKnife.bind(this);

        init();

        intent = getIntent();
        String no1 = intent.getStringExtra("no1");
        String no2 = intent.getStringExtra("no2");
        String danwei = intent.getStringExtra("danwei");
        tmid = intent.getStringExtra("tmid");
        yiZhuState = intent.getStringExtra("state");
        yfmc = intent.getStringExtra("yfmc");

        tvZxyzName.setText(yfmc);

        if (yfmc.contains(Constant.DIYAN) || yfmc.contains(Constant.CSX) || yfmc.contains(Constant.PRN)) {
            tvZxyzCishu.setVisibility(View.VISIBLE);

            List<Yizhu> list_xml = new ArrayList<>();
            Bundle bundle = (Bundle) getIntent().getExtras().get("bundle");
            if (bundle != null) {
                yizhu = (Yizhu) bundle.getSerializable("yizhu");
                String cishu = yizhu.getBeiZhu();
                if (StringUtils.stringNull(cishu).equals("")) {
                    tvZxyzCishu.setVisibility(View.GONE);
//                    num = 0;
//                    tvZxyzCishu.setText(Constant.CHENGYI + " 0");
                } else {
                    num = Integer.parseInt(cishu);
                    tvZxyzCishu.setText(Constant.CHENGYI + " " + cishu);
                }
                list_xml.add(yizhu);
            }
        } else {
            tvZxyzCishu.setVisibility(View.GONE);
        }

        TextView textView1 = (TextView) findViewById(R.id.ff1);
        TextView textView2 = (TextView) findViewById(R.id.ff2);
        TextView tv_zxyz_danwei = (TextView) findViewById(R.id.tv_zxyz_danwei);//单位
        textView1.setText(no1);
        textView2.setText(no2);
        tv_zxyz_danwei.setText(danwei);

        ImageView imageView1 = (ImageView) findViewById(R.id.cc1);
        ImageView imageView2 = (ImageView) findViewById(R.id.cc2);
        ImageView imageView3 = (ImageView) findViewById(R.id.cc3);
        ImageView imageView4 = (ImageView) findViewById(R.id.cc4);
        ImageView imageView5 = (ImageView) findViewById(R.id.cc5);
        ImageView imageView6 = (ImageView) findViewById(R.id.cc6);

        ArrayList<ImageView> list = new ArrayList<>();
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);
        list.add(imageView4);
        list.add(imageView5);
        list.add(imageView6);
        /*imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("xxxxxxxxxxxxxxxxxxxxxxx");
                Toast.makeText(ZXYZActivity.this,"xxxxx",Toast.LENGTH_LONG).show();
            }
        });*/
        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ZXYZActivity.this,BryzActivity.class));
                finish();
            }
        });
        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            list.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = MyApplication.getInstance().getListZXYZ();

                    if (yfmc.contains(Constant.DIYAN) || yfmc.contains(Constant.CSX) || yfmc.contains(Constant.PRN)) {
                        if (finalI == 0) {
                            int cishu = num + 1;
                            String cc = s.replaceAll("<DC Name=\"BeiZhu\" Num=\"0\">" + yizhu.getBeiZhu() + "</DC>", "<DC Name=\"BeiZhu\" Num=\"0\">" + cishu + "</DC>");
                            String bb = cc.replaceAll("<DC Name=\"YiZhuZT\" Num=\"0\">" + MyApplication.getInstance().getChangeYIZHU() + "</DC>", "<DC Name=\"YiZhuZT\" Num=\"0\">" + "0" + "</DC>");
                            String ff = bb.replaceAll("ThreeMills", "开始");
                            ff+=Constant.ONE;
                            network(ff);
                        } else {
                            ToastUtil.showLong("此医嘱只允许点击开始来增加次数!");
                        }

                    } else {
//                        final int d = finalI;
                        String x = ZhuanHuanTool.toString1(finalI);
                        int hh = 0;
                        String gg = "";
                        switch (finalI) {
                            case 0:
                                if (TextUtils.equals(yiZhuState, "6")) {
                                    ToastUtil.showLong("该医嘱已经开始执行了，不能重复点击!");
                                    hh = 0;
                                } else {
                                    hh = 6;
                                    gg = "开始";
                                }
//                          hh=6;
//                          gg="开始";
                                break;
                            case 1:
                                hh = 3;
                                gg = "暂停";
                                break;
                            case 2:
                                hh = 5;
                                gg = "继续";
                                break;
                            case 3:
                                hh = 1;
                                gg = "结束";
                                break;
                            case 4:
                                hh = 2;
                                gg = "异常中断";
                                break;
                            case 5:
                                hh = 4;
                                gg = "停用";
                                break;
                        }
                        if (TextUtils.equals(yiZhuState, "1")) {
                            ToastUtil.showLong("该医嘱已经结束执行了!");
                            hh = 0;
                            return;
                        }
                        if (hh != 0) {


                            String cc = s.replaceAll("<DC Name=\"YiZhuZT\" Num=\"0\">" + MyApplication.getInstance().getChangeYIZHU() + "</DC>", "<DC Name=\"YiZhuZT\" Num=\"0\">" + hh + "</DC>");
                            String ff = cc.replaceAll("ThreeMills", gg);
                            ff+=Constant.ONE;
                            //Toast.makeText(ZXYZActivity.this,ff,Toast.LENGTH_LONG).show();
                            List<BRLB> listBRLB = new ArrayList<>();
                            listBRLB = MyApplication.getInstance().getListBRLB();
                            String ll = listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID();
                            network(ff);
                        }
                    }
                }
            });
        }


    }

    /*
     * */
    private void network(String ff) {

        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(ff)
                .setContext(ZXYZActivity.this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        log.e(ff);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Timer timer = new Timer();
                TimerTask tast = new TimerTask() {
                    @Override
                    public void run() {
                        //startActivity(localIntent);
                    }
                };
                timer.schedule(tast, 5500);

//                          Intent intent1=new Intent(ZXYZActivity.this,BryzActivity.class);
//                          intent1.putExtra("hostory","11");
//                          //startActivity(intent1);
//                          finish();
                Intent intentToBRYZ = new Intent();
                intentToBRYZ.putExtra("ok", true);
                intentToBRYZ.putExtra("tmid", tmid);
                //intentToBRYZ.putExtra("leibie",xxx);
                setResult(RESULT_OK, intentToBRYZ);
                finish();
            }

            @Override
            public void fail(String info) {

            }
        });
    }

    private void init() {
        app = MyApplication.getInstance();
        listBRLB = app.getListBRLB();

        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvTitleInclude.setText("执行医嘱");
    }

    private void chuanCan(List<Yizhu> list_xml) {
        String xml = null;
        try {
            xml = createXml(list_xml);
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
    }

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
}
