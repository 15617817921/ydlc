package enjoyor.enjoyorzemobilehealth.activity.hulistate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.BqxhcxActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;
import enjoyor.enjoyorzemobilehealth.entities.ZhiXingJL;
import enjoyor.enjoyorzemobilehealth.entities.common.HuLiShow;
import enjoyor.enjoyorzemobilehealth.rcy.zhedie.ExpandableItemAdapter;
import enjoyor.enjoyorzemobilehealth.rcy.zhedie.Item;
import enjoyor.enjoyorzemobilehealth.rcy.zhedie.ItemVH;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.TUPIAN;

public class HuLiDetailsAct extends BaseActivity {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_empty_content)
    TextView tvEmptyContent;
    @BindView(R.id.ll_empty_data)
    LinearLayout llEmptyData;
    //    private BaseRecyclerAdapter<HuLiShow> mAdapter;
    private RecyclerViewAdapter mAdapter;
    private MyApplication app;
    private DateUtil dateUtil;

    private List<BRLB> listBRLB;
    private String yhgh = "";
    private String bqdm = "";

    private ZhierCall zhierCall;

    //上个activity传过来的参数
    private String title;
    private int num;
    private List<HuLiShow> list_show;
    private int showPosition;//点击 要展开的 位置


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_huli_detail);
        ButterKnife.bind(this);
        init();
        initHandler(this);
        initNework(num);
    }

    private void initListener() {
        mAdapter.setExpandableToggleListener(new ExpandableItemAdapter.ExpandableToggleListener() {
            @Override
            public void onExpand(Item item, int position) {
                HuLiShow huLiShow = (HuLiShow) item;
                showPosition = position;
                serachmsg(huLiShow);
//                zhiXjilu(huLiShow);
//                Toast.makeText(getApplicationContext(), ((Group) item).title + " 展开", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCollapse(Item item) {
//                Toast.makeText(getApplicationContext(), ((Group) item).title + " 收起", Toast.LENGTH_SHORT).show();
            }
        });
//        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HuLiShow show = list_show.get(position);
//                if(show.isShow()){
//                    show.setShow(false);
//                }else {
//                    show.setShow(true);
//                }
//                mAdapter.notifyDataSetInvalidated();
//            }
//        });

    }

    private final int TYPE_GROUP = 0xfa01;
    private final int TYPE_CHILD = 0xfa02;

    public class RecyclerViewAdapter extends ExpandableItemAdapter {
        @Override
        public ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            ItemVH itemVH = null;
            switch (viewType) {
                case TYPE_GROUP:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_huli_detail_rcy, parent, false);
                    itemVH = new GroupVH(view);
                    break;

                case TYPE_CHILD:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xunhui_clv, parent, false);
                    itemVH = new ChildVH(view);
                    break;
            }
            return itemVH;
        }

        @Override
        public void onBindViewHolder(ItemVH holder, final int position) {
            Item item = getItem(position);
            switch (getItemViewType(position)) {
                case TYPE_GROUP:
                    final HuLiShow bean = (HuLiShow) item;
                    GroupVH groupVH = (GroupVH) holder;
//                    groupVH.item_num.setText((position + 1) + "");
                    String yizhuZT = bean.getYizhuZT();
                    switch (yizhuZT) {
                        case "0":
                            groupVH.item_ivZt.setImageResource(R.drawable.wzx);
                            break;
                        case "1":
                            groupVH.item_ivZt.setImageResource(R.drawable.icon_jieshu3x);
                            break;
                        case "2":
                            groupVH.item_ivZt.setImageResource(R.drawable.icon_zhongduan3x);
                            break;
                        case "3":
                            groupVH.item_ivZt.setImageResource(R.drawable.icon_zanting3x);
                            break;
                        case "4":
                            groupVH.item_ivZt.setImageResource(R.drawable.icon_tingyong3x);
                            break;
                        case "5":
                            groupVH.item_ivZt.setImageResource(R.drawable.icon_jixu3x);
                            break;
                        case "6":
                            groupVH.item_ivZt.setImageResource(R.drawable.icon_kaishi3x);
                            break;
                        default:
                            break;
                    }

//                    groupVH.item_xscikshu.setText(Constant.CHENGYI + bean.getChishu());
                    groupVH.item_name.setText(bean.getName());
                    groupVH.item_chuang.setText(bean.getChuang() + "床");
//                    groupVH.item_chuang.setText(bean.getBrzyID() + "床");

                    groupVH.item_xunshi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            commitToServer(bean);
//                            xunShi(0, bean.getBrzyID(), Integer.parseInt(bean.getChishu()));
                        }
                    });
//                    groupVH.item_huitui.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            xunShi(1, bean.getBrzyID(), Integer.parseInt(bean.getChishu()));
//                        }
//                    });

                    groupVH.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toggle(bean, position);
                            notifyDataSetChanged();
                        }
                    });
                    break;

                case TYPE_CHILD:
                    XunHuiJL xunHui = (XunHuiJL) item;
                    ChildVH childVH = (ChildVH) holder;
                    String leixing = xunHui.getCaoZuoLX();
                    if (leixing.equals(Constant.ZERO)) {
                        childVH.tv_zxZhuangT.setText("扫码执行");
                    }else {
                        childVH.tv_zxZhuangT.setText("手动执行");
                    }


                    childVH.tv_xunhuitime.setText(xunHui.getXunHuiSJ());
                    childVH.tv_xhHushi.setText(xunHui.getXunHuiRen());
//                    ZhiXingJL xunHui = (ZhiXingJL) item;
//                    ChildVH childVH = (ChildVH) holder;
//                    String cishu = xunHui.getCishu();
//
//                    childVH.tv_zxZhuangT.setText(cishu);
//
//                    childVH.tv_xunhuitime.setText(xunHui.getZhiXingSJ());
//                    childVH.tv_xhHushi.setText(xunHui.getZhiXingRen());
                    break;
            }
        }

        private class GroupVH extends ItemVH {
            //            public TextView item_num;
            public TextView item_name;
            public TextView item_chuang;
            //            public TextView item_xscikshu;
            public TextView item_xunshi;
            //            public TextView item_huitui;
            public ImageView item_ivZt;

            public GroupVH(View itemView) {
                super(itemView);
//                item_num = (TextView) itemView.findViewById(R.id.item_num);
                item_ivZt = (ImageView) itemView.findViewById(R.id.item_ivZt);
                item_name = (TextView) itemView.findViewById(R.id.item_name);
                item_chuang = (TextView) itemView.findViewById(R.id.item_chuang);
//                item_xscikshu = (TextView) itemView.findViewById(R.id.item_xscikshu);
                item_xunshi = (TextView) itemView.findViewById(R.id.item_xunshi);
//                item_huitui = (TextView) itemView.findViewById(R.id.item_huitui);
//                text.setBackgroundColor(Color.RED);
            }

            @Override
            public int getType() {
                return TYPE_GROUP;
            }
        }

        private class ChildVH extends ItemVH {
            public TextView tv_xunhuitime;
            public TextView tv_xhHushi;
            public TextView tv_zxZhuangT;

            public ChildVH(View itemView) {
                super(itemView);
                tv_zxZhuangT = (TextView) itemView.findViewById(R.id.tv_zxZhuangT);
                tv_xunhuitime = (TextView) itemView.findViewById(R.id.tv_xunhuitime);
                tv_xhHushi = (TextView) itemView.findViewById(R.id.tv_xhHushi);
//                text1.setTextColor(Color.LTGRAY);
//                text2.setTextColor(Color.BLUE);
            }

            @Override
            public int getType() {
                return TYPE_CHILD;
            }
        }
    }
//        病区代码¤2018/11/15 0:00:00¤2018/11/15 23:59:59¤1  0 特级，1一级 2二级 3 三级

    private void initData() {
        LinearLayoutManager layoutManage = new LinearLayoutManager(this);
        layoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        rcy.setLayoutManager(layoutManage);
        mAdapter = new RecyclerViewAdapter();
        rcy.setAdapter(mAdapter);


        list_show = new ArrayList<>();

//        HuLiShow huLi = null;
        for (int i = 0; i < list_huli.size(); i++) {
            String zyid = list_huli.get(i).getBingRenZYID();
            String beizhu = list_huli.get(i).getBeiZhu();
            String tiaomaID = list_huli.get(i).getTiaoMaID();
            String yizhuZT = list_huli.get(i).getYiZhuZT();
            String yizhumc = list_huli.get(i).getYiZhuMC();
            for (int j = 0; j < listBRLB.size(); j++) {
                BRLB brlb = listBRLB.get(j);
                HuLiShow huLi = new HuLiShow();
                if (zyid.equals(brlb.getBINGRENZYID())) {
                    huLi.setChuang(brlb.getCHUANGWEIHAO());
                    huLi.setName(brlb.getXINGMING());
                    huLi.setBrzyID(brlb.getBINGRENZYID());
                    huLi.setYizhuZT(yizhuZT);
                    huLi.setYizhuMC(yizhumc);
                    if (beizhu.equals("")) {
                        huLi.setChishu("0");
                    } else {
                        huLi.setChishu(beizhu);
                    }
                    huLi.setTiaoMaID(tiaomaID);
                    huLi.isExpand = false;


                    list_show.add(huLi);
                }
            }
        }
        list_show = maopao(list_show);

        for (int i = 0; i < list_show.size(); i++) {
            mAdapter.addItem(list_show.get(i));
        }
        tvTitleInclude.setText(title + "(" + list_show.size() + "人）");
        initListener();
    }

    private List<HuLiShow> maopao(List<HuLiShow> list_jl) {
        List<HuLiShow> list = new ArrayList<>();
        Map<Integer, HuLiShow> map_zhengchang = new TreeMap<>();
        Map<Integer, HuLiShow> map_jia = new TreeMap<>();

        for (int i = 0; i < list_jl.size(); i++) {
            String str = list_jl.get(i).getChuang();
            if (str.contains("+")) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(str);
                m.find();
                int group = Integer.parseInt(m.group());
                map_jia.put(group, list_jl.get(i));
            } else {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(str);
                m.find();
                int group = Integer.parseInt(m.group());
                map_zhengchang.put(group, list_jl.get(i));
            }
        }
        Set<Integer> set1 = map_zhengchang.keySet();
        Iterator<Integer> iterator1 = set1.iterator();
        while (iterator1.hasNext()) {
            int key = iterator1.next();
            HuLiShow value = (HuLiShow) map_zhengchang.get(key);
            list.add(value);
            log.e(key + "===");
        }
        Set<Integer> set = map_jia.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            int key = iterator.next();
            HuLiShow value = (HuLiShow) map_jia.get(key);
            list.add(value);
            log.e(key + "===");
        }

        return list;
    }

    private String startTime = "00:00:00";
    private String endTime = "23:59:59";

    private void initNework(int jibie) {
//        病区代码¤2018/11/15 0:00:00¤2018/11/15 23:59:59¤1  0 特级，1一级 2二级 3 三级
        String date = dateUtil.getYear_Day();
        String canshu = bqdm + Constant.FUHAO + date + startTime + Constant.FUHAO + date + endTime + Constant.FUHAO + jibie;
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("0401520")
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
                log.e("护理级别--" + data);
                StringXmlParser parser = new StringXmlParser(xmlHandler2,
                        new Class[]{Yizhu.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {

                log.e(info);
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    List<Yizhu> list_huli = null;

    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (list_huli.size() != 0 && !list_huli.get(0).getBingRenZYID().equals("")) {
                        llEmptyData.setVisibility(View.GONE);
                        initData();
                    } else {
                        tvTitleInclude.setText(title);
                        tvRight.setText("");
                        llEmptyData.setVisibility(View.VISIBLE);
                        tvEmptyContent.setText("查询不到任何护理等级信息");
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            Yizhu yizhu = (Yizhu) msg.obj;
                            String beiZhu = yizhu.getBeiZhu();
                            list_huli.add(yizhu);

                            break;
                        default:
                            break;
                    }
                    break;
                case TUPIAN:
                    log.e("TUPIAN");
                    break;
                default:
                    break;
            }
        }
    };


    public String createXml(List<Yizhu> list) throws Exception {
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

    private void yijianXunshi() {
        for (int i = 0; i < list_huli.size(); i++) {
            String beizhu = list_huli.get(i).getBeiZhu();
            if (beizhu.equals("")) {
//                int num = Integer.parseInt(beizhu)+;
                list_huli.get(i).setBeiZhu("1");
                list_huli.get(i).setCiShu("1");
            } else {
                int num = Integer.parseInt(beizhu) + 1;
                list_huli.get(i).setBeiZhu(num + "");
                list_huli.get(i).setCiShu(num + "");
            }
        }
        String xml = null;
        try {
            xml = createXml(list_huli);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder s = new StringBuilder();
        //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
        s.append("开始" + "¤");
        s.append(xml + "¤");
        s.append("" + "¤");
        s.append(app.getYhxm() + "¤");
        s.append(yhgh + "¤");
        s.append(bqdm + "¤");
        s.append(1 + "¤");
        s.append(0 + "¤");


        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(s.toString())
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(s.toString());
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                list_show.clear();
                list_huli.clear();
                log.e(data);
                initNework(num);
                ToastUtil.showLong(title + "一键巡视成功");
            }

            @Override
            public void fail(String info) {
            }
        });

    }

    private void serachmsg(HuLiShow bean) {
        listXunHuiJL.clear();
        String year_month_day = dateUtil.getYear_Day();
        String kaishiSJ = year_month_day + " 0:00:00";
        String jieshuSJ = year_month_day + " 23:59:59";
        String canshu = bean.getBrzyID() + "¤" + kaishiSJ + "¤" + jieshuSJ + "¤" + "BR";
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("03043001")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        log.e(canshu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                StringXmlParser parser = new StringXmlParser(xmlHandler_showmsg, new Class[]{XunHuiJL.class});
                log.e(data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                log.e(info);
            }
        });
    }

    private List<XunHuiJL> listXunHuiJL = new ArrayList<>();
    MyXmlHandler xmlHandler_showmsg = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if(listXunHuiJL.size()!=0){
                        //更新第0组
                        HuLiShow mGroup = (HuLiShow) mAdapter.getItem(showPosition);
                        mGroup.clearSubItems();

                        listXunHuiJL = dateUtil.hulichaxunOrderList(false, listXunHuiJL);
//                    mGroup.title = "zhangphil";
                        for (int i = 0; i < listXunHuiJL.size(); i++) {
                            XunHuiJL zxjl = new XunHuiJL();
                            zxjl.huLiShow = mGroup;
//                        zxjl.setZhiXingRen(list_zxjl.get(i).getZhiXingRen());
//                        zxjl.setZhiXingSJ(list_zxjl.get(i).getZhiXingSJ());
//                        zxjl.setYiZhuZXZT(list_zxjl.get(i).getYiZhuZXZT());
                            mGroup.addSubItem(listXunHuiJL.get(i));
                        }
                        mAdapter.setItem(mGroup);
                        mAdapter.notifyDataSetChanged();
                    }

//                    lvZXJL.setAdapter(new ZhiXingJiLuAdapter(ZhiXingJiLuActivity.this, mData));
////                    TextView view= (TextView) findViewById(R.id.tv_empty);
////                    lvZXJL.setEmptyView(view);
//                    LinearLayout noData = (LinearLayout) findViewById(R.id.nodata);
//                    lvZXJL.setEmptyView(noData);
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            XunHuiJL bean = (XunHuiJL) msg.obj;
                            if (!TextUtils.isEmpty(bean.getBingRenZYID())) {
                                listXunHuiJL.add(bean);
                            }
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    };

    private void commitToServer(final HuLiShow bean) {
//        String[] split = dengji.split("×");
        String canShu = bqdm + "¤" + bean.getBrzyID() + "¤" + bean.getName() + "¤" + yhgh
                + "¤" + bean.getYizhuMC() + "¤" + "" + "¤" + app.getYhxm() + "¤" + Constant.ONE;
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("03043006")
                .setMessage(NetWork.GongYong)
                .setCanshu(canShu)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("请求中...")
                .build();
        log.e(canShu);
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e(data);
                ToastUtil.showLong(bean.getChuang()+"巡视成功");
                //刷新页面
               serachmsg(bean);
            }

            @Override
            public void fail(String info) {
                ToastUtil.showLong("巡视失败");
                log.e(info);
            }
        });
    }

    /*
     * 巡视病人
     * */
    private void xunShi(final int tag, String brzyID, int cishu) {
        String tou = "";
        List<Yizhu> list_xml = new ArrayList<>();
        for (int i = 0; i < list_huli.size(); i++) {
            Yizhu yizhu = list_huli.get(i);
            if (brzyID.equals(list_huli.get(i).getBingRenZYID())) {
                tou = "开始";
                if (tag == 0) {
                    yizhu.setBeiZhu((cishu + 1) + "");
                    yizhu.setCiShu((cishu + 1) + "");
                } else {
                    if (cishu < 1) {
                        ToastUtil.showLong("不可回退！");
                        return;
                    } else if (cishu == 1) {
                        yizhu.setYiZhuZT("0");
                        tou = "未执行";
                    }
                    yizhu.setBeiZhu((cishu - 1) + "");
                    yizhu.setCiShu((cishu - 1) + "");
                }

                list_xml.add(yizhu);
            }
        }
        String xml = null;
        try {
            xml = createXml(list_xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder s = new StringBuilder();
        //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
        s.append(tou + "¤");
        s.append(xml + "¤");
        s.append("" + "¤");
        s.append(app.getYhxm() + "¤");
        s.append(yhgh + "¤");
        s.append(bqdm + "¤");
        s.append(1 + "¤");
        s.append(0 + "¤");

        zhierCall = (new ZhierCall())

                .setId("1000")
                .setNumber("0400902")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(s.toString())
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中..")
                .build();
        log.e(s.toString());
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                list_show.clear();
                list_huli.clear();
                log.e(data);
                initNework(num);
                if (tag == 0) {
                    ToastUtil.showLong(title + "巡视成功");
                } else {
                    ToastUtil.showLong(title + "回退成功");
                }
            }

            @Override
            public void fail(String info) {

            }
        });
    }

    private void init() {
        list_huli = new ArrayList<>();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);

        app = MyApplication.getInstance();
        listBRLB = app.getListBRLB();


        yhgh = app.getYhgh();
        bqdm = app.getBqdm();
        dateUtil = DateUtil.getInstance();

//        listBRLB = settext("")"";
//
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        num = intent.getIntExtra("num", -1);

        tvRight.setText("巡视记录");
        tvRight.setTextSize(14);
        tvTitleInclude.setText(title);
//        tvRight.setTextColor();
    }

//    @OnClick(R.id.iv_back_include)
//    public void onViewClicked() {
//        finish();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_back_include, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_include:
                startActivity(new Intent(HuLiDetailsAct.this, HomePageActivity.class));
                finish();
                break;
            case R.id.tv_right:
                startActivity(new Intent(HuLiDetailsAct.this, BqxhcxActivity.class));
//                yijianXunshi();
                break;
        }
    }

    private void zhiXjilu(HuLiShow huLiShow) {
        list_zxjl.clear();
        zhierCall = (new ZhierCall())
                .setId(yhgh)
                .setNumber("0401101")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(huLiShow.getTiaoMaID())
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                log.e("" + data);
                StringXmlParser parser = new StringXmlParser(handler_zxjl,
                        new Class[]{ZhiXingJL.class});
                parser.parse(data);
//                StringXmlParser parser = new StringXmlParser(handler, cla);
//                parser.parse(data);
//                parseData(myXmlHandler, new Class[]{ZhiXingJL.class}, data);
            }

            @Override
            public void fail(String info) {
                log.e("" + info);
            }
        });
    }

    private List<ZhiXingJL> list_zxjl = new ArrayList<>();

    MyXmlHandler handler_zxjl = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    //更新第0组
                    HuLiShow mGroup = (HuLiShow) mAdapter.getItem(showPosition);
                    mGroup.clearSubItems();
                    list_zxjl = dateUtil.invertOrderList(false, list_zxjl);
//                    mGroup.title = "zhangphil";
                    for (int i = 0; i < list_zxjl.size(); i++) {
                        ZhiXingJL zxjl = new ZhiXingJL();
                        zxjl.huLiShow = mGroup;
//                        zxjl.setZhiXingRen(list_zxjl.get(i).getZhiXingRen());
//                        zxjl.setZhiXingSJ(list_zxjl.get(i).getZhiXingSJ());
//                        zxjl.setYiZhuZXZT(list_zxjl.get(i).getYiZhuZXZT());
                        mGroup.addSubItem(list_zxjl.get(i));
                    }
                    mAdapter.setItem(mGroup);
                    mAdapter.notifyDataSetChanged();
//                    lvZXJL.setAdapter(new ZhiXingJiLuAdapter(ZhiXingJiLuActivity.this, mData));
////                    TextView view= (TextView) findViewById(R.id.tv_empty);
////                    lvZXJL.setEmptyView(view);
//                    LinearLayout noData = (LinearLayout) findViewById(R.id.nodata);
//                    lvZXJL.setEmptyView(noData);
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            ZhiXingJL bean = (ZhiXingJL) msg.obj;
                            if (!TextUtils.isEmpty(bean.getYiZhuZXZT())) {
                                list_zxjl.add(bean);
                            }
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    };
}
