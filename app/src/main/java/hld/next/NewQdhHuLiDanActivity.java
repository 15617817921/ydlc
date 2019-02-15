package hld.next;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.entities.BRLB;
import com.imscs.barcodemanager.BarcodeManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.BryzActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.adapter.LeftAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import my_network.NetWork;
import my_network.ZhierCall;
import okhttp3.OkHttpClient;

/**
 * Created by dantevsyou on 2017/12/6.
 */

public class NewQdhHuLiDanActivity extends AppCompatActivity implements View.OnClickListener,ScanFactory.FragScan {
    private ImageView iv_danger_back;
    private LinearLayout ll_msg;
    private ImageView ivTouXiang;
    private TextView tv_bingrenname;
    private TextView tv_chuanghao;
    private TextView tv_date_before;
    private TextView tv_date_after;
    private ListView lv_left;
    private LeftAdapter leftAdapter;
    /**
     * 数据源
     */
    private String data[];
    public static int mPosition;

    private String curNyr;//年月日
    private String lastNyr;

    public String curtime, lastweek;//年月日时分秒
    private DateUtil instance;

    public String getCurtime() {
        return curtime;
    }

    public void setCurtime(String curtime) {
        this.curtime = curtime;
    }

    public void setLastweek(String lastweek) {
        this.lastweek = lastweek;
    }

    public String getLastweek() {
        return lastweek;
    }

    private String type;


    private Intent intent;
    private FrameLayout fragment_danger;
    private NewRightFragment fragment;
    private FragmentTransaction fragmentTransaction;
    private Bundle bundle;
    private String name;
    private String chuangweihao;
    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private String curdate;
    private String sfm;

    private String patientName;
    private String bedNum;
    private String patientZyid;
    private String ruyuanDate;
    private String bingrenid;
    private String sex;
    private String age;
//    private String pgrname;


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

    private NewQdhHuLiDanActivity.DoDecodeThread mDoDecodeThread;

    //扫描提示音
    private boolean playBeep=true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;
    String url="http://192.46.193.25:8080/services/rest/TSBISInterface/";
    ZhierCall zhierCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hulidan);
        initBeep();
        initView();
        init();
        initTime();
        setMoren();
        setData();
        initLinstener();

        //扫码相关
        mDoDecodeThread = new NewQdhHuLiDanActivity.DoDecodeThread();
        mDoDecodeThread.start();

        //
        getData();
    }

    private void getData(){
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("1000202")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu("")
                .setContext(this)
                .setPort(5000)
                .build();


        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                String s=data;
                /*StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{BRLB.class});
                parser.parse(data);*/
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }
    private final OkHttpClient client = new OkHttpClient();
   /* private void httpPost(final String post,final String url){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, post))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    *//*if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
                    }*//*
                    String body=response.body().string();
                    if(!(body.equals(""))){
                        subscriber.onNext(body);
                    }else{
                        Toast.makeText(NewQdhHuLiDanActivity.this,"无法获取此病人血袋信息",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    String ss=e.toString();
                    Toast.makeText(NewQdhHuLiDanActivity.this,"执行失败",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(NewQdhHuLiDanActivity.this,"执行失败",Toast.LENGTH_LONG).show();
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
                                    //WFSXHeDuiAdapter adapter=new WFSXHeDuiAdapter(SxbbcjActivity.this,listJianYanJG);
                                    Message=resultBben.getMessage();
                                    List<ResultBben.MessageBean> Message=new ArrayList<>();
                                    for(int j=0;j<resultBben.getMessage().size();j++){
                                        if(resultBben.getMessage().get(j).getSampleState().equals("1")){
                                            Message.add(resultBben.getMessage().get(j));
                                        }
                                    }
                                    listBgd.setAdapter(new CommonAdapter<ResultBben.MessageBean>(SxbbcjActivity.this,R.layout.layout_sxbbc_item,Message) {
                                        @Override
                                        protected void convert(ViewHolder viewHolder, ResultBben.MessageBean item, int position) {
                                            viewHolder.setText(R.id.lx,item.getSampleName());
                                            viewHolder.setText(R.id.jysj,item.getPrintUser());
                                            viewHolder.setText(R.id.jysj2,item.getPrintTime());
                                            viewHolder.setText(R.id.bbtm,item.getBarCode());
                                            //jysj2
                                           *//* if(item.getSampleState().equals("1")){
                                                viewHolder.setText(R.id.zt,"已打印");
                                            }else if(item.getSampleState().equals("2")){
                                                viewHolder.setText(R.id.zt,"已采集");
                                            }*//*


                                        }
                                    });
                                }


                            }else if(resultBben.getState()==0){
                                Toast.makeText(SxbbcjActivity.this,"执行失败",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }*/


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

    private void initView() {
        lv_left = (ListView) findViewById(R.id.lv_left);
        fragment_danger = (FrameLayout) findViewById(R.id.fragment_danger);

        iv_danger_back = (ImageView) findViewById(R.id.iv_danger_back);
        ll_msg = (LinearLayout) findViewById(R.id.ll_msg);
        ivTouXiang= (ImageView) findViewById(R.id.iv_head);
        tv_bingrenname = (TextView) findViewById(R.id.tv_bingrenname);
        tv_chuanghao = (TextView) findViewById(R.id.tv_chuanghao);
        tv_date_before = (TextView) findViewById(R.id.tv_date_before);
        tv_date_after = (TextView) findViewById(R.id.tv_date_after);
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.my_bule), 0);

        intent = getIntent();
        String tag = intent.getStringExtra(Constant.TAG);
        try {
            if (tag.equals("bingrenlist")) {
                name = intent.getStringExtra(Constant.BRNAME);
                chuangweihao = intent.getStringExtra(Constant.BRCWH);
            } else if (tag.equals("home")) {
                BRLB brlb = MyApplication.getInstance().getListBRLB().get(0);
                name = brlb.getXINGMING();//姓名
                chuangweihao = brlb.getCHUANGWEIHAO();//床位号
            }

        } catch (Exception e) {
            finish();
            //Log.e("NewQdhHuLiDanActivity-94", e.toString());
        }

        tv_chuanghao.setText(chuangweihao);
        tv_bingrenname.setText(name);

    }

    private void initTime() {
        format = new SimpleDateFormat("yyyy/MM/dd");
        instance = DateUtil.getInstance();
        curNyr = instance.getYear_Day();
        sfm = instance.getHour_s();
        lastNyr = instance.lastWeek();

        tv_date_before.setText(lastNyr);
        tv_date_after.setText(curNyr);

        curdate = curNyr + "" + sfm;
        lastweek = lastNyr + " " + sfm;
        setCurtime(curdate);
        setLastweek(lastweek);
        Log.e("默认时间", curdate + "===" + lastweek);
    }

    private void setMoren() {
        data = getResources().getStringArray(R.array.danger_menus);

        //创建fragment
        fragment = new NewRightFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_danger, fragment);
        //通过bundle传值给MyFragment
        bundle = new Bundle();
        bundle.putString("type", data[0]);
        bundle.putInt("i", 1);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }


    private void setData() {
        leftAdapter = new LeftAdapter(NewQdhHuLiDanActivity.this, data);
        lv_left.setAdapter(leftAdapter);
    }

    private void initLinstener() {
        iv_danger_back.setOnClickListener(this);
        ll_msg.setOnClickListener(this);
        tv_date_before.setOnClickListener(this);
        tv_date_after.setOnClickListener(this);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                freshPage(mPosition);
            }
        });
    }

    private void freshPage(int position) {
        //拿到当前位置
        //一级分类id
        type = data[position];
        //即时刷新adapter
        leftAdapter.notifyDataSetChanged();
        fragment = new NewRightFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_danger, fragment);
        bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putInt("i", position + 1);

        //新增得扫描腕带要传的数据
        bundle.putString("patientName",patientName);
        bundle.putString("bedNum",bedNum);
        bundle.putString("patientZyid",patientZyid);
        bundle.putString("ruyuanDate",ruyuanDate);
        bundle.putString("bingrenid",bingrenid);
        bundle.putString("sex",sex);
        bundle.putString("age",age);

        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_danger_back:
                finish();
                break;
            case R.id.ll_msg:
                intent = new Intent(NewQdhHuLiDanActivity.this, BrlbActivity.class);
                intent.putExtra("which", "danger");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_date_before:
                //时间选择器
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调

                        String time = format.format(date);
                        lastweek = time + " " + sfm;
                        setLastweek(lastweek);
                        Log.e("前", lastweek);
//                        freshPage(mPosition);
                        tv_date_before.setText(time);
                    }
                }).setDate(instance.getCalendar(tv_date_before.getText().toString()))
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isDialog(true)
                        .build();
                pvTime.show();
                break;
            case R.id.tv_date_after:
                //时间选择器
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        String time = format.format(date);
                        curdate = time + " " + sfm;
                        setCurtime(curdate);
                        freshPage(mPosition);
                        tv_date_after.setText(time);
                        Log.e("后", curdate);
                    }
                }).setDate(instance.getCalendar(tv_date_after.getText().toString()))
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isDialog(true)
                        .build();
                pvTime.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(" shengmingonStart","onStart");
        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new NewQdhHuLiDanActivity.DoDecodeThread();
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
                return NewQdhHuLiDanActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(NewQdhHuLiDanActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, NewQdhHuLiDanActivity.this.getClass().getName())) {

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
//            checkAndChangePatient(MyApplication.getInstance().getListBRLB().get(0).getBINGRENZYID());
        }else if(s.length==1){
            playFailBeepSound();
            Toast.makeText(NewQdhHuLiDanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkAndChangePatient(String zyid) {
        List<BRLB> brlbList=new ArrayList<>();
        brlbList=MyApplication.getInstance().getListBRLB();
        boolean isContain=false;
        for (BRLB b:brlbList){
            if(TextUtils.equals(zyid,b.getBINGRENZYID())){
                patientName=b.getXINGMING();
                bedNum=b.getCHUANGWEIHAO();
                patientZyid=b.getBINGRENZYID();
                ruyuanDate=b.getRUYUANSJ();
                bingrenid=b.getBINGRENID();
                sex=b.getXINGBIE();
                age=b.getNIANLING();
                isContain=true;
                break;
            }
        }
        //判断是否包含扫描的腕带的信息
        if(isContain){
            if(TextUtils.equals(sex,"男")){
                ivTouXiang.setImageResource(R.drawable.icon_men);
            }else {
                ivTouXiang.setImageResource(R.drawable.icon_women);
            }
            tv_bingrenname.setText(patientName);
            tv_chuanghao.setText(bedNum+"床");
            //切换
            freshPage(mPosition);
            playSuccessBeepSound();
            Toast.makeText(NewQdhHuLiDanActivity.this, "病人切换成功!", Toast.LENGTH_SHORT).show();
        }else {
            playFailBeepSound();
            Toast.makeText(NewQdhHuLiDanActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
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
        mPosition = 0;
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
//            checkAndChangePatient(MyApplication.getInstance().getListBRLB().get(1).getBINGRENZYID());
            }else if(s.length==1){
                playFailBeepSound();
                Toast.makeText(NewQdhHuLiDanActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPosition = 0;
//        if(haha!=null){
//            haha = null;
//        }
        Log.e(" onDestroy","onDestroy");
    }
}
