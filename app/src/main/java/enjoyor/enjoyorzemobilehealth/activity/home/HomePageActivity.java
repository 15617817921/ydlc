package enjoyor.enjoyorzemobilehealth.activity.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.entities.BRLB;
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.BingQuYiZhuActivity;
import enjoyor.enjoyorzemobilehealth.activity.HuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.activity.JkjyActivity;
import enjoyor.enjoyorzemobilehealth.activity.LoginActivity;
import enjoyor.enjoyorzemobilehealth.activity.RypgActivity;
import enjoyor.enjoyorzemobilehealth.activity.SsHuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.activity.YcPingGuDanActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.DangerActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.HomePageEntity;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import hld.next.NewQdhHuLiDanActivity;
import qdh.hdl.NewHuliDanActivity;
import qdh.sd.SxsdActivity;
import qdh.sxbbcj.SxbbcjActivity;
import qdh.sxbbsc.SxbbscActivity;

public class HomePageActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    List<HomePageEntity> list = new ArrayList<>();
    private Intent intent;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        initHandler(this);
        setList();
        setStatusBar();
        setToolBar();
        setRecyclerView();
        textView = (TextView) findViewById(R.id.gh);
        textView.setText("当前工号为：" + MyApplication.getInstance().getYhgh() + "  " + MyApplication.getInstance().getYzmc());
        ImageView imageView = (ImageView) findViewById(R.id.image_o2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * 从资源文件中获得图片资源和名字资源
     */
    private void setList() {
//        try{}
        Resources re = getResources();
        TypedArray name = re.obtainTypedArray(R.array.homepage_name);
        TypedArray drawable = re.obtainTypedArray(R.array.homepage_drawable);
        log.e("初始化主页面个数" + name.length() + "--" + drawable.length());
        for (int i = 0; i < re.getStringArray(R.array.homepage_name).length; i++) {
            if(MyApplication.getInstance().getYzmc().contains("眼")){

            }
            list.add(new HomePageEntity(re.getDrawable(drawable.getResourceId(i, 0)), name.getString(i)));
        }
        name.recycle();
        drawable.recycle();
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new CommonAdapter<HomePageEntity>(this, R.layout.homepage_recycler_view_item, list) {
            @Override
            protected void convert(final ViewHolder holder, HomePageEntity homePageEntity, int position) {
                holder.setImageDrawable(R.id.image_home, list.get(position).getDrawable());
                holder.setText(R.id.text_home, list.get(position).getName());

                final String name = list.get(position).getName();
                holder.setOnClickListener(R.id.bbb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (name) {
                            case "病人列表":
                                startActivity(new Intent(HomePageActivity.this, BrlbActivity.class));
//                                finish();
                                break;
                            case "病人医嘱":
                                List<BRLB> list = MyApplication.getInstance().getListBRLB();
                                intent = new Intent(HomePageActivity.this, BryzBbenActivity.class);
                                intent.putExtra("xingming", list.get(MyApplication.getInstance().getChoosebr()).getXINGMING());
                                intent.putExtra("xingbie", list.get(MyApplication.getInstance().getChoosebr()).getXINGBIE());
                                intent.putExtra("chuanghao", list.get(MyApplication.getInstance().getChoosebr()).getCHUANGWEIHAO());
                                intent.putExtra("brid", list.get((MyApplication.getInstance().getChoosebr())).getBINGRENID());
                                startActivity(intent);
//                                finish();
                                break;
                            case "医嘱核对":
                                startActivity(new Intent(HomePageActivity.this, BingQuYiZhuActivity.class));
//                                finish();
                                break;
                            case "入院评估":
                                startActivity(new Intent(HomePageActivity.this, RypgActivity.class));
//                                finish();
                                break;
                            case "危险评估":
                                intent = new Intent(HomePageActivity.this, DangerActivity.class);
                                intent.putExtra(Constant.TAG, "home");
                                SaveUtils.put(HomePageActivity.this, Constant.IS_COMMIT, "homepage");
                                startActivity(intent);
//                                finish();
                                break;
                            case "生命体征录入":
                                startActivity(new Intent(HomePageActivity.this, ShengMingTiZhengLuRuActivity.class));
//                                finish();
                                break;
                            case "体温查看":
                                startActivity(new Intent(HomePageActivity.this, TemperatureChartActivity.class));
//                                finish();
                                break;
                            case "护理单":
                                startActivity(new Intent(HomePageActivity.this, HuLiDanActivity.class));
//                                finish();
                                break;
                            case "信息查询":
                                startActivity(new Intent(HomePageActivity.this, XxcxActivity.class));
//                                finish();
                                break;
                            case "巡回记录":
                                startActivity(new Intent(HomePageActivity.this, XhcxActivity.class));
//                                finish();
                                break;
                            case "健康教育":
                                startActivity(new Intent(HomePageActivity.this, JkjyActivity.class));
                                break;
                            case "血糖监测":
//                                startActivity(new Intent(HomePageActivity.this,BloodSugarCheckActivity.class));
                                startActivity(new Intent(HomePageActivity.this, enjoyor.enjoyorzemobilehealth.activity.bzyxyytfy.BloodSugarCheckActivity.class));

//                                finish();
                                break;
                            case "手术护理单":
                                startActivity(new Intent(HomePageActivity.this, SsHuLiDanActivity.class));
//                                finish();
                                break;
                            case "压疮评估单":
                                startActivity(new Intent(HomePageActivity.this, YcPingGuDanActivity.class));
//                                finish();
                                break;
                            case "检验核对":
                                startActivity(new Intent(HomePageActivity.this, HeDuiActivity.class));
//                                finish();
                            case "新护理单":
                                startActivity(new Intent(HomePageActivity.this, NewHuliDanActivity.class));
//                                finish();
                                break;
                            case "血交叉采集":
                                startActivity(new Intent(HomePageActivity.this, SxbbcjActivity.class));
//                                finish();
                                break;
                            case "血交叉送出":
                                startActivity(new Intent(HomePageActivity.this, SxbbscActivity.class));
//                                finish();
                                break;
                            case "血袋核对":
                                startActivity(new Intent(HomePageActivity.this, SxsdActivity.class));
//                                finish();
                                break;
                            case "输血":
                                startActivity(new Intent(HomePageActivity.this, SXHeDuiActivity.class));
//                                finish();
                                break;
                            case "护理表单":
                                intent = new Intent(HomePageActivity.this, NewQdhHuLiDanActivity.class);
                                intent.putExtra(Constant.TAG, "home");
                                SaveUtils.put(HomePageActivity.this, Constant.IS_COMMIT, "homepage");
                                startActivity(intent);
//                                finish();
                                break;
                            case "病人流转":
                                startActivity(new Intent(HomePageActivity.this, ChuangWeiLiuZhuanActivity.class));
//                              finish();
                                break;
                            case "护理查询":
                                startActivity(new Intent(HomePageActivity.this, HuLiActivity.class));
//                                finish();
                                break;
                            case "滴眼":
                                startActivity(new Intent(HomePageActivity.this, DiYanActivity.class));
////                                finish();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });

    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(HomePageActivity.this, mColor, 0);
    }

    /**
     * 设置ToolBar
     */
    private void setToolBar() {
        toolbar.setTitle("银江移动医疗");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(HomePageActivity.this, "xx", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_notifications:
                        Toast.makeText(HomePageActivity.this, "xx", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Tool设置菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.findItem(RcyMoreAdapter.id.action_search).getIcon().setBounds(0,0,20,20);
        //getMenuInflater().inflate(RcyMoreAdapter.menu.hone_page_menu, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //finish();
            showTips();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void showTips() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("退出程序").setMessage("是否退出程序")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }
}
