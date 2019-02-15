package enjoyor.enjoyorzemobilehealth.activity.xxcx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.ShengMingTiZhengLuRuActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.base.BaseRecyclerAdapter;
import enjoyor.enjoyorzemobilehealth.base.CommonAdapter;
import enjoyor.enjoyorzemobilehealth.base.SmartViewHolder;
import enjoyor.enjoyorzemobilehealth.base.ViewHolder;
import enjoyor.enjoyorzemobilehealth.entities.HighShow;
import enjoyor.enjoyorzemobilehealth.entities.HighWen;
import enjoyor.enjoyorzemobilehealth.entities.WenDuMsg;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.views.CoustomGridView;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class HighTiwenActivity extends BaseActivity {

    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rcy_highwen)
    RecyclerView rcyHighwen;
    @BindView(R.id.tv_empty_content)
    TextView tvEmptyContent;
    @BindView(R.id.ll_empty_data)
    LinearLayout llEmptyData;

    private BaseRecyclerAdapter<HighShow> mAdapter;
    private MyApplication app;
    private List<BRLB> listBRLB;//病人列表
    private String wendu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_tiwen);
        ButterKnife.bind(this);
        init();
        initData();
        initListener();
    }

    private void initListener() {

    }

    private void initData() {
//        入参：bqdm 变成bqdm+” ¤”+shuzhi(37.5,38 自己选择)
        String bqdm = app.getBqdm();
        String yhid = app.getYhgh();
        String canshu = bqdm+ Constant.FUHAO+wendu;
        ZhierCall zhierCall = (new ZhierCall())
                .setId(yhid)
                .setNumber("0600504")
                .setMessage(NetWork.SMTZ)
                .setCanshu(canshu)//病区代吗
                .setContext(this)
                .setPort(5000).setDialogmes("查询中...")
                .build();
        log.e(yhid + "--参数--" + bqdm);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                log.e(data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{HighWen.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });


    }


    public double getMax(double[] myList) {
//        double[] myList = {1.9, 2.9, 3.4, 3.5, 10, 11, 15, 1, -1, -4.2}; //定义一维数组
        double num = myList[0];  //0为第一个数组下标
        for (int i = 0; i < myList.length; i++) {  //开始循环一维数组
            num = (myList[i] < num ? num : myList[i]);  //三元运算符，详情看注解
        }
        log.e("最大值为" + num);  //跳出循环，输出结果
        return num;
    }

    private void init() {
        app = MyApplication.getInstance();
        listBRLB = app.getListBRLB();
        Intent intent = getIntent();
        wendu = intent.getStringExtra("wendu");
        tvTitleInclude.setText("高体温病人("+ wendu +")");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }

    @OnClick(R.id.iv_back_include)
    public void onViewClicked() {
        finish();
    }


    private List<HighWen> listHighWen = new ArrayList<>();
    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (listHighWen.size() == 0 || listHighWen.get(0).getBingRenZYID().equals("")) {
                        llEmptyData.setVisibility(View.VISIBLE);
                        rcyHighwen.setVisibility(View.GONE);
                        tvEmptyContent.setText("查询不到任何高温记录");
                        log.e("查询到空表");
                    } else {
                        llEmptyData.setVisibility(View.GONE);
                        rcyHighwen.setVisibility(View.VISIBLE);
                        setAdapter();
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listHighWen.add((HighWen) msg.obj);
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

    private void setAdapter() {
        final List<HighShow> list_data = new ArrayList<>();

        HighShow show = null;
        BRLB brlb = null;//病人列表
        HighWen bean = null;//现有数据
        for (int i = 0; i < listBRLB.size(); i++) {//病人列表
            brlb = listBRLB.get(i);
            String bingrenzyid = brlb.getBINGRENZYID();
            show = new HighShow();
            String xingming = brlb.getXINGMING();
            show.setAge(brlb.getNIANLING());
            show.setName(xingming);
            show.setSex(brlb.getXINGBIE());
            show.setChuang(brlb.getCHUANGWEIHAO());

            int cs = 0;
            for (int j = 0; j < listHighWen.size(); j++) {   //所有数据
                bean = listHighWen.get(j);
                if (bean.getBingRenZYID().equals(bingrenzyid)) { //病人住院id

                    log.e(cs + "--");

                    if (cs == 0) {
                        show.setTime1(bean.getJiLuSJ());
                        show.setTiwen1(bean.getShuZhi1());
                    } else if (cs == 1) {
                        show.setTime2(bean.getJiLuSJ());
                        show.setTiwen2(bean.getShuZhi1());
                    } else if (cs == 2) {
                        show.setTime3(bean.getJiLuSJ());
                        show.setTiwen3(bean.getShuZhi1());
                    } else if (cs == 3) {
                        show.setTime4(bean.getJiLuSJ());
                        show.setTiwen4(bean.getShuZhi1());
                    }
                    cs++;
                }

            }
            if (show.getTiwen1().length() > 0) {
                list_data.add(show);
            }
        }

        rcyHighwen.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rcyHighwen.setItemAnimator(new DefaultItemAnimator());
//        rcyHighwen.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(this, R.color.item_view)));
        rcyHighwen.setAdapter(mAdapter = new BaseRecyclerAdapter<HighShow>(list_data, R.layout.item_highwen) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, HighShow bean, int position) {

                holder.text(R.id.tv_high_age, bean.getAge());
                holder.text(R.id.tv_high_name, bean.getName());
                holder.text(R.id.tv_high_chuang, bean.getChuang());

                ImageView iv_image = (ImageView) holder.findViewById(R.id.iv_high_image);
                if (bean.getSex().equals("女")) {
                    iv_image.setImageResource(R.drawable.icon_women);
                } else {
                    iv_image.setImageResource(R.drawable.icon_men);
                }
                final CoustomGridView gv_tiwen = (CoustomGridView) holder.findViewById(R.id.gv_tiwen);
                gv_tiwen.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case KeyEvent.ACTION_UP:
                                startActivity(new Intent(HighTiwenActivity.this, ShengMingTiZhengLuRuActivity.class));
                                log.e("gv_tiwen.setOnTouchListene");
                                break;
                        }
                        log.e("gv_tiwen.setOnTouchListene");
                        return false;
                    }
                });
                String tiwen1 = bean.getTiwen1();
                String tiwen2 = bean.getTiwen2();
                String tiwen3 = bean.getTiwen3();
                String tiwen4 = bean.getTiwen4();

                String time1 = bean.getTime1();
                String time2 = bean.getTime2();
                String time3 = bean.getTime3();
                String time4 = bean.getTime4();

                List<WenDuMsg> list_tiwen_msg = new ArrayList<>();
                if (!time1.equals("")) {
                    list_tiwen_msg.add(new WenDuMsg(time1, tiwen1));
                }
                if (!time2.equals("")) {
                    list_tiwen_msg.add(new WenDuMsg(time2, tiwen2));
                }
                if (!time3.equals("")) {
                    list_tiwen_msg.add(new WenDuMsg(time3, tiwen3));
                }
                if (!time4.equals("")) {
                    list_tiwen_msg.add(new WenDuMsg(time4, tiwen4));
                }

                setTiWenAdapter(gv_tiwen, list_tiwen_msg);

            }
        });

        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                log.e("setOnItemClickListener");
                startActivity(new Intent(HighTiwenActivity.this, ShengMingTiZhengLuRuActivity.class));
            }
        });
    }

    /*
     *体温展示明细
     */
    private void setTiWenAdapter(CoustomGridView gv_tiwen, final List<WenDuMsg> list_tiwen_msg) {
//        double[] myList = new double[list_tiwen_msg.size()];
//        log.e(myList.length + "===");
//        for (int i = 0; i < list_tiwen_msg.size(); i++) {
//            myList[i] = list_tiwen_msg.get(i).getTiwen();
//        }
//        final double max = getMax(myList);//查找最高温度
        gv_tiwen.setAdapter(new CommonAdapter<WenDuMsg>(HighTiwenActivity.this, list_tiwen_msg, R.layout.item_tiwen_msg) {
            @Override
            public void convert(final ViewHolder holder, List<WenDuMsg> datas, String tag, int position) {
                WenDuMsg bean = datas.get(position);
                String[] split = bean.getTime().split(" ");
                for (int i = 0; i < split.length; i++) {
                    log.e(split[i]);
                }
                String day = split[0].substring(5, split[0].length());
                String hour = split[1].substring(0, 5);
                holder.setText(R.id.tv_high_time_day1, day);
                holder.setText(R.id.tv_high_time_hour1, hour);
                TextView tvTw = (TextView) holder.getView(R.id.tv_high_wendu1);
                tvTw.setTextColor(getResources().getColor(R.color.red));

//                if (max == bean.getTiwen()) {
                tvTw.setTextColor(getResources().getColor(R.color.red));
//                }
                tvTw.setText(bean.getTiwen() + "");
//
//             final LinearLayout llitem=   holder.getView(R.id.ll_item_tiwen);
//                llitem.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return llitem.onTouchEvent(event) ;
//                    }
//                    });

//                llitem.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(HighTiwenActivity.this, ShengMingTiZhengLuRuActivity.class));
//                    }
//                });
            }
        });
    }
}
