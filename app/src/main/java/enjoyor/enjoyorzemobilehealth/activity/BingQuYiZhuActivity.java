package enjoyor.enjoyorzemobilehealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bben.date.NowDate;
import com.bben.date.ProDialog;
import com.bben.saomiao.SaoMiao;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.YiZhuHD;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.ZhuanHuanTool;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.TUPIAN;


/*
1. 获得全病区的医嘱
 */
public class BingQuYiZhuActivity extends BaseActivity implements ScanFactory.FragScan {
    RecyclerView recyclerView;
    ZhierCall zhierCall;
    MyXmlHandler xmlHandler;
    //KT50
    private String RECE_DATA_ACTION = "com.se4500.onDecodeComplete";
    private String START_SCAN_ACTION = "com.geomobile.se4500barcode";
    private String STOP_SCAN = "com.geomobile.se4500barcode.poweroff";
    public static final String BAR_READ_ACTION = "SYSTEM_BAR_READ";//条码广播
    public static final String RFID_READ_ACTION = "SYSTEM_RFID_READ";//RFID广播：
    public String action;
    private String time1 = NowDate.get() + " 00:00:00";
    private String time2 = NowDate.get() + " 23:59:59";
    private String ll;
    private ArrayList<YiZhuHD> list_yizhu = new ArrayList<>();
    public ImageView all_image = null;
    public ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
    public ArrayList<TextView> textViewArrayList = new ArrayList<>();
    int image = 0;
    //    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private Spinner spinner;


    //选择是执行收药、摆药、还是复合
    int state = 1;

    ZhierCall zhierCall2;
    ArrayList<ArrayList<YiZhuHD>> hd = new ArrayList<>();
    private ImageView imageView;

    private ArrayList<ArrayList<YiZhuHD>> shouYaoList = new ArrayList<>();
    private ArrayList<ArrayList<YiZhuHD>> baiYaoList = new ArrayList<>();
    private ArrayList<ArrayList<YiZhuHD>> fuHeList = new ArrayList<>();
    //扫描提示音
    private boolean playBeep = true;
    private AudioManager successAudioManager;
    private MediaPlayer successMediaPlayer;
    private AudioManager failAudioManager;
    private MediaPlayer failMediaPlayer;
    private AudioManager scanAudioManager;
    private MediaPlayer scanMediaPlayer;

    private String yhid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bing_qu_yi_zhu);

        yhid = MyApplication.getInstance().getYhgh();

        initBeep();
        List<BRLB> listBRLB = new ArrayList<>();

        listBRLB = MyApplication.getInstance().getListBRLB();
        final String ll = listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID();
        recyclerView = (RecyclerView) findViewById(R.id.my_re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        textView1 = (TextView) findViewById(R.id.all_zhu);
        textView2 = (TextView) findViewById(R.id.all_zhu2);
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(this, mColor, 0);
        imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BingQuYiZhuActivity.this, HomePageActivity.class));
                finish();
            }
        });
        textView3 = (TextView) findViewById(R.id.nn4);
        textView4 = (TextView) findViewById(R.id.nn5);
        textView5 = (TextView) findViewById(R.id.nn6);
        spinner = (Spinner) findViewById(R.id.spinner2);

        //spinner选择
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        state = 1;
                        textView2.setText("未核对" + shouYaoList.size() + "组");
                        final ArrayList<ArrayList<YiZhuHD>> hd2 = shouYaoList;
//                        for (int i = 0; i < hd.size(); i++) {
//                            if ("0".equals(hd.get(i).get(0).getYiZhuZT())) {
//                                hd2.add(hd.get(i));
//                            }
//                        }

                        try {
                            recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<YiZhuHD>>(getBaseContext(), R.layout.bryz_recycler_view_item, hd2) {
                                String tmid = "";
                                int nn = 1;
                                int bb = 0;

                                @Override
                                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<YiZhuHD> yizhus, final int position) {

                                    LinearLayout linearLayout = holder.getView(R.id.real_back);
                                    ViewGroup.LayoutParams ip;
                                    ip = linearLayout.getLayoutParams();
                                    ip.height = dp2px(BingQuYiZhuActivity.this, 72) + hd2.get(position).size() * dp2px(BingQuYiZhuActivity.this, 50);
                                    linearLayout.setLayoutParams(ip);
                                    linearLayout.invalidate();

                                    final int vv = position;
                                    View view = holder.getView(R.id.my_list);
                                    ListView listView = (ListView) view.findViewById(R.id.my_list);
                                    listView.setAdapter(new CommonAdapter<YiZhuHD>(getBaseContext(), R.layout.bryz_singleitem, hd2.get(vv)) {

                                        @Override
                                        protected void convert(ViewHolder viewHolder, YiZhuHD item, int position) {
                                            viewHolder.setText(R.id.nn1, hd2.get(vv).get(position).getYiZhuMC());
                                            viewHolder.setTag(R.id.nn2, "剂量  " + hd2.get(vv).get(position).getJiLiang() + hd2.get(vv).get(position).getJiLiangDW());
                                        }
                                    });

                                    listView.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                    /*holder.setOnClickListener(R.id.bdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(BingQuYiZhuActivity2222.this,BrlbActivity.class));
                            finish();
                        }
                    });*/

                                    if (hd2.size() == 0) {

                                    } else {
                                        tmid = hd2.get(0).get(0).getTiaoMaID();
                                    }

//                                    holder.setVisible(R.id.pp, false);
                                    //i[0]++;

//                                    System.out.print("位置：" + position);
                                    log.e("位置：" + position);

                                    switch (hd2.get(position).get(0).getYiZhuZT()) {
                                        case "0":
                                            holder.setText(R.id.yizhu2, "未执行");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 0);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "1":
                                            holder.setText(R.id.yizhu2, "结束");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 1);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "2":
                                            holder.setText(R.id.yizhu2, "异常中断");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 2);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "3":
                                            holder.setText(R.id.yizhu2, "暂停");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 3);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(151, 198, 52));
                                            break;
                                        case "4":
                                            holder.setText(R.id.yizhu2, "停用");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 4);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "5":
                                            holder.setText(R.id.yizhu2, "继续");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 5);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "6":
                                            holder.setText(R.id.yizhu2, "开始");
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 6);
                                            image++;
                                            break;
                                        case "7":
                                            holder.setText(R.id.yizhu2, "复核");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            sendObject(image, 7);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "8":
                                            holder.setText(R.id.yizhu2, "提药");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 8);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "9":
                                            holder.setText(R.id.yizhu2, "收药");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 9);
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            image++;
                                    }


                                    int gb = position + 1;
                                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                                    holder.setText(R.id.yizhu6, hd2.get(position).get(0).getPinCi());
                                    holder.setText(R.id.yizhu7, hd2.get(position).get(0).getYongFaMC());
                                    holder.setText(R.id.nn4, hd2.get(position).get(0).getXingMing());
                                    holder.setText(R.id.nn5, "床位" + hd2.get(position).get(0).getChuangWeiHao());
                                    holder.setText(R.id.nn6, "住院号" + hd2.get(position).get(0).getBingRenZYID());
                                }
                            });
                        } catch (Exception e) {

                        }
                        break;
                    case 1:
                        state = 2;
                        textView2.setText("未核对" + baiYaoList.size() + "组");
                        final ArrayList<ArrayList<YiZhuHD>> hd3 = baiYaoList;
//                        for (int i = 0; i < hd.size(); i++) {
//                            if ("9".equals(hd.get(i).get(0).getYiZhuZT())) {
//                                hd3.add(hd.get(i));
//                            }
//                        }

                        try {
                            recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<YiZhuHD>>(getBaseContext(), R.layout.bryz_recycler_view_item, hd3) {
                                String tmid = "";
                                int nn = 1;
                                int bb = 0;

                                @Override
                                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<YiZhuHD> yizhus, final int position) {

                                    LinearLayout linearLayout = holder.getView(R.id.real_back);
                                    ViewGroup.LayoutParams ip;
                                    ip = linearLayout.getLayoutParams();
                                    ip.height = dp2px(BingQuYiZhuActivity.this, 72) + hd3.get(position).size() * dp2px(BingQuYiZhuActivity.this, 50);
                                    linearLayout.setLayoutParams(ip);
                                    linearLayout.invalidate();

                                    final int vv = position;
                                    View view = holder.getView(R.id.my_list);
                                    ListView listView = (ListView) view.findViewById(R.id.my_list);
                                    listView.setAdapter(new CommonAdapter<YiZhuHD>(getBaseContext(), R.layout.bryz_singleitem, hd3.get(vv)) {

                                        @Override
                                        protected void convert(ViewHolder viewHolder, YiZhuHD item, int position) {
                                            viewHolder.setText(R.id.nn1, hd3.get(vv).get(position).getYiZhuMC());
                                            viewHolder.setTag(R.id.nn2, "剂量  " + hd3.get(vv).get(position).getJiLiang() + hd3.get(vv).get(position).getJiLiangDW());
                                        }
                                    });

                                    listView.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                    /*holder.setOnClickListener(R.id.bdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(BingQuYiZhuActivity2222.this,BrlbActivity.class));
                            finish();
                        }
                    });*/

                                    if (hd3.size() == 0) {

                                    } else {
                                        tmid = hd3.get(0).get(0).getTiaoMaID();
                                    }

//                                    holder.setVisible(R.id.pp, false);
                                    //i[0]++;

                                    System.out.print("位置：" + position);

                                    switch (hd3.get(position).get(0).getYiZhuZT()) {
                                        case "0":
                                            holder.setText(R.id.yizhu2, "未执行");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 0);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "1":
                                            holder.setText(R.id.yizhu2, "结束");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 1);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "2":
                                            holder.setText(R.id.yizhu2, "异常中断");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 2);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "3":
                                            holder.setText(R.id.yizhu2, "暂停");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 3);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(151, 198, 52));
                                            break;
                                        case "4":
                                            holder.setText(R.id.yizhu2, "停用");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 4);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "5":
                                            holder.setText(R.id.yizhu2, "继续");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 5);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "6":
                                            holder.setText(R.id.yizhu2, "开始");
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 6);
                                            image++;
                                            break;
                                        case "7":
                                            holder.setText(R.id.yizhu2, "复核");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            sendObject(image, 7);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "8":
                                            holder.setText(R.id.yizhu2, "提药");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 8);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "9":
                                            holder.setText(R.id.yizhu2, "收药");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 9);
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            image++;
                                    }


                                    int gb = position + 1;
                                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                                    holder.setText(R.id.yizhu6, hd3.get(position).get(0).getPinCi());
                                    holder.setText(R.id.yizhu7, hd3.get(position).get(0).getYongFaMC());
                                    holder.setText(R.id.nn4, hd3.get(position).get(0).getXingMing());
                                    holder.setText(R.id.nn5, "床位" + hd3.get(position).get(0).getChuangWeiHao());
                                    holder.setText(R.id.nn6, "住院号" + hd3.get(position).get(0).getBingRenZYID());
                                }
                            });
                        } catch (Exception e) {

                        }


                        break;
                    case 2:
                        state = 3;
                        textView2.setText("未核对" + fuHeList.size() + "组");
                        final ArrayList<ArrayList<YiZhuHD>> hd4 = fuHeList;
//                        for (int i = 0; i < hd.size(); i++) {
//                            if ("8".equals(hd.get(i).get(0).getYiZhuZT())) {
//                                hd4.add(hd.get(i));
//                            }
//                        }

                        try {
                            recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<YiZhuHD>>(getBaseContext(), R.layout.bryz_recycler_view_item, hd4) {
                                String tmid = "";
                                int nn = 1;
                                int bb = 0;

                                @Override
                                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<YiZhuHD> yizhus, final int position) {

                                    LinearLayout linearLayout = holder.getView(R.id.real_back);
                                    ViewGroup.LayoutParams ip;
                                    ip = linearLayout.getLayoutParams();
                                    ip.height = dp2px(BingQuYiZhuActivity.this, 72) + hd4.get(position).size() * dp2px(BingQuYiZhuActivity.this, 50);
                                    linearLayout.setLayoutParams(ip);
                                    linearLayout.invalidate();

                                    final int vv = position;
                                    View view = holder.getView(R.id.my_list);
                                    ListView listView = (ListView) view.findViewById(R.id.my_list);
                                    listView.setAdapter(new CommonAdapter<YiZhuHD>(getBaseContext(), R.layout.bryz_singleitem, hd4.get(vv)) {

                                        @Override
                                        protected void convert(ViewHolder viewHolder, YiZhuHD item, int position) {
                                            viewHolder.setText(R.id.nn1, hd4.get(vv).get(position).getYiZhuMC());
                                            viewHolder.setTag(R.id.nn2, "剂量  " + hd4.get(vv).get(position).getJiLiang() + hd4.get(vv).get(position).getJiLiangDW());
                                        }
                                    });

                                    listView.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                    /*holder.setOnClickListener(R.id.bdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(BingQuYiZhuActivity2222.this,BrlbActivity.class));
                            finish();
                        }
                    });*/

                                    if (hd4.size() == 0) {

                                    } else {
                                        tmid = hd4.get(0).get(0).getTiaoMaID();
                                    }

//                                    holder.setVisible(R.id.pp, false);
                                    //i[0]++;

                                    System.out.print("位置：" + position);

                                    switch (hd4.get(position).get(0).getYiZhuZT()) {
                                        case "0":
                                            holder.setText(R.id.yizhu2, "未执行");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 0);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "1":
                                            holder.setText(R.id.yizhu2, "结束");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 1);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "2":
                                            holder.setText(R.id.yizhu2, "异常中断");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 2);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "3":
                                            holder.setText(R.id.yizhu2, "暂停");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 3);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(151, 198, 52));
                                            break;
                                        case "4":
                                            holder.setText(R.id.yizhu2, "停用");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 4);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "5":
                                            holder.setText(R.id.yizhu2, "继续");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 5);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "6":
                                            holder.setText(R.id.yizhu2, "开始");
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 6);
                                            image++;
                                            break;
                                        case "7":
                                            holder.setText(R.id.yizhu2, "复核");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            sendObject(image, 7);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "8":
                                            holder.setText(R.id.yizhu2, "提药");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 8);
                                            image++;
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            break;
                                        case "9":
                                            holder.setText(R.id.yizhu2, "收药");
                                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                            sendObject(image, 9);
                                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                            image++;
                                    }


                                    int gb = position + 1;
                                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                                    holder.setText(R.id.yizhu6, hd4.get(position).get(0).getPinCi());
                                    holder.setText(R.id.yizhu7, hd4.get(position).get(0).getYongFaMC());
                                    holder.setText(R.id.nn4, hd4.get(position).get(0).getXingMing());
                                    holder.setText(R.id.nn5, "床位" + hd4.get(position).get(0).getChuangWeiHao());
                                    holder.setText(R.id.nn6, "住院号" + hd4.get(position).get(0).getBingRenZYID());
                                }
                            });
                        } catch (Exception e) {

                        }

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //获得病区代码
        String s = MyApplication.getInstance().getBqdm();
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        //获得账户
        String name = preferences2.getString("id", "");

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        month++;
        /*time1="2017"+"-"+month+"-"+day+" 00:00:00";
        time2="2017"+"-"+month+"-"+day+" 23:59:59";*/
        String t1 = time1;
        String t2 = time2;
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0401519")
//                .setNumber("0401509")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(time1 + "¤" + time2 + "¤" + s)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        final ProDialog proDialog = new ProDialog(this);
        proDialog.show();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                String s = data;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.print("----------------------------------------------------\n");
                System.out.print("获取到了请求数据，开始解析：" + df.format(new Date()) + "\n");

                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{YiZhuHD.class});
//                Log.d("login5",data);
                parser.parse(data);
                proDialog.close();
            }

            @Override
            public void fail(String info) {
                Toast.makeText(BingQuYiZhuActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });


        xmlHandler = new MyXmlHandler(this, this) {
            @Override
            public void handlerMessage(Message msg) {
                switch (msg.what) {
                    case END:
                        //从全病区医嘱中选出输液、注射、口服
                        ArrayList<YiZhuHD> choose_list = new ArrayList<>();
//                        for(YiZhuHD yizhu:list_yizhu){
//                            if(yizhu.getYiZhuLB().equals("1")||yizhu.getYiZhuLB().equals("4"))
//                            {
//                                choose_list.add(yizhu);
//
//                            }
//                        }
                        for (YiZhuHD yizhu : list_yizhu) {
                            if (yizhu.getYongFaFL().equals("1") || yizhu.getYongFaFL().equals("2") ||
                                    yizhu.getYongFaFL().equals("4")
                                    ) {
                                choose_list.add(yizhu);
                            }
                        }
                        list_yizhu = null;
                        list_yizhu = new ArrayList<>();

                        for (YiZhuHD yiZhuHD : choose_list) {
                            list_yizhu.add(yiZhuHD);
                        }
                        ArrayList<YiZhuHD> list2 = new ArrayList<>();


//                        for(int i=0;i<list_yizhu.size();i++){
//                            if("9".equals(list_yizhu.get(i).getYiZhuFL())){
//                                list_yizhu.get(i).setYiZhuFL("8");
//                            }
//                        }

//                        for(int i=0;i<list_yizhu.size();i++){
//                            if("9".equals(list_yizhu.get(i).getYiZhuFL())){
//                                list2.add(list_yizhu.get(i));
//                            }
//                        }

//                        for(int i=0;i<list_yizhu.size();i++){
//                            if("0".equals(list_yizhu.get(i).getYiZhuZT())){
//                                list2.add(list_yizhu.get(i));
//                            }
//                        }
//                        setRecyclerView(5,list2);

                        setRecyclerView(5, list_yizhu);

                        break;
                    case NODE:
                        switch (msg.arg1) {
                            case 0:
                                YiZhuHD bean = (YiZhuHD) msg.obj;
                                if (!TextUtils.isEmpty(bean.getBingRenZYID())) {
                                    list_yizhu.add((YiZhuHD) msg.obj);
                                }
//                                list_yizhu.add((YiZhuHD) msg.obj);
                                break;
                            default:
                                break;
                        }
                        break;
                    case TUPIAN:
                        int i = msg.getData().getInt("what");
                        int j = msg.getData().getInt("what2");
                        ImageView imageView = (ImageView) msg.getData().getParcelable("object");
                        switch (i) {
                            case 0:
                                imageViewArrayList.get(j).setImageResource(R.drawable.wzx);
                                textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                                break;
                            case 1:
                                imageViewArrayList.get(j).setImageResource(R.drawable.icon_jieshu3x);
                                textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                                break;
                            case 2:

                                imageViewArrayList.get(j).setImageResource(R.drawable.icon_zhongduan3x);
                                textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                                break;
                            case 3:

                                imageViewArrayList.get(j).setImageResource(R.drawable.icon_zanting3x);
                                textViewArrayList.get(j).setTextColor(Color.rgb(151, 198, 52));
                                break;
                            case 4:

                                imageViewArrayList.get(j).setImageResource(R.drawable.icon_tingyong3x);
                                textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                                break;
                            case 5:

                                imageViewArrayList.get(j).setImageResource(R.drawable.icon_jixu3x);
                                textViewArrayList.get(j).setTextColor(Color.rgb(0, 0, 0));
                                break;
                            case 6:

                                imageViewArrayList.get(j).setImageResource(R.drawable.icon_kaishi3x);
                                textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));
                                break;
                            case 7:

                                imageViewArrayList.get(j).setImageResource(R.drawable.fh);
                                textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));
                                break;
                            case 8:

                                imageViewArrayList.get(j).setImageResource(R.drawable.by);
                                textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));
                                break;
                            case 9:

                                imageViewArrayList.get(j).setImageResource(R.drawable.sy);
                                textViewArrayList.get(j).setTextColor(Color.rgb(108, 199, 241));
                                break;

                        }

                        break;
                    default:
                        break;
                }
            }
        };

        //mobi扫描头驱动
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECE_DATA_ACTION);
        filter.addAction(BAR_READ_ACTION);
        filter.addAction(RFID_READ_ACTION);
        filter.addAction("com.ge.action.barscan");
        //
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("lachesis_barcode_value_notice_broadcast");
        filter2.addAction("android.provider.sdlMessage");
        this.registerReceiver(myReceiver2, filter2);

        //
        SaoMiao.get("bben", this, myReceiver2);


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
        if (successAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
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
        if (playBeep && successMediaPlayer != null) {
            successMediaPlayer.start();
        }
    }

    /**
     * 播放beep声
     */
    private void playFailBeepSound() {
        //检查铃音模式
        if (failAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }

        if (playBeep && failMediaPlayer != null) {
            failMediaPlayer.start();
        }
    }

    /**
     * 播放beep声
     */
    private void playScanSuccessBeepSound() {
        //检查铃音模式
        if (scanAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        if (playBeep && scanMediaPlayer != null) {
            scanMediaPlayer.start();
        }
    }

    // s:扫描出来的代码 b:需要变成的医嘱状态代码
    public void sc(String[] s, int b) {
        List<YiZhuHD> list_yizhuxx = new ArrayList<>();
        final String sc = s[0];//需要匹配的条码ID
        final int bb = b;

        //遍历全医嘱，改变医嘱状态
        for (int i = 0; i < hd.size(); i++) {
            if (s[0].equals(hd.get(i).get(0).getTiaoMaID())) {
                for (int j = 0; j < hd.get(i).size(); j++) {
                    YiZhuHD yizhu1 = hd.get(i).get(j);
                    yizhu1.setYiZhuZT(b + "");
                    list_yizhuxx.add(hd.get(i).get(j));
                }
            }
        }
        //Toast.makeText(BingQuYiZhuActivity2222.this,list_yizhuxx.size()+"",Toast.LENGTH_LONG).show();
        String ss = null;
        try {
            ss = createXml(list_yizhuxx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ss.replaceAll("0","9");

        List<BRLB> listBRLB = new ArrayList<>();
        listBRLB = MyApplication.getInstance().getListBRLB();
        final String ll = listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID();
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");

        StringBuilder vv = new StringBuilder();
        //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
        vv.append(ZhuanHuanTool.toString1(b) + "¤");
        vv.append(ss + "¤");
        vv.append(list_yizhuxx.get(0).getBingRenZYID() + "¤");
        vv.append(MyApplication.getInstance().getYhxm() + "¤");
        vv.append(name + "¤");
        vv.append(MyApplication.getInstance().getBqdm() + "¤");
        vv.append(1 + "¤");
        vv.append(0 + "¤");

        zhierCall2 = (new ZhierCall())
                .setId(yhid)
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(vv.toString())
                .setContext(BingQuYiZhuActivity.this)
                .setPort(5000).setDialogmes("加载中...")
                .build();

        zhierCall2.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                playSuccessBeepSound();
                ArrayList<ArrayList<YiZhuHD>> selectList_copy = new ArrayList<>();
                if (state == 1) {
//                    ArrayList<ArrayList<YiZhuHD>> hd2 = new ArrayList<>();
//                    hd2 = hd;
//                    for (int i = 0; i < hd2.size(); i++) {
//                        if (sc.equals(hd2.get(i).get(0).getTiaoMaID())) {
//
//                            //hd.remove(i);
//                            for (int j = 0; j < hd.get(i).size(); j++) {
//                                hd.get(i).get(j).setYiZhuZT(bb + "");
//                                // yizhu1.setYiZhuZT(b+"");
//                                //list_yizhuxx.add(hd.get(i).get(j));
//                            }
//                        }
//                    }
                    for (int i = 0; i < shouYaoList.size(); i++) {
                        if (sc.equals(shouYaoList.get(i).get(0).getTiaoMaID())) {
                            baiYaoList.add(shouYaoList.get(i));
                            shouYaoList.remove(i);
                            break;
                        }
                    }
                    selectList_copy = shouYaoList;
                } else if (state == 2) {
//                    ArrayList<ArrayList<YiZhuHD>> hd2 = new ArrayList<>();
//                    hd2 = hd;
//                    for (int i = 0; i < hd2.size(); i++) {
//                        if (sc.equals(hd2.get(i).get(0).getTiaoMaID())) {
//
//                            //hd.remove(i);
//                            for (int j = 0; j < hd.get(i).size(); j++) {
//                                hd.get(i).get(j).setYiZhuZT(bb + "");
//                                // yizhu1.setYiZhuZT(b+"");
//                                //list_yizhuxx.add(hd.get(i).get(j));
//                            }
//                        }
//                    }
                    for (int i = 0; i < baiYaoList.size(); i++) {
                        if (sc.equals(baiYaoList.get(i).get(0).getTiaoMaID())) {
                            fuHeList.add(baiYaoList.get(i));
                            baiYaoList.remove(i);
                            break;
                        }
                    }
                    selectList_copy = baiYaoList;
                } else if (state == 3) {
//                    ArrayList<ArrayList<YiZhuHD>> hd2 = new ArrayList<>();
//                    hd2 = hd;
//                    for (int i = 0; i < hd2.size(); i++) {
//                        if (sc.equals(hd2.get(i).get(0).getTiaoMaID())) {
//
//                            //hd.remove(i);
//                            for (int j = 0; j < hd.get(i).size(); j++) {
//                                hd.get(i).get(j).setYiZhuZT(bb + "");
//                                // yizhu1.setYiZhuZT(b+"");
//                                //list_yizhuxx.add(hd.get(i).get(j));
//                            }
//                        }
//                    }
                    for (int i = 0; i < fuHeList.size(); i++) {
                        if (sc.equals(fuHeList.get(i).get(0).getTiaoMaID())) {
//                            baiYaoList.add(shouYaoList.get(i));
                            fuHeList.remove(i);
                            break;
                        }
                    }
                    selectList_copy = fuHeList;
                }
                final ArrayList<ArrayList<YiZhuHD>> selectList = selectList_copy;

                //设置列表========================================================

//                final ArrayList<ArrayList<YiZhuHD>> fenzhu_list = hd;
//                for (int i = 0; i < fenzhu_list.size(); i++) {
//                    if (sc.equals(fenzhu_list.get(i).get(0).getTiaoMaID())) {
//
//                        //hd.remove(i);
//                        for (int j = 0; j < fenzhu_list.get(i).size(); j++) {
//                            fenzhu_list.get(i).remove(j);
//
//                        }
//                    }
//                }

//                ArrayList<ArrayList<YiZhuHD>> finalList=new ArrayList<>();
//                    if(state==2){
////                        ArrayList<ArrayList<YiZhuHD>> hd3=new ArrayList<>();
//                        for(int i=0;i<fenzhu_list.size();i++){
//                            if("9".equals(hd.get(i).get(0).getYiZhuZT())){
//                                finalList.add(hd.get(i));
//                            }
//                        }
//                    }else if(state==3){
//                        for(int i=0;i<fenzhu_list.size();i++){
//                            if("8".equals(hd.get(i).get(0).getYiZhuZT())){
//                                finalList.add(hd.get(i));
//                            }
//                        }
//                    }else {
//                        finalList=fenzhu_list;
//                    }
//
//                final  ArrayList<ArrayList<YiZhuHD>> finList=finalList;

                textView2.setText("未核对" + selectList.size() + "组");
                try {
                    recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<YiZhuHD>>(getBaseContext(), R.layout.bryz_recycler_view_item, selectList) {
                        String tmid = "";
                        int nn = 1;
                        int bb = 0;

                        @Override
                        protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<YiZhuHD> yizhus, final int position) {

                            LinearLayout linearLayout = holder.getView(R.id.real_back);
                            ViewGroup.LayoutParams ip;
                            ip = linearLayout.getLayoutParams();
                            ip.height = dp2px(BingQuYiZhuActivity.this, 72) + selectList.get(position).size() * dp2px(BingQuYiZhuActivity.this, 50);
                            linearLayout.setLayoutParams(ip);
                            linearLayout.invalidate();

                            final int vv = position;
                            View view = holder.getView(R.id.my_list);
                            ListView listView = (ListView) view.findViewById(R.id.my_list);
                            listView.setAdapter(new CommonAdapter<YiZhuHD>(getBaseContext(), R.layout.bryz_singleitem, selectList.get(vv)) {

                                @Override
                                protected void convert(ViewHolder viewHolder, YiZhuHD item, int position) {
                                    viewHolder.setText(R.id.nn1, selectList.get(vv).get(position).getYiZhuMC());
                                    viewHolder.setTag(R.id.nn2, "剂量  " + selectList.get(vv).get(position).getJiLiang() + selectList.get(vv).get(position).getJiLiangDW());
                                }
                            });

                            listView.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                    /*holder.setOnClickListener(R.id.bdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(BingQuYiZhuActivity2222.this,BrlbActivity.class));
                            finish();
                        }
                    });*/

                            if (selectList.size() == 0) {

                            } else {
                                tmid = selectList.get(0).get(0).getTiaoMaID();
                            }

//                            holder.setVisible(R.id.pp, false);
                            //i[0]++;

                            System.out.print("位置：" + position);

                            switch (selectList.get(position).get(0).getYiZhuZT()) {
                                case "0":
                                    holder.setText(R.id.yizhu2, "未执行");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 0);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "1":
                                    holder.setText(R.id.yizhu2, "结束");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 1);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "2":
                                    holder.setText(R.id.yizhu2, "异常中断");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 2);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "3":
                                    holder.setText(R.id.yizhu2, "暂停");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 3);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(151, 198, 52));
                                    break;
                                case "4":
                                    holder.setText(R.id.yizhu2, "停用");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 4);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "5":
                                    holder.setText(R.id.yizhu2, "继续");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 5);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "6":
                                    holder.setText(R.id.yizhu2, "开始");
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 6);
                                    image++;
                                    break;
                                case "7":
                                    holder.setText(R.id.yizhu2, "复核");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    sendObject(image, 7);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "8":
                                    holder.setText(R.id.yizhu2, "提药");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 8);
                                    image++;
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    break;
                                case "9":
                                    holder.setText(R.id.yizhu2, "收药");
                                    imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                                    textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                                    sendObject(image, 9);
                                    holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                                    image++;
                            }


                            int gb = position + 1;
                            holder.setText(R.id.yizhu4, "第" + gb + "组");

                            holder.setText(R.id.yizhu6, selectList.get(position).get(0).getPinCi());
                            holder.setText(R.id.yizhu7, selectList.get(position).get(0).getYongFaMC());
                            holder.setText(R.id.nn4, selectList.get(position).get(0).getXingMing());
                            holder.setText(R.id.nn5, "床位" + selectList.get(position).get(0).getChuangWeiHao());
                            holder.setText(R.id.nn6, "住院号" + selectList.get(position).get(0).getBingRenZYID());
                        }
                    });
                } catch (Exception e) {

                }
                //设置列表========================================================


                Toast.makeText(BingQuYiZhuActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail(String info) {
                playFailBeepSound();
                Toast.makeText(BingQuYiZhuActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //==========mobi=============================

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }

    private BingQuYiZhuActivity.DoDecodeThread mDoDecodeThread;
    private Handler mDoDecodeHandler;

    @Override
    protected void onStart() {
        super.onStart();
        if (android.os.Build.MODEL.equals("m80") || android.os.Build.MODEL.equals("m80s")) {
            mDoDecodeThread = new DoDecodeThread();
            mDoDecodeThread.start();
            initSaoMiao();
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(BingQuYiZhuActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        if (android.os.Build.MODEL.equals("m80") || android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyDown(keyCode, event, BingQuYiZhuActivity.this.getClass().getName())) {

                return true;

            }
        }


        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // LogUtil.i("keyCode", keyCode + ":" + event.getAction());
        // scanning.onKeyUp(keyCode, event);
        if (android.os.Build.MODEL.equals("m80") || android.os.Build.MODEL.equals("m80s")) {
            if (scanInf.onKeyUp(keyCode, event)) {
                return true;
            }
        }

        return super.onKeyUp(keyCode, event);
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
                return BingQuYiZhuActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public void putDataToFrag(String data, int keycode) {
        playScanSuccessBeepSound();
        String[] s = data.split("\\*");
        //Toast.makeText(BryzBbenActivity.this,s[0],Toast.LENGTH_SHORT).show();
        System.out.print(data);
        String to = s[0];
        //匹配后的医嘱ID放在这里
        ArrayList<YiZhuHD> yizhu = new ArrayList<>();

        for (int i = 0; i < list_yizhu.size(); i++) {
            if (to.equals(list_yizhu.get(i).getTiaoMaID())) {
                yizhu.add(list_yizhu.get(i));

            }
        }
        //获得当前医嘱，v为下一个医嘱
        //
//        int v=0;
//        switch(list_yizhu.get(0).getYiZhuZT()){
//            case "0":
//                v=1;
//                break;
//            case "9":
//                v=2;
//                break;
//            case "8":
//                v=3;
//                break;
//        }

        //Toast.makeText(BingQuYiZhuActivity2222.this,s[2]+"",Toast.LENGTH_LONG).show();
        //state 1：收药 2：摆药 3：复合
        //
        if (yizhu.size() > 0) {
            if (state == 1) {
                if (yizhu.get(0).getYiZhuZT().equals("0")) {
                    sc(s, 9);
                } else {
                    playFailBeepSound();
                    showDialog();
                }
            } else if (state == 2) {
                if (yizhu.get(0).getYiZhuZT().equals("9")) {
                    sc(s, 8);
                } else {
                    playFailBeepSound();
                    showDialog();
                }

            } else if (state == 3) {
                if (yizhu.get(0).getYiZhuZT().equals("8")) {
                    sc(s, 7);

                } else {
                    playFailBeepSound();
                    showDialog();
                }
            }
        } else {
            playFailBeepSound();
            showDialog();
        }

    }

    //==========mobi=============================


    //bben扫描获取
    private final BroadcastReceiver myReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            playScanSuccessBeepSound();
            String data = intent.getStringExtra("lachesis_barcode_value_notice_broadca st_data_string");

//            if (android.os.Build.MODEL.equals("m80s") && intent.getAction().equals(RECE_DATA_ACTION)) {
//                data = intent.getStringExtra("se4500");
//                Toast.makeText(BingQuYiZhuActivity.this, data, Toast.LENGTH_LONG).show();
//            }

            String[] s = data.split("\\*");
            String to = s[0];
            //匹配后的医嘱ID放在这里
            ArrayList<YiZhuHD> yizhu = new ArrayList<>();
            for (int i = 0; i < list_yizhu.size(); i++) {
                if (to.equals(list_yizhu.get(i).getBingRenZYID())) {
                    yizhu.add(list_yizhu.get(i + 1));
                }
            }
//            //获得当前医嘱，v为下一个医嘱
//            //
//            int v = 0;
//            switch (list_yizhu.get(0).getYiZhuZT()) {
//                case "0":
//                    v = 1;
//                    break;
//                case "9":
//                    v = 2;
//                    break;
//                case "8":
//                    v = 3;
//                    break;
//            }

            //Toast.makeText(BingQuYiZhuActivity2222.this,s[2]+"",Toast.LENGTH_LONG).show();
            //state 1：收药 2：摆药 3：复合
//            if (state == 1) {
//                if (v == 1) {
//                    sc(s, 9);
//                } else {
//                    showDialog();
//                }
//            } else if (state == 2) {
//                if (v == 2) {
//                    sc(s, 8);
//                } else {
//                    showDialog();
//                }
//
//            } else if (state == 3) {
//                if (v == 3) {
//                    sc(s, 7);
//
//                } else {
//                    showDialog();
//                }
//            }
            if (yizhu.size() > 0) {
                if (state == 1) {
                    if (yizhu.get(0).getYiZhuZT().equals("0")) {
                        sc(s, 9);
                    } else {
                        playFailBeepSound();
                        showDialog();
                    }
                } else if (state == 2) {
                    if (yizhu.get(0).getYiZhuZT().equals("9")) {
                        sc(s, 8);
                    } else {
                        playFailBeepSound();
                        showDialog();
                    }

                } else if (state == 3) {
                    if (yizhu.get(0).getYiZhuZT().equals("8")) {
                        sc(s, 7);

                    } else {
                        playFailBeepSound();
                        showDialog();
                    }
                }
            } else {
                playFailBeepSound();
                showDialog();
            }
        }
    };


    //设置列表
    public void setRecyclerView(final int index, final List<YiZhuHD> list1) {
        View one = null;
        System.out.print("list大小：" + list1.size() + "\n");
        RecyclerView recyclerView = this.recyclerView;
//        final int[] i = {0};
        //进行分组
        final ArrayList<ArrayList<YiZhuHD>> fenzhu_list = new ArrayList<>();
        ArrayList<YiZhuHD> jiahuan = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();
        String fenzhu1 = "";
        try {
            fenzhu1 = list1.get(0).getTiaoMaID();
            all.add(fenzhu1);
        } catch (Exception e) {

        }

        for (YiZhuHD yizhu : list1) {
            ArrayList<String> all2 = new ArrayList<>();
            int o = 0;
            for (String s : all) {
                if (yizhu.getTiaoMaID().equals(s)) {
                    o = 1;
                    break;
                }
            }

            if (o == 0) {
                all.add(yizhu.getTiaoMaID());
            }
        }

        //用分好的进行排序
        for (String fenzhu : all) {
            int ff = 0;
            for (YiZhuHD yizhu : list1) {
                if (fenzhu.equals(yizhu.getTiaoMaID())) {
                    jiahuan.add(yizhu);
                    ff = 1;
                }
            }
            if (ff == 1) {
                fenzhu_list.add(jiahuan);
                jiahuan = null;
                jiahuan = new ArrayList<>();
            }

        }

        //收药、摆药、复合页面数据添加
        for (int j = 0; j < fenzhu_list.size(); j++) {
            if ("0".equals(fenzhu_list.get(j).get(0).getYiZhuZT())) {
                shouYaoList.add(fenzhu_list.get(j));
            } else if ("9".equals(fenzhu_list.get(j).get(0).getYiZhuZT())) {
                baiYaoList.add(fenzhu_list.get(j));
            } else if ("8".equals(fenzhu_list.get(j).get(0).getYiZhuZT())) {
                fuHeList.add(fenzhu_list.get(j));
            }
        }


        hd = fenzhu_list;

        //设置总组数，以及为核对总数
//        textView1.setText("共" + fenzhu_list.size() + "组液体");
        int c = fenzhu_list.size();
        for (int ik = 0; ik < fenzhu_list.size(); ik++) {
            if (fenzhu_list.get(ik).get(0).getYiZhuZT().equals("7")) {
                c--;
            }
        }
        textView2.setText("未核对" + shouYaoList.size() + "组");


        try {
            recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<YiZhuHD>>(getBaseContext(), R.layout.bryz_recycler_view_item, shouYaoList) {
                String tmid = "";
                int nn = 1;
                int bb = 0;

                @Override
                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<YiZhuHD> yizhus, final int position) {

                    LinearLayout linearLayout = holder.getView(R.id.real_back);
                    ViewGroup.LayoutParams ip;
                    ip = linearLayout.getLayoutParams();
                    ip.height = dp2px(BingQuYiZhuActivity.this, 72) + shouYaoList.get(position).size() * dp2px(BingQuYiZhuActivity.this, 50);
                    linearLayout.setLayoutParams(ip);
                    linearLayout.invalidate();

                    final int vv = position;
                    View view = holder.getView(R.id.my_list);
                    ListView listView = (ListView) view.findViewById(R.id.my_list);
                    listView.setAdapter(new CommonAdapter<YiZhuHD>(getBaseContext(), R.layout.bryz_singleitem, shouYaoList.get(vv)) {

                        @Override
                        protected void convert(ViewHolder viewHolder, YiZhuHD item, int position) {
                            viewHolder.setText(R.id.nn1, shouYaoList.get(vv).get(position).getYiZhuMC());
                            viewHolder.setTag(R.id.nn2, "剂量  " + shouYaoList.get(vv).get(position).getJiLiang() + shouYaoList.get(vv).get(position).getJiLiangDW());
                        }
                    });

                    listView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    /*holder.setOnClickListener(R.id.bdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(BingQuYiZhuActivity2222.this,BrlbActivity.class));
                            finish();
                        }
                    });*/

                    if (shouYaoList.size() == 0) {

                    } else {
                        tmid = shouYaoList.get(0).get(0).getTiaoMaID();
                    }

//                    holder.setVisible(R.id.pp, false);
//                    i[0]++;

                    System.out.print("位置：" + position);

                    switch (shouYaoList.get(position).get(0).getYiZhuZT()) {
                        case "0":
                            holder.setText(R.id.yizhu2, "未执行");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 0);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "1":
                            holder.setText(R.id.yizhu2, "结束");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 1);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "2":
                            holder.setText(R.id.yizhu2, "异常中断");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 2);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "3":
                            holder.setText(R.id.yizhu2, "暂停");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 3);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(151, 198, 52));
                            break;
                        case "4":
                            holder.setText(R.id.yizhu2, "停用");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 4);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "5":
                            holder.setText(R.id.yizhu2, "继续");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 5);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "6":
                            holder.setText(R.id.yizhu2, "开始");
                            holder.setTextColor(R.id.yizhu2, Color.rgb(108, 199, 241));
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 6);
                            image++;
                            break;
                        case "7":
                            holder.setText(R.id.yizhu2, "复核");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            sendObject(image, 7);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "8":
                            holder.setText(R.id.yizhu2, "提药");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 8);
                            image++;
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            break;
                        case "9":
                            holder.setText(R.id.yizhu2, "收药");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image, 9);
                            holder.setTextColor(R.id.yizhu2, Color.rgb(0, 0, 0));
                            image++;
                    }


                    int gb = position + 1;
                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                    holder.setText(R.id.yizhu6, shouYaoList.get(position).get(0).getPinCi());
                    holder.setText(R.id.yizhu7, shouYaoList.get(position).get(0).getYongFaMC());
                    holder.setText(R.id.nn4, shouYaoList.get(position).get(0).getXingMing());
                    holder.setText(R.id.nn5, "床位" + shouYaoList.get(position).get(0).getChuangWeiHao());
                    holder.setText(R.id.nn6, "住院号" + shouYaoList.get(position).get(0).getBingRenZYID());
                }
            });
        } catch (Exception e) {

        }
    }

    //    //分组后的list进行设置
//    public void setList(ArrayList<ArrayList<YiZhuHD>> fenzhu_list){
//
//    }
    //下面的代码都是次要的=======================================
    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public void sendObject(int j, int i) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("what", i);
        bundle.putInt("what2", j);
        message.setData(bundle);
        message.what = TUPIAN;
        xmlHandler.sendMessage(message);
    }


    ScanInterface scanInf;

    //mobi扫描头接收器

    //弹出对话框
    private void showDialog() {
        // TODO Auto-generated method stub

        new AlertDialog.Builder(BingQuYiZhuActivity.this).setTitle("提示")

                .setMessage("无法操作！")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        // TODO Auto-generated method stub
                        dialog.dismiss();
//                        finish();
                    }

                }).show();//在按键响应事件中显示此对话框
    }

    //创建上传的XML
    public String createXml(List<YiZhuHD> list) throws Exception {
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

        System.out.print(s.toString() + "xxxxx");
        return s.toString();
    }
}
