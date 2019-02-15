package enjoyor.enjoyorzemobilehealth.activity.infosearch;


import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.BryzActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.JcjgAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.JCJG;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.views.JcjgDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/7/7.
 */

public class JcjgcxActivity extends BaseActivity  implements ScanFactory.FragScan{
    private DateUtil instance;
    private SimpleDateFormat format;
    private TimePickerView pvTime;
    private ZhierCall zhierCall;
    private TextView xm;
    private ImageView tx;
    private TextView cxsj;
    private TextView ch;
    private ListView jcd;
    private ImageView back;
    private LinearLayout nodata;
    private LinearLayout layout;
    private String chaXunSJ;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private String zyid;
    private JcjgDialog dialog;
    private int flag=0;
    private List<JCJG> listJCJG=new ArrayList<JCJG>();
    private JCJG jcjg=null;
    private  BRLB brlb=null;
    private int position;
    private int selectPos=0;
    private String tiaoMa;
    //扫描提示音
    private boolean playBeep=true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;
    private JcjgcxActivity.DoDecodeThread mDoDecodeThread;
    private Handler mDoDecodeHandler;
    ScanInterface scanInf;
    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }
    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_jcjgcx);
        defineData();
        clickData();
        initData();
    }
    private void defineData(){
        xm=(TextView) findViewById(R.id.mz);
        tx=(ImageView) findViewById(R.id.tx);
        ch=(TextView) findViewById(R.id.ch);
        cxsj=(TextView) findViewById(R.id.cxsj);
        jcd=(ListView) findViewById(R.id.jcd);
        back=(ImageView) findViewById(R.id.back);
        layout=(LinearLayout) findViewById(R.id.top);

        nodata=(LinearLayout) findViewById(R.id.nodata);
        brlb=MyApplication.getInstance().getOther_brlb();
    }
    private void clickData(){
        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","6");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(JcjgcxActivity.this,BrlbActivity.class);
                intent.putExtra("which","6");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(JcjgcxActivity.this,XxcxActivity.class));
                finish();
            }
        });
        cxsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance= DateUtil.getInstance();
                format=new SimpleDateFormat("yyyy/M/d");
                //时间选择器
                pvTime = new TimePickerView.Builder(JcjgcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        chaXunSJ=format.format(date);
                        flag=1;
                        initData();
                    }
                }).setDate(instance.getCalendar(cxsj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isDialog(true)
                        .build();
                pvTime.show();

            }
        });
    }
    private void initData(){

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");
        MyApplication instance = MyApplication.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        Date d= null;

        if(flag==1){

        }else if(brlb==null&&flag==0){
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            chaXunSJ =instance.getListBRLB().get(0).getRUYUANSJ().replace('-','/');
            bingRenXM =instance.getListBRLB().get(0).getXINGMING();
            bingRenXB =instance.getListBRLB().get(0).getXINGBIE();
            chuangHao =instance.getListBRLB().get(0).getCHUANGWEIHAO();
            /*try {
                d = formatter.parse(chaXunSJ);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chaXunSJ = format.format(d);*/
        }else if(brlb!=null&&flag==0){
            Intent intent=getIntent();
            zyid=intent.getStringExtra("BINGRENZYID");
            chaXunSJ=intent.getStringExtra("RUYUANSJ").replace('-','/');
            chuangHao=intent.getStringExtra("CHUANGHAO");
            bingRenXM=intent.getStringExtra("XINGMING");
            bingRenXB=intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);
           /* try {
                d = formatter.parse(chaXunSJ);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chaXunSJ = format.format(d);*/
        }
        Log.d("111111111212",chaXunSJ);
        listJCJG.clear();
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0500201")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(zyid+"¤"+chaXunSJ)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JCJG.class});

                Log.v("login11", data);
                parser.parse(data);

            }
            @Override
            public void fail(String info) {
            }

        });
    }
    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    xm.setText(bingRenXM);
                    ch.setText(chuangHao+"床");
                    cxsj.setText(chaXunSJ);
                    if(bingRenXB.equals("男")){
                        tx.setImageResource(R.drawable.icon_men);
                    }else{
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    if (listJCJG.get(0).getYiZhuID()==""){
                        nodata.setVisibility(View.VISIBLE);
                        jcd.setVisibility(View.GONE);
                    }else{
                        nodata.setVisibility(View.GONE);
                        jcd.setVisibility(View.VISIBLE);
                        JcjgAdapter adapter=new JcjgAdapter(JcjgcxActivity.this,listJCJG);
                        jcd.setAdapter(adapter);
                        jcd.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                JCJG jcjg = new JCJG();
                                jcjg.setJianChaXM(listJCJG.get(position).getJianChaXM());
                                jcjg.setJianChaSJ(listJCJG.get(position).getJianChaSJ());
                                jcjg.setZhenDuanJG(listJCJG.get(position).getZhenDuanJG());
                                dialog = new JcjgDialog(JcjgcxActivity.this,jcjg);
                                dialog.show();

                            }
                        });
                    }

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJCJG.add((JCJG) msg.obj);
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
                return JcjgcxActivity.this;
            }
        }.getInstance(4, this, 0);
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
                Toast.makeText(JcjgcxActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            }else{
                initData();
                playSuccessBeepSound();
                Toast.makeText(JcjgcxActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(JcjgcxActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(JcjgcxActivity.this, XxcxActivity.class));
            finish();
            return true;
        }

//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyDown(keyCode, event, JcjgcxActivity.this.getClass().getName())) {
//
//                return true;
//
//            }
//        }

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
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        data= data.trim().replace("��", "¤").replace("?","¤");
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        String[] s1 = data.split("\\¤");
        Pattern pattern = Pattern.compile("^[-+]?[0-9]");

        if(s1[0].equals("st72")){
            zxBR(s[1]);
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
        initSaoMiao();
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

    //千岛湖的广播接收器
    private  final BroadcastReceiver myReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try{
                String action = intent.getAction();
                String data=intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
                if(android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")){
                    data = intent.getStringExtra("decode_rslt");
                }
                if(android.os.Build.MODEL.equals("N1")){
                    data = intent.getStringExtra("msg");
                }
                data= data.trim().replace("��", "¤").replace("?","¤");
                String[] s=data.split("\\*");
                Toast.makeText(JcjgcxActivity.this,data,Toast.LENGTH_SHORT).show();
                System.out.print(data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if(s1[0].equals("st72")){
                    zxBR(s1[1]);
                }else{
                  Toast.makeText(JcjgcxActivity.this,"条码不正确！",Toast.LENGTH_LONG).show();
                }
            }catch(Exception e){
                AlertDialog.Builder builder = new AlertDialog.Builder(JcjgcxActivity.this);
                builder.setMessage("无法得到扫描结果");
                builder.show();
            }
        }


    };

}
