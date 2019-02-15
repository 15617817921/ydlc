package qdh.sxq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.google.gson.Gson;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.BryzActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import my_network.NetWork;
import my_network.ZhierCall;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import qdh.jyhd.HeDuiAdapter;
import qdh.jyhd.HeDuiJLActivity;
import qdh.jyhd.JianYanJG;
import qdh.sd.PostBbeanOut0002;
import qdh.sd.PostBbenIn0006;
import qdh.sd.PostBbenOut0006;
import qdh.sd.PostBeanIn0002;
import qdh.sxbbcj.PostBben;
import qdh.sxbbcj.ResultBben;
import qdh.sxbbcj.SecondCheckUser;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.EXTRA_DECODE_DATA;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by dantevsyou on 2017/12/2.
 */

public class SxqhdActivity extends BaseActivity implements ScanFactory.FragScan {
    private ZhierCall zhierCall;
    private List<JianYanJG> listJianYanJG=new ArrayList<>();
    private static final int REQUEST_CODE = 1; // 请求码
    private LinearLayout nodata;
    private ListView listBgd;
    private TextView tv_hedui;
    private TextView bq;
    private String userName;
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
    private SxqhdActivity.DoDecodeThread mDoDecodeThread;
    private Handler mDoDecodeHandler;
    ScanInterface scanInf;
    String url2="http://192.46.193.25:8080/services/rest/TSBISInterface";
    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sxqhd);

        Intent intent=getIntent();
        selectPos=intent.getIntExtra("position",0);
        initBeep();
        defineData();
        clickData();
        initData();

        TextView textView= (TextView) findViewById(R.id.bc);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SxqhdActivity.this);
                alertBuilder.setTitle("是否确认保存");
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // 关闭提示框
                        alertDialog2.dismiss();
                        String state1=ResultMsg2.get(0).getHasCheck();
                        String state2=ResultMsg2.get(0).getHasStart();
                        String state3=ResultMsg2.get(0).getHasFifteen();
                        String state4=ResultMsg2.get(0).getHasEnd();


                        Gson gson1=new Gson();
                        if(state1.equals("1")){
                            //添加限制条件
                            if(radio_group.equals("输血开始"))
                            {
                                if(state2.equals("0")){
                                    //post
                                    now_bag_code=ResultMsg2.get(0).getBagCode();
                                    /*zhierCall = (new ZhierCall())
                                            .setId(userID)
                                            .setNumber("0306502")
                                            .setMessage(NetWork.GongYong)
                                            .setCanshu(MyApplication.getInstance().getBqdm())
                                            .setContext(SxqhdActivity.this)
                                            .setPort(5000)
                                            .build();
                                    zhierCall.start(new NetWork.SocketResult() {
                                        @Override
                                        public void success(String data) {
                                            String s=data;
                                            StringXmlParser parser = new StringXmlParser(xmlHandler3,
                                                    new Class[]{SecondCheckUser.class});

                                            Log.v("login11", data);
                                            parser.parse(data);
                                        }

                                        @Override
                                        public void fail(String info) {

                                        }
                                    });*/
                                   bbcj2("");
                                }else {
                                    playFailBeepSound();
                                    Toast.makeText(SxqhdActivity.this,"此血袋已经开始",Toast.LENGTH_SHORT).show();
                                }

                            }else if(radio_group.equals("输血15分钟")){
                                if(state2.equals("1")&&state3.equals("0")){
                                    //post
                                    PostBbenIn0007 postBbenIn0007=get007("2",ResultMsg2.get(0).getBagCode(),"");
                                    post0007(gson1.toJson(postBbenIn0007),url2);
                                }else if(state2.equals("0")){
                                    playFailBeepSound();
                                    Toast.makeText(SxqhdActivity.this,"此血袋还未开始",Toast.LENGTH_SHORT).show();
                                }else if(state2.equals("1")&&state3.equals("1")){
                                    playFailBeepSound();
                                    Toast.makeText(SxqhdActivity.this,"此血袋15分钟以操作",Toast.LENGTH_SHORT).show();
                                }

                            }else if(radio_group.equals("输血结束")){
                                if((state2.equals("1")&&state3.equals("1")&&state4.equals("0"))||(state2.equals("1")&&state3.equals("0")&&state4.equals("0"))){
                                    //post
                                    PostBbenIn0007 postBbenIn0007=get007("3",ResultMsg2.get(0).getBagCode(),"");
                                    post0007(gson1.toJson(postBbenIn0007),url2);
                                }else if(state2.equals("0")){
                                    playFailBeepSound();
                                    Toast.makeText(SxqhdActivity.this,"此血袋还未开始",Toast.LENGTH_SHORT).show();
                                }else if(state2.equals("1")&&state4.equals("1")){
                                    playFailBeepSound();
                                    Toast.makeText(SxqhdActivity.this,"此血袋已经结束",Toast.LENGTH_SHORT).show();
                                }

                                       /* if(state2.equals("1")&&state3.equals("0")&&state4.equals("0")){
                                            //post
                                            PostBbenIn0007 postBbenIn0007=get007("3",ResultMsg.get(0).getBagCode(),"");
                                            post0007(gson1.toJson(postBbenIn0007),url2);
                                        } else {

                                        }*/

                            }
                        }else {
                            playFailBeepSound();
                            Toast.makeText(SxqhdActivity.this,"此血袋未复核",Toast.LENGTH_LONG).show();
                        }


                    }
                });
                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO 业务逻辑代码

                        // 关闭提示框
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2 = alertBuilder.create();
                alertDialog2.show();
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                radio_group=String.valueOf(radioButton.getText());
                if(radio_group.equals("输血开始")){

                    textView3.setVisibility(View.VISIBLE);
                    spinner1.setVisibility(View.VISIBLE);
                    spinner2.setVisibility(View.VISIBLE);

                    textView4.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.VISIBLE);
                }else if(radio_group.equals("输血15分钟")){

                    textView3.setVisibility(View.GONE);
                    spinner1.setVisibility(View.GONE);
                    spinner2.setVisibility(View.GONE);

                    textView4.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                }else if(radio_group.equals("输血结束")){

                    textView3.setVisibility(View.GONE);
                    spinner1.setVisibility(View.GONE);
                    spinner2.setVisibility(View.GONE);

                    textView4.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                }
            }
        });

        initSmtz();


        list2.add("");
        list2.add("左");
        list2.add("右");


        s_spinner2=list2.get(0);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_spinner2=list2.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
    List<String> list1=new ArrayList<>();
    List<String> list2=new ArrayList<>();
    List<String> list3=new ArrayList<>();
    String s_spinner1="";
    String s_spinner2="";
    String s_spinner3="";


    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7,editText8,editText9,editText10;
    Spinner spinner1,spinner2,spinner3;

    TextView textView1,textView2,textView3,textView4;
    private void initSmtz(){
        editText1= (EditText) findViewById(R.id.hs1);
        editText2= (EditText) findViewById(R.id.jz2);
        editText3= (EditText) findViewById(R.id.xd3);
        editText4= (EditText) findViewById(R.id.ks4);
        editText5= (EditText) findViewById(R.id.xl5);
        editText6= (EditText) findViewById(R.id.tw6);
        editText7= (EditText) findViewById(R.id.hx7);
        editText8= (EditText) findViewById(R.id.yq8);
        editText9= (EditText) findViewById(R.id.xy9);
        editText10= (EditText) findViewById(R.id.xy10);

        spinner1= (Spinner) findViewById(R.id.spinner1);
        spinner2= (Spinner) findViewById(R.id.spinner2);
        spinner3= (Spinner) findViewById(R.id.spinner3);

        textView1= (TextView) findViewById(R.id.jzz);
        textView2= (TextView) findViewById(R.id.kssj);
        textView3= (TextView) findViewById(R.id.szfs);
        textView4= (TextView) findViewById(R.id.sxqyy);



    }
    String radio_group="输血开始";

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

    private void defineData(){
        nodata=(LinearLayout) findViewById(R.id.nodata);
        listBgd=(ListView) findViewById(R.id.bgd);
        tv_hedui=(TextView) findViewById(R.id.tv_hedui);
        bq=(TextView) findViewById(R.id.bq);
        userID = MyApplication.getInstance().getYhgh();
        userName = MyApplication.getInstance().getYhxm();
        back = (ImageView) findViewById(R.id.back);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new SxqhdActivity.DoDecodeThread();
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
                return SxqhdActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(SxqhdActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, SxqhdActivity.this.getClass().getName())) {

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
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        if(s[0].equals("st72")){
            zxBR(s[1]);
        }else if(s[0].equals("st99")){
            ypZX(s[2]);
            //Toast.makeText(BryzLianjianActivity.this,data,Toast.LENGTH_LONG).show();
        }
//            //核对病人列表并更新数据
//            checkBRLBAndUpdate(zyid);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        this.registerReceiver(myReceiver, filter1);
        //湛江注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.jaway.action.DECODE_RESULT");
        this.registerReceiver(myReceiver, filter);
        //千岛湖广播注册
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("shmaker.android.intent.action.SCANER_DECODE_DATA");
        this.registerReceiver(myReceiver, filter2);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(myReceiver);
    }

    //廉江扫描的广播接收器
    private  final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //代码处理
            String text = intent.getStringExtra(EXTRA_DECODE_DATA);
            //Toast.makeText(BryzLianjianActivity.this,text,Toast.LENGTH_LONG).show();
            String data=text;
            String[] s=data.split("\\|");
            //Toast.makeText(BryzLianjianActivity.this,text+"大小："+s.length,Toast.LENGTH_LONG).show();
            System.out.print(data);
            //st72是腕带，切换病人


            //核心------------
            if(s.length>=4){
                zxBR(s[1].trim());
            }else if(s.length==1){
                //ypZX(s[0].trim());
                //Toast.makeText(BryzLianjianActivity.this,data,Toast.LENGTH_LONG).show();
                if(s[0].trim().length()<=8){
                    //扫血袋
                    xdhd(s[0].trim());
                }

            }
        }
    };

    //输血核心
    private void sxdh(String code){
        PostBbenIn0006 postBbenIn0006=new PostBbenIn0006();
        postBbenIn0006.setTranCode("0007");
        //
        PostBbenIn0006.ArgsBean argsBean=new PostBbenIn0006.ArgsBean();

    }

    private void bbcj2(final String barCode){
        LayoutInflater factory = LayoutInflater.from(SxqhdActivity.this);//提示框
        final View view = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
        final EditText edit=(EditText)view.findViewById(R.id.editText);//获得输入框对象

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder
                .setTitle("请输入工号")//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s= String.valueOf(edit.getText());
                                if(s.equals("")){
                                    Toast.makeText(SxqhdActivity.this,"请输入正确工号",Toast.LENGTH_SHORT).show();
                                }else if(MyApplication.getInstance().getYhgh().equals(s)){
                                    Toast.makeText(SxqhdActivity.this,"执行者和见证者工号相同",Toast.LENGTH_SHORT).show();
                                }else {

                                    bbcj_in(barCode,s);
                                }

                            }
                        }).setNegativeButton("取消", null).create().show();
    }

    private void bbcj_in(String barCode,String s){

                    cs1=barCode;
                    cs2=MyApplication.getInstance().getYhgh();
                    zhierCall = (new ZhierCall())
                            .setId(userID)
                            .setNumber("0306502")
                            .setMessage(NetWork.GongYong)
                            .setCanshu(s)
                            .setContext(this)
                            .setPort(5000).setDialogmes("加载中...")
                            .build();
                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {
                            String s=data;
                        /*String json= (new XmlToJson.Builder(data).build()).toJson().toString();
                        Gson gson=new Gson();
                        SecondCheckUser secondCheckUser=gson.fromJson(json,SecondCheckUser.class);*/
                            //选择核对人
                            //showSingleAlertDialog();
                            //List<SecondCheckUser.DSBean.DTBean.DRBean.DCBean> names=secondCheckUser.getDS().getDT().getDR().get(0).getDC();
                            second_list=null;
                            second_list=new ArrayList<>();
                            StringXmlParser parser = new StringXmlParser(xmlHandler4,
                                    new Class[]{SecondCheckUser.class});

                            Log.v("login11", data);
                            parser.parse(data);
                        }

                        @Override
                        public void fail(String info) {
                            Toast.makeText(SxqhdActivity.this,"获取不到第二执行人目录",Toast.LENGTH_LONG).show();
                            playFailBeepSound();
                        }
                    });
                }


    MyXmlHandler xmlHandler4=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    //

                    if(second_list.size()!=0&&!(second_list.get(0).getYongHuGH().equals(""))){
                        //TODO 业务逻辑代码
                        //String kk=second_list.get(index2[0]).getYongHuMM();
                        //cs3=kk;

                        //事件
                        String inputName = second_list.get(0).getYongHuGH();
                        if(!(inputName.equals(""))){

                            String second_name="";
                            for(SecondCheckUser s:second_list){
                                if(s.getYongHuGH().equals(inputName)){
                                    second_name=s.getYongHuXM();
                                    break;
                                }
                            }
                            ///
                            if(second_name.equals("")){
                                Toast.makeText(SxqhdActivity.this,"请输入正确工号",Toast.LENGTH_SHORT).show();
                            }else{
                                //TODO 业务逻辑代码
                                //String kk=second_list.get(index2[0]).getYongHuMM();
                                //cs3=kk;

                                //String kk=second_list.get(index2[0]).getYongHuMM();
                                Gson gson=new Gson();
                                PostBbenIn0007 postBbenIn0007=get007("1",now_bag_code,second_name+"|"+inputName);
                                post0007(gson.toJson(postBbenIn0007),url2);
                                //
                            }
                            //this.dismiss();
                            ///
                            //alertDialog2.dismiss();
                        }else{
                            Toast.makeText(SxqhdActivity.this,"工号为空",Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(SxqhdActivity.this,"请输入正确工号",Toast.LENGTH_SHORT).show();
                    }
                    //
                    second_list=null;
                    second_list=new ArrayList<>();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            SecondCheckUser bean=(SecondCheckUser) msg.obj;
                            second_list.add(bean);
                            Log.d("fafdafasdf","fafafa111166");
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

    int state=0;
    //血袋信息获取
    private void xdhd(String bagCode){
        if(stock_out_no.equals(""))
        {
            PostBeanIn0002 postBeanIn0002=new PostBeanIn0002();
            postBeanIn0002.setTranCode("0002");
            //
            PostBeanIn0002.ArgsBean argsBean=new PostBeanIn0002.ArgsBean();
            argsBean.setPatientId(MyApplication.getInstance().getListBRLB().get(selectPos).getBINGRENZYID());
            argsBean.setStockOutNo("");
            argsBean.setBagCode(bagCode.trim());
            postBeanIn0002.setArgs(argsBean);
            //
            Gson gson=new Gson();
            httpPost(gson.toJson(postBeanIn0002),url2);

        }else{
            playFailBeepSound();
            Toast.makeText(SxqhdActivity.this,"请先扫描出库单",Toast.LENGTH_LONG).show();
        }
    }

    String stock_out_no="";
    //获取当前出库血袋信息
    private void xdxx(String tranCode){
        //url
        String url=url2;
        //body
        PostBeanIn0002 postBeanIn0002=new PostBeanIn0002();
        postBeanIn0002.setTranCode("0002");
        PostBeanIn0002.ArgsBean argsBean=new PostBeanIn0002.ArgsBean();
        //病人id
        argsBean.setPatientId(MyApplication.getInstance().getListBRLB().get(selectPos).getBINGRENZYID());
        argsBean.setStockOutNo(tranCode.trim());
        postBeanIn0002.setArgs(argsBean);
        //gson
        Gson gson=new Gson();
        httpPost(gson.toJson(postBeanIn0002),url);
    }

    public  void  showSingleAlertDialog2(final String[] items2, final ArrayList<SecondCheckUser> second_list){
        LayoutInflater factory = LayoutInflater.from(SxqhdActivity.this);//提示框
        final View view = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
        final EditText edit=(EditText)view.findViewById(R.id.editText);//获得输入框对象

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder
                .setTitle("请输入工号")//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //事件
                                String inputName = edit.getText().toString();
                                if(!(inputName.equals(""))){

                                    String second_name="";
                                    for(SecondCheckUser s:second_list){
                                        if(s.getYongHuGH().equals(inputName)){
                                            second_name=s.getYongHuXM();
                                            break;
                                        }
                                    }
                                    ///
                                    if(second_name.equals("")){
                                        Toast.makeText(SxqhdActivity.this,"请输入正确工号",Toast.LENGTH_SHORT).show();
                                    }else{
                                        //TODO 业务逻辑代码
                                        //String kk=second_list.get(index2[0]).getYongHuMM();
                                        //cs3=kk;

                                        //String kk=second_list.get(index2[0]).getYongHuMM();
                                        Gson gson=new Gson();
                                        PostBbenIn0007 postBbenIn0007=get007("1",now_bag_code,second_name+"|"+inputName);
                                        post0007(gson.toJson(postBbenIn0007),url2);
                                        //
                                    }
                                    //this.dismiss();
                                    ///
                                    //alertDialog2.dismiss();
                                }else{
                                    Toast.makeText(SxqhdActivity.this,"工号为空",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消", null).create().show();
    }

    private List<ResultBben.MessageBean> Message=new ArrayList<>();
    String cs1="";
    String cs2="";
    String cs3 = "";
    String cs4="";
    //输血标本采集
    private void bbcj(String barCode){

        int b=Message.size();
        for(int i=0;i<Message.size();i++){
            if(Message.get(i).getBarCode().equals(barCode)){
                if(Message.get(i).getSampleState().equals("1")){
                    cs1=barCode;
                    cs2=MyApplication.getInstance().getYhgh();
                    zhierCall = (new ZhierCall())
                            .setId(userID)
                            .setNumber("0306502")
                            .setMessage(NetWork.GongYong)
                            .setCanshu(MyApplication.getInstance().getBqdm())
                            .setContext(this)
                            .setPort(5000).setDialogmes("加载中...")
                            .build();
                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {
                            String s=data;
                        /*String json= (new XmlToJson.Builder(data).build()).toJson().toString();
                        Gson gson=new Gson();
                        SecondCheckUser secondCheckUser=gson.fromJson(json,SecondCheckUser.class);*/
                            //选择核对人
                            //showSingleAlertDialog();
                            //List<SecondCheckUser.DSBean.DTBean.DRBean.DCBean> names=secondCheckUser.getDS().getDT().getDR().get(0).getDC();
                            StringXmlParser parser = new StringXmlParser(xmlHandler3,
                                    new Class[]{SecondCheckUser.class});

                            Log.v("login11", data);
                            parser.parse(data);
                        }

                        @Override
                        public void fail(String info) {

                        }
                    });
                }else{
                    Toast.makeText(SxqhdActivity.this,"此条码已经执行",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    ArrayList<SecondCheckUser> second_list=new ArrayList<>();
    MyXmlHandler xmlHandler3=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(android.os.Message msg) {
            switch (msg.what) {
                case END:
                    String[] results=new String[second_list.size()];
                    for(int i=0;i<results.length;i++){
                        results[i]=second_list.get(i).getYongHuXM();
                    }
                    showSingleAlertDialog2(results,second_list);
                    second_list=null;
                    second_list=new ArrayList<>();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            SecondCheckUser bean=(SecondCheckUser) msg.obj;
                            second_list.add(bean);
                            Log.d("fafdafasdf","fafafa111166");
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
    // 单选提示框
    private AlertDialog alertDialog2;
    private String now_bag_code="";
    public void showSingleAlertDialog(String[] items2, final ArrayList<SecondCheckUser> second_list){
        final String[] items = items2;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择执行人");
        final int[] index2 = {0};
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int index) {
                //Toast.makeText(SxqhdActivity.this, items[index], Toast.LENGTH_SHORT).show();
               index2[0] =index;
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TODO 业务逻辑代码
                String kk=second_list.get(index2[0]).getYongHuXM();
                Gson gson=new Gson();
                PostBbenIn0007 postBbenIn0007=get007("1",now_bag_code,items[index2[0]]+"|"+kk);
                post0007(gson.toJson(postBbenIn0007),url2);

                // 关闭提示框
                alertDialog2.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO 业务逻辑代码

                // 关闭提示框
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
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
                Toast.makeText(SxqhdActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            }else{
                initData();
                playSuccessBeepSound();
                Toast.makeText(SxqhdActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(SxqhdActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
        }
    }

    private void ypZX(String tm){
        tiaoMa=tm;
        int flag=0;
        for(int i=0;i<listJianYanJG.size();i++){
            if(listJianYanJG.get(i).getTiaoMaID().equals(tiaoMa)){
                flag=1;
            }
        }
        if(flag==0){
            playFailBeepSound();
            Toast.makeText(SxqhdActivity .this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
        }else{
            checkData();
        }

    }

    private void clickData(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SxqhdActivity.this,HomePageActivity.class));
                finish();
            }
        });
        tv_hedui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiaoMa="127";
                int flag=0;
                for(int i=0;i<listJianYanJG.size();i++){
                    if(listJianYanJG.get(i).getTiaoMaID().equals(tiaoMa)){
                        flag=1;
                    }
                }
                if(flag==0){
                    Toast.makeText(SxqhdActivity .this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
                    return;
                }
                checkData();
            }
        });
        bq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SxqhdActivity.this,HeDuiJLActivity.class));
                finish();
            }
        });
        xm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SxqhdActivity.this,BrlbActivity.class);
                intent.putExtra("which","sxq");
                startActivity(intent);
                finish();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                selectPos = data.getIntExtra("position", 0);
                initData();
            }
        }

        //url
        String url="http://192.46.193.25:9090/services/rest/TSBISInterface/";
        //body
        PostBben postBben=new PostBben();
        postBben.setTranCode("0001");
        PostBben.ArgsBean argsBean=new PostBben.ArgsBean();
        argsBean.setPatientId(MyApplication.getInstance().getListBRLB().get(selectPos).getBINGRENZYID());
        //argsBean.setWardCode(MyApplication.getInstance().getBqdm());
        postBben.setArgs(argsBean);
        //gson
        Gson gson=new Gson();
        httpPost2(gson.toJson(postBben),url);
    }

    //http请求
    private final OkHttpClient client = new OkHttpClient();
    private void httpData(final String url){

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }
                }catch (Exception e){
                    Toast.makeText(SxqhdActivity.this,"获取不到数据",Toast.LENGTH_LONG).show();
                }

                String s="";
                subscriber.onNext(s);

            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SxqhdActivity.this,"获取不到数据",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(s!=null){
                            Gson gson2 = new Gson();
                            //类型1 s=gson2.fromJson(s,类型1.class);

                        }
                    }
                });

    }

    private void httpPost4(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, post))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    /*if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }*/
                    String body=response.body().string();
                    if(!(body.equals(""))){
                        subscriber.onNext(body);
                    }

                }catch (Exception e){
                    String ss=e.toString();
                    Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    subscriber.onNext("");
                }


            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();
                            PostBbenOut0009 postBbenOut0009=gson.fromJson(s,PostBbenOut0009.class);
                            if(postBbenOut0009.getState()==1){
                                //核心
                                list3.add("");
                                for(PostBbenOut0009.MessageBean b:postBbenOut0009.getMessage()){
                                    list3.add(b.getDataName());
                                }
                                if(list3.size()==0){
                                    s_spinner3=list3.get(0);
                                }
                                ArrayAdapter<String> adapter3=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner3.setAdapter(adapter3);
                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        s_spinner3=list3.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }else{
                                Toast.makeText(SxqhdActivity.this,"错误",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private void httpPost3(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, post))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    /*if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }*/
                    String body=response.body().string();
                    if(!(body.equals(""))){
                        subscriber.onNext(body);
                    }

                }catch (Exception e){
                    String ss=e.toString();
                    Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    subscriber.onNext("");
                }


            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();
                            PostBbenOut0009 postBbenOut0009=gson.fromJson(s,PostBbenOut0009.class);
                            if(postBbenOut0009.getState()==1){
                                //核心
                                list1.add("");
                                for(PostBbenOut0009.MessageBean b:postBbenOut0009.getMessage()){
                                    list1.add(b.getDataName());
                                }
                                if(list1.size()!=0){
                                    s_spinner1=list1.get(0);
                                }
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        s_spinner1=list1.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }else{
                                Toast.makeText(SxqhdActivity.this,"错误",Toast.LENGTH_LONG).show();
                            }

                            //

                        }

                    }
                });
    }


    private void httpPost2(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, post))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    /*if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }*/
                    String body=response.body().string();
                    if(!(body.equals(""))){
                        subscriber.onNext(body);
                    }

                }catch (Exception e){
                    String ss=e.toString();
                    Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    subscriber.onNext("");
                }


            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();
                            PostBbenOut0006 postBbenOut0006=gson.fromJson(s,PostBbenOut0006.class);
                            if(postBbenOut0006.getState()==1)
                            {
                                Toast.makeText(SxqhdActivity.this,"执行成功",Toast.LENGTH_LONG).show();
                                //刷新
                                xdxx(stock_out_no);
                            }else{
                                Toast.makeText(SxqhdActivity.this,postBbenOut0006.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }


    List<PostBbeanOut0002.MessageBean> ResultMsg2=new ArrayList<>();
    private void httpPost(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, post))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    /*if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }*/
                    String body=response.body().string();
                    if(!(body.equals(""))){
                        subscriber.onNext(body);
                    }

                }catch (Exception e){
                    String ss=e.toString();
                    Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    subscriber.onNext("");
                }


            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(SxqhdActivity.this,"",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();
                            PostBbeanOut0002 postBbeanOut0002=gson.fromJson(s,PostBbeanOut0002.class);
                            if(postBbeanOut0002.getState()==1&&postBbeanOut0002.getMessage().size()!=0){
                                listBgd.setVisibility(View.VISIBLE);
                                nodata.setVisibility(View.GONE);
                                List<PostBbeanOut0002.MessageBean> ResultMsg=postBbeanOut0002.getMessage();

                                ResultMsg2=null;
                                ResultMsg2=new ArrayList<>();

                                ResultMsg2=postBbeanOut0002.getMessage();
                                listBgd.setAdapter(new CommonAdapter<PostBbeanOut0002.MessageBean>(SxqhdActivity.this,R.layout.layout_sxqhd_item,ResultMsg) {
                                    @Override
                                    protected void convert(ViewHolder viewHolder, PostBbeanOut0002.MessageBean item, int position) {
                                        viewHolder.setText(R.id.lx,item.getBloodName());
                                        //viewHolder.setText(R.id.jynm,item.getBloodAmount()+item.getBloodUnit());
                                        viewHolder.setText(R.id.jysj,item.getBloodAmount()+item.getBloodUnit());
                                        viewHolder.setText(R.id.jysj2,item.getAboName()+"|"+item.getRhName());
                                        viewHolder.setText(R.id.bbtm,item.getStockOutNo());

                                        TextView textView_brxx= (TextView) findViewById(R.id.brxx);
                                        textView_brxx.setText("病人血型："+item.getPatientAboName()+"|"+item.getPatientRhName());
                                        //editText5.setFocusable(true);
                                        //editText5.setFocusable(false);
                                    }
                                });
                                //state=1;
                                //stock_out_no=ResultMsg.get(0).getStockOutNo();

                                LinearLayout linearLayout= (LinearLayout) findViewById(R.id.all);
                                linearLayout.setVisibility(View.VISIBLE);

                                //spinner
                                //核心
                                PostBbenIn0009 postBbenIn1=new PostBbenIn0009();
                                postBbenIn1.setTranCode("0009");
                                //
                                PostBbenIn0009.ArgsBean argsBean=new PostBbenIn0009.ArgsBean();
                                argsBean.setDataType("infusionMethod");
                                postBbenIn1.setArgs(argsBean);


                                PostBbenIn0009 postBbenIn2=new PostBbenIn0009();
                                postBbenIn2.setTranCode("0009");
                                //
                                PostBbenIn0009.ArgsBean argsBean2=new PostBbenIn0009.ArgsBean();
                                argsBean2.setDataType("preTransfusionMedicine");
                                postBbenIn2.setArgs(argsBean2);

                                Gson gson1=new Gson();
                                httpPost3(gson1.toJson(postBbenIn1,PostBbenIn0009.class),url);
                                httpPost4(gson1.toJson(postBbenIn2,PostBbenIn0009.class),url);
/*
                                String state1=ResultMsg.get(0).getHasCheck();
                                String state2=ResultMsg.get(0).getHasStart();
                                String state3=ResultMsg.get(0).getHasFifteen();
                                String state4=ResultMsg.get(0).getHasEnd();


                                Gson gson1=new Gson();
                                if(state1.equals("1")){
                                    //添加限制条件
                                    if(radio_group.equals("输血开始"))
                                    {
                                       if(state2.equals("0")){
                                           //post
                                           now_bag_code=ResultMsg.get(0).getBagCode();
                                           zhierCall = (new ZhierCall())
                                                   .setId(userID)
                                                   .setNumber("0306502")
                                                   .setMessage(NetWork.GongYong)
                                                   .setCanshu(MyApplication.getInstance().getBqdm())
                                                   .setContext(SxqhdActivity.this)
                                                   .setPort(5000)
                                                   .build();
                                           zhierCall.start(new NetWork.SocketResult() {
                                               @Override
                                               public void success(String data) {
                                                   String s=data;
                                                   StringXmlParser parser = new StringXmlParser(xmlHandler3,
                                                           new Class[]{SecondCheckUser.class});

                                                   Log.v("login11", data);
                                                   parser.parse(data);
                                               }

                                               @Override
                                               public void fail(String info) {

                                               }
                                           });
                                       }else {
                                           playFailBeepSound();
                                           Toast.makeText(SxqhdActivity.this,"此血袋已经开始",Toast.LENGTH_SHORT).show();
                                       }

                                    }else if(radio_group.equals("输血15分钟")){
                                        if(state2.equals("1")&&state3.equals("0")){
                                            //post
                                            PostBbenIn0007 postBbenIn0007=get007("2",ResultMsg.get(0).getBagCode(),"");
                                            post0007(gson1.toJson(postBbenIn0007),url2);
                                        }else if(state2.equals("0")){
                                            playFailBeepSound();
                                            Toast.makeText(SxqhdActivity.this,"此血袋还未开始",Toast.LENGTH_SHORT).show();
                                        }else if(state2.equals("1")&&state3.equals("1")){
                                            playFailBeepSound();
                                            Toast.makeText(SxqhdActivity.this,"此血袋15分钟以操作",Toast.LENGTH_SHORT).show();
                                        }

                                    }else if(radio_group.equals("输血结束")){
                                        if((state2.equals("1")&&state3.equals("1")&&state4.equals("0"))||(state2.equals("1")&&state3.equals("0")&&state4.equals("0"))){
                                            //post
                                            PostBbenIn0007 postBbenIn0007=get007("3",ResultMsg.get(0).getBagCode(),"");
                                            post0007(gson1.toJson(postBbenIn0007),url2);
                                        }else if(state2.equals("0")){
                                            playFailBeepSound();
                                            Toast.makeText(SxqhdActivity.this,"此血袋还未开始",Toast.LENGTH_SHORT).show();
                                        }else if(state2.equals("1")&&state4.equals("1")){
                                            playFailBeepSound();
                                            Toast.makeText(SxqhdActivity.this,"此血袋已经结束",Toast.LENGTH_SHORT).show();
                                        }

                                       *//* if(state2.equals("1")&&state3.equals("0")&&state4.equals("0")){
                                            //post
                                            PostBbenIn0007 postBbenIn0007=get007("3",ResultMsg.get(0).getBagCode(),"");
                                            post0007(gson1.toJson(postBbenIn0007),url2);
                                        } else {

                                        }*//*

                                    }
                                }else {
                                    playFailBeepSound();
                                    Toast.makeText(SxqhdActivity.this,"此血袋未复核",Toast.LENGTH_LONG).show();
                                }*/


                                //输入生命体征，然后回传


                            }else {
                                playFailBeepSound();
                                listBgd.setVisibility(View.GONE);
                                nodata.setVisibility(View.VISIBLE);
                                Toast.makeText(SxqhdActivity.this,"此出库单号和病人不匹配",Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });
    }

    private PostBbenIn0007 get007(String s1,String s2,String s3){
        PostBbenIn0007 postBbenIn0007=new PostBbenIn0007();
        postBbenIn0007.setTranCode("0007");
        //
        PostBbenIn0007.ArgsBean argsBean=new PostBbenIn0007.ArgsBean();
        argsBean.setStepCode(s1);
        argsBean.setBagCode(s2);
        argsBean.setFirstCheckUser(MyApplication.getInstance().getYhxm()+"|"+MyApplication.getInstance().getYhgh());
        argsBean.setSecondCheckUser(s3);
        //
        argsBean.setTemperature(String.valueOf(editText6.getText()));

        argsBean.setBloodPressHigh(String.valueOf(editText9.getText()));
        argsBean.setBloodPressLow(String.valueOf(editText10.getText()));

        argsBean.setHeartRate(String.valueOf(editText5.getText()));
        argsBean.setBreathing(String.valueOf(editText7.getText()));
        argsBean.setBloodOxygen(String.valueOf(editText8.getText()));
        //
        argsBean.setInfusionMethod(s_spinner3);
        argsBean.setInfusionPosition(s_spinner2);
        argsBean.setTransfusionMedicine(s_spinner1);
        //
        postBbenIn0007.setArgs(argsBean);
        //
        return postBbenIn0007;
    }

    private void post0007(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, post))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    /*if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }*/
                    String body=response.body().string();
                    if(!(body.equals(""))){
                        subscriber.onNext(body);
                    }

                }catch (Exception e){
                    String ss=e.toString();
                    Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    subscriber.onNext("");
                }


            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        playFailBeepSound();
                        Toast.makeText(SxqhdActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();

                            PostBbenOut0007 postBbenOut0007=new PostBbenOut0007();
                            postBbenOut0007=gson.fromJson(s,PostBbenOut0007.class);
                            if(postBbenOut0007.getState()==1){
                                LinearLayout linearLayout= (LinearLayout) findViewById(R.id.all);
                                linearLayout.setVisibility(View.GONE);
                                editText1.setText("");
                                editText2.setText("");
                                editText3.setText("");
                                editText4.setText("");
                                editText5.setText("");
                                editText6.setText("");
                                editText7.setText("");
                                editText8.setText("");
                                editText9.setText("");
                                editText10.setText("");
                                //
                                List<String> list1=new ArrayList<>();
                                list1.add("");
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter);

                                //ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter);

                                //ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner3.setAdapter(adapter);
                                //
                                playSuccessBeepSound();
                                Toast.makeText(SxqhdActivity.this,postBbenOut0007.getMessage(),Toast.LENGTH_LONG).show();
                            }else{
                                LinearLayout linearLayout= (LinearLayout) findViewById(R.id.all);
                                linearLayout.setVisibility(View.GONE);
                                editText1.setText("");
                                editText2.setText("");
                                editText3.setText("");
                                editText4.setText("");
                                editText5.setText("");
                                editText6.setText("");
                                editText7.setText("");
                                editText8.setText("");
                                editText9.setText("");
                                editText10.setText("");
                                //
                                List<String> list1=new ArrayList<>();
                                list1.add("");
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter);

                                //ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter);

                                //ArrayAdapter<String> adapter=new ArrayAdapter<String>(SxqhdActivity.this, android.R.layout.simple_spinner_item,list1);
                                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner3.setAdapter(adapter);
                                //
                                playFailBeepSound();
                                Toast.makeText(SxqhdActivity.this,postBbenOut0007.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });
    }
    private void initData(){
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        bingQuDM=preferences2.getString("bqdm","");
        // userID=preferences2.getString("id","");
        MyApplication instance =MyApplication.getInstance();
        zyid = instance.getListBRLB().get(selectPos).getBINGRENZYID();
        bingRenXM =instance.getListBRLB().get(selectPos).getXINGMING();
        bingRenXB =instance.getListBRLB().get(selectPos).getXINGBIE();
        chuangHao =instance.getListBRLB().get(selectPos).getCHUANGWEIHAO();
        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        xm.setText(bingRenXM);
        ch.setText(chuangHao + "床");
        listJianYanJG.clear();
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401506")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(zyid)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        /*zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JianYanJG.class});

                Log.v("login11", data);
                parser.parse(data);

            }

            @Override
            public void fail(String info) {
                Toast.makeText(SxqhdActivity .this, info, Toast.LENGTH_LONG).show();
            }

        });*/
    }

    private void checkData(){

        String canshu=zyid+"¤"+tiaoMa+"¤"+userName+"¤"+userID;
        Log.d("fadafsd",canshu);
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0401507")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                Toast.makeText(SxqhdActivity .this, "核对成功", Toast.LENGTH_LONG).show();
                initData();

            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(SxqhdActivity .this, info, Toast.LENGTH_LONG).show();
            }

        });
    }
    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(android.os.Message msg) {
            switch (msg.what) {
                case END:
                    if(listJianYanJG.size()==0){
                        listBgd.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else{
                        listBgd.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        HeDuiAdapter adapter=new HeDuiAdapter(SxqhdActivity.this,listJianYanJG, zyid, tiaoMa, userName, userID);
                        listBgd.setAdapter(adapter);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            JianYanJG bean=(JianYanJG) msg.obj;
                            if(!TextUtils.isEmpty(bean.getBingRenZYID())){
                                listJianYanJG.add((JianYanJG) msg.obj);
                            }
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

    //
    //public abstract void iniView();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
