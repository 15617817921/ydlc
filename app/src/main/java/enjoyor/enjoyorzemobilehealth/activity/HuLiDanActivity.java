package enjoyor.enjoyorzemobilehealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.adapter.HuLiDanAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.ChuLiang;
import enjoyor.enjoyorzemobilehealth.entities.FenBiao;
import enjoyor.enjoyorzemobilehealth.entities.HuLi;
import enjoyor.enjoyorzemobilehealth.entities.HuLiJiLu;
import enjoyor.enjoyorzemobilehealth.entities.HuLiJiLuWH;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMu;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;
import enjoyor.enjoyorzemobilehealth.entities.RuLiang;
import enjoyor.enjoyorzemobilehealth.entities.RuLiangAndChuLiangBean;
import enjoyor.enjoyorzemobilehealth.entities.XiangMuMX;
import enjoyor.enjoyorzemobilehealth.hulidan.fragment.ChuLiangFragment;
import enjoyor.enjoyorzemobilehealth.hulidan.fragment.JiChuXiangMuFragment;
import enjoyor.enjoyorzemobilehealth.hulidan.fragment.RuLiangFragment;
import enjoyor.enjoyorzemobilehealth.hulidan.fragment.TeShuQingKuangFragment;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.CreateXmlUtil;
import enjoyor.enjoyorzemobilehealth.views.ConfirmAndCancelDialog;
import jiexi.json.XmlToJson;
import my_network.NetWork;
import my_network.ZhierCall;
import qdh.hdl.QDHHuiLiDan;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/5/24.
 */

public class HuLiDanActivity extends AppCompatActivity implements View.OnClickListener, ScanFactory.FragScan {
    String moni="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<root><caijirq>2014-10-15</caijirq><caijisj>16:30</caijisj><yishi></yishi><tiwenlb>耳温</tiwenlb><tiwen></tiwen><maibo>80</maibo><xinlv></xinlv><huxi>18</huxi><shuzhangya>123</shuzhangya><shousuoya>78</shousuoya><yangbaohd></yangbaohd><guandaohl></guandaohl><guandaohuli><repeat/></guandaohuli><ruliang><repeat/></ruliang><chuliang><repeat/></chuliang><teshuqkycl> 患者精神略软，卧床休息，偶有恶心，无呕吐，纳差，嘱保持情绪稳定。 </teshuqkycl><jichuhl></jichuhl><zhentongbeng></zhentongbeng><tengtongpf></tengtongpf><qiekoufl></qiekoufl><zidingyi></zidingyi><zuotongkong></zuotongkong><youtongkong></youtongkong><zuoguangfs></zuoguangfs><youguangfs></youguangfs><shanghongxian></shanghongxian><xiahongxian></xiahongxian><qianming>郑勇红</qianming></root>";
    private Context context;
    private ImageView mIvBack;
    private ImageView mIvHldDetail;
    private ImageView mIvAdd;
    private ImageView mIvTouXiang;
    private TextView mTvPatientName;
    private TextView mTvChuangHao;
//    private TextView mTvHM;
//    private TextView mTvYMD;
    private TextView mTvTime;
    private Button btnSave;
    private TabLayout tabHuLiDan;
    private ViewPager vpHuLiDan;

    private static final int REQUEST_CODE = 1; // 请求码
    //选中第几个病人
    private int selectPos = 0;
    //选中第几个护理记录单号
    private int selectHLJLDHPos=0;

    private MyApplication myApplication;
    private String yhid;
    private String bingRenZYId;
    private String bingQuDM;
    private String bingRenName;
    private String chuangHao;

    private String huLiJiLuDanHao;

    //所有控件基本信息
    private List<HuLiJiLuWH> huLiJiLuWHList;
    private List<FenBiao> fenBiaoList;
    private List<HuLiJiLu> huLiJiLuList;
    private List<HuLi> huLiList;
    private List<RuLiang> ruLiangAllData;//所有入量数据
    private List<ChuLiang> chuLiangAllData;//所有出量数据

    //基础项目页面数据集
    private List<JiChuXiangMuBean> jiChuXiangMuBeanList;
    private List<JiChuXiangMuBean> ruLiangData;//入量页面数据
    private List<JiChuXiangMuBean> chuLiangData;//出量页面数据

    private List<String> caiJiTimeList;


    //从入量和出量页面接收到的list
    private List<RuLiangAndChuLiangBean> ruLiangList;
    private List<RuLiangAndChuLiangBean> chuLiangList;

    //护理单所有页面添加时总的数据集合
    private List<JiChuXiangMuBean> allHuLiDanPageAddList;

    private String[] tabs = new String[]{"基础项目", "入量", "出量", "特殊情况"};
    private List<Fragment> mFragmentList;

    private JiChuXiangMuFragment jiChuXiangMuFragment;
    private RuLiangFragment ruLiangFragment;
    private ChuLiangFragment chuLiangFragment;
    private TeShuQingKuangFragment teShuQingKuangFragment;

    private String zongJieType;//总结类型
    private String  teShuQingKuang;//特殊情况
    private String tempZongJieType;
    private String tempTeShuQingKuang;

    private boolean isAddHLJLD=true;

    private String tiaoMa;
    //扫描提示音
    private boolean playBeep=true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;
    private HuLiDanActivity.DoDecodeThread mDoDecodeThread;
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

        if(successAudioManager.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
            playBeep=false;
        }
        if(playBeep&&successMediaPlayer!=null){
            successMediaPlayer.start();
        }
    }
    /**
     * 播放beep声
     */
    private void playFailBeepSound() {
        //检查铃音模式
        if(failAudioManager.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
            playBeep=false;
        }

        if(playBeep&&failMediaPlayer!=null){
            failMediaPlayer.start();
        }
    }

    /**
     * 播放beep声
     */
    private void playScanSuccessBeepSound() {
        //检查铃音模式
        if(scanAudioManager.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
            playBeep=false;
        }
        if(playBeep&&scanMediaPlayer!=null){
            scanMediaPlayer.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new DoDecodeThread();
            mDoDecodeThread.start();
            initSaoMiao();
        }

    }

    public void initSaoMiao() {
        scanInf = new ScanFactory() {
            public void handleData(String data, int keycode) {
                super.handleData(data, keycode);

            }

            public ScanInterface getInstance(int flag, BryzActivity context) {

                return null;
            }

            public Activity getFragment() {
                return HuLiDanActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(HuLiDanActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyDown(keyCode, event, HuLiDanActivity.this.getClass().getName())) {
//
//                return true;
//
//            }
//        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyUp(keyCode, event)) {
//                return true;
//            }
//        }

        return super.onKeyUp(keyCode, event);
    }
    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        data= data.trim().replace("��", "¤").replace("?","¤");
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        String[] s1 = data.split("\\¤");
        Pattern pattern = Pattern.compile("^[-+]?[0-9]");

        if(s[0].equals("st72")){
            zxBR(s[1]);
        }
    }
    //去病人列表中核实是否有这条id，如果有去切换医嘱
    private void zxBR(String brid){
        try{
            List<BRLB> listBRLB=new ArrayList<>();
            listBRLB=MyApplication.getInstance().getListBRLB();
            int i=0;
            for(int j=0;j<listBRLB.size();j++){
                if(brid.equals(listBRLB.get(j).getBINGRENZYID())){
                    selectPos=j;
                    i=1;
                    break;
                }
            }

            if(i==0){
                Toast.makeText(HuLiDanActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            }else{
                getData();
                playSuccessBeepSound();
                Toast.makeText(HuLiDanActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(HuLiDanActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
        }
    }

    MyXmlHandler getAllDataHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if(huLiJiLuList.size()>0){
                        mTvTime.setText(huLiJiLuList.get(selectHLJLDHPos).getCaiJiSJ());
                    }
                    for(int i=0;i<huLiJiLuList.size();i++){
                        caiJiTimeList.add(huLiJiLuList.get(i).getCaiJiSJ());
                    }
                    Log.i("data", "------fenBiaoList大小" + fenBiaoList.size());
                    Log.i("data", "------fenBiaoList大小" + huLiJiLuWHList.size());
                    MyApplication.getInstance().setHuLiJiLuWHList(huLiJiLuWHList);
                    if(isAddHLJLD){
                        setEmptyPageData();
                    }else {
                        setAllPageData();
                    }
                    initTabLayout();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            break;
                        case 1:
                            //所有控件基本信息
                            //huLiJiLuWHList.add((HuLiJiLuWH) msg.obj);
                            break;
                        case 2:
                            //所有控件基本信息
                            huLiJiLuWHList.add((HuLiJiLuWH) msg.obj);
                            break;
                        case 3:
                            huLiJiLuList.add((HuLiJiLu) msg.obj);
                            break;
                        case 4:
                            fenBiaoList.add((FenBiao) msg.obj);
                            break;
                        case 5:
                            huLiList.add((HuLi) msg.obj);
                            //Log.i("data","========"+((HuLi) msg.obj).getJiLuSJ());
                            break;
                        case 6:
                            ruLiangAllData.add((RuLiang) msg.obj);
                            break;
                        case 7:
                            chuLiangAllData.add((ChuLiang) msg.obj);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hulidan);
        myApplication = MyApplication.getInstance();
        context = this;

        Intent intent=getIntent();
        selectPos=intent.getIntExtra("position",0);

        //初始化控件
        initView();
        //网络请求获取数据
        getData();
        //initTabLayout();

        Gson gson2 = new Gson();
        QDHHuiLiDan qdhHuiLiDan=gson2.fromJson((new XmlToJson.Builder(moni).build()).toJson().toString(),QDHHuiLiDan.class);
    }

    private void getData() {
        mFragmentList = new ArrayList<>();

        huLiJiLuWHList = new ArrayList<>();
        fenBiaoList = new ArrayList<>();
        huLiJiLuList = new ArrayList<>();
        huLiList=new ArrayList<>();
        ruLiangAllData=new ArrayList<>();
        chuLiangAllData=new ArrayList<>();
        ruLiangData=new ArrayList<>();
        chuLiangData=new ArrayList<>();

        caiJiTimeList=new ArrayList<>();
        jiChuXiangMuBeanList = new ArrayList<>();
        ruLiangList = new ArrayList<>();
        chuLiangList = new ArrayList<>();
        allHuLiDanPageAddList = new ArrayList<>();

        yhid=myApplication.getYhgh();
        bingRenZYId = myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
        bingQuDM = myApplication.getListBRLB().get(selectPos).getBINGQUDM();
        bingRenName = myApplication.getListBRLB().get(selectPos).getXINGMING();
        mTvPatientName.setText(bingRenName);
        String xingBie=myApplication.getListBRLB().get(selectPos).getXINGBIE();
        if(TextUtils.equals(xingBie,"男")){
            mIvTouXiang.setImageResource(R.drawable.icon_men);
        }else {
            mIvTouXiang.setImageResource(R.drawable.icon_women);
        }
        chuangHao = myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO();
        mTvChuangHao.setText(chuangHao+"床");
        Log.i("data", "------执行了");

//        String canShu = "0404" + NetWork.SEPARATE + "20111201";
        String canShu = bingQuDM + NetWork.SEPARATE + bingRenZYId;
        System.out.println("开始执行1000202方法");
        ZhierCall call = new ZhierCall()
                .setId(yhid)
                .setNumber("1000202")
                .setMessage(NetWork.HULIJIRU)
                .setCanshu(canShu)
                .setContext(context)
                .setPort(5000)
                .build();

        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.i("data", "------data" + data);
                String s=data;
                parseData(getAllDataHandler, new Class[]{XiangMuMX.class, HuLiJiLu.class,
                        HuLiJiLuWH.class,JiChuXiangMu.class
                        , FenBiao.class,
                        HuLi.class, RuLiang.class, ChuLiang.class}, data);
            }

            @Override
            public void fail(String info) {
                Log.i("data", "------info" + info);
            }
        });
    }

    /**
     * 设置各页面初始时数据
     */
    private void setAllPageData() {
        jiChuXiangMuBeanList.clear();
        ruLiangData.clear();
        chuLiangData.clear();
        huLiJiLuDanHao=huLiJiLuList.get(selectHLJLDHPos).getHuLiJiLuDanHao();
        zongJieType=huLiJiLuList.get(selectHLJLDHPos).getZongJieLX();//获取总结类型
        teShuQingKuang=huLiJiLuList.get(selectHLJLDHPos).getTeShuQingKuang();//获取特殊情况
        tempZongJieType=zongJieType;
        tempTeShuQingKuang=teShuQingKuang;

        Log.i("data","护理单号------------"+huLiJiLuDanHao);
        for(int i=0;i<fenBiaoList.size();i++){
            FenBiao fenBiao=fenBiaoList.get(i);
            JiChuXiangMuBean bean=new JiChuXiangMuBean();
            bean.setHuLiJiLuID("");
            bean.setHuLiJiLuDanHao(huLiJiLuDanHao);
            bean.setJiChuXiangMuID(fenBiao.getJiChuXiangMuID());
            bean.setJiChuXiangMuMC(fenBiao.getJiChuXiangMuMC());
            bean.setShuZhi("");
            bean.setJiLuSJ("");
            bean.setDanWei(fenBiao.getDanWei());
            bean.setCaiJiRen(myApplication.getYhxm());
            bean.setCaiJiRQ("");
            bean.setCaiJiSJ("");
            bean.setBingQuID(bingQuDM);
            bean.setBingRenZYID(bingRenZYId);
            bean.setBingRenXM(bingRenName);
            bean.setChuangHao(chuangHao);
            bean.setCaiJiRenID(yhid);
            bean.setHuanYeBZ("0");
            jiChuXiangMuBeanList.add(bean);
        }

        //给基础项目控件赋值
        for(int i=0;i<huLiList.size();i++){
            HuLi huLi=huLiList.get(i);
            if(TextUtils.equals(huLi.getHuLiJiLuDanHao(),huLiJiLuDanHao)){
                if(!TextUtils.equals(huLi.getJiChuXiangMuMC(),"入量")&&!TextUtils.equals(huLi.getJiChuXiangMuMC(),"出量")){
                    for(int j=0;j<jiChuXiangMuBeanList.size();j++){
                        JiChuXiangMuBean jiChuXiangMuBean=jiChuXiangMuBeanList.get(j);
                        if(TextUtils.equals(huLi.getJiChuXiangMuMC(),jiChuXiangMuBean.getJiChuXiangMuMC())){
                            jiChuXiangMuBean.setHuLiJiLuID(huLi.getHuLiJiLuID());
                            jiChuXiangMuBean.setShuZhi(huLi.getShuZhi());
                            break;
                        }
                    }
                }
            }
        }


        for(int i=0;i<ruLiangAllData.size();i++){
            RuLiang ruLiang=ruLiangAllData.get(i);
            if(huLiJiLuDanHao.equals(ruLiang.getHuLiJiLuDanHao())){
                JiChuXiangMuBean bean=new JiChuXiangMuBean();
                bean.setHuLiJiLuID(ruLiang.getHuLiJiLuID());
                bean.setHuLiJiLuDanHao(ruLiang.getHuLiJiLuDanHao());
                bean.setJiChuXiangMuID(ruLiang.getJiChuXiangMuID());
                bean.setJiChuXiangMuMC(ruLiang.getJiChuXiangMuMC());
                bean.setShuZhi(ruLiang.getShuZhi());
                bean.setJiLuSJ(ruLiang.getJiLuSJ());
                bean.setDanWei(ruLiang.getDanWei());
                bean.setCaiJiRen(ruLiang.getCaiJiRen());
                bean.setCaiJiRQ(ruLiang.getCaiJiRQ());
                bean.setCaiJiSJ(ruLiang.getCaiJiSJ());
                bean.setBingQuID(ruLiang.getBingQuID());
                bean.setBingRenZYID(ruLiang.getBingRenZYID());
                bean.setBingRenXM(ruLiang.getBingRenXM());
                bean.setChuangHao(ruLiang.getChuangHao());
                bean.setCaiJiRenID(ruLiang.getCaiJiRenID());
                bean.setHuanYeBZ(ruLiang.getHuanYeBZ());
                ruLiangData.add(bean);
                break;
            }
        }

        for(int i=0;i<chuLiangAllData.size();i++){
            ChuLiang chuLiang=chuLiangAllData.get(i);
            if(huLiJiLuDanHao.equals(chuLiang.getHuLiJiLuDanHao())){
                JiChuXiangMuBean bean=new JiChuXiangMuBean();
                bean.setHuLiJiLuID(chuLiang.getHuLiJiLuID());
                bean.setHuLiJiLuDanHao(chuLiang.getHuLiJiLuDanHao());
                bean.setJiChuXiangMuID(chuLiang.getJiChuXiangMuID());
                bean.setJiChuXiangMuMC(chuLiang.getJiChuXiangMuMC());
                bean.setShuZhi(chuLiang.getShuZhi());
                bean.setJiLuSJ(chuLiang.getJiLuSJ());
                bean.setDanWei(chuLiang.getDanWei());
                bean.setCaiJiRen(chuLiang.getCaiJiRen());
                bean.setCaiJiRQ(chuLiang.getCaiJiRQ());
                bean.setCaiJiSJ(chuLiang.getCaiJiSJ());
                bean.setBingQuID(chuLiang.getBingQuID());
                bean.setBingRenZYID(chuLiang.getBingRenZYID());
                bean.setBingRenXM(chuLiang.getBingRenXM());
                bean.setChuangHao(chuLiang.getChuangHao());
                bean.setCaiJiRenID(chuLiang.getCaiJiRenID());
                bean.setHuanYeBZ(chuLiang.getHuanYeBZ());
                chuLiangData.add(bean);
                break;
            }
        }

    }

    /**
     * 设置空值页面数据
     */
    private void setEmptyPageData(){
        jiChuXiangMuBeanList.clear();
        ruLiangData.clear();
        chuLiangData.clear();
        zongJieType="";
        teShuQingKuang="";
        tempZongJieType="";
        tempTeShuQingKuang="";

        for(int i=0;i<fenBiaoList.size();i++){
            FenBiao fenBiao=fenBiaoList.get(i);
            JiChuXiangMuBean bean=new JiChuXiangMuBean();
            bean.setHuLiJiLuID("");
            bean.setHuLiJiLuDanHao("");
            bean.setJiChuXiangMuID(fenBiao.getJiChuXiangMuID());
            bean.setJiChuXiangMuMC(fenBiao.getJiChuXiangMuMC());
            bean.setShuZhi("");
            bean.setJiLuSJ("");
            bean.setDanWei(fenBiao.getDanWei());
            bean.setCaiJiRen(myApplication.getYhxm());
            bean.setCaiJiRQ("");
            bean.setCaiJiSJ("");
            bean.setBingQuID(bingQuDM);
            bean.setBingRenZYID(bingRenZYId);
            bean.setBingRenXM(bingRenName);
            bean.setChuangHao(chuangHao);
            bean.setCaiJiRenID(yhid);
            bean.setHuanYeBZ("");
            jiChuXiangMuBeanList.add(bean);
        }
        for(int i=0;i<jiChuXiangMuBeanList.size();i++){
            JiChuXiangMuBean bean=jiChuXiangMuBeanList.get(i);
            Log.i("bean","---"+bean.getShuZhi());
        }
    }

    private void initTabLayout() {
        mFragmentList.clear();

        //基础项目界面
        jiChuXiangMuFragment=new JiChuXiangMuFragment();
        //jiChuXiangMuFragment.setData(jiChuXiangMuBeanList);
        mFragmentList.add(jiChuXiangMuFragment);
        Log.i("data","jiChuXiangMuBeanList------"+jiChuXiangMuBeanList.size());

//        ruLiangFragment = RuLiangFragment.newInstance(ruLiangData);
        ruLiangFragment=new RuLiangFragment();
//        ruLiangFragment.setData(ruLiangData);
        mFragmentList.add(ruLiangFragment);
        if(ruLiangData.size()>0){
            Log.i("data","ruLiangData------------"+ruLiangData.get(0).getShuZhi());
        }

        chuLiangFragment=new ChuLiangFragment();
//        chuLiangFragment.setData(chuLiangData);
        mFragmentList.add(chuLiangFragment);
        if(chuLiangData.size()>0){
            Log.i("data","chuLiangData------------"+chuLiangData.get(0).getShuZhi());
        }


        teShuQingKuangFragment=new TeShuQingKuangFragment();
//        teShuQingKuangFragment.setData(zongJieType,teShuQingKuang);
        Log.i("data",zongJieType+"zongJieType----teShuQingKuang"+teShuQingKuang);
        mFragmentList.add(teShuQingKuangFragment);

        //mFragmentList.add(TeShuQingKuangFragment.newInstance());

        HuLiDanAdapter mHuLiDanAdapter = new HuLiDanAdapter(getSupportFragmentManager(), tabs, mFragmentList);
        vpHuLiDan.setAdapter(mHuLiDanAdapter);
        //防止fragment切换时页面重新绘制
        vpHuLiDan.setOffscreenPageLimit(4);
        tabHuLiDan.setupWithViewPager(vpHuLiDan);
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvAdd= (ImageView) findViewById(R.id.iv_add);
        mIvHldDetail= (ImageView) findViewById(R.id.iv_hulidan_detail);
        mIvTouXiang= (ImageView) findViewById(R.id.iv_hulidan_touxiang);
        mTvPatientName = (TextView) findViewById(R.id.tv_hulidan_patient_name);
        mTvChuangHao= (TextView) findViewById(R.id.tv_chuanghao);
//        mTvYMD = (TextView) findViewById(R.id.tv_time_ymd);
//        mTvHM = (TextView) findViewById(R.id.tv_time_hm);
        mTvTime= (TextView) findViewById(R.id.tv_time);
        btnSave = (Button) findViewById(R.id.btn_save);
        tabHuLiDan = (TabLayout) findViewById(R.id.tab_hulidan);
        vpHuLiDan = (ViewPager) findViewById(R.id.vp_hulidan);

        mIvBack.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
        mIvHldDetail.setOnClickListener(this);
        mIvTouXiang.setOnClickListener(this);
        mTvPatientName.setOnClickListener(this);

        btnSave.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                huLiJiLuDanHao="";
                //新增护理记录单号
                setEmptyPageData();
                initTabLayout();
                //标志置true
                isAddHLJLD=true;
                selectHLJLDHPos=0;
                break;
            case R.id.iv_hulidan_detail:
                Intent detailIntent=new Intent(HuLiDanActivity.this,HuLiDanDetailActivity.class);
                detailIntent.putExtra("huLiJiLuList", (Serializable) huLiJiLuList);
//                startActivity(detailIntent);
                startActivityForResult(detailIntent, REQUEST_CODE);
                break;
            case R.id.iv_hulidan_touxiang:
            case R.id.tv_hulidan_patient_name:
                Intent intent = new Intent(HuLiDanActivity.this, BrlbActivity.class);
                intent.putExtra("which","HLD");
                startActivity(intent);
                finish();
                //startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.btn_save:
                Log.i("+++++++","///////");
                Log.i("data","++++++"+teShuQingKuangFragment.zongJieType+"------"+teShuQingKuangFragment.teShuQingKuang);
                //弹出确定取消对话框
                final ConfirmAndCancelDialog dialog=new ConfirmAndCancelDialog(context);
                dialog.setOnConfirmOrCancelClickListener(new ConfirmAndCancelDialog.OnConfirmOrCancelClickListener() {
                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        //整合所有数据并提交
                        changeAllData();
                        Log.i("------","点击了确定");
                    }

                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
//                changeAllData();
                break;
            default:
                break;
        }
    }

    /**
     * 整合所有数据
     */
    private void changeAllData() {
        allHuLiDanPageAddList.clear();

        for (int i = 0; i < jiChuXiangMuBeanList.size(); i++) {
            JiChuXiangMuBean bean = jiChuXiangMuBeanList.get(i);
            if (!TextUtils.isEmpty(bean.getPanDuanBZ())) {
                allHuLiDanPageAddList.add(bean);
            }
        }
        addRuLiang();
        addChuLiang();
        addZongJieType();
        addTeShuQingKuang();
        String time=GetNowDate();
        for (int i=0;i<allHuLiDanPageAddList.size();i++){
            JiChuXiangMuBean bean=allHuLiDanPageAddList.get(i);
            bean.setJiLuSJ(time);
            bean.setCaiJiRQ(time);
            bean.setCaiJiSJ(time);
        }

        //判断有无数据添加或修改
        if(allHuLiDanPageAddList.size()>0){
            //上传的参数
            String canShu;
            if(isAddHLJLD){
                canShu="1"+ NetWork.SEPARATE+ CreateXmlUtil.createHuLiDanXml(allHuLiDanPageAddList,"tab_CaoZuo")+ NetWork.SEPARATE+"0";
                Log.i("-------","参数"+canShu);
            }else {
                canShu="0"+ NetWork.SEPARATE+ CreateXmlUtil.createHuLiDanXml(allHuLiDanPageAddList,"tab_CaoZuo")+ NetWork.SEPARATE+"0";
                Log.i("-------","参数"+canShu);
            }
       System.out.println("开始执行1000302方法");
        ZhierCall call = new ZhierCall()
                .setId(yhid)
                .setNumber("1000302")
                .setMessage(NetWork.HULIJIRU)
                .setCanshu(canShu)
                .setContext(context)
                .setPort(5000)
                .build();

            call.start(new NetWork.SocketResult() {
                @Override
                public void success(String data) {
                    Log.i("data","提交成功------"+data);
                    Toast.makeText(context,"保存成功!",Toast.LENGTH_SHORT).show();
                    //标志置false
                    isAddHLJLD=false;
                    //刷新页面数据
                    getData();
                }

                @Override
                public void fail(String info) {
                    Log.i("data","提交失败------"+info);
//                    Toast.makeText(context,"数据未修改!",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context,"数据未修改!",Toast.LENGTH_SHORT).show();
        }


    }

    private void addTeShuQingKuang() {

        Log.i("qqqqqqq",teShuQingKuang+"----"+tempTeShuQingKuang);
        if(TextUtils.isEmpty(teShuQingKuang)&&!TextUtils.isEmpty(tempTeShuQingKuang)){
            selectKongJianXinXi("特殊情况",tempTeShuQingKuang,"1");
        }else if(!TextUtils.isEmpty(teShuQingKuang)&&!TextUtils.equals(tempTeShuQingKuang,teShuQingKuang)&&!TextUtils.isEmpty(tempTeShuQingKuang)){
            //修改时根据护理记录单号从huli表中找到对应的控件信息
            for(int i=0;i<huLiList.size();i++){
                HuLi huLi=huLiList.get(i);
                if(TextUtils.equals(huLiJiLuDanHao,huLi.getHuLiJiLuDanHao())){
                    if(TextUtils.equals(huLi.getJiChuXiangMuMC(),"特殊情况")){
                        JiChuXiangMuBean jiChuXiangMuBean=new JiChuXiangMuBean();
                        jiChuXiangMuBean.setHuLiJiLuID(huLi.getHuLiJiLuID());
                        jiChuXiangMuBean.setHuLiJiLuDanHao(huLiJiLuDanHao);
                        jiChuXiangMuBean.setJiChuXiangMuID(huLi.getJiChuXiangMuID());
                        jiChuXiangMuBean.setJiChuXiangMuMC(huLi.getJiChuXiangMuMC());
                        jiChuXiangMuBean.setShuZhi(tempTeShuQingKuang);
                        jiChuXiangMuBean.setDanWei(huLi.getDanWei());
                        jiChuXiangMuBean.setCaiJiRen(myApplication.getYhxm());
                        jiChuXiangMuBean.setCaiJiRenID(myApplication.getYhgh());
                        jiChuXiangMuBean.setBingRenXM(bingRenName);
                        jiChuXiangMuBean.setBingRenZYID(bingRenZYId);
                        jiChuXiangMuBean.setChuangHao(chuangHao);
                        jiChuXiangMuBean.setBingQuID(huLi.getBingQuID());
                        jiChuXiangMuBean.setPanDuanBZ("0");

                        allHuLiDanPageAddList.add(jiChuXiangMuBean);
                        break;
                    }
                }
            }
//            selectKongJianXinXi("特殊情况",teShuQingKuangFragment.teShuQingKuang,"0");
        }
    }

    private void addZongJieType() {

        Log.i("qqqqqqq",zongJieType+"----"+tempZongJieType);
        if(!TextUtils.equals("暂无",tempZongJieType)){
            if(TextUtils.isEmpty(zongJieType)&&!TextUtils.isEmpty(tempZongJieType)&&!TextUtils.equals(tempZongJieType,"无")){
                selectKongJianXinXi("总结类型",tempZongJieType,"1");
            }else if(!TextUtils.isEmpty(zongJieType)&&!TextUtils.equals(tempZongJieType,zongJieType)){
                //修改时根据护理记录单号从huli表中找到对应的控件信息
                for(int i=0;i<huLiList.size();i++){
                    HuLi huLi=huLiList.get(i);
                    if(TextUtils.equals(huLiJiLuDanHao,huLi.getHuLiJiLuDanHao())){
                        if(TextUtils.equals(huLi.getJiChuXiangMuMC(),"总结类型")){
                            JiChuXiangMuBean jiChuXiangMuBean=new JiChuXiangMuBean();
                            jiChuXiangMuBean.setHuLiJiLuID(huLi.getHuLiJiLuID());
                            jiChuXiangMuBean.setHuLiJiLuDanHao(huLiJiLuDanHao);
                            jiChuXiangMuBean.setJiChuXiangMuID(huLi.getJiChuXiangMuID());
                            jiChuXiangMuBean.setJiChuXiangMuMC(huLi.getJiChuXiangMuMC());
                            jiChuXiangMuBean.setShuZhi(tempZongJieType);
                            jiChuXiangMuBean.setDanWei(huLi.getDanWei());
                            jiChuXiangMuBean.setCaiJiRen(myApplication.getYhxm());
                            jiChuXiangMuBean.setCaiJiRenID(myApplication.getYhgh());
                            jiChuXiangMuBean.setBingRenXM(bingRenName);
                            jiChuXiangMuBean.setBingRenZYID(bingRenZYId);
                            jiChuXiangMuBean.setChuangHao(chuangHao);
                            jiChuXiangMuBean.setBingQuID(huLi.getBingQuID());
                            jiChuXiangMuBean.setPanDuanBZ("0");

                            allHuLiDanPageAddList.add(jiChuXiangMuBean);
                            break;
                        }
                    }
                }
//                selectKongJianXinXi("总结类型",tempZongJieType,"0");
            }
        }
    }

    /**
     * 整合出量数据
     */
    private void addChuLiang() {


        if (chuLiangList.size() > 0) {
            String chuAll="";
            for (int i = 0; i < chuLiangList.size(); i++) {
                RuLiangAndChuLiangBean bean=chuLiangList.get(i);
                if(!TextUtils.isEmpty(bean.getContent())&&!TextUtils.isEmpty(bean.getValue())){
                    if(i!=chuLiangList.size()-1){
                        chuAll=chuAll+bean.getContent()+"$"+bean.getValue()+"*";
                    }else {
                        chuAll=chuAll+bean.getContent()+"$"+bean.getValue();
                    }
                }
            }

            //判断最后一个字符是不是*
            String lastChar=chuAll.substring(chuAll.length()-1);
            if(TextUtils.equals(lastChar,"*")){
                chuAll=chuAll.substring(0,chuAll.length()-1);
            }

            if(!TextUtils.isEmpty(chuAll)&&chuLiangData.size()==0){
                selectKongJianXinXi("出量",chuAll,"1");
            }else if(chuLiangData.size()!=0&&!TextUtils.equals(chuLiangData.get(0).getShuZhi(),chuAll)){
                chuLiangData.get(0).setShuZhi(chuAll);
                chuLiangData.get(0).setPanDuanBZ("0");
                allHuLiDanPageAddList.add(chuLiangData.get(0));
            }

        }
    }

    private void selectKongJianXinXi(String kongJianName, String value,String panDuanBiaoZhi) {
        for(int i=0;i<huLiJiLuWHList.size();i++){
            HuLiJiLuWH bean=huLiJiLuWHList.get(i);
            if(bean.getJiChuXiangMuMC().equals(kongJianName)){
                JiChuXiangMuBean jiChuXiangMuBean=new JiChuXiangMuBean();
                jiChuXiangMuBean.setHuLiJiLuDanHao(huLiJiLuDanHao);
                jiChuXiangMuBean.setJiChuXiangMuID(bean.getJiChuXiangMuID());
                jiChuXiangMuBean.setJiChuXiangMuMC(bean.getJiChuXiangMuMC());
                jiChuXiangMuBean.setShuZhi(value);
                jiChuXiangMuBean.setDanWei(bean.getDanWei());
                jiChuXiangMuBean.setCaiJiRen(myApplication.getYhxm());
                jiChuXiangMuBean.setCaiJiRenID(myApplication.getYhgh());
                jiChuXiangMuBean.setBingRenXM(bingRenName);
                jiChuXiangMuBean.setBingRenZYID(bingRenZYId);
                jiChuXiangMuBean.setChuangHao(chuangHao);
                jiChuXiangMuBean.setBingQuID(bean.getBingQuDM());
                jiChuXiangMuBean.setPanDuanBZ(panDuanBiaoZhi);

                allHuLiDanPageAddList.add(jiChuXiangMuBean);
                break;
            }
        }
    }

    /**
     * 整合入量数据
     */
    private void addRuLiang() {


        if (ruLiangList.size() > 0) {
            String ruAll="";
            for (int i = 0; i < ruLiangList.size(); i++) {
                //RuLiangAndChuLiangBean bean=ruLiangFragment.ruLiangList.get(i);
                RuLiangAndChuLiangBean bean=ruLiangList.get(i);
                if(!TextUtils.isEmpty(bean.getContent())&&!TextUtils.isEmpty(bean.getValue())){
                    Log.i("data",bean.getContent()+"------"+bean.getValue());
                    if(i!=ruLiangList.size()-1){
                        ruAll=ruAll+bean.getContent()+"$"+bean.getValue()+"*";
                    }else {
                        ruAll=ruAll+bean.getContent()+"$"+bean.getValue();
                    }
                }
            }

            //判断最后一个字符是不是*
            String lastChar=ruAll.substring(ruAll.length()-1);
            if(TextUtils.equals(lastChar,"*")){
                ruAll=ruAll.substring(0,ruAll.length()-1);
            }

            if(!TextUtils.isEmpty(ruAll)&&ruLiangData.size()==0){
                selectKongJianXinXi("入量",ruAll,"1");
            }else if(ruLiangData.size()!=0&&!TextUtils.equals(ruLiangData.get(0).getShuZhi(),ruAll)){
                ruLiangData.get(0).setShuZhi(ruAll);
                ruLiangData.get(0).setPanDuanBZ("0");
                allHuLiDanPageAddList.add(ruLiangData.get(0));
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int pos = data.getIntExtra("position", 0);
                String cjsj=data.getStringExtra("cjsj");
                Log.i("data", "------" + pos);


                //设置采集时间
                mTvTime.setText(cjsj);
                selectHLJLDHPos=pos;
                //标志置false
                isAddHLJLD=false;
                //根据选择的不同的护理记录单号显示不同的数据
                setAllPageData();
                initTabLayout();
            }
        }
    }

    private String GetNowDate(){
        String temp_str="";
        Date date = new Date();
        //HH表示24小时制,如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str=sdf.format(date);
        return temp_str;
    }

    /**
     * 接收从基础项目的adapter传递过来的数据
     * @param mList
     */
    public void getJiChuXiangMuData(List<JiChuXiangMuBean> mList){
        this.jiChuXiangMuBeanList=mList;
    }

    /**
     * 接收从入量和出量adapter传递过来的数据
     * @param mList
     * @param fromWhich
     */
    public void getFinalData(List<RuLiangAndChuLiangBean> mList,int fromWhich){
        if(fromWhich==0){
            //接收入量页面的数据
            ruLiangList=mList;
            Log.i("ruLiangList","---"+ruLiangList.size());
        }else {
            //接收出量页面的数据
            chuLiangList=mList;
            Log.i("chuLiangList","---"+chuLiangList.size());
        }
    }

    /**
     * 接收从TeShuQingKuangFragment返回的数据
     * @param zongJieType
     * @param teShuQingKuang
     */
    public void getTeShuQingKuangFragmentData(String zongJieType,String teShuQingKuang){
        this.tempZongJieType=zongJieType;
        this.tempTeShuQingKuang=teShuQingKuang;
    }

    public String getZongJieType() {
        return zongJieType;
    }

    public String getTeShuQingKuang() {
        return teShuQingKuang;
    }

    public List<JiChuXiangMuBean> getJiChuXiangMuBeanList() {
        return jiChuXiangMuBeanList;
    }


    public List<JiChuXiangMuBean> getRuLiangData() {
        return ruLiangData;
    }

    public List<JiChuXiangMuBean> getChuLiangData() {
        return chuLiangData;
    }


}
