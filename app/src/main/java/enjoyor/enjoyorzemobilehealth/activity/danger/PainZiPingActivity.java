package enjoyor.enjoyorzemobilehealth.activity.danger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.RcyMoreAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.AllWXYS;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import my_network.NetWork;
import my_network.ZhierCall;

//import enjoyor.enjoyorzemobilehealth.adapter.RcyAdapter;

/**
 * 疼痛程度评估(自评)
 */
public class PainZiPingActivity extends Activity {
    @BindView(R.id.iv_colse2)
    ImageView ivColse2;
    @BindView(R.id.tv_home_title2)
    TextView tvHomeTitle2;
    @BindView(R.id.tv_commit2)
    TextView tvCommit2;
    @BindView(R.id.tv_score)
    EditText tvScore;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.tv_fengxian)
    TextView tvFengxian;
    @BindView(R.id.rc_ziping)
    RecyclerView rcZiping;
    @BindView(R.id.activity_danger_add2)
    LinearLayout activityDangerAdd2;
    @BindView(R.id.sj)
    TextView sj;
    private String shijian;
    private SimpleDateFormat format;
    private String result = Constant.ZIPING;
    private String f;
    private DateUtil dateUtil;
    private String date;
    private String sfm;
    private Intent intent;
    private String tag;
    private String updataFen;
    private String itemId;
    private String inPfxx;
    private String lastPfMsg="";
    private String uesrid;
    private String brzyid;
    private String rydate;
    private String name;
    private String brid;
    private String sex;
    private String age;
    private String bqmc;
    private String pingguren;
    private String bqId;
    private String[] pfxxTags = new String[]{"", "", "", "", "", "", ""};
    private String[] contents = new String[]{"", "", "", "", "", "", ""};
    private String[] buwei =new String[]{"HuLiJi","N","C","B","S","W","BU","G","SH","UA","F","HA","T","CA","A","FQ","Q"};
    private String[] xingzhi =new String[]{"TH","FR","D","S","SH","P","B","R","DP","O"};
    private String[] zhengzhuang =new String[]{"N","V","C","CF","A","I","T","S","BV","HuLiJi","BL","D","O"};
    private String[] guilv =new String[]{"C","I"};
    private String[] yaowu =new String[]{"A","B"};
    private String[] wuli =new String[]{"BP","HuLiJi","C","M","O"};
    private String[] yindao =new String[]{"D","GT","R","E","S","RT","O"};
    private String pfContent = "";
    private CenterDialog centerDialog;
    private ZhierCall zhierCall;
    private RcyMoreAdapter adapter_rec;
    private String pfDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_add2);
        ButterKnife.bind(this);
        init();
        initCanShu();
        Data();
        initListener();
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.hui), 0);
        f = NetWork.SEPARATE;
        dateUtil = DateUtil.getInstance();
        date = dateUtil.getDate();
        sfm = date.substring(date.length() - 8, date.length());
    }

    private void initCanShu() {
        intent = getIntent();
        String title = intent.getStringExtra("title");
        tvHomeTitle2.setText(title);
        tag = intent.getStringExtra(Constant.TAG);
        if (tag.equals("add")) {
            shijian = dateUtil.getDate();
            tvScore.setSelection(tvScore.length());
        } else if (tag.equals("updata")) {
            AllWXYS bean = (AllWXYS) intent.getSerializableExtra("bean");
            String fenxian = bean.getJB();
            shijian = bean.getJLSJ();
            updataFen = bean.getZPF();
            itemId = bean.getID();
            inPfxx = bean.getFZxx();
            pfDetails = bean.getITEMDetails();//评分内容
            //判断是否一样
            lastPfMsg = inPfxx;

            initPfxx();   //评分信息处理
            Log.e("信息", inPfxx + "--" + itemId);

            tvFengxian.setText(fenxian);
            tvScore.setText(updataFen);
            tvScore.setSelection(tvScore.length());
            getCurDate(fenxian.toString().trim());
        }
        sj.setText(shijian);
        uesrid = (String) SaveUtils.get(this, Constant.USERID, "");//用户登录ID
        brzyid = getIntent().getStringExtra(Constant.BRZYID);//病人住院ID
        rydate = getIntent().getStringExtra(Constant.RYSJ);//病人入院时间
        name = getIntent().getStringExtra(Constant.BRNAME);//病人姓名
        brid = getIntent().getStringExtra(Constant.BRID);//病人id
        sex = getIntent().getStringExtra(Constant.SEX);//病人性别
        age = getIntent().getStringExtra(Constant.AGE);//病人年龄
        bqmc = (String) SaveUtils.get(this, Constant.BQMC, "");//病区名称
        MyApplication instance = MyApplication.getInstance();

//        loginid = instance.getYhgh();//用户工号，登录ID 1000
        pingguren = instance.getYhxm();//评估人姓名
        bqId = instance.getBqdm();//病区ID
    }

    private void initPfxx() {
        if(pfDetails.equals("")){
            pfDetails="#####";
        }
        String[] details = pfDetails.split("#",-1);
        String pf1 = inPfxx.substring(0, 33);//17
        String pf2 = inPfxx.substring(34, 53);//10
        String pf3 = inPfxx.substring(54, 79);//13
        String pf4 = inPfxx.substring(80, 83);//2
        String pf5 = inPfxx.substring(84, 87);//2
        String pf6 = inPfxx.substring(88, 97);//5
        String pf7 = inPfxx.substring(98, inPfxx.length());//7

        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
            if (split1[i].equals("1")) {
                pfxxTag1[i] = "1#";
            } else {
                pfxxTag1[i] = "0#";
            }
        }
        String s1 = "";
        for (int j = 0; j < pfxxTag1.length; j++) {
            s1 += pfxxTag1[j];
        }
        pfxxTags[0] = s1;
        contents[0] = details[0];
        /////////2
        String[] split2 = pf2.split("#");
        String[] pfxxTag2 = new String[split2.length];
        for (int i = 0; i < split2.length; i++) {
            if (split2[i].equals("1")) {
                pfxxTag2[i] = "1#";
            } else {
                pfxxTag2[i] = "0#";
            }
        }
        String s2 = "";
        for (int j = 0; j < pfxxTag2.length; j++) {
            s2 += pfxxTag2[j];
        }
        pfxxTags[1] = s2;
        contents[1] = details[1];

        /////////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
            if (split3[i].equals("1")) {
                pfxxTag3[i] = "1#";
            } else {
                pfxxTag3[i] = "0#";
            }
        }
        String s3 = "";
        for (int j = 0; j < pfxxTag3.length; j++) {
            s3 += pfxxTag3[j];
        }
        pfxxTags[2] = s3;
        contents[2] = details[2];

        /////////4
        String[] split4 = pf4.split("#");
        String[] pfxxTag4 = new String[split4.length];
        for (int i = 0; i < split4.length; i++) {
            if (split4[i].equals("1")) {
                pfxxTag4[i] = "1#";

            } else {
                pfxxTag4[i] = "0#";
            }
        }
        String s4 = "";
        for (int j = 0; j < pfxxTag4.length; j++) {
            s4 += pfxxTag4[j];
        }
        pfxxTags[3] = s4;
        contents[3] = details[3];
        Log.e("fen", s4);
        /////////5
        String[] split5 = pf5.split("#");
        String[] pfxxTag5 = new String[split5.length];
        for (int i = 0; i < split5.length; i++) {
            if (split5[i].equals("1")) {
                pfxxTag5[i] = "1#";
            } else {
                pfxxTag5[i] = "0#";
            }
        }
        String s5 = "";
        for (int j = 0; j < pfxxTag5.length; j++) {
            s5 += pfxxTag5[j];
        }
        pfxxTags[4] = s5;
        contents[4] = details[4];
        /////////6
        String[] split6 = pf6.split("#");
        String[] pfxxTag6 = new String[split6.length];
        for (int i = 0; i < split6.length; i++) {
            if (split6[i].equals("1")) {
                pfxxTag6[i] = "1#";
            } else {
                pfxxTag6[i] = "0#";
            }
        }
        String s6 = "";
        for (int j = 0; j < pfxxTag6.length; j++) {
            s6 += pfxxTag6[j];
        }
        pfxxTags[5] = s6;
        contents[5] = details[5];
        /////////7
        String[] split7 = pf7.split("#");
        String[] pfxxTag7 = new String[split7.length];
        for (int i = 0; i < split7.length; i++) {
            if (split7[i].equals("1")) {
                pfxxTag7[i] = "1#";
            } else {
                pfxxTag7[i] = "0#";
            }
        }
        String s7 = "";
        for (int j = 0; j < pfxxTag7.length; j++) {
            s7 += pfxxTag7[j];
        }
        pfxxTags[6] = s7;
        contents[6] = details[6];
        Log.e("展示每个评分信息", pfxxTags[0] + "   " + pfxxTags[1] + "   " + pfxxTags[2] + "   " + pfxxTags[3] + "   " + pfxxTags[4] + "   " + pfxxTags[5] + "   " + pfxxTags[6]);
    }

    private void getCurDate(String fxjb) {
        String sfm = shijian.substring(shijian.length() - 8, shijian.length());
        if (fxjb.equals("无痛")) {
            String afterWeek = dateUtil.afterOtherWeek(shijian);
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("轻度疼痛")) {
            String day = dateUtil.afterOtherDay(shijian);
            date = day + " " + sfm;
        } else if (fxjb.equals("中度疼痛")) {
            date = dateUtil.getOther12Hour(shijian);
        } else if (fxjb.equals("重度疼痛")) {
            date = dateUtil.getOther4Hour(shijian);
        }
    }

    private void Data() {
        YaChuangActivity da = new YaChuangActivity();
        LinearLayoutManager manager = new LinearLayoutManager(PainZiPingActivity.this);
        rcZiping.setLayoutManager(manager);
        adapter_rec = new RcyMoreAdapter(PainZiPingActivity.this, R.layout.gv_item_cbmore, da.json(result), tag, inPfxx);
        rcZiping.setAdapter(adapter_rec);
    }


    private void initListener() {
        //评分tag和分值
        adapter_rec.setCheckListener(new RcyMoreAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String pfxxTag, int sorce, int position, String title, String content) {
                switch (title) {
                    case "部位":
                        pfxxTags[0] = pfxxTag;
                        contents[0] = content;
                        break;
                    case "性质":
                        pfxxTags[1] = pfxxTag;
                        contents[1] = content;
                        break;
                    case "伴随症状":
                        pfxxTags[2] = pfxxTag;
                        contents[2] = content;
                        break;
                    case "疼痛规律":
                        pfxxTags[3] = pfxxTag;
                        contents[3] = content;
                        break;
                    case "药物治疗":
                        pfxxTags[4] = pfxxTag;
                        contents[4] = content;
                        break;
                    case "物理治疗":
                        pfxxTags[5] = pfxxTag;
                        contents[5] = content;
                        break;
                    case "引导治疗":
                        pfxxTags[6] = pfxxTag;
                        contents[6] = content;
                        break;
                    default:
                        break;
                }
                if(pfxxTags[0].equals("")){
                    pfxxTags[0]="0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0";
                }
                if(pfxxTags[1].equals("")){
                    pfxxTags[1]="#0#0#0#0#0#0#0#0#0#0";
                }
                if(pfxxTags[2].equals("")){
                    pfxxTags[2]="#0#0#0#0#0#0#0#0#0#0#0#0#0";
                }
                if(pfxxTags[3].equals("")){
                    pfxxTags[3]="#0#0";
                }
                if(pfxxTags[4].equals("")){
                    pfxxTags[4]="#0#0";
                }
                if(pfxxTags[5].equals("")){
                    pfxxTags[5]="#0#0#0#0#0";
                }
                if(pfxxTags[6].equals("")){
                    pfxxTags[6]="#0#0#0#0#0#0#0";
                }
                lastPfMsg = pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4] + pfxxTags[5] + pfxxTags[6];
                pfContent = contents[0] + "#" + contents[1] + "#" + contents[2] + "#" + contents[3] + "#" + contents[4] + "#" + contents[5] + "#" + contents[6];
                Log.e("pfxxTags", lastPfMsg + "   " + pfContent);
            }
        });
        tvScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = s.toString().trim();
                if (trim.equals("") || trim.equals(null)) {
                    return;
                }
                if (trim.length()==2&&trim.startsWith("0")) {
                    trim = trim.substring(1, 2);
                    tvScore.setText(trim);
                    tvScore.setSelection(tvScore.length());
                }
                int sorce = Integer.parseInt(trim);
                if (sorce > 10) {
                    ToastUtils.makeToast(PainZiPingActivity.this, "输入范围1-10分");
                    tvScore.setText("");
                    sorce = 0;
                    return;
                }
                if (sorce == 0) {
                    tvFengxian.setText("无痛");
                } else if (sorce >= 1 && sorce <= 3) {
                    tvFengxian.setText("轻度疼痛");
                } else if (sorce >= 4 && sorce <= 6) {
                    tvFengxian.setText("中度疼痛");
                } else if (sorce >= 7 && sorce <= 10) {
                    tvFengxian.setText("重度疼痛");
                }
                getCurDate(tvFengxian.getText().toString());
            }
        });
    }


    @OnClick({R.id.iv_colse2, R.id.tv_commit2,R.id.sj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sj:
                dateUtil = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/M/d HuLiJi:mm:ss");
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(PainZiPingActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        shijian =format.format(date);
                        sj.setText(shijian);
                    }
                }).setDate(dateUtil.getCalendarSfm(sj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择评估日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(TimePickerView.Type.ALL)
                        //.isDialog(true)
                        .build();
                pvTime.show();
                break;
            case R.id.iv_colse2:
                finish();
                break;
            case R.id.tv_commit2:

                if (tag.equals("add")) {
                    /*if (lastPfMsg.endsWith("#")) {
                        lastPfMsg = lastPfMsg.substring(0, lastPfMsg.length() - 1);
                        Log.e("-----", lastPfMsg);
                    }*/

                    /*String[] split = lastPfMsg.split("#");
                    int num = 0;
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].equals("1")) {
                            num++;
                        }
                    }*/
                    if (tvScore.getText().toString().equals("")) {
                        ToastUtils.makeToast(PainZiPingActivity.this, "请输入合理的分值");
                        return;
                    } else {
                        centerDialog = new CenterDialog(PainZiPingActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(PainZiPingActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();
                    }
                } else if (tag.equals(Constant.UPDATA)) {
                    if (lastPfMsg.endsWith("#")) {
                        lastPfMsg = lastPfMsg.substring(0, lastPfMsg.length() - 1);
                        Log.e("-----", lastPfMsg);
                    }
                    if (inPfxx.equals(lastPfMsg) && tvScore.getText().toString().trim().equals(updataFen)) {
                        Log.e("是否一样", inPfxx + "=" + lastPfMsg);
                        ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
                        return;
                    } else {
                        centerDialog = new CenterDialog(PainZiPingActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(PainZiPingActivity.this);
                                    netWorkUpdata();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();
                    }
                }
                break;
        }
    }

    /**
     * 网络修改传参
     */
    private void netWorkUpdata() {
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
        }
        lastPfMsg = lastPfMsg.replaceAll("##", "#");
        String allscore = tvScore.getText().toString().trim();
        String fenxian = tvFengxian.getText().toString().trim();
        String content="";
        if(lastPfMsg==""){
            lastPfMsg="0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0¤";
            content="######";
        }else{


            String str =lastPfMsg.replace("#","");
            String str1=str.substring(0,17);
            String str2=str.substring(17,27);
            String str3=str.substring(27,40);
            String str4=str.substring(40,42);
            String str5=str.substring(42,44);
            String str6=str.substring(44,49);
            String str7=str.substring(49,56);
            for(int i=0;i<str1.length();i++){
                if(str1.substring(i,i+1).equals("1")){
                    content=content+"("+buwei[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str2.length();i++){
                if(str2.substring(i,i+1).equals("1")){
                    content=content+"("+xingzhi[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str3.length();i++){
                if(str3.substring(i,i+1).equals("1")){
                    content=content+"("+zhengzhuang[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str4.length();i++){
                if(str4.substring(i,i+1).equals("1")){
                    content=content+"("+guilv[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str5.length();i++){
                if(str5.substring(i,i+1).equals("1")){
                    content=content+"("+yaowu[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str6.length();i++){
                if(str6.substring(i,i+1).equals("1")){
                    content=content+"("+wuli[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str7.length();i++){
                if(str7.substring(i,i+1).equals("1")){
                    content=content+"("+yindao[i]+")";
                }
            }
        }

        String canshu = brzyid + f + shijian + f + allscore + f + fenxian + f + "" + f + pingguren + f + shijian + f + "5" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + "" + f + content + f + itemId;
        ;
        Log.e("网络修改传参", canshu);
        //网络参数设置
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_UPDATA)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.e("数据", data);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "修改成功");
            }

            @Override
            public void fail(String info) {
                Log.e("fail", info);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "修改失败");
            }
        });
    }

    /**
     * 网络添加传参
     */
    private void netWorkAdd() {
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
        }

        lastPfMsg = lastPfMsg.replaceAll("##", "#");
        String allscore = tvScore.getText().toString().trim();
        String fenxian = tvFengxian.getText().toString().trim();
        String content="";
        if(lastPfMsg==""){
            lastPfMsg="0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0¤";
            content="######";
        }else{

            String str =lastPfMsg.replace("#","");
            String str1=str.substring(0,17);
            String str2=str.substring(17,27);
            String str3=str.substring(27,40);
            String str4=str.substring(40,42);
            String str5=str.substring(42,44);
            String str6=str.substring(44,49);
            String str7=str.substring(49,56);
            for(int i=0;i<str1.length();i++){
                if(str1.substring(i,i+1).equals("1")){
                    content=content+"("+buwei[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str2.length();i++){
                if(str2.substring(i,i+1).equals("1")){
                    content=content+"("+xingzhi[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str3.length();i++){
                if(str3.substring(i,i+1).equals("1")){
                    content=content+"("+zhengzhuang[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str4.length();i++){
                if(str4.substring(i,i+1).equals("1")){
                    content=content+"("+guilv[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str5.length();i++){
                if(str5.substring(i,i+1).equals("1")){
                    content=content+"("+yaowu[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str6.length();i++){
                if(str6.substring(i,i+1).equals("1")){
                    content=content+"("+wuli[i]+")";
                }
            }
            content=content+"#";
            for(int i=0;i<str7.length();i++){
                if(str7.substring(i,i+1).equals("1")){
                    content=content+"("+yindao[i]+")";
                }
            }
        }

        String canshu = brzyid + f + shijian + f + allscore + f + fenxian + f + "" + f + pingguren + f + shijian + f + "5" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + "" + f + content;
        Log.e("网络添加传参", canshu);
        //网络参数设置
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_COMMIT)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                Log.e("数据", data);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "保存成功");
            }

            @Override
            public void fail(String info) {
                Log.e("fail", info);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "保存失败");
            }
        });
    }

    /**
     * 添加时间提醒
     */
    private void netWorkTime() {
        getCurDate(tvFengxian.getText().toString().trim());
        //一共4个参数：病人住院ID，病区ID，评估内容，提醒时间
//        示例：20111201¤0404¤压疮¤2017/6/14 15:33:24
        String canshu = brzyid + f + bqId + f + "疼痛程度评估(自评)" + f + date;
        //网络参数设置
        Log.e("添加时间提醒", canshu);
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_TIME)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                Log.e("数据", "添加提醒成功");
            }

            @Override
            public void fail(String info) {
                Log.e("fail", info + "添加提醒成功");
            }
        });
    }
}