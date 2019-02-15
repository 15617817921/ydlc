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
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import enjoyor.enjoyorzemobilehealth.entities.SSCX;
import enjoyor.enjoyorzemobilehealth.entities.ShuHouLD;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import enjoyor.enjoyorzemobilehealth.views.ShoushuMCDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017-08-22.
 */

public class SsHuLiDanActivity extends BaseActivity implements ScanFactory.FragScan{
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

    private List<SSCX> listsscx=new ArrayList<>();
    private List<ShuHouLD> listshld=new ArrayList<>();
    private List<ShuHouLD> listshldMC=new ArrayList<>();
    private ShuHouLD beforeBean =new ShuHouLD();
    private ShuHouLD afterBean =new ShuHouLD();
    private LinearLayout ssxz;
    private TextView ssmc;
    private ImageView ss_detail;
    private TextView sj;
    private ImageView iv_add;
    private Button btn_save;
    private LinearLayout shxz;
    private TextView shsj;
    private LinearLayout sv_data;
    private LinearLayout nodata;
    private AppCompatEditText et_yishi;
    private AppCompatEditText et_xinlv;
    private AppCompatEditText et_huxi;
    private AppCompatEditText et_xueya;
    private AppCompatEditText et_spo2;
    private AppCompatEditText et_wowei;
    private AppCompatEditText et_yinshi;
    private AppCompatEditText et_pifu;
    private AppCompatEditText et_qiekoufl;
    private AppCompatEditText et_yinliuliangitem;
    private AppCompatEditText et_liang;
    private AppCompatEditText et_xingzhuang;
    private AppCompatEditText et_qitaitem;
    private AppCompatEditText et_qitaneirong;
    private TextView tv_qianming;
    private String shijian;
    private int updateType;
    private String itemID;

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

    private SsHuLiDanActivity.DoDecodeThread mDoDecodeThread;

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
        setContentView(R.layout.activity_sshld);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context =this;
        initBeep();
        defineData();
        clickData();
        initData();

        //扫码相关
        mDoDecodeThread = new SsHuLiDanActivity.DoDecodeThread();
        mDoDecodeThread.start();
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

    /**
     * 接收从Dialog传递过来的手术名称
     */
    public void getShouShuMC(String shouShouMC){
        ssmc.setText(shouShouMC);
        listshldMC.clear();
        shxz.setEnabled(true);
        int  flag=0;
        for(int i=0;i<listshld.size();i++){
            if(listshld.get(i).getShouShuMC().equals(shouShouMC)){
                if(!listshld.get(i).getShuHouDate().equals("")&&!listshld.get(i).getShuHouTime().equals("")&&flag==0){
                    SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HuLiJi:mm:ss");
                    SimpleDateFormat format1=new SimpleDateFormat("yyyy/MM/dd ");
                    SimpleDateFormat format2=new SimpleDateFormat("HuLiJi:mm:ss");

                    try {
                        Date date1 = sdf.parse(listshld.get(i).getShuHouDate());
                        Date date2 = sdf.parse(listshld.get(i).getShuHouTime());
                        shsj.setText(format1.format(date1)+format2.format(date2));
                        shxz.setEnabled(false);
                        flag=1;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                ShuHouLD shld =listshld.get(i);
                listshldMC.add(shld);
            }
        }

    }
    private void defineData(){
        ssxz = (LinearLayout) findViewById(R.id.ssxz);
        ssmc = (TextView) findViewById(R.id.ssmc);
        shxz = (LinearLayout) findViewById(R.id.shxz);
        shsj = (TextView) findViewById(R.id.shsj);
        sj =(TextView) findViewById(R.id.sj);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        btn_save = (Button) findViewById(R.id.btn_save);
        back = (ImageView) findViewById(R.id.back);
        layout = (LinearLayout) findViewById(R.id.top);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);
        brlb = MyApplication.getInstance().getOther_brlb();
        ss_detail = (ImageView) findViewById(R.id.iv_shoushuhuli_detail);
        sv_data =(LinearLayout) findViewById(R.id.sv_data);
        nodata =(LinearLayout) findViewById(R.id.nodata);
        et_yishi =(AppCompatEditText) findViewById(R.id.et_yishi);
        et_xinlv =(AppCompatEditText) findViewById(R.id.et_xinlv);
        et_huxi =(AppCompatEditText) findViewById(R.id.et_huxi);
        et_xueya =(AppCompatEditText) findViewById(R.id.et_xueya);
        et_spo2 =(AppCompatEditText) findViewById(R.id.et_spo2);
        et_wowei =(AppCompatEditText) findViewById(R.id.et_wowei);
        et_yinshi =(AppCompatEditText) findViewById(R.id.et_yinshi);
        et_pifu =(AppCompatEditText) findViewById(R.id.et_pifu);
        et_qiekoufl =(AppCompatEditText) findViewById(R.id.et_qiekoufl);
        et_yinliuliangitem =(AppCompatEditText) findViewById(R.id.et_yinliuliangitem);
        et_liang =(AppCompatEditText) findViewById(R.id.et_liang);
        et_xingzhuang =(AppCompatEditText) findViewById(R.id.et_xingzhuang);
        et_qitaitem =(AppCompatEditText) findViewById(R.id.et_qitaitem);
        et_qitaneirong =(AppCompatEditText) findViewById(R.id.et_qitaneirong);
        tv_qianming =(TextView) findViewById(R.id.tv_qianming);
    }
    private void clickData(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "SsHuLiDan");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(context, BrlbActivity.class);
                intent.putExtra("which", "SsHuLiDan");
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
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmptyPageData();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ssmc.getText().toString().equals("请选择")){
                    Toast.makeText(context, "手术名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shsj.getText().toString().equals("请选择")){
                    Toast.makeText(context, "术后时间不能为空", Toast.LENGTH_SHORT).show();
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
        ssxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lx = ssmc.getText().toString();
                ShoushuMCDialog dialog =new ShoushuMCDialog(context,listsscx,lx);
                dialog.show();

            }
        });
        shxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ssmc.getText().toString().equals("请选择")){
                    Toast.makeText(context, "请先选择手术名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/MM/dd HuLiJi:mm:ss");
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        shsj.setText(format.format(date));
                    }
                }).setDate(instance.getCalendarSfm(shsj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(TimePickerView.Type.ALL)
                        //.isDialog(true)
                        .build();
                pvTime.show();

            }
        });
        ss_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ssmc.getText().toString().equals("请选择")){
                    Toast.makeText(context, "请选择手术名称", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Intent detailIntent=new Intent(context,SsHuLiDanDtailActivity.class);
                    detailIntent.putExtra("listShuHouLD", (Serializable) listshldMC);
                    startActivityForResult(detailIntent, REQUEST_CODE);
                }


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
                setAllPageData(listshldMC);

            }
        }
    }
    //加载数据
    private void setAllPageData(List<ShuHouLD> list){
        sj.setText(shijian);
        for(int i=0;i<list.size();i++){
            ShuHouLD shld = list.get(i);
            if(shld.getJLSJ().equals(shijian)){
                et_yishi.setText(shld.getYiShi());
                et_xinlv.setText(shld.getXinLv());
                et_huxi.setText(shld.getHuXi());
                et_xueya.setText(shld.getXueYa());
                et_spo2.setText(shld.getSpo2());
                et_wowei.setText(shld.getWoWei());
                et_yinshi.setText(shld.getYinShi());
                et_pifu.setText(shld.getPiFu());
                et_qiekoufl.setText(shld.getQieKouFL());
                et_yinliuliangitem.setText(shld.getYinLiuLiangITEM());
                et_liang.setText(shld.getLiang());
                et_xingzhuang.setText(shld.getXingZhuang());
                et_qitaitem.setText(shld.getQiTaITEM());
                et_qitaneirong.setText(shld.getQiTaNeiRong());
                tv_qianming.setText(shld.getQianMing());
                sj.setText(shld.getJLSJ());
                itemID = shld.getID();
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
        et_yishi.setText("");
        et_xinlv.setText("");
        et_huxi.setText("");
        et_xueya.setText("");
        et_spo2.setText("");
        et_wowei.setText("");
        et_yinshi.setText("");
        et_pifu.setText("");
        et_qiekoufl.setText("");
        et_yinliuliangitem.setText("");
        et_liang.setText("");
        et_xingzhuang.setText("");
        et_qitaitem.setText("");
        et_qitaneirong.setText("");
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
            num="0601101";
            //更新记录时间到当前
            /*Date currentTime =new Date();
            SimpleDateFormat formatter=new SimpleDateFormat("yyy/MM/dd HuLiJi:mm:ss");
            shijian = formatter.format(currentTime);*/
        }else if(updateType==2){
            s.append(itemID+"¤");
            num="0601102";
        }
        s.append(xml);
        Log.d("-------------",s.toString());
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber(num)
                .setMessage(NetWork.SMTZ)
                .setCanshu(s.toString())
                .setContext(context)
                .setPort(5000)
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
        StringBuilder s=new StringBuilder();
        //1.头部

        s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>"+"\r\n");
        s.append("<DS Name=\"596012\" Num=\"1\">"+"\r\n");
        s.append("<DT Name=\"Table1\" Num=\"1\">"+"\r\n");

        //2.中部
        s.append("<DR Name=\"26820551\" Num=\"19\">"+"\r\n");
        s.append("<DC Name=\"JLSJ\" Num=\"0\">"+shijian+"</DC>"+"\r\n");
        s.append("<DC Name=\"YiShi\" Num=\"0\">"+et_yishi.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"XinLv\" Num=\"0\">"+et_xinlv.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"HuXi\" Num=\"0\">"+et_huxi.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"XueYa\" Num=\"0\">"+et_xueya.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"Spo2\" Num=\"0\">"+et_spo2.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"WoWei\" Num=\"0\">"+et_wowei.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"YinShi\" Num=\"0\">"+et_yinshi.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"PiFu\" Num=\"0\">"+et_pifu.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"QieKouFL\" Num=\"0\">"+et_qiekoufl.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"YinLiuLiangITEM\" Num=\"0\">"+et_yinliuliangitem.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"Liang\" Num=\"0\">"+et_liang.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"XingZhuang\" Num=\"0\">"+et_xingzhuang.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"QiTaITEM\" Num=\"0\">"+et_qitaitem.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"QiTaNeiRong\" Num=\"0\">"+et_qitaneirong.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"QianMing\" Num=\"0\">"+tv_qianming.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"ShouShuMC\" Num=\"0\">"+ssmc.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"ShuHouDate\" Num=\"0\">"+shsj.getText()+"</DC>"+"\r\n");
        s.append("<DC Name=\"ShuHouTime\" Num=\"0\">"+shsj.getText()+"</DC>"+"\r\n");
        s.append("</DR>"+"\r\n");


        //3.尾部
        s.append("</DT>"+"\r\n");
        s.append("</DS>");

        return s.toString();
    }
    private void initData(){
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        bingQuDM=preferences2.getString("bqdm","");
        userID=preferences2.getString("id","");
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
      ;

        listsscx.clear();
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0600503")
                .setMessage(NetWork.SMTZ)
                .setCanshu(zyid)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("zzzz3",data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{SSCX.class});
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
                    if(listsscx.size()==0){
                        sv_data.setVisibility(View.GONE);
                        btn_save.setVisibility(View.GONE);
                        iv_add.setVisibility(View.GONE);
                        ss_detail.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else{
//                        if(TextUtils.isEmpty(listsscx.get(0).getBingRenZYID())){
//                            sv_data.setVisibility(View.GONE);
//                            btn_save.setVisibility(View.GONE);
//                            iv_add.setVisibility(View.GONE);
//                            ss_detail.setVisibility(View.GONE);
//                            nodata.setVisibility(View.VISIBLE);
//                        }else {
                            listshld.clear();
                            SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HuLiJi:mm:ss");
                            Calendar c = Calendar.getInstance();
                            String afterSJ = formatter.format(c.getTime());
                            c.add(Calendar.MONTH, -1);
                            String beforeSJ= formatter.format(c.getTime());
                            zhierCall = (new ZhierCall())
                                    .setId(userID)
                                    .setNumber("0601100")
                                    .setMessage(NetWork.SMTZ)
                                    .setCanshu(zyid+"¤"+beforeSJ+"¤"+afterSJ+"¤"+bingQuDM)
                                    .setContext(context)
                                    .setPort(5000)
                                    .build();
                            zhierCall.start(new NetWork.SocketResult() {
                                @Override
                                public void success(String data) {
                                    Log.d("zzzz4",data);
                                    StringXmlParser parser = new StringXmlParser(xmlHandler1,
                                            new Class[]{ShuHouLD.class});
                                    parser.parse(data);
                                }

                                @Override
                                public void fail(String info) {
                                    Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                                }
                            });
//                        }
                    }


                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            SSCX bean= (SSCX) msg.obj;
                            if(!TextUtils.isEmpty(bean.getBingRenZYID())){
                                listsscx.add((SSCX) msg.obj);
                            }
//                            listsscx.add((SSCX) msg.obj);
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
    MyXmlHandler xmlHandler1=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if(!ssmc.getText().toString().equals("请选择")){
                        listshldMC.clear();
                        for(int i=0;i<listshld.size();i++){
                            if(listshld.get(i).getShouShuMC().equals(ssmc.getText().toString())){
                                ShuHouLD shld =listshld.get(i);
                                listshldMC.add(shld);
                            }
                        }
                        setAllPageData(listshldMC);
                    }else{
                        setEmptyPageData();
                    }


                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listshld.add((ShuHouLD) msg.obj);
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
        }else if(s.length==1){
            playFailBeepSound();
            Toast.makeText(SsHuLiDanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAndChangePatient(String s) {
        List<BRLB> brlbList=new ArrayList<>();
        brlbList=MyApplication.getInstance().getListBRLB();
        boolean isContain=false;
        for (BRLB b:brlbList){
            if(TextUtils.equals(s,b.getBINGRENZYID())){
                zyid=b.getBINGRENZYID();
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
            Toast.makeText(SsHuLiDanActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
        }else {
            playFailBeepSound();
            Toast.makeText(SsHuLiDanActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
        }
    }

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
                return SsHuLiDanActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(SsHuLiDanActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, SsHuLiDanActivity.this.getClass().getName())) {

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
                Toast.makeText(SsHuLiDanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
