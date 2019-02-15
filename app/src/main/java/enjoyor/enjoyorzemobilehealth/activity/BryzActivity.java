package enjoyor.enjoyorzemobilehealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentFive;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentFour;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentOne;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentSix;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentThree;
import enjoyor.enjoyorzemobilehealth.bryz.fragments.FragmentTwo;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;
import enjoyor.enjoyorzemobilehealth.scan.ScanFactory;
import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;
import enjoyor.enjoyorzemobilehealth.utlis.ListDataSave;
import enjoyor.enjoyorzemobilehealth.utlis.ZhuanHuanTool;
import enjoyor.enjoyorzemobilehealth.views.BottomMenu;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.TUPIAN;

//import org.jdom.Document;

//新版本中无效
public class BryzActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener,ScanFactory.FragScan{

//    Document document=new Document();
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar navigationBar;
    @BindView(R.id.no4)
    ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    //fragments
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private FragmentFour fragmentFour;
    private FragmentFive fragmentFive;
    private FragmentSix fragmentSix;
    //view
    List<View> list=new ArrayList<>();
    private BottomMenu menuWindow;
    ZhierCall zhierCall;
    List<Yizhu> list_yizhu=new ArrayList<>();
    ListDataSave dataSave;
    ImageView back;

    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;
    private WindowManager.LayoutParams windowManagerParams = null;
    private Handler mDoDecodeHandler;
    private final int ID_SCANSETTING = 0x12;
    private final int ID_CLEAR_SCREEN = 0x13;
    private final int ID_SCANTOUCH = 0x14;

    private EditText mDecodeResultEdit = null;
    private final int SCANKEY_LEFT = 301;
    private final int SCANKEY_RIGHT = 300;
    private final int SCANKEY_CENTER = 302;
    private final int SCANTIMEOUT = 3000;
    long mScanAccount = 0;
    private boolean mbKeyDown = true;
    private boolean scanTouch_flag = true;
    ScanInterface scanInf;
    public ImageView all_image=null;
    public ArrayList<ImageView> imageViewArrayList=new ArrayList<>();
    public ArrayList<TextView>  textViewArrayList=new ArrayList<>();
    int image=0;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }
    private BryzActivity.DoDecodeThread mDoDecodeThread;

    private String time1="2017-08-12 00:00:00";
    private String time2="2017-08-12 23:59:59";
    private String ll;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bryz);
        ButterKnife.bind(this);
        getFragments();
        navigationBar
                .setActiveColor("#3F90EB")
                .setInActiveColor("#4B4A4A")
                .setBarBackgroundColor("#00FFFFFF");
        navigationBar
                .setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        linearLayout= (LinearLayout) findViewById(R.id.no3);
        BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("1")
                .setBackgroundColorResource(R.color.colorAccent)
                .setBorderWidth(0);
        dataSave = new ListDataSave(this, "yizhu");
        navigationBar
                .addItem(new BottomNavigationItem(R.drawable.icon_koufu3x, "口服"))
                .addItem(new BottomNavigationItem(R.drawable.icon_shuye3x, "输液"))
                .addItem(new BottomNavigationItem(R.drawable.icon_linshi3x, "临时"))
                .addItem(new BottomNavigationItem(R.drawable.icon_zhushe3x, "注射"))
                .addItem(new BottomNavigationItem(R.drawable.icon_zhiliao3x, "治疗"))
                .addItem(new BottomNavigationItem(R.drawable.icon_qita3x, "其他"))
                .initialise();
         navigationBar.setTabSelectedListener(this);
         LinearLayout linearLayoutxx= (LinearLayout) findViewById(R.id.no3);
         linearLayoutxx.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 startActivity(new Intent(BryzActivity.this,BrlbActivity.class));
                 finish();
             }
         });

         //导航栏中的tab添加小圆点
        navigationBar.addItem(new BottomNavigationItem(R.drawable.icon_shuye3x, "输液").setActiveColorResource(R.color.text_color).setBadgeItem(badgeItem));



        setStatusBar();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.print("----------------------------------------------------\n");
        System.out.print("开始获取医嘱数据："+ df.format(new Date())+"\n");

        List<BRLB> listBRLB=new ArrayList<>();
        listBRLB=MyApplication.getInstance().getListBRLB();

        ll=listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID();
        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0400101")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(ll + "¤" + time1 + "¤"+time2)
                .setContext(this)
                .setPort(5000)
                .setDialogmes("加载中...")
                .build();
        Toast.makeText(BryzActivity.this,ll + "¤" + time1 + "¤"+time2,Toast.LENGTH_LONG).show();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.print("----------------------------------------------------\n");
                System.out.print("获取到了请求数据，开始解析："+ df.format(new Date())+"\n");

               StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{Yizhu.class});
                //Log.d("login5",data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                Toast.makeText(BryzActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent=getIntent();
        String xm=intent.getStringExtra("xingming");
        String xb=intent.getStringExtra("xingbie");
        String cwh=intent.getStringExtra("chuanghao");

        ImageView imageView= (ImageView) findViewById(R.id.imageView);
        TextView textView= (TextView) findViewById(R.id.bingrenname);
        TextView textView1= (TextView) findViewById(R.id.chuanghao);
        if(xb.equals("男")){
            imageView.setImageResource(R.drawable.icon_men);
        }else {
            imageView.setImageResource(R.drawable.icon_women);
        }
        textView.setText(xm);
        textView1.setText(cwh+"床");

        viewPager.setOffscreenPageLimit(1);

   ;

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setDistanceToTriggerSync(400);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.my_bule);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        LinearLayout xx= (LinearLayout) findViewById(R.id.no3);
        onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list_yizhu=null;
                list_yizhu=new ArrayList<Yizhu>();
                zhierCall = (new ZhierCall())
                        .setId("1000")
                        .setNumber("0400101")
                        .setMessage(NetWork.YIZHU_ZHIXING)
                        .setCanshu(ll + "¤" + time1 + "¤"+time2)
                        .setContext(getBaseContext())
                        .setPort(5000).setDialogmes("加载中...")
                        .build();

                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        System.out.print("----------------------------------------------------\n");
                        System.out.print("获取到了请求数据，开始解析："+ df.format(new Date())+"\n");

                        StringXmlParser parser = new StringXmlParser(xmlHandler,
                                new Class[]{Yizhu.class});
                        //Log.d("login5",data);
                        parser.parse(data);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(BryzActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String info) {
                        // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        final float[] mPosX = new float[1];
        final float[] mPosY = new float[1];
        final float[] mCurPosX = new float[1];
        final float[] mCurPosY = new float[1];

       xx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX[0] = event.getX();
                        mPosY[0] = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX[0] = event.getX();
                        mCurPosY[0] = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY[0] - mPosY[0] > 0 && (Math.abs(mCurPosY[0] - mPosY[0]) > 25)) {
                            //向下滑動
                           linearLayout.setVisibility(View.INVISIBLE);
                        } else if (mCurPosY[0] - mPosY[0] < 0 && (Math.abs(mCurPosY[0] - mPosY[0]) > 25)) {
                            //向上滑动
                            linearLayout.setVisibility(View.GONE);
                        }
                        break;
                }
                return true;
            }
        });

        mDoDecodeThread = new BryzActivity.DoDecodeThread();
        mDoDecodeThread.start();

        initSaoMiao();

    }

    private void getFragments() {

        list=null;
        list=new ArrayList<>();

        LayoutInflater inflater=getLayoutInflater();
        View view1=inflater.inflate(R.layout.fragment_one,null);
        View view2=inflater.inflate(R.layout.fragment_two,null);
        View view3=inflater.inflate(R.layout.fragment_three,null);
        View view4=inflater.inflate(R.layout.fragment_four,null);
        View view5=inflater.inflate(R.layout.fragment_five,null);
        View view6=inflater.inflate(R.layout.fragment_six,null);

        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);
        list.add(view6);


        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager=null;
                startActivity(new Intent(BryzActivity.this,HomePageActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                break;
            case 4:
                viewPager.setCurrentItem(4);
                break;
            case 5:
                viewPager.setCurrentItem(5);
                break;
            default:
                viewPager.setCurrentItem(6);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(this, mColor, 0);
    }

    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    System.out.print("----------------------------------------------------\n");
                    System.out.print("解析完成："+ df.format(new Date())+"\n");

                    System.out.print("医嘱:"+list_yizhu.get(0).getKaiZhuSJ()+"\n");

                    final List<Yizhu> list1=new ArrayList<>();
                    final List<Yizhu> list2=new ArrayList<>();
                    final List<Yizhu> list3=new ArrayList<>();
                    final List<Yizhu> list4=new ArrayList<>();
                    final List<Yizhu> list5=new ArrayList<>();
                    final List<Yizhu> list6=new ArrayList<>();

                    for(int i=0;i<list_yizhu.size();i++){
                        System.out.print("下面打印出医嘱分类:"+list_yizhu.get(i).getYongFaFL()+"\n");
                        switch(list_yizhu.get(i).getYongFaFL()){
                            case "1":
                                //System.out.print("注射:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list1.add(list_yizhu.get(i));
                                break;
                            case "2":
                                //System.out.print("治疗:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list2.add(list_yizhu.get(i));
                                break;
                            case "3":
                                //System.out.print("口服:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list3.add(list_yizhu.get(i));
                                break;
                            case "4":
                                list4.add(list_yizhu.get(i));
                                break;
                            case "5":
                                /*//System.out.print("其它医嘱:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list5.add(list_yizhu.get(i));*/
                                break;
                            case "6":
                                //System.out.print("其它医嘱:"+list_yizhu.get(i).getYongFaFL()+"\n");
                                list5.add(list_yizhu.get(i));
                                break;
                        }
                    }

                    System.out.print("list1测试就否存在数据:"+" "+list1.size()+"\n");
                    System.out.print("list2测试就否存在数据:"+" "+list2.size()+"\n");
                    System.out.print("list3测试就否存在数据:"+" "+list3.size()+"\n");
                    System.out.print("list4测试就否存在数据:"+" "+list4.size()+"\n");
                    //System.out.print("list5测试就否存在数据:"+list5.get(0).getYongFaFL()+"\n");
                    //System.out.print("list6测试就否存在数据:"+list6.get(0).getYongFaFL()+"\n");




                    PagerAdapter pagerAdapter=new PagerAdapter() {
                        @Override
                        public int getCount() {
                            return list.size();
                        }

                        @Override
                        public boolean isViewFromObject(View arg0, Object arg1) {
                            return arg0 == arg1;
                        }

                        @Override
                        public void destroyItem(ViewGroup container, int position, Object object) {
                            container.removeView(list.get(position));
                        }

                        @Override
                        public Object instantiateItem(View container, int position) {
                            ((ViewPager) container).addView(list.get(position), 0);
                            //navigationBar.selectTab(position);
                            return list.get(position);
                        }
                    };

                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            navigationBar.selectTab(position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                   /* try {
                        createXml(list2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    viewPager.setOffscreenPageLimit(6);
                    viewPager.setCurrentItem(1);
                    setRecyclerView(0,list4);
                    setRecyclerView(1,list1);
                    setRecyclerView(2,list3);
                    setRecyclerView(3,list2);
                    setRecyclerView(4,list6);
                    setRecyclerView(5,list5);



                    break;

                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            list_yizhu.add((Yizhu) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                case TUPIAN:
                    int i=msg.getData().getInt("what");
                    int j=msg.getData().getInt("what2");
                    ImageView imageView=(ImageView)msg.getData().getParcelable("object");
                    switch (i) {
                       case 0:
                           imageViewArrayList.get(j).setImageResource(R.drawable.wzx);
                           textViewArrayList.get(j).setTextColor(Color.rgb(151,198,52));
                          break;
                        case 1:
                           imageViewArrayList.get(j).setImageResource(R.drawable.icon_jieshu3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0,0,0));
                           break;
                        case 2:

                           imageViewArrayList.get(j).setImageResource(R.drawable.icon_zhongduan3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0,0,0));
                           break;
                        case 3:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_zanting3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(151,198,52));
                            break;
                        case 4:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_tingyong3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0,0,0));
                            break;
                        case 5:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_jixu3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(0,0,0));
                            break;
                        case 6:

                            imageViewArrayList.get(j).setImageResource(R.drawable.icon_kaishi3x);
                            textViewArrayList.get(j).setTextColor(Color.rgb(108,199,241));
                            break;
                        case 7:
                           //holder.setText(RcyMoreAdapter.id.yizhu2, "复核");
                            break;
                       case 8:
                           //holder.setText(RcyMoreAdapter.id.yizhu2, "提药");

                            break;
                        case 9:
                            //holder.setText(RcyMoreAdapter.id.yizhu2, "收药");

                    }

                    break;
                default:
                    break;
            }
        }
    };

    public void dataSave(List<Yizhu> list1,SharedPreferences.Editor editor,String name){
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(list1);
        System.out.print("Gson转化数据："+strJson+"\n");

        editor.clear();
        editor.putString(name, strJson);
        editor.commit();
    }

    public void setRecyclerView(final int index, final List<Yizhu> list1) {
        View one = null;
        System.out.print("list大小：" + list1.size() + "\n");
        viewPager.setCurrentItem(0);
        switch (index) {
            case 0:
                one = list.get(index).findViewById(R.id.one);
                break;
            case 1:
                one = list.get(index).findViewById(R.id.two);
                break;
            case 2:
                one = list.get(index).findViewById(R.id.three);
                break;
            case 3:
                System.out.print("加载第4个界面");
                one = list.get(index).findViewById(R.id.four);
                break;
            case 4:
                one = list.get(index).findViewById(R.id.five);
                break;
            case 5:
                one = list.get(index).findViewById(R.id.six);
                break;

        }
        //list1.add(list1.get(0));
        RecyclerView recyclerView = (RecyclerView) one.findViewById(R.id.fragment_recyclerview);
        final int[] i = {0};
        //进行分组
        final ArrayList<ArrayList<Yizhu>> fenzhu_list=new ArrayList<>();
        ArrayList<Yizhu> jiahuan=new ArrayList<>();
        ArrayList<String> all=new ArrayList<>();
        String fenzhu1="";
        try{
            fenzhu1=list1.get(0).getTiaoMaID();
            all.add(fenzhu1);
        }catch (Exception e){

        }

        for(Yizhu yizhu:list1){
            ArrayList<String> all2=new ArrayList<>();
            int o=0;
            for(String s:all){
                if(yizhu.getTiaoMaID().equals(s)){
                    o=1;
                    break;
                }
            }

            if(o==0){
                all.add(yizhu.getTiaoMaID());
            }
        }

        //用分好的进行排序
        for(String fenzhu:all){
            int ff=0;
            for(Yizhu yizhu:list1){
                if(fenzhu.equals(yizhu.getTiaoMaID())){
                    jiahuan.add(yizhu);
                    ff=1;
                }
            }
            if(ff==1){
                fenzhu_list.add(jiahuan);
                jiahuan=null;
                jiahuan=new ArrayList<>();
            }

        }

        for(int p=0;p<fenzhu_list.size();p++)
        {
           System.out.println("分组后的代码为:");
           System.out.println("一共有："+fenzhu_list.get(0).size());
           System.out.println("个数："+fenzhu_list.get(0).size());
        }
       try{
            recyclerView.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArrayList<Yizhu>>(getBaseContext(), R.layout.bryz_recycler_view_item, fenzhu_list) {
                String tmid="";
                int nn=1;
                int bb=0;
                @Override
                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ArrayList<Yizhu> yizhus, final int position) {

                    LinearLayout linearLayout=holder.getView(R.id.real_back);
                    ViewGroup.LayoutParams ip;
                    ip=linearLayout.getLayoutParams();
                    ip.height=dp2px(BryzActivity.this,72)+fenzhu_list.get(position).size()*dp2px(BryzActivity.this,50);
                    linearLayout.setLayoutParams(ip);
                    linearLayout.invalidate();

                    final int vv=position;
                    View view=holder.getView(R.id.my_list);
                    ListView listView= (ListView) view.findViewById(R.id.my_list);
                    listView.setAdapter(new CommonAdapter<Yizhu>(getBaseContext(), R.layout.bryz_singleitem, fenzhu_list.get(vv)) {

                        @Override
                        protected void convert(ViewHolder viewHolder, Yizhu item, int position) {
                            viewHolder.setText(R.id.nn1,fenzhu_list.get(vv).get(position).getYiZhuMC());
                            viewHolder.setTag(R.id.nn2,"剂量  "+fenzhu_list.get(vv).get(position).getJiLiang()+fenzhu_list.get(vv).get(position).getJiLiangDW());
                        }
                    });

                    listView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    holder.setOnClickListener(R.id.bdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("which","2");
                            MyApplication.getInstance().setOther_brlb(null);
                            Intent intent=new Intent(BryzActivity.this,BrlbActivity.class);
                            intent.putExtra("which","2");
                            startActivity(intent);
                            finish();
                        }
                    });

                if(fenzhu_list.size()==0){

                }else {
                    tmid=fenzhu_list.get(0).get(0).getTiaoMaID();
                }

                    holder.setOnClickListener(R.id.more, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final View layout=findViewById(R.id.bottom);
                            layout.setVisibility(View.VISIBLE);
                            Button button= (Button) layout.findViewById(R.id.btn2);
                            Button button1= (Button) layout.findViewById(R.id.btn_cancel);
                            String[] s=new String[1];
                            s[0]=fenzhu_list.get(position).get(0).getTiaoMaID();
                            toZX(s);

                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    layout.setVisibility(View.GONE);
                                }
                            });

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(BryzActivity.this,ZXYZActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("no1",fenzhu_list.get(position).get(0).getYiZhuMC());
                                    bundle.putString("no2",fenzhu_list.get(position).get(0).getYongLiang());
                                    intent.putExtra("no1",fenzhu_list.get(position).get(0).getYiZhuMC());
                                    intent.putExtra("no2",fenzhu_list.get(position).get(0).getJiLiang());
                                    layout.setVisibility(View.GONE);
                                    startActivity(intent);
                                    //finish();
                                }
                            });

                        }
                    });

                    if(position!=0)
                    {
//                        holder.setVisible(R.id.pp,false);
                        i[0]++;
                    }
                    System.out.print("位置："+position);

                    //if(position)
                    switch (fenzhu_list.get(position).get(0).getYiZhuZT()) {
                        case "0":
                            holder.setText(R.id.yizhu2, "未执行");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,0);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "1":
                            holder.setText(R.id.yizhu2, "结束");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,1);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "2":
                            holder.setText(R.id.yizhu2, "异常中断");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,2);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "3":
                            holder.setText(R.id.yizhu2, "暂停");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,3);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(151,198,52));
                            break;
                        case "4":
                            holder.setText(R.id.yizhu2, "停用");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,4);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "5":
                            holder.setText(R.id.yizhu2, "继续");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,5);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "6":
                            holder.setText(R.id.yizhu2, "开始");
                            holder.setTextColor(R.id.yizhu2,Color.rgb(108,199,241));
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,6);
                            image++;
                            break;
                        case "7":
                            holder.setText(R.id.yizhu2, "复核");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,7);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "8":
                            holder.setText(R.id.yizhu2, "提药");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,8);
                            image++;
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            break;
                        case "9":
                            holder.setText(R.id.yizhu2, "收药");
                            imageViewArrayList.add((ImageView) holder.getView(R.id.yizhu1));
                            textViewArrayList.add((TextView) holder.getView(R.id.yizhu2));
                            sendObject(image,9);
                            holder.setTextColor(R.id.yizhu2,Color.rgb(0,0,0));
                            image++;
                    }

                    //holder.setText(RcyMoreAdapter.id.yizhu3, list1.get(position).getYiZhuMC());
                    int gb=position+1;
                    holder.setText(R.id.yizhu4, "第" + gb + "组");

                    //holder.setText(RcyMoreAdapter.id.yizhu5, list1.get(position).getJiLiang() + list1.get(position).getJiLiangDW());
                    holder.setText(R.id.yizhu6, fenzhu_list.get(position).get(0).getPinCi());
                    holder.setText(R.id.yizhu7, fenzhu_list.get(position).get(0).getYongFaMC());
                    holder.setText(R.id.nn4,fenzhu_list.get(position).get(0).getCaoZuoRen());
                    holder.setTag(R.id.nn5,fenzhu_list.get(position).get(0).getCaoZuoSJ());


                }
            });
        }catch (Exception e){

        }



    }

    MediaPlayer player=new MediaPlayer();
    //核实病人列表
    private void zxBR(String brid){
        List<BRLB> listBRLB=new ArrayList<>();
        listBRLB=MyApplication.getInstance().getListBRLB();
        int i=0;
        for(BRLB x:listBRLB){
            if(brid.equals(x.getBINGRENZYID()+"*"))
            {
                getYZ(brid);
                i=1;
                break;
            }
        }

        if(i==0){
            Toast.makeText(BryzActivity.this,"扫码病人ID失败",Toast.LENGTH_SHORT).show();
            player.start();
        }
    }

    private void toZX(String[] tmid){
        List<BRLB> listBRLB=new ArrayList<>();
        listBRLB=MyApplication.getInstance().getListBRLB();
        List<Yizhu> list_shangchuan=new ArrayList<>();
        int i=0;
        for(String s:tmid)
        {
            for(Yizhu x:list_yizhu){
                System.out.println(x.getTiaoMaID());
                if(s.equals(x.getTiaoMaID()))
                {
                    list_shangchuan.add(x);
                    i=1;
                    break;
                }
            }
        }

        String all;
        String xml=null;
        String kk=list_shangchuan.get(0).getYiZhuZT();
        for(int l=0;l<list_shangchuan.size();l++)
        {
            //list_shangchuan.get(l).setYiZhuZT(ZhuanHuanTool.toString1(ZhuanHuanTool.toInt((Integer.parseInt(kk)+1)+"")));
        }
        try {
            xml=createXml(list_shangchuan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Yizhu s:list_shangchuan){
            s.setYiZhuZT((Integer.parseInt(s.getYiZhuZT())+1)+"");
        }
        StringBuilder s=new StringBuilder();
        //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
        s.append("ThreeMills"+"¤");
        s.append(xml+"¤");
        s.append(listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID()+"¤");
        s.append(MyApplication.getInstance().getYhxm()+"¤");
        s.append(MyApplication.getInstance().getYhgh()+"¤");
        s.append(tmid[0]+"¤");
        s.append(0+"¤");
        s.append(0+"¤");

        MyApplication.getInstance().setListZXYZ(s.toString());
        MyApplication.getInstance().setChangeYIZHU(list_shangchuan.get(0).getYiZhuZT());
    }
    //核实医嘱信息
    private void ypZX(String[] tmid){
        List<BRLB> listBRLB=new ArrayList<>();
        listBRLB=MyApplication.getInstance().getListBRLB();
        List<Yizhu> list_shangchuan=new ArrayList<>();
        int i=0;
        for(String s:tmid)
        {
            for(Yizhu x:list_yizhu){
                System.out.println(x.getTiaoMaID());
                if(s.equals(x.getTiaoMaID()))
                {
                    list_shangchuan.add(x);
                    i=1;
                    break;
                }
            }
        }

        String pp=list_shangchuan.get(0).getYiZhuZT();
        if(pp.equals(ZhuanHuanTool.toInt("未执行"))){
           Toast.makeText(BryzActivity.this,"收药或配药", Toast.LENGTH_LONG).show();
        }else if (pp.equals(ZhuanHuanTool.toInt("摆药"))||pp.equals(ZhuanHuanTool.toInt("收药"))){
            if(i==1){
                String all;
                String xml=null;
                try {
                    xml=createXml(list_shangchuan);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for(Yizhu s:list_shangchuan){
                    s.setYiZhuZT((Integer.parseInt(s.getYiZhuZT())+1)+"");
                }
                StringBuilder s=new StringBuilder();
                //s.append(list_shangchuan.get(0).getYiZhuZT()+"¤");
                s.append("ThreeMills"+"¤");
                s.append(xml+"¤");
                s.append(listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID()+"¤");
                s.append(MyApplication.getInstance().getYhxm()+"¤");
                s.append(MyApplication.getInstance().getYhgh()+"¤");
                s.append(tmid[0]+"¤");
                s.append(0+"¤");
                s.append(0+"¤");

                MyApplication.getInstance().setListZXYZ(s.toString());
                MyApplication.getInstance().setChangeYIZHU(list_shangchuan.get(0).getYiZhuZT());
                String ll=listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID();
                zhierCall = (new ZhierCall())
                        .setId("1000")
                        .setNumber("0400902")
                        .setMessage(NetWork.YIZHU_ZHIXING)
                        .setCanshu(s.toString())
                        .setContext(this)
                        .setPort(5000)
                        .setDialogmes("加载中...")
                        .build();

                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(BryzActivity.this,"成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void fail(String info) {
                        Toast.makeText(BryzActivity.this, info, Toast.LENGTH_LONG).show();
                    }
                });
            }
            if(i==0){
                Toast.makeText(BryzActivity.this,"扫码病人ID失败",Toast.LENGTH_SHORT).show();
                player.start();
            }
        }else if(pp.equals(ZhuanHuanTool.toInt("结束"))){
            Toast.makeText(BryzActivity.this,"输液结束",Toast.LENGTH_SHORT).show();
        }
    }

    private void getYZ(String id){



        zhierCall = (new ZhierCall())
                .setId(id)
                .setNumber("0400101")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu( ll + "¤" +time1 + "¤"+time2)
                .setContext(this)
                .setPort(5000).setDialogmes("加载中...")
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{Yizhu.class});
                //Log.d("login5",data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });


    }

    public MediaPlayer createLocalMp3(){
        /**
         * 创建音频文件的方法：
         * 1、播放资源目录的文件：MediaPlayer.create(HighTiwenActivity.this,RcyMoreAdapter.raw.beatit);//播放res/raw 资源目录下的MP3文件
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
         */
        MediaPlayer mp=MediaPlayer.create(BryzActivity.this, R.raw.failure);
        mp.stop();
        return mp;
    }

    public String createXml( List<Yizhu> list) throws Exception
    {
        for(int i=0;i<list.size();i++)
        {
            list.get(i).getYiZhuZT();
        }
         //1.头部

         StringBuilder s=new StringBuilder();
         s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>"+"\r\n");
         s.append("<DS Name=\"59408853\" Num=\"1\">"+"\r\n");
         s.append("<DT Name=\"my_YiZhu\" Num=\""+list.size()+"\">"+"\r\n");

         //2.中部
        for(int j=0;j<list.size();j++)
        {
            Field[] fields=list.get(j).getClass().getDeclaredFields();
            s.append("<DR Name=\"56152722\" Num=\"35\">"+"\r\n");
            for(int i=0;i<fields.length;i++)
            {
                s.append("<DC Name=\""+fields[i].getName()+"\" Num=\"0\">"+fields[i].get(list.get(j))+"</DC>"+"\r\n");
            }
            s.append("</DR>"+"\r\n");
        }

         //3.尾部
        s.append("</DT>"+"\r\n");
        s.append("</DS>");

        System.out.print(s.toString()+"xxxxx");
        return s.toString();
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
                return BryzActivity.this;
            }
        }.getInstance(4, this, 0);
    }

    @Override
    public void putDataToFrag(String data, int keycode) {
        //Toast.makeText(BryzActivity.this,data,Toast.LENGTH_SHORT).show();

        String[] s=data.split("\\*");

        Toast.makeText(BryzActivity.this,s[0],Toast.LENGTH_SHORT).show();
        System.out.print(data);
       if(s[0].equals("st72")){
            zxBR(s[1]);
        }else if(s[0].equals("st99")){
           String[] a=new  String[1];
         /* for(int i=2;i<(s.length-1);i++)
              a[i-1]=s[i];*/
           a[0]=s[2];
           ypZX(a);
           Toast.makeText(BryzActivity.this,a[0],Toast.LENGTH_LONG).show();
        }
    }

    long exitTime=0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            /*if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }*/
            startActivity(new Intent(BryzActivity.this,HomePageActivity.class));
            finish();
            return true;
        }
        else if (scanInf.onKeyDown(keyCode, event, BryzActivity.this.getClass().getName())) {

            return true;

        }

        return super.onKeyDown(keyCode, event);
    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // LogUtil.i("keyCode", keyCode + ":" + event.getAction());
        // scanning.onKeyUp(keyCode, event);
        if (scanInf.onKeyUp(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void sendObject(int j,int i)
    {
        Message message=new Message();
        Bundle bundle=new Bundle();
        //bundle.putParcelable("object", (Parcelable) view);
        bundle.putInt("what",i);
        bundle.putInt("what2",j);
        message.setData(bundle);
        message.what=TUPIAN;
        xmlHandler.sendMessage(message);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefreshListener.onRefresh();
    }
}
