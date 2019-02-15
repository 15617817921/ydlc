package qdh.sxbbsc;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import qdh.sxbbcj.PostBben;
import qdh.sxbbcj.PostBben003;
import qdh.sxbbcj.PostBbenResult0004;
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
 * Created by dantevsyou on 2017/12/1.
 */

public class SxbbscActivity extends BaseActivity implements ScanFactory.FragScan {
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
    private SxbbscActivity.DoDecodeThread mDoDecodeThread;
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
        setContentView(R.layout.activity_sxbbsc);

        Intent intent=getIntent();
        selectPos=intent.getIntExtra("position",0);
        initBeep();
        defineData();
        clickData();
        initData();

        //url
        String url=url2;
        //body
        PostBben postBben=new PostBben();
        postBben.setTranCode("0001");
        PostBben.ArgsBean argsBean=new PostBben.ArgsBean();
        argsBean.setPatientId("");
        argsBean.setWardCode(MyApplication.getInstance().getBqdm());
        postBben.setArgs(argsBean);
        //gson
        Gson gson=new Gson();
        httpPost(gson.toJson(postBben),url);
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
            mDoDecodeThread = new SxbbscActivity.DoDecodeThread();
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
                return SxbbscActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(SxbbscActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, SxbbscActivity.this.getClass().getName())) {

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

            if(s.length>=4){
                zxBR(s[1].trim());
            }else if(s.length==1){
                //ypZX(s[0].trim());
                //Toast.makeText(BryzLianjianActivity.this,data,Toast.LENGTH_LONG).show();
                int i=0;
                for(ResultBben.MessageBean m:Message){
                    if(m.equals(s[0].trim())){
                        bbcj(s[0].trim());
                        i=1;
                    }
                }
                if(i==0){
                    playFailBeepSound();
                    Toast.makeText(SxbbscActivity.this,"此病区没有此标本标签",Toast.LENGTH_SHORT).show();
                }


            }
        }
    };
    private List<ResultBben.MessageBean> Message=new ArrayList<>();
    String cs1="";
    String cs2="";
    String cs3 = "";
    //输血标本送出
    private void bbcj(String barCode){

        int b=Message.size();
        for(int i=0;i<Message.size();i++){
            if(Message.get(i).getBarCode().equals(barCode)){
                if(Message.get(i).getSampleState().equals("2")){
                    cs1=barCode;
                    cs2=MyApplication.getInstance().getYhgh();

                    PostBben0004In postBben0004In=new PostBben0004In();
                    postBben0004In.setTranCode("0004");
                    //
                    PostBben0004In.ArgsBean argsBean=new PostBben0004In.ArgsBean();
                    argsBean.setBarCode(barCode);
                    argsBean.setSendOutUser(MyApplication.getInstance().getYhxm()+"|"+MyApplication.getInstance().getYhgh());
                    argsBean.setOperatorationTime("");
                    postBben0004In.setArgs(argsBean);

                    //
                    Gson gson=new Gson();
                    String s=gson.toJson(postBben0004In);
                    httpPost2(s,url2);

                }else{
                    playFailBeepSound();
                    Toast.makeText(SxbbscActivity.this,"此条码已经执行",Toast.LENGTH_LONG).show();
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
                    showSingleAlertDialog(results,second_list);
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
    public void showSingleAlertDialog(String[] items2, final ArrayList<SecondCheckUser> second_list){
        final String[] items = items2;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择执行人");
        final int[] index2 = {0};
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int index) {
                //Toast.makeText(SxbbscActivity.this, items[index], Toast.LENGTH_SHORT).show();

                index2[0] =index;
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TODO 业务逻辑代码
                String kk=second_list.get(index2[0]).getYongHuXM();
                cs3=kk;
                PostBben003 postBben003=new PostBben003();
                postBben003.setTranCode("0003");
                //
                PostBben003.ArgsBean argsBean=new PostBben003.ArgsBean();
                argsBean.setBarCode(cs1);
                argsBean.setFirstCheckUser(cs2);
                argsBean.setSecondCheckUser(cs3);
                argsBean.setOperatorationTime("");
                postBben003.setArgs(argsBean);
                //
                Gson gson=new Gson();
                String s=gson.toJson(postBben003,PostBben003.class);
                httpPost2(s,url2);

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
                Toast.makeText(SxbbscActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
                playFailBeepSound();
            }else{
                initData();
                playSuccessBeepSound();
                Toast.makeText(SxbbscActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(SxbbscActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SxbbscActivity .this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
        }else{
            checkData();
        }

    }

    private void clickData(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SxbbscActivity.this,HomePageActivity.class));
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
                    Toast.makeText(SxbbscActivity .this, "没有该条码匹配项", Toast.LENGTH_LONG).show();
                    return;
                }
                checkData();
            }
        });
        bq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SxbbscActivity.this,HeDuiJLActivity.class));
                finish();
            }
        });
        xm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SxbbscActivity.this,BrlbActivity.class);
                intent.putExtra("which","sxbbcj");
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
        httpPost(gson.toJson(postBben),url);
    }

    //http请求
    private final OkHttpClient client = new OkHttpClient();
    private void httpData(final String url){

        Observable.create(new Observable.OnSubscribe<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    Toast.makeText(SxbbscActivity.this,"获取不到数据",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(SxbbscActivity.this,"获取不到数据",Toast.LENGTH_LONG).show();
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

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");

    private void httpPost2(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    playFailBeepSound();
                    String ss=e.toString();
                    Toast.makeText(SxbbscActivity.this,"执行失败",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(SxbbscActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();
                            PostBbenResult0004 postBben003=gson.fromJson(s,PostBbenResult0004.class);
                            if(postBben003.getState()==1){
                                Toast.makeText(SxbbscActivity.this,"执行成功",Toast.LENGTH_LONG).show();
                                playSuccessBeepSound();
                                //url
                                String url=url2;
                                //body
                                PostBben postBben=new PostBben();
                                postBben.setTranCode("0001");
                                PostBben.ArgsBean argsBean=new PostBben.ArgsBean();
                                argsBean.setPatientId("");
                                argsBean.setWardCode(MyApplication.getInstance().getBqdm());
                                postBben.setArgs(argsBean);
                                //gson
                                Gson gson2=new Gson();
                                httpPost(gson2.toJson(postBben),url);
                            }else{
                                playFailBeepSound();
                                Toast.makeText(SxbbscActivity.this,postBben003.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }
    private void httpPost(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    Toast.makeText(SxbbscActivity.this,"执行失败",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(SxbbscActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String s) {
                        if(!(s.equals("")))
                        {
                            Gson gson=new Gson();
                            ResultBben resultBben=gson.fromJson(s,ResultBben.class);
                            if(resultBben.getState()==1){
                                if(resultBben.getMessage().size()==0){
                                    listBgd.setVisibility(View.GONE);
                                    nodata.setVisibility(View.VISIBLE);
                                }else{
                                    listBgd.setVisibility(View.VISIBLE);
                                    nodata.setVisibility(View.GONE);
                                    //WFSXHeDuiAdapter adapter=new WFSXHeDuiAdapter(SxbbscActivity.this,listJianYanJG);
                                    Message=resultBben.getMessage();
                                    List<ResultBben.MessageBean> Message=new ArrayList<>();
                                    for(int j=0;j<resultBben.getMessage().size();j++){
                                        if(resultBben.getMessage().get(j).getSampleState().equals("2")){
                                            Message.add(resultBben.getMessage().get(j));
                                        }
                                    }
                                    listBgd.setAdapter(new CommonAdapter<ResultBben.MessageBean>(SxbbscActivity.this,R.layout.layout_sxbbc_item,Message) {
                                        @Override
                                        protected void convert(ViewHolder viewHolder, ResultBben.MessageBean item, int position) {
                                            viewHolder.setText(R.id.lx,item.getSampleName());
                                            viewHolder.setText(R.id.jysj,item.getPrintUser());
                                            viewHolder.setText(R.id.jysj2,item.getPrintTime());
                                            viewHolder.setText(R.id.bbtm,item.getBarCode());
                                        }
                                    });
                                }


                            }else if(resultBben.getState()==0){
                                Toast.makeText(SxbbscActivity.this,"执行失败",Toast.LENGTH_LONG).show();
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
                Toast.makeText(SxbbscActivity .this, info, Toast.LENGTH_LONG).show();
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
                Toast.makeText(SxbbscActivity .this, "核对成功", Toast.LENGTH_LONG).show();
                initData();

            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(SxbbscActivity .this, info, Toast.LENGTH_LONG).show();
            }

        });
    }
    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if(listJianYanJG.size()==0){
                        listBgd.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else{
                        listBgd.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        HeDuiAdapter adapter=new HeDuiAdapter(SxbbscActivity.this,listJianYanJG, zyid, tiaoMa, userName, userID);
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
}