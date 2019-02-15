package enjoyor.enjoyorzemobilehealth.activity.bzyxyytfy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.imscs.barcodemanager.BarcodeManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.BryzActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.ShuXueHuLiJLD;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.CreateXmlUtil;
import enjoyor.enjoyorzemobilehealth.views.ConfirmAndCancelDialog;
import enjoyor.enjoyorzemobilehealth.views.DateTimeDialogOnlyYMD;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;


/**
 * Created by Administrator on 2017/9/19.
 */

public class BloodSugarCheckActivity extends AppCompatActivity implements View.OnClickListener, DateTimeDialogOnlyYMD.MyOnDateSetListener,ScanFactory.FragScan {
    private ImageView ivBack;
    private ImageView ivDetail;
    private ImageView ivTouXiang;
    private TextView tvName;
    private TextView tvChuangNum;
    private LinearLayout timeSelectLayout;
    private TextView time;
    private Button btnSave;

    private TextView tvMorningBeforeJiLuRen;
    private AppCompatEditText etMorningBeforeValue;
    private TextView tvMorningAfterJiLuRen;
    private AppCompatEditText etMorningAfterValue;
    private TextView tvNoonBeforeJiLuRen;
    private AppCompatEditText etNoonBeforeValue;
    private TextView tvNoonAfterJiLuRen;
    private AppCompatEditText etNoonAfterValue;
    private TextView tvEveningBeforeJiLuRen;
    private AppCompatEditText etEveningBeforeValue;
    private TextView tvEveningAfterJiLuRen;
    private AppCompatEditText etEveningAfterValue;
    private TextView tvSleepBeforeJiLuRen;
    private AppCompatEditText etSleepBeforeValue;
    private TextView tvYeJianJiLuRen;
    private AppCompatEditText etYeJianValue;
    private AppCompatEditText etBeiZhu;

    private static final int REQUEST_CODE = 1; // 请求码
    private int selectPos=0;
    private MyApplication myApplication;
    private String yhid;
    private String yhxm;
    private String bqdm;
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat compareFormat=new SimpleDateFormat("yyyy/MM/dd");
    private String patientName;
    private String bedNum;
    private String bingRenZYID;
    private ProgressDialog progressDialog;
    private DateTimeDialogOnlyYMD mDateTimeDialogOnlyYMD;
    private ZhierCall zhierCall;
    private Context context;

    private List<ShuXueHuLiJLD> mData=new ArrayList<>();

    private String tempTime;

    private boolean isChange=false;
    private ShuXueHuLiJLD finalBean=new ShuXueHuLiJLD();
    private ShuXueHuLiJLD preBean=new ShuXueHuLiJLD();

    private boolean isFirstSetData=true;

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

    private BloodSugarCheckActivity.DoDecodeThread mDoDecodeThread;

    //扫描提示音
    private boolean playBeep=true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;

    MyXmlHandler getDataHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what){
                case END:
                    setNewValue(tempTime);
                    progressDialog.dismiss();
                    break;
                case NODE:
                    switch (msg.arg1){
                        case 0:
                            ShuXueHuLiJLD bean= (ShuXueHuLiJLD) msg.obj;
                            if(!TextUtils.isEmpty(bean.getBingrenzyid())){
                                mData.add(bean);
                            }
                            break;
                        case 1:
                            break;
                        case 2:
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
        setContentView(R.layout.activity_bloodsugarcheck_bzyxy);
        context=this;
        myApplication=MyApplication.getInstance();
        yhid=myApplication.getYhgh();
        yhxm=myApplication.getYhxm();
        bqdm=myApplication.getBqdm();
        initBeep();
        initView();
        initListener();
        initData();

        //扫码相关
        mDoDecodeThread = new BloodSugarCheckActivity.DoDecodeThread();
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

    private void initData() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        String xingBie=myApplication.getListBRLB().get(selectPos).getXINGBIE();
        if(TextUtils.equals(xingBie,"男")){
            ivTouXiang.setImageResource(R.drawable.icon_men);
        }else {
            ivTouXiang.setImageResource(R.drawable.icon_women);
        }
        patientName=myApplication.getListBRLB().get(selectPos).getXINGMING();
        tvName.setText(patientName);
        tvChuangNum.setText(myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO()+"床");
        bingRenZYID=myApplication.getListBRLB().get(selectPos).getBINGRENZYID();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //int minute = calendar.get(Calendar.MINUTE);
        time.setText(year + "-" + month + "-" + day);

        tempTime=compareFormat.format(calendar.getTime())+"";
        //请求数据
        getData();
    }

    private void getData() {
        //清空数据源
        mData.clear();

        String canShu=" AND BingRenZYID='"+bingRenZYID+"' AND BingQuDM='"+bqdm+"'";
        zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("1000920")
                .setMessage(NetWork.HULIJIRU)
                .setCanshu(canShu)
                .setContext(BloodSugarCheckActivity.this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
//                progressDialog.dismiss();
                Log.i("data","---------data"+data);
                parseData(getDataHandler,new Class[]{ShuXueHuLiJLD.class},data);
            }

            @Override
            public void fail(String info) {
                Log.i("data","---------info"+info);
            }
        });
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

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivDetail.setOnClickListener(this);
        ivTouXiang.setOnClickListener(this);
        tvName.setOnClickListener(this);
        timeSelectLayout.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void initView() {
        ivBack= (ImageView) findViewById(R.id.back);
        ivDetail= (ImageView) findViewById(R.id.iv_bloodsugar_detail);
        ivTouXiang= (ImageView) findViewById(R.id.tx);
        tvName= (TextView) findViewById(R.id.mz);
        tvChuangNum= (TextView) findViewById(R.id.ch);
        timeSelectLayout= (LinearLayout) findViewById(R.id.sjxz);
        time= (TextView) findViewById(R.id.sj);
        btnSave= (Button) findViewById(R.id.btn_save);

        tvMorningBeforeJiLuRen= (TextView) findViewById(R.id.tv_morning_before);
        etMorningBeforeValue= (AppCompatEditText) findViewById(R.id.et_morning_before);
        tvMorningAfterJiLuRen= (TextView) findViewById(R.id.tv_morning_after);
        etMorningAfterValue= (AppCompatEditText) findViewById(R.id.et_morning_after);

        tvNoonBeforeJiLuRen= (TextView) findViewById(R.id.tv_noon_before);
        etNoonBeforeValue= (AppCompatEditText) findViewById(R.id.et_noon_before);
        tvNoonAfterJiLuRen= (TextView) findViewById(R.id.tv_noon_after);
        etNoonAfterValue= (AppCompatEditText) findViewById(R.id.et_noon_after);

        tvEveningBeforeJiLuRen= (TextView) findViewById(R.id.tv_evening_before);
        etEveningBeforeValue= (AppCompatEditText) findViewById(R.id.et_evening_before);
        tvEveningAfterJiLuRen= (TextView) findViewById(R.id.tv_evening_after);
        etEveningAfterValue= (AppCompatEditText) findViewById(R.id.et_evening_after);

        tvSleepBeforeJiLuRen= (TextView) findViewById(R.id.tv_beforesleep);
        etSleepBeforeValue= (AppCompatEditText) findViewById(R.id.et_beforesleep);

        tvYeJianJiLuRen= (TextView) findViewById(R.id.tv_yejian);
        etYeJianValue= (AppCompatEditText) findViewById(R.id.et_yejian);

        etBeiZhu= (AppCompatEditText) findViewById(R.id.et_beizhu);

        //日期选择器
        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(this, this, true, true, true);

//        etMorningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvMorningBeforeJiLuRen.getText().toString(),etMorningBeforeValue.getText().toString(),etMorningBeforeValue));
//        etMorningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvMorningAfterJiLuRen.getText().toString(),etMorningAfterValue.getText().toString(),etMorningAfterValue));
//        etNoonBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvNoonBeforeJiLuRen.getText().toString(),etNoonBeforeValue.getText().toString(),etNoonBeforeValue));
//        etNoonAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvNoonAfterJiLuRen.getText().toString(),etNoonAfterValue.getText().toString(),etNoonAfterValue));
//        etEveningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvEveningBeforeJiLuRen.getText().toString(),etEveningBeforeValue.getText().toString(),etEveningBeforeValue));
//        etEveningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvEveningAfterJiLuRen.getText().toString(),etEveningAfterValue.getText().toString(),etEveningAfterValue));
//        etSleepBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvSleepBeforeJiLuRen.getText().toString(),etSleepBeforeValue.getText().toString(),etSleepBeforeValue));
//        etYeJianValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvYeJianJiLuRen.getText().toString(),etYeJianValue.getText().toString(),etYeJianValue));
//        etBeiZhu.addTextChangedListener(new MyEdittextTextChangeWatcher("",etBeiZhu.getText().toString(),etBeiZhu));

//        etMorningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvMorningBeforeJiLuRen.getText().toString(),etMorningBeforeValue));
//        etMorningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvMorningAfterJiLuRen.getText().toString(),etMorningAfterValue));
//        etNoonBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvNoonBeforeJiLuRen.getText().toString(),etNoonBeforeValue));
//        etNoonAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvNoonAfterJiLuRen.getText().toString(),etNoonAfterValue));
//        etEveningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvEveningBeforeJiLuRen.getText().toString(),etEveningBeforeValue));
//        etEveningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvEveningAfterJiLuRen.getText().toString(),etEveningAfterValue));
//        etSleepBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvSleepBeforeJiLuRen.getText().toString(),etSleepBeforeValue));
//        etYeJianValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvYeJianJiLuRen.getText().toString(),etYeJianValue));
//        etBeiZhu.addTextChangedListener(new MyEdittextTextChangeWatcher("",etBeiZhu));
        etMorningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etMorningBeforeValue));
        etMorningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etMorningAfterValue));
        etNoonBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etNoonBeforeValue));
        etNoonAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etNoonAfterValue));
        etEveningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etEveningBeforeValue));
        etEveningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etEveningAfterValue));
        etSleepBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etSleepBeforeValue));
        etYeJianValue.addTextChangedListener(new MyEdittextTextChangeWatcher(etYeJianValue));
        etBeiZhu.addTextChangedListener(new MyEdittextTextChangeWatcher(etBeiZhu));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(BloodSugarCheckActivity.this,HomePageActivity.class));
                finish();
                break;
            case R.id.iv_bloodsugar_detail:
                break;
            case R.id.tx:
            case R.id.mz:
                Intent intent=new Intent(BloodSugarCheckActivity.this, BrlbActivity.class);
                intent.putExtra("which","XTJC");
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.sjxz:
                mDateTimeDialogOnlyYMD.hideOrShow();
                break;
            case R.id.btn_save:
                //弹出确定取消对话框
                final ConfirmAndCancelDialog dialog=new ConfirmAndCancelDialog(context);
                dialog.setOnConfirmOrCancelClickListener(new ConfirmAndCancelDialog.OnConfirmOrCancelClickListener() {
                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        //保存数据
                        saveData();
                    }

                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }

    private void saveData() {
        //记录原始的数据
        finalBean.setRiqi(tempTime);
        finalBean.setZaocanq(etMorningBeforeValue.getText().toString());
        finalBean.setZaocanqqm(tvMorningBeforeJiLuRen.getText().toString());
        finalBean.setZaocanh(etMorningAfterValue.getText().toString());
        finalBean.setZaocanhqm(tvMorningAfterJiLuRen.getText().toString());

        finalBean.setWucanq(etNoonBeforeValue.getText().toString());
        finalBean.setWucanqqm(tvNoonBeforeJiLuRen.getText().toString());
        finalBean.setWucanh(etNoonAfterValue.getText().toString());
        finalBean.setWucanhqm(tvNoonAfterJiLuRen.getText().toString());

        finalBean.setWancanq(etEveningBeforeValue.getText().toString());
        finalBean.setWancanqqm(tvEveningBeforeJiLuRen.getText().toString());
        finalBean.setWancanh(etEveningAfterValue.getText().toString());
        finalBean.setWancanhqm(tvEveningAfterJiLuRen.getText().toString());

        finalBean.setShuiqian(etSleepBeforeValue.getText().toString());
        finalBean.setShuiqianqm(tvSleepBeforeJiLuRen.getText().toString());

        finalBean.setWanjian(etYeJianValue.getText().toString());
        finalBean.setWanjianqm(tvYeJianJiLuRen.getText().toString());

        finalBean.setBeizhu(etBeiZhu.getText().toString());
        finalBean.setBingrenzyid(bingRenZYID);
        finalBean.setBingqudm(bqdm);

        String canShu= CreateXmlUtil.createXueTangCheckXml(finalBean,"ShuXueHuLiJLD");
        if(isChange){
            //进度对话框
            progressDialog=new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("加载中...");
            progressDialog.show();

            if(TextUtils.isEmpty(finalBean.getId())){
                //新增记录
                zhierCall = (new ZhierCall())
                        .setId(yhid)
                        .setNumber("1000917")
                        .setMessage(NetWork.HULIJIRU)
                        .setCanshu(canShu)
                        .setContext(context)
                        .setPort(5000)
                        .setDialogmes("加载中...")
                        .build();
                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(context,"数据保存成功!",Toast.LENGTH_SHORT).show();
                        //刷新数据
                        getData();
                    }

                    @Override
                    public void fail(String info) {
                        progressDialog.dismiss();
                        Toast.makeText(context,"数据保存失败!",Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                //修改记录
                zhierCall = (new ZhierCall())
                        .setId(yhid)
                        .setNumber("1000918")
                        .setMessage(NetWork.HULIJIRU)
                        .setCanshu(canShu)
                        .setContext(context)
                        .setPort(5000)
                        .setDialogmes("加载中...")
                        .build();
                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(context,"数据保存成功!",Toast.LENGTH_SHORT).show();
                        //刷新数据
                        getData();
                    }

                    @Override
                    public void fail(String info) {
                        progressDialog.dismiss();
                        Toast.makeText(context,"数据保存失败!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else {
            Toast.makeText(context,"数据未作任何修改!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(Calendar calendar) {
        Date date=calendar.getTime();
        time.setText(format.format(date)+"");
        tempTime=compareFormat.format(date)+"";
        //重新给控件赋值
        setNewValue(tempTime);
    }

    private void setNewValue(String selectTime){
        //重置标志
        isFirstSetData=true;
        isChange=false;
        preBean=null;
        preBean=new ShuXueHuLiJLD();

        finalBean=null;
        finalBean=new ShuXueHuLiJLD();

        boolean isExist=false;
        if(mData.size()>0){
            for(ShuXueHuLiJLD bean:mData){
                if(TextUtils.equals(strChange(bean.getRiqi()),selectTime)){
                    tvMorningBeforeJiLuRen.setText(bean.getZaocanqqm());
                    etMorningBeforeValue.setText(bean.getZaocanq());
                    tvMorningAfterJiLuRen.setText(bean.getZaocanhqm());
                    etMorningAfterValue.setText(bean.getZaocanh());

                    tvNoonBeforeJiLuRen.setText(bean.getWucanqqm());
                    etNoonBeforeValue.setText(bean.getWucanq());
                    tvNoonAfterJiLuRen.setText(bean.getWucanhqm());
                    etNoonAfterValue.setText(bean.getWucanh());

                    tvEveningBeforeJiLuRen.setText(bean.getWancanqqm());
                    etEveningBeforeValue.setText(bean.getWancanq());
                    tvEveningAfterJiLuRen.setText(bean.getWancanhqm());
                    etEveningAfterValue.setText(bean.getWancanh());

                    tvSleepBeforeJiLuRen.setText(bean.getShuiqianqm());
                    etSleepBeforeValue.setText(bean.getShuiqian());

                    tvYeJianJiLuRen.setText(bean.getWanjianqm());
                    etYeJianValue.setText(bean.getWanjian());

                    etBeiZhu.setText(bean.getBeizhu());

                    //修改时要上传得到的ID
                    finalBean.setId(bean.getId());

                    isExist=true;
                    break;
                }
            }
        }
        if(!isExist){
            tvMorningBeforeJiLuRen.setText("");
            etMorningBeforeValue.setText("");
            tvMorningAfterJiLuRen.setText("");
            etMorningAfterValue.setText("");

            tvNoonBeforeJiLuRen.setText("");
            etNoonBeforeValue.setText("");
            tvNoonAfterJiLuRen.setText("");
            etNoonAfterValue.setText("");

            tvEveningBeforeJiLuRen.setText("");
            etEveningBeforeValue.setText("");
            tvEveningAfterJiLuRen.setText("");
            etEveningAfterValue.setText("");

            tvSleepBeforeJiLuRen.setText("");
            etSleepBeforeValue.setText("");

            tvYeJianJiLuRen.setText("");
            etYeJianValue.setText("");

            etBeiZhu.setText("");
        }
        //记录原始数据
        preBean.setRiqi(tempTime);
        preBean.setZaocanq(etMorningBeforeValue.getText().toString());
        preBean.setZaocanqqm(tvMorningBeforeJiLuRen.getText().toString());
        preBean.setZaocanh(etMorningAfterValue.getText().toString());
        preBean.setZaocanhqm(tvMorningAfterJiLuRen.getText().toString());

        preBean.setWucanq(etNoonBeforeValue.getText().toString());
        preBean.setWucanqqm(tvNoonBeforeJiLuRen.getText().toString());
        preBean.setWucanh(etNoonAfterValue.getText().toString());
        preBean.setWucanhqm(tvNoonAfterJiLuRen.getText().toString());

        preBean.setWancanq(etEveningBeforeValue.getText().toString());
        preBean.setWancanqqm(tvEveningBeforeJiLuRen.getText().toString());
        preBean.setWancanh(etEveningAfterValue.getText().toString());
        preBean.setWancanhqm(tvEveningAfterJiLuRen.getText().toString());

        preBean.setShuiqian(etSleepBeforeValue.getText().toString());
        preBean.setShuiqianqm(tvSleepBeforeJiLuRen.getText().toString());

        preBean.setWanjian(etYeJianValue.getText().toString());
        preBean.setWanjianqm(tvYeJianJiLuRen.getText().toString());

        preBean.setBeizhu(etBeiZhu.getText().toString());
        preBean.setId(finalBean.getId());
        preBean.setBingrenzyid(bingRenZYID);
        preBean.setBingqudm(bqdm);

        //把是否第一次settext的标志置反
        isFirstSetData=false;

//        etMorningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvMorningBeforeJiLuRen.getText().toString(),etMorningBeforeValue.getText().toString(),etMorningBeforeValue));
//        etMorningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvMorningAfterJiLuRen.getText().toString(),etMorningAfterValue.getText().toString(),etMorningAfterValue));
//        etNoonBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvNoonBeforeJiLuRen.getText().toString(),etNoonBeforeValue.getText().toString(),etNoonBeforeValue));
//        etNoonAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvNoonAfterJiLuRen.getText().toString(),etNoonAfterValue.getText().toString(),etNoonAfterValue));
//        etEveningBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvEveningBeforeJiLuRen.getText().toString(),etEveningBeforeValue.getText().toString(),etEveningBeforeValue));
//        etEveningAfterValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvEveningAfterJiLuRen.getText().toString(),etEveningAfterValue.getText().toString(),etEveningAfterValue));
//        etSleepBeforeValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvSleepBeforeJiLuRen.getText().toString(),etSleepBeforeValue.getText().toString(),etSleepBeforeValue));
//        etYeJianValue.addTextChangedListener(new MyEdittextTextChangeWatcher(tvYeJianJiLuRen.getText().toString(),etYeJianValue.getText().toString(),etYeJianValue));
//        etBeiZhu.addTextChangedListener(new MyEdittextTextChangeWatcher("",etBeiZhu.getText().toString(),etBeiZhu));

    }

    private String strChange(String dateStr){
        Date date = null;
        String result="";
        try {
            date=compareFormat.parse(dateStr);
            result=compareFormat.format(date)+"";
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("ParseException","ParseException字符串转日期格式异常");
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CODE){
                int pos=data.getIntExtra("position",0);
                Log.i("data","------"+pos);
                selectPos=pos;
                //重新获取值
                initData();
            }
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
                return BloodSugarCheckActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(BloodSugarCheckActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, BloodSugarCheckActivity.this.getClass().getName())) {

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
        Log.i("data", "M80扫码返回" + data);
        String[] s = data.split("\\*");
        if (s[0].equals("st72")) {
            //核对病人列表并更新数据
//            checkAndChangePatient(s[1]);
            checkAndChangePatient(MyApplication.getInstance().getListBRLB().get(1).getBINGRENZYID());
        } else if (s.length == 1) {
            playFailBeepSound();
            Toast.makeText(BloodSugarCheckActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
        }
//            //核对病人列表并更新数据
//            checkBRLBAndUpdate(zyid);
    }

    private void checkAndChangePatient(String zyid) {
        List<BRLB> brlbList=new ArrayList<>();
        brlbList=MyApplication.getInstance().getListBRLB();
        boolean isContain=false;
        for (int i=0;i<brlbList.size();i++){
            BRLB b=brlbList.get(i);
            if(TextUtils.equals(zyid,b.getBINGRENZYID())){
                selectPos=i;
                isContain=true;
                break;
            }
        }
        //判断是否包含扫描的腕带的信息
        if(isContain){
            playSuccessBeepSound();
            //重新获取值
            initData();
        }else {
            playFailBeepSound();
            Toast.makeText(BloodSugarCheckActivity.this,"匹配不成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
        this.registerReceiver(myReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(myReceiver);

    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            playScanSuccessBeepSound();
            String data = intent.getStringExtra("data");
            Log.i("data", "bben扫码返回数据" + data);
            String[] s = data.split("\\*");
            if (s[0].equals("st72")) {
                //核对病人列表并更新数据
                checkAndChangePatient(s[1]);
            } else if (s.length == 1) {
                playFailBeepSound();
                Toast.makeText(BloodSugarCheckActivity.this, "请扫描正确的病人腕带条码!", Toast.LENGTH_SHORT).show();
            }
//            //核对病人列表并更新数据
//            checkBRLBAndUpdate(zyid);
        }
    };

    private class MyEdittextTextChangeWatcher implements TextWatcher{
//        private String defaultYH;
//        private String defaultText;
        private AppCompatEditText editText;

        public MyEdittextTextChangeWatcher(AppCompatEditText editText) {
//            this.defaultYH=defaultYH;
//            this.defaultText=defaultText;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String nowStr=s.toString();
            if(nowStr.length()>0){
                switch (editText.getId()){
                    case R.id.et_morning_before:
                        etMorningBeforeValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_morning_after:
                        etMorningAfterValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_noon_before:
                        etNoonBeforeValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_noon_after:
                        etNoonAfterValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_evening_before:
                        etEveningBeforeValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_evening_after:
                        etEveningAfterValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_beforesleep:
                        etSleepBeforeValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_yejian:
                        etYeJianValue.setSelection(nowStr.length());
                        break;
                    case R.id.et_beizhu:
                        etBeiZhu.setSelection(nowStr.length());
                        break;
                }
            }
            //如果是第一次setText则不触发文本变化监听
            if(!isFirstSetData){
                switch (editText.getId()){
                    case R.id.et_morning_before:
                        if(!TextUtils.equals(preBean.getZaocanq(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvMorningBeforeJiLuRen.setText("");
                            }else {
                                tvMorningBeforeJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvMorningBeforeJiLuRen.setText(preBean.getZaocanqqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_morning_after:
                        if(!TextUtils.equals(preBean.getZaocanh(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvMorningAfterJiLuRen.setText("");
                            }else {
                                tvMorningAfterJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvMorningAfterJiLuRen.setText(preBean.getZaocanhqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_noon_before:
                        if(!TextUtils.equals(preBean.getWucanq(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvNoonBeforeJiLuRen.setText("");
                            }else {
                                tvNoonBeforeJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvNoonBeforeJiLuRen.setText(preBean.getWucanqqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_noon_after:
                        if(!TextUtils.equals(preBean.getWucanh(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvNoonAfterJiLuRen.setText("");
                            }else {
                                tvNoonAfterJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvNoonAfterJiLuRen.setText(preBean.getWucanhqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_evening_before:
                        if(!TextUtils.equals(preBean.getWancanq(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvEveningBeforeJiLuRen.setText("");
                            }else {
                                tvEveningBeforeJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvEveningBeforeJiLuRen.setText(preBean.getWancanqqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_evening_after:
                        if(!TextUtils.equals(preBean.getWancanh(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvEveningAfterJiLuRen.setText("");
                            }else {
                                tvEveningAfterJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvEveningAfterJiLuRen.setText(preBean.getWancanhqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_beforesleep:
                        if(!TextUtils.equals(preBean.getShuiqian(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvSleepBeforeJiLuRen.setText("");
                            }else {
                                tvSleepBeforeJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvSleepBeforeJiLuRen.setText(preBean.getWanjianqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_yejian:
                        if(!TextUtils.equals(preBean.getWanjian(),nowStr)){
                            if(TextUtils.isEmpty(nowStr)){
                                tvYeJianJiLuRen.setText("");
                            }else {
                                tvYeJianJiLuRen.setText(yhxm);
                            }
                            isChange=true;
                        }else {
                            tvYeJianJiLuRen.setText(preBean.getWanjianqm());
                            isChange=false;
                        }
                        break;
                    case R.id.et_beizhu:
                        if(!TextUtils.equals(preBean.getBeizhu(),nowStr)){
                            isChange=true;
                        }else {
                            isChange=false;
                        }
                        break;
                }
            }
        }
    }
}
