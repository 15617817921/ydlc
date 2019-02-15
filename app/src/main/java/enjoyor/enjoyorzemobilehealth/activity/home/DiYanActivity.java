package enjoyor.enjoyorzemobilehealth.activity.home;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.PingTieXinXiActivity;
import enjoyor.enjoyorzemobilehealth.activity.ZXYZActivity;
import enjoyor.enjoyorzemobilehealth.activity.ZhiXingJiLuActivity;
import enjoyor.enjoyorzemobilehealth.activity.hulistate.HuLiDetailsAct;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.StringUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import enjoyor.enjoyorzemobilehealth.views.PopUpWindowWithNoItemClick;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.TUPIAN;

public class DiYanActivity extends BaseActivity {//0401601
    @BindView(R.id.iv_include_back)
    ImageView ivIncludeBack;
    @BindView(R.id.tv_include_title)
    TextView tvIncludeTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_bingrenname)
    TextView tvBingrenname;
    @BindView(R.id.tv_chuanghao)
    TextView tvChuanghao;
    @BindView(R.id.tv_zyhao)
    TextView tvZyhao;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_diyan_curdate)
    TextView tvDiyanCurdate;
    @BindView(R.id.rcy_diyan)
    RecyclerView rcyDiyan;


    private MyApplication app;
    private ZhierCall zhierCall;
    private List<BRLB> listBRLB;

    private String yhgh;
    private String curTime;//开始时间
    private String startTimeString = " 00:00:00";//开始时间
    private String endTimeString = " 23:59:59";//结束时间
    private DateUtil dateUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diyan);
        ButterKnife.bind(this);
        init();
        initNework();
        initData();
        initListener();
    }

    private void initNework() {//374159
        curTime = dateUtil.getYear_Month_Day();
        tvDiyanCurdate.setText(curTime);
        String bingrenzyid = listBRLB.get(app.getChoosebr()).getBINGRENZYID();
        String startTime = curTime + startTimeString;
        String endTime = curTime + endTimeString;
        String canshu = bingrenzyid + "¤" + startTime + "¤" + endTime;
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("0401601")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e(data);
                StringXmlParser parser = new StringXmlParser(xmlHandler, new Class[]{Yizhu.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e(info);
                //Toast.makeText(DiYanActivity.this, info+"请重新扫码！", Toast.LENGTH_LONG).show();

            }
        });
    }

    List<Yizhu> list_diyan = new ArrayList<>();
    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    initData();
                    break;
                case NODE:
                    list_diyan.add((Yizhu) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        app = MyApplication.getInstance();
        listBRLB = app.getListBRLB();
        yhgh = app.getYhgh();
        BRLB brlb = listBRLB.get(app.getChoosebr());

        setTouxiang(brlb);
        dateUtil = DateUtil.getInstance();
        tvIncludeTitle.setText("滴眼");
    }

    private void setTouxiang(BRLB brlb) {
        String xb = brlb.getXINGBIE();
        if (xb.equals("男")) {
            ivHead.setImageResource(R.drawable.icon_men);
        } else {
            ivHead.setImageResource(R.drawable.icon_women);
        }
        tvBingrenname.setText(brlb.getXINGMING());
        tvChuanghao.setText(brlb.getCHUANGWEIHAO());
        tvZyhao.setText(brlb.getBINGRENID());
    }


    private void initListener() {

    }

    private void startIntent(int position, String title) {
        Intent intent = new Intent(DiYanActivity.this, HuLiDetailsAct.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("num", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @NonNull
    private ArrayList<ArrayList<Yizhu>> getArrayLists(int index, List<Yizhu> list1) {
        //进行分组
        final ArrayList<ArrayList<Yizhu>> fenzhu_list = new ArrayList<>();
        ArrayList<Yizhu> jiahuan = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();
        String fenzhu1 = "";
        try {
            fenzhu1 = list1.get(0).getTiaoMaID();
            all.add(fenzhu1);
        } catch (Exception e) {

        }

        for (Yizhu yizhu : list1) {
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
            for (Yizhu yizhu : list1) {
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

        //将当前页面的扫描的条码ID所在的分组和第一组调换位置，显示在最前面
        try {
            switch (Integer.parseInt(app.getBryz_no())) {
                case 1:
                    if (index == 1 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 2:
                    if (index == 3 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 3:
                    if (index == 4 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 4:
                    if (index == 0 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 5:
                    if (index == 2 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                case 6:
                    if (index == 5 && !TextUtils.isEmpty(selectTMID)) {
                        int defaultPos = 0;
                        for (int j = 0; j < fenzhu_list.size(); j++) {
                            if (TextUtils.equals(selectTMID, fenzhu_list.get(j).get(0).getTiaoMaID())) {
                                defaultPos = j;
                                break;
                            }
                        }
                        Collections.swap(fenzhu_list, 0, defaultPos);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.e(e.getMessage() + "--" + e.toString());
        }
        return fenzhu_list;
    }

    public ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
    public ArrayList<TextView> textViewArrayList = new ArrayList<>();

    public void sendObject(int j, int i) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("what", i);
        bundle.putInt("what2", j);
        message.setData(bundle);
        message.what = TUPIAN;
        xmlHandler.sendMessage(message);
    }

    private void initData() {
        final ArrayList<ArrayList<Yizhu>> diyanLists = getArrayLists(0, list_diyan);
        rcyDiyan.setLayoutManager(new LinearLayoutManager(this));
        rcyDiyan.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<Yizhu>>(DiYanActivity.this, R.layout.bryz_recycler_view_item, diyanLists) {
            String tmid = "";
            int nn = 1;
            int bb = 0;

            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<Yizhu> yizhus, final int position) {

                String mc = diyanLists.get(position).get(0).getYongFaMC();
                log.e("标题--" + mc);
                //总的状态
                final String yiZhuZT = diyanLists.get(position).get(0).getYiZhuZT();

                final int vv = position;
                ArrayList<Yizhu> tempList = new ArrayList<Yizhu>();
//                    Button btnClickMore= holder.getView(R.id.btn_clickmore);
                ImageView ivClickMore = holder.getView(R.id.iv_clickmore);
                if (diyanLists.get(vv).size() > 5) {
                    tempList.add(diyanLists.get(vv).get(0));
                    tempList.add(diyanLists.get(vv).get(1));
                    tempList.add(diyanLists.get(vv).get(2));
                    tempList.add(diyanLists.get(vv).get(3));
                    tempList.add(diyanLists.get(vv).get(4));
                    ivClickMore.setVisibility(View.VISIBLE);
                    ivClickMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopUpWindowWithNoItemClick popWindow = new PopUpWindowWithNoItemClick(DiYanActivity.this.getParent(), diyanLists.get(vv));
                            popWindow.showAtLocation(DiYanActivity.this.getParent().findViewById(R.id.ll_parent), Gravity.CENTER, 0, 0);
//                                PopUpWindowWithNoItemClick popWindow = new PopUpWindowWithNoItemClick(DiYanActivity.this, fenzhu_list.get(vv));
//                                popWindow.showAtLocation(DiYanActivity.this.findViewById(R.id.ll_parent), Gravity.CENTER, 0, 0);
                            backgroundAlpha(0.7f);
                            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    //设置背景透明度
                                    backgroundAlpha(1f);
                                }
                            });
                        }
                    });
                } else {
                    tempList = diyanLists.get(vv);
//                        holder.getView(R.id.btn_clickmore).setVisibility(View.GONE);
                    ivClickMore.setVisibility(View.GONE);
                }

                LinearLayout linearLayout = holder.getView(R.id.real_back);
                ViewGroup.LayoutParams ip;
                ip = linearLayout.getLayoutParams();
                ip.height = dp2px(DiYanActivity.this, 72) + tempList.size() * dp2px(DiYanActivity.this, 50);
                linearLayout.setLayoutParams(ip);
                linearLayout.invalidate();

                final ArrayList<Yizhu> finalList = tempList;

                View view = holder.getView(R.id.my_list);
                ListView listView = (ListView) view.findViewById(R.id.my_list);
                setInAdapter(yiZhuZT, finalList, listView);//内层 展示
                listView.setVisibility(View.VISIBLE);
                if (diyanLists.size() == 0) {
                } else {
                    tmid = diyanLists.get(0).get(0).getTiaoMaID();
                }
                //省略号 弹窗
                moreClick(yiZhuZT, holder, position, diyanLists);
                //就是上面透明的那一栏
//                    holder.setVisible(R.id.pp, false);
//                    i[0]++;
                log.e("位置：" + position);

//                    String yiZhuZT = fenzhu_list.get(position).get(0).getYiZhuZT();
                switch (yiZhuZT) {
                    case "0":
                        holder.setText(R.id.yizhu2, "未执行");
                        holder.setImageResource(R.id.yizhu1, R.drawable.wzx);
                        break;
                    case "1":
                        holder.setText(R.id.yizhu2, "结束");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_jieshu3x);
                        break;
                    case "2":
                        holder.setText(R.id.yizhu2, "异常中断");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_zhongduan3x);
                        break;
                    case "3":
                        holder.setText(R.id.yizhu2, "暂停");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_zanting3x);
                        break;
                    case "4":
                        holder.setText(R.id.yizhu2, "停用");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_tingyong3x);

                        break;
                    case "5":
                        holder.setText(R.id.yizhu2, "继续");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_jixu3x);
                        break;
                    case "6":
                        holder.setText(R.id.yizhu2, "开始");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_kaishi3x);
                        break;
                    case "7":
                        holder.setText(R.id.yizhu2, "复核");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_fuhe);

                        break;
                    case "8":
                        holder.setText(R.id.yizhu2, "摆药");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_baiyao);
                        break;
                    case "9":
                        holder.setText(R.id.yizhu2, "收药");
                        holder.setImageResource(R.id.yizhu1, R.drawable.icon_shouyao);
                    default:
                        break;
                }
                holder.setText(R.id.yizhu10, diyanLists.get(position).get(0).getJieShuSJ());

                TextView tv_name = holder.getView(R.id.yizhu7);//分类 如：静滴 csx 文字遗嘱
                TextView tv_qd = holder.getView(R.id.yizhu6);// 频次  qd

                int gb = position + 1;
                TextView tv_zu = holder.getView(R.id.yizhu4);//第几组
//                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                setTextColor(yiZhuZT, tv_name);
                setTextColor(yiZhuZT, tv_qd);
                setTextColor(yiZhuZT, tv_zu);

                Yizhu yizhu = diyanLists.get(position).get(0);
                String yongFaMC = yizhu.getYongFaMC();
                String yanbie = "";
                if (yongFaMC.contains(Constant.DIYAN)) {
                    yanbie = yizhu.getBz();
                }
                String yongFaFL = yizhu.getYongFaFL();
                String pinCi = yizhu.getPinCi();
                String title_zuhe = yizhu.getPinCi();
                if (!yizhu.getBeiZhu().equals("") && yongFaFL.equals("6")) { //其他医嘱 显示 执行次数  备注
                    tv_name.setText(yongFaMC + " " + yanbie + Constant.CHENGYI + " " + yizhu.getBeiZhu());
                } else {
                    tv_name.setText(yongFaMC + " " + yanbie);
                }

                tv_qd.setText(yizhu.getPinCi());
                tv_zu.setText("第" + gb + "组");
//                    holder.setText(R.id.yizhu7, fenzhu_list.get(position).get(0).getYongFaMC());

                TextView tv_zxr = holder.getView(R.id.nn4);//执行人
                TextView tv_zxsj = holder.getView(R.id.nn5);//执行时间
                TextView tv_iszx = holder.getView(R.id.nn6);//是否执行
                setTextColor(yiZhuZT, tv_zxr);
                setTextColor(yiZhuZT, tv_zxsj);
                setTextColor(yiZhuZT, tv_iszx);
                tv_zxr.setText(diyanLists.get(position).get(0).getCaoZuoRen());
                tv_zxsj.setText(diyanLists.get(position).get(0).getCaoZuoSJ());
                if (!TextUtils.isEmpty(diyanLists.get(position).get(0).getCaoZuoSJ())) {
//                        holder.setText(R.id.nn6, "执行");
                    tv_iszx.setText("执行");
                }
            }
        });
    }

    //像素转换
    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void setInAdapter(final String yiZhuZT, final ArrayList<Yizhu> finalList, ListView listView) {
        listView.setAdapter(new CommonAdapter<Yizhu>(getBaseContext(), R.layout.bryz_singleitem, finalList) {
            @Override
            protected void convert(ViewHolder viewHolder, Yizhu item, int position) {
//                            log.e(finalList.get(position).getYiZhuFL() + "--" + finalList.get(position).getYiZhuFL() + "--" + finalList.get(position).getYiZhuZT());
                TextView textView = viewHolder.getView(R.id.nn1);
                TextView tv_jiliang = viewHolder.getView(R.id.nn2);
                setTextColor(yiZhuZT, textView);
                setTextColor(yiZhuZT, tv_jiliang);
                textView.setText(finalList.get(position).getYiZhuMC());
                tv_jiliang.setText("剂量  " + finalList.get(position).getJiLiang() + finalList.get(position).getJiLiangDW());
//                            viewHolder.setText(R.id.nn1, finalList.get(position).getYiZhuMC());
//                            viewHolder.setText(R.id.nn2, "剂量  " + finalList.get(position).getJiLiang() + finalList.get(position).getJiLiangDW());
            }
        });
    }

    /*
     *根据状态来改变相应的字体
     */
    private void setTextColor(String yiZhuZT, TextView tv_name) {
        // 0--未执行 1--结束 2--异常中断 3--暂停 4--停用 5--继续 6--开始
        if (yiZhuZT.equals("0") || yiZhuZT.equals("3")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt0));
        } else if (yiZhuZT.equals("6") || yiZhuZT.equals("2")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt1));
        } else if (yiZhuZT.equals("1") || yiZhuZT.equals("5")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt3));
        } else if (yiZhuZT.equals("4")) {
            tv_name.setTextColor(getResources().getColor(R.color.zt6));
        }
    }

    /**
     * 省略号  弹出底部弹窗
     *
     * @param yiZhuZT
     * @param holder
     * @param position
     * @param fenzhu_list
     */
    private void moreClick(final String yiZhuZT, com.zhy.adapter.recyclerview.base.ViewHolder holder, final int position, final ArrayList<ArrayList<Yizhu>> fenzhu_list) {
        holder.setOnClickListener(R.id.more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行医嘱，底部弹框
                final View layout = findViewById(R.id.bottom);
                layout.setVisibility(View.VISIBLE);
                Button bt_ptXinXi = (Button) layout.findViewById(R.id.btn1);
                Button bt_zxyizhu = (Button) layout.findViewById(R.id.btn2);
                Button bt_zxJiLu = (Button) layout.findViewById(R.id.btn3);
                Button bt_back = (Button) layout.findViewById(R.id.bt_back);
                Button bt_cancle = (Button) layout.findViewById(R.id.btn_cancel);

                String[] s = new String[1];
                s[0] = fenzhu_list.get(position).get(0).getTiaoMaID();
                final String tiaomaID = s[0];
                //保存医嘱状态
                toZX(s);
                //如果不是未执行状态状态  则显示回退
                if (!yiZhuZT.equals(Constant.STATE_O)) {
                    bt_back.setVisibility(View.VISIBLE);
                    bt_back.setText("回退");
                    bt_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layout.setVisibility(View.GONE);
                            Yizhu yz = fenzhu_list.get(position).get(0);
                            //底部切换
                            app.setBryz_no(yz.getYongFaFL());
                            app.setBryz_bj("true");
                            backDialog(tiaomaID);
                        }
                    });
                } else {
                    bt_back.setVisibility(View.GONE);
                }


                bt_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setVisibility(View.GONE);
                    }
                });

                bt_zxyizhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                                    String yongFaMC = fenzhu_list.get(position).get(0).getYongFaMC();
                        Yizhu yz = fenzhu_list.get(position).get(0);
                        Intent intent = new Intent(DiYanActivity.this, ZXYZActivity.class);
//                                    Bundle bundle=new Bundle();
//                                    bundle.putString("no1",fenzhu_list.get(position).get(0).getYiZhuMC());
//                                    bundle.putString("no2",fenzhu_list.get(position).get(0).getYongLiang());
                        String yongFaMC = yz.getYongFaMC();// 标题
                        String pici = yz.getPinCi();// 频次
                        String title = yongFaMC + " | " + pici;
                        /*
                        区分滴眼
                        */
                        Yizhu yizhu = null;
                        if (title.contains(Constant.DIYAN) || title.contains(Constant.CSX) || title.contains(Constant.PRN)) {   //滴眼
                            yizhu = new Yizhu();
                            if (!StringUtils.stringNull(tiaomaID).equals("")) {
                                for (Yizhu x : list_diyan) {
                                    if (tiaomaID.equals(x.getTiaoMaID())) {
                                        yizhu = x;
                                        break;
                                    }
                                }
                            }
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("yizhu", yizhu);
                            intent.putExtra("bundle", bundle);
                        } else {  //正常流程


                        }
                        intent.putExtra("no1", yz.getYiZhuMC());
                        intent.putExtra("no2", yz.getJiLiang());
                        intent.putExtra("no3", yz.getYiZhuLB());
                        intent.putExtra("state", yz.getYiZhuZT());
                        intent.putExtra("danwei", yz.getJiLiangDW());
                        intent.putExtra("tmid", yz.getTiaoMaID());
                        intent.putExtra("yfmc", title);//包括 用途路径  |   频次

                        app.setBryz_no(yz.getYongFaFL());
//                        startActivityForResult(intent, REQUEST_CODE);
                        layout.setVisibility(View.GONE);
                    }
                });

                //瓶贴信息按钮的单击事件
                bt_ptXinXi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentToPingTieXinXi = new Intent(DiYanActivity.this, PingTieXinXiActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", listBRLB.get(app.getChoosebr()).getXINGMING());
                        bundle.putString("bedNum", listBRLB.get(app.getChoosebr()).getCHUANGWEIHAO());
                        bundle.putSerializable("yizhu", fenzhu_list.get(position));
                        intentToPingTieXinXi.putExtras(bundle);
                        startActivity(intentToPingTieXinXi);
                        layout.setVisibility(View.GONE);
                        log.e(listBRLB.get(app.getChoosebr()).getXINGMING() + listBRLB.get(app.getChoosebr()).getCHUANGWEIHAO());
                    }
                });

                //执行记录的单击事件
                bt_zxJiLu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentZhiXingJiLu = new Intent(DiYanActivity.this, ZhiXingJiLuActivity.class);
                        intentZhiXingJiLu.putExtra("tmid", fenzhu_list.get(position).get(0).getTiaoMaID());
                        startActivity(intentZhiXingJiLu);
                        layout.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void toZX(String[] tmid) {
        try {
            List<Yizhu> list_shangchuan = new ArrayList<>();
            int i = 0;
            for (String s : tmid) {
                for (Yizhu x : list_diyan) {
                    System.out.println(x.getTiaoMaID());
                    if (s.equals(x.getTiaoMaID())) {
                        list_shangchuan.add(x);
                        i = 1;
                        break;
                    }
                }
            }

            String all;
            String xml = null;
            String kk = list_shangchuan.get(0).getYiZhuZT();
            for (int l = 0; l < list_shangchuan.size(); l++) {
                //list_shangchuan.get(l).setYiZhuZT(ZhuanHuanTool.toString1(ZhuanHuanTool.toInt((Integer.parseInt(kk)+1)+"")));
            }
            try {
                xml = createXml(list_shangchuan);
            } catch (Exception e) {
                e.printStackTrace();
            }

            StringBuilder s = new StringBuilder();
            //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
            s.append("ThreeMills" + "¤");
            s.append(xml + "¤");
            s.append(listBRLB.get(app.getChoosebr()).getBINGRENZYID() + "¤");
            s.append(app.getYhxm() + "¤");
            s.append(app.getYhgh() + "¤");
            s.append(app.getBqdm() + "¤");
            s.append(1 + "¤");
            s.append(0 + "¤");

            app.setListZXYZ(s.toString());
            app.setChangeYIZHU(list_shangchuan.get(0).getYiZhuZT());
        } catch (Exception e) {
            Toast.makeText(DiYanActivity.this, "匹配不成功", Toast.LENGTH_SHORT).show();
        }
    }

    //chang
    public String createXml(List<Yizhu> list) throws Exception {
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
        return s.toString();
    }

    /**
     * 执行医嘱  回退
     *
     * @param tmid
     */
    public void backDialog(final String tmid) {
        AlertDialog alertDialog = new AlertDialog.Builder(DiYanActivity.this)
                .setCancelable(false)
                .setTitle("提示").setMessage("医嘱是否回退到未执行状态？")
                .setPositiveButton("回退", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startHuitui(tmid);
                    }
                }).setNegativeButton("稍后再说",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }

    /*
     *请求网络开始回退
     **/
    private void startHuitui(String tmid) {
        List<Yizhu> list_shangchuan = new ArrayList<>();
        //获得TMID
//        String tiaomaid = "";
//        for (Yizhu x : list_yizhu) {
//            if (tmid.equals(x.getYiZhuMXID())) {
//                tiaomaid = x.getTiaoMaID();
//                break;
//            }
//        }
        //通过条码ID去取医嘱
        if (!StringUtils.stringNull(tmid).equals("")) {
            for (Yizhu x : list_diyan) {
                if (tmid.equals(x.getTiaoMaID())) {
                    x.setBeiZhu("0");
                    list_shangchuan.add(x);
                    break;
                }
            }
        }
        String sc_xml = null;
        try {
            sc_xml = createXml(list_shangchuan);
        } catch (Exception e) {
            log.e("创建xml文件出错" + e.getMessage() + "--" + e.toString());
            e.printStackTrace();
        }
        StringBuilder s = new StringBuilder();
        s.append("未执行¤");
        s.append(sc_xml + "¤");
        s.append(listBRLB.get(app.getChoosebr()).getBINGRENZYID() + "¤");
        s.append(app.getYhxm() + "¤");
        s.append(app.getYhgh() + "¤");
        s.append(app.getBqdm() + "¤");
        s.append(1 + "¤");
        s.append(0 + "¤" + Constant.ONE);

        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(s.toString())
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e("回退参数--" + s.toString());
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e(data);
//                playSuccessBeepSound();
                app.setBryz_bj("true");
                ToastUtil.showLong("回退医嘱成功");
//                //刷新
//                swipeRefreshLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(true);
//                    }
//                });
//                onRefreshListener.onRefresh();
            }

            @Override
            public void fail(String info) {
                log.e(info);
//                playFailBeepSound();
                ToastUtil.showLong("回退医嘱失败");
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(DiYanActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.iv_include_back, R.id.ll_msg, R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_diyan_curdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_include_back:
                finish();
                break;
            case R.id.ll_msg:
                //跳转病人列表
                break;
            case R.id.tv_one:
                break;
            case R.id.tv_two:
                break;
            case R.id.tv_three:
                break;
            case R.id.tv_diyan_curdate:
//              切换时间

                break;
            default:
                break;
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
        //initSaoMiao();
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
        //this.unregisterReceiver(myReceiver);
        if (myReceiver2 != null) {
            this.unregisterReceiver(myReceiver2);
            log.e("广播解绑");
        }
        super.onPause();
    }

    private String selectTMID;
    // m80 扫码广播
    private final BroadcastReceiver myReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();
                String data = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
                if (Build.MODEL.equals("m80") || Build.MODEL.equals("m80s")) {
                    data = intent.getStringExtra("decode_rslt");
                }
                if (Build.MODEL.equals("N1")) {
                    data = intent.getStringExtra("msg");
                }
                data = data.trim().replace("��", "¤").replace("?", "¤");
                data = data.replaceAll("陇", "¤");
                String[] s = data.split("\\*");

                log.e("条码" + data);
                String[] s1 = data.split("\\¤");
                //st72是腕带，切换病人
                //药品的话只有一串数字
                if (s1[0].equals("st72")) {
                    qiehuanBr(s1[1]);
                } else if (s.length > 2) {
                    //获得条码ID
                    //获得条码ID
                    selectTMID = s[0];
                } else {

                }
            } catch (Exception e) {
                log.e(e.getMessage() + "--" + e.toString());
            }
        }


    };

    private void qiehuanBr(String wandaihao) {
        boolean isture = false;
        for (int i = 0; i < listBRLB.size(); i++) {
            if (listBRLB.get(i).getBINGRENZYID().equals(wandaihao)) {
                app.setChoosebr(i);
                isture = true;
            }
        }
        if (isture) {
            setTouxiang(listBRLB.get(app.getChoosebr()));
            initNework();
        } else {
            ToastUtil.showLong("请检查病人是否在科室内");
        }
    }
}
