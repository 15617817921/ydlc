package enjoyor.enjoyorzemobilehealth.activity;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.imscs.barcodemanager.BarcodeManager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.YaChuangJL;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import enjoyor.enjoyorzemobilehealth.views.GongYongDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017-08-28.
 */

public class YcPingGuDanActivity extends BaseActivity implements ScanFactory.FragScan{
    private DateUtil instance;
    private SimpleDateFormat format;
    private Context context;
    private static final int REQUEST_CODE = 1; // 请求码
    private String userID;
    private String bingQuDM;
    private String zyid;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private ImageView back;
    private ImageView tx;
    private TextView xm;
    private TextView ch;
    private LinearLayout layout;
    private BRLB brlb = null;
    private ZhierCall zhierCall;
    private String shijian;
    private int updateType;
    private String itemID;
    private TextView sj;
    private LinearLayout yczg_select;
    private FrameLayout cmsz_select;
    private FrameLayout liang_select;
    private FrameLayout yanse_select;
    private FrameLayout qscl_select;
    private FrameLayout jbcl_select;
    private FrameLayout fq_select;
    private TextView yczg;
    private TextView cmsz;
    private TextView liang;
    private TextView yanse;
    private TextView qscl;
    private TextView jbcl;
    private TextView fq;
    private TextView mianji;
    private TextView buwei;
    private ImageView iv_ycpinggudan_detail;
    private ImageView iv_add;
    private Button btn_save;
    private TextView tv_qianming;
    private List<YaChuangJL> listYaChuangJL= new ArrayList<>();
    private String[] zhuangGuiLX = new String[]{"压疮痊愈", "压疮未愈", "压疮未愈出院", "压疮未愈死亡"};
    private String[] seZe =  new String[]{"A 水泡","B 黑色","C 黄色","D 红色"};
    private String[] fmwLiang =  new String[]{"A 无","B +","C ++","D +++","E ++++"};
    private String[] fmwYanSe =  new String[]{"A 水状","B 血性","C 脓性"};
    private String[] qsChuLi =  new String[]{"A 翻身","B 气垫床","C 营养","D 其他"};
    private String[] jbChuLi =  new String[]{"A 水胶体敷料","B 泡沫敷料","C 藻酸盐敷料","D 清创","E 其他"};
    private String [] fenqi = new String[]{"Ⅰ期","II期","Ⅲ期","Ⅳ期","不可分期"};
    private GongYongDialog.Dialogcallback mCallback_item;
    private TextView tv;

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


    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }

    private YcPingGuDanActivity.DoDecodeThread mDoDecodeThread;

    //扫描提示音
    private boolean playBeep=true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;

    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_ycpinggudan);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context =this;
        initBeep();
        //扫码相关
        mDoDecodeThread = new YcPingGuDanActivity.DoDecodeThread();
        mDoDecodeThread.start();

        mCallback_item=new GongYongDialog.Dialogcallback(){
            @Override
            public void dialogdo(String string) {
                tv.setText(string);
            }
        };
        defineData();
        clickData();
        initData();
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

//		//注册默认音频通道
//		getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
//		audioManager=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        //检查铃音模式
        if(successAudioManager.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
            playBeep=false;
        }
//		mediaPlayer=new MediaPlayer();
//		//指定播放的声音通道
//		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//		//当播放完毕一次后，重新指向流文件的开头，以准备下次播放
//		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//
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

    private void defineData(){
        yczg = (TextView) findViewById(R.id.yczg);
        cmsz = (TextView) findViewById(R.id.cmsz);
        liang = (TextView) findViewById(R.id.liang);
        yanse = (TextView) findViewById(R.id.yanse);
        qscl = (TextView) findViewById(R.id.qscl);
        jbcl = (TextView) findViewById(R.id.jbcl);
        fq = (TextView) findViewById(R.id.fq);
        yczg_select = (LinearLayout) findViewById(R.id.yczg_select);
        cmsz_select = (FrameLayout) findViewById(R.id.cmsz_select);
        liang_select = (FrameLayout) findViewById(R.id.liang_select);
        yanse_select = (FrameLayout) findViewById(R.id.yanse_select);
        qscl_select = (FrameLayout) findViewById(R.id.qscl_select);
        jbcl_select = (FrameLayout) findViewById(R.id.jbcl_select);
        fq_select = (FrameLayout) findViewById(R.id.fq_select);
        mianji = (TextView) findViewById(R.id.mianji);
        buwei =(TextView) findViewById(R.id.buwei);
        sj= (TextView) findViewById(R.id.sj);
        btn_save = (Button) findViewById(R.id.btn_save);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_ycpinggudan_detail = (ImageView) findViewById(R.id.iv_ycpinggudan_detail);
        tv_qianming = (TextView) findViewById(R.id.tv_qianming);
        brlb = MyApplication.getInstance().getOther_brlb();
        layout = (LinearLayout) findViewById(R.id.top);
        back = (ImageView) findViewById(R.id.back);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);
    }
    private void clickData(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "YcPingGuDan");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(context, BrlbActivity.class);
                intent.putExtra("which", "YcPingGuDan");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(context,HomePageActivity.class));
                finish();
            }
        });
        sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/M/d HuLiJi:mm:ss");
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        shijian =format.format(date);
                        sj.setText(shijian);
                    }
                }).setDate(instance.getCalendarSfm(sj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择护理日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(TimePickerView.Type.ALL)
                        //.isDialog(true)
                        .build();
                pvTime.show();
            }
        });
        yczg_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= yczg.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,zhuangGuiLX,lx,mCallback_item);
                diaolog.setTexttitle("压疮转归");
                tv=yczg;
                diaolog.show();
            }
        });
        cmsz_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= cmsz.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,seZe,lx,mCallback_item);
                diaolog.setTexttitle("创面色泽");
                tv=cmsz;
                diaolog.show();
            }
        });
        liang_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= liang.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,fmwLiang,lx,mCallback_item);
                diaolog.setTexttitle("分泌物量");
                tv=liang;
                diaolog.show();
            }
        });
        yanse_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= yanse.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,fmwYanSe,lx,mCallback_item);
                diaolog.setTexttitle("分泌物颜色");
                tv=yanse;
                diaolog.show();
            }
        });
        qscl_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= qscl.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,qsChuLi,lx,mCallback_item);
                diaolog.setTexttitle("全身处理");
                tv=qscl;
                diaolog.show();
            }
        });
        jbcl_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= jbcl.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,jbChuLi,lx,mCallback_item);
                diaolog.setTexttitle("局部处理");
                tv=jbcl;
                diaolog.show();
            }
        });
        fq_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx= fq.getText().toString();
                GongYongDialog diaolog =new GongYongDialog(context,fenqi,lx,mCallback_item);
                diaolog.setTexttitle("分期");
                tv=fq;
                diaolog.show();
            }
        });
        iv_ycpinggudan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent=new Intent(context,YcPingGuDanDetailActivity.class);
                detailIntent.putExtra("listYaChuangJL", (Serializable) listYaChuangJL);
                startActivityForResult(detailIntent, REQUEST_CODE);
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmptyPageData();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yczg.getText().toString().equals("请选择")){
                    Toast.makeText(context, "请选择压疮转归", Toast.LENGTH_SHORT).show();
                    yczg.setEnabled(true);
                    return;
                }
                CenterDialog centerDialog = new CenterDialog(context, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CenterDialog dialog, View view) {
                        if (view.getId() == R.id.bt_yes) {
                            netWorkUpdata();
                        }

                    }
                });
                centerDialog.show();



            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                String cjsj=data.getStringExtra("cjsj");
                int pos = data.getIntExtra("position", 0);
                //设置采集时间
                shijian=cjsj;
                //重新加载数据，不需要加载方法
                setAllPageData(listYaChuangJL);

            }
        }
    }
    //加载数据
    private void setAllPageData(List<YaChuangJL> list){
        sj.setText(shijian);
        for(int i=0;i<list.size();i++){
            YaChuangJL bean=list.get(i);
            if(bean.getJLSJ().equals(shijian)){
                yczg.setText(bean.getYaChuangZG());
                cmsz.setText(bean.getSeZe());
                liang.setText(bean.getLiang());
                yanse.setText(bean.getYanSe());
                qscl.setText(bean.getQSChuLi());
                jbcl.setText(bean.getJBChuLi());
                fq.setText(bean.getFenQi());
                mianji.setText(bean.getMianJi());
                buwei.setText(bean.getBuWei());
                tv_qianming.setText(bean.getQianZi());
                itemID =bean.getID();
                updateType=2;
            }
        }
    }
    //加载空数据
    private void setEmptyPageData(){
        Date currentTime =new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyy/M/d HuLiJi:mm:ss");
        shijian = formatter.format(currentTime);
        sj.setText(shijian);
        //yczg.setText("请选择");
        cmsz.setText("请选择");
        liang.setText("请选择");
        yanse.setText("请选择");
        qscl.setText("请选择");
        jbcl.setText("请选择");
        fq.setText("请选择");
        mianji.setText("");
        buwei.setText("");
        tv_qianming.setText(MyApplication.getInstance().getYhxm());
        updateType=1;
    }
    //保存数据
    private void netWorkUpdata(){
        String xml=null;
        try {
            xml=createXml();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder s=new StringBuilder();
        String num="";
        if(updateType==1){
            s.append(zyid+"¤"+bingQuDM+"¤");
            num="0601201";
            //更新记录时间到当前
            /*Date currentTime =new Date();
            SimpleDateFormat formatter=new SimpleDateFormat("yyy/MM/dd HuLiJi:mm:ss");
            shijian = formatter.format(currentTime);*/
        }else if(updateType==2){
            s.append(itemID+"¤");
            num="0601202";
        }
        s.append(xml);
        Log.d("-------------",s.toString());
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber(num)
                .setMessage(NetWork.SMTZ)
                .setCanshu(s.toString())
                .setContext(context)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Toast.makeText(context,"成功", Toast.LENGTH_LONG).show();
                commitData();



            }

            @Override
            public void fail(String info) {
                Toast.makeText(context, info, Toast.LENGTH_LONG).show();
            }
        });

    }
    //获取xml
    private String createXml() throws Exception{
        YaChuangJL bean= getData();
        StringBuilder s=new StringBuilder();
        //1.头部

        s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>"+"\r\n");
        s.append("<DS Name=\"596012\" Num=\"1\">"+"\r\n");
        s.append("<DT Name=\"Table1\" Num=\"1\">"+"\r\n");

        //2.中部
        s.append("<DR Name=\"26820551\" Num=\"11\">"+"\r\n");
        s.append("<DC Name=\"JLSJ\" Num=\"0\">"+bean.getJLSJ()+"</DC>"+"\r\n");
        s.append("<DC Name=\"YaChuangZG\" Num=\"0\">"+bean.getYaChuangZG()+"</DC>"+"\r\n");
        s.append("<DC Name=\"BuWei\" Num=\"0\">"+bean.getBuWei()+"</DC>"+"\r\n");
        s.append("<DC Name=\"FenQi\" Num=\"0\">"+bean.getFenQi()+"</DC>"+"\r\n");
        s.append("<DC Name=\"MianJi\" Num=\"0\">"+bean.getMianJi()+"</DC>"+"\r\n");
        s.append("<DC Name=\"SeZe\" Num=\"0\">"+bean.getSeZe()+"</DC>"+"\r\n");
        s.append("<DC Name=\"Liang\" Num=\"0\">"+bean.getLiang()+"</DC>"+"\r\n");
        s.append("<DC Name=\"YanSe\" Num=\"0\">"+bean.getYanSe()+"</DC>"+"\r\n");
        s.append("<DC Name=\"QSChuLi\" Num=\"0\">"+bean.getQSChuLi()+"</DC>"+"\r\n");
        s.append("<DC Name=\"JBChuLi\" Num=\"0\">"+bean.getJBChuLi()+"</DC>"+"\r\n");
        s.append("<DC Name=\"QianZi\" Num=\"0\">"+bean.getQianZi()+"</DC>"+"\r\n");
        s.append("</DR>"+"\r\n");


        //3.尾部
        s.append("</DT>"+"\r\n");
        s.append("</DS>");

        return s.toString();
    }
    private YaChuangJL getData(){
        YaChuangJL bean= new YaChuangJL();
        bean.setYaChuangZG(yczg.getText().toString());
        bean.setMianJi(mianji.getText().toString());
        bean.setBuWei(buwei.getText().toString());
        bean.setJLSJ(shijian);
        bean.setQianZi(tv_qianming.getText().toString());

        if(cmsz.getText().toString().equals("请选择")){
            bean.setSeZe("");
        }else{
            bean.setSeZe(cmsz.getText().toString());
        }
        if(liang.getText().toString().equals("请选择")){
            bean.setLiang("");
        }else{
            bean.setLiang(liang.getText().toString());
        }
        if(yanse.getText().toString().equals("请选择")){
            bean.setYanSe("");
        }else{
            bean.setYanSe(yanse.getText().toString());
        }
        if(qscl.getText().toString().equals("请选择")){
            bean.setQSChuLi("");
        }else{
            bean.setQSChuLi(qscl.getText().toString());
        }
        if(jbcl.getText().toString().equals("请选择")){
            bean.setJBChuLi("");
        }else{
            bean.setJBChuLi(jbcl.getText().toString());
        }
        if(fq.getText().toString().equals("请选择")){
            bean.setFenQi("");
        }else{
            bean.setFenQi(fq.getText().toString());
        }
        return bean;
    }
    private void initData(){
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        bingQuDM=preferences2.getString("bqdm","");
        userID=preferences2.getString("id","");
//        String bingRenXM="";
//        String bingRenXB="";
//        String chuangHao="";
        if(brlb==null){
            MyApplication instance = MyApplication.getInstance();
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            bingRenXM =instance.getListBRLB().get(0).getXINGMING();
            bingRenXB =instance.getListBRLB().get(0).getXINGBIE();
            chuangHao =instance.getListBRLB().get(0).getCHUANGWEIHAO();
        }else{
            Intent intent =getIntent();
            zyid=intent.getStringExtra("BINGRENZYID");
            chuangHao=intent.getStringExtra("CHUANGHAO");
            bingRenXM=intent.getStringExtra("XINGMING");
            bingRenXB=intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);

        }
        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        xm.setText(bingRenXM);
        ch.setText(chuangHao + "床");
        commitData();

    }
    private void commitData(){
        listYaChuangJL.clear();
        SimpleDateFormat formatter=new SimpleDateFormat("yyy/MM/dd HuLiJi:mm:ss");
        Calendar c = Calendar.getInstance();
        String afterSJ = formatter.format(c.getTime());
        c.add(Calendar.MONTH, -1);
        String beforeSJ= formatter.format(c.getTime());
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0601200")
                .setMessage(NetWork.SMTZ)
                .setCanshu(zyid+"¤"+beforeSJ+"¤"+afterSJ+"¤"+bingQuDM)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("zzzz3",data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{YaChuangJL.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                Toast.makeText(context, info, Toast.LENGTH_LONG).show();
            }
        });
    }
    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                if(listYaChuangJL.size()!=0){
                    if(!listYaChuangJL.get(0).getID().equals("")){
                        yczg.setText(listYaChuangJL.get(0).getYaChuangZG());
                        yczg_select.setEnabled(false);
                    }
                }
                if(sj.getText().toString().equals("")){
                    setEmptyPageData();
                }else{
                    setAllPageData(listYaChuangJL);
                }

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listYaChuangJL.add((YaChuangJL) msg.obj);
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
        Log.e(" shengmingonStart","onStart");
        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new DoDecodeThread();
            mDoDecodeThread.start();
            initSaoMiao();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("shengmingonStop","onStop");
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
                return YcPingGuDanActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(YcPingGuDanActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, YcPingGuDanActivity.this.getClass().getName())) {

                return true;

            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyUp(keyCode, event)) {
                return true;
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     * 扫描后调用的返回数据的方法
     * @param data
     * @param keycode
     */
    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        String[] s=data.split("\\*");
        //st72是腕带，切换病人
        //药品的话只有一串数字
        if(s[0].equals("st72")){
            checkAndChangePatient(s[1]);
        }else {
            playFailBeepSound();
            Toast.makeText(YcPingGuDanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAndChangePatient(String s) {
        List<BRLB> brlbList=new ArrayList<>();
        brlbList=MyApplication.getInstance().getListBRLB();
        boolean isContain=false;
        for (BRLB b:brlbList){
            if(TextUtils.equals(s,b.getBINGRENZYID())){
                zyid=s;
                bingRenXM=b.getXINGMING();
                bingRenXB=b.getXINGBIE();
                chuangHao=b.getCHUANGWEIHAO();
                isContain=true;
                break;
            }
        }
        //判断是否包含扫描的腕带的信息
        if(isContain){
            if(TextUtils.equals(bingRenXB,"男")){
                tx.setImageResource(R.drawable.icon_men);
            }else {
                tx.setImageResource(R.drawable.icon_women);
            }
            xm.setText(bingRenXM);
            ch.setText(chuangHao+"床");
            //重新请求数据
            commitData();
            playSuccessBeepSound();
            Toast.makeText(YcPingGuDanActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
        }else {
            playFailBeepSound();
            Toast.makeText(YcPingGuDanActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(" shengmingonPause","onPause");
        this.unregisterReceiver(myReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(" shengmingonRestart","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(" shengmingonResume","onResume");
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        this.registerReceiver(myReceiver, filter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            playScanSuccessBeepSound();
            String data = intent.getStringExtra("data");
            Log.i("data", "bben扫码返回数据" + data);
            String[] s = data.split("\\*");
            //st72是腕带，切换病人
            //药品的话只有一串数字
            if(s[0].equals("st72")){
                checkAndChangePatient(s[1]);
            }else if(s.length==1){
                playFailBeepSound();
                Toast.makeText(YcPingGuDanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
