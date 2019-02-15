package enjoyor.enjoyorzemobilehealth.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.hulistate.HuLiDetailsAct;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.base.BaseRecyclerAdapter;
import enjoyor.enjoyorzemobilehealth.base.SmartViewHolder;
import my_network.ZhierCall;

public class HuLiActivity extends BaseActivity {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.rcy_huli)
    RecyclerView rcyHuli;
    private BaseRecyclerAdapter<String> mAdapter;
    private ZhierCall zhierCall;
    private String yhgh = "";
    private String bqdm;
//    private HuLiNum huLiNum;//各个护理的个数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huli);
        ButterKnife.bind(this);
        initHandler(this);
        init();
//        initNework();
        initData();
    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);

        MyApplication application = MyApplication.getInstance();
        yhgh = application.getYhgh();
        bqdm = application.getBqdm();
        tvTitleInclude.setText("护理查询");
    }

//    private String[] strings = new String[]{"I级护理", "II级护理", "Ⅲ级护理", "特级护理", "健康教育"};
    private String[] strings = new String[]{"I级护理", "II级护理", "Ⅲ级护理", "特级护理"};

    private void initListener() {
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (strings[position]) {
                    case "特级护理":
                        startIntent(0, strings[position]);
                        break;
                    case "I级护理":
                        startIntent(1, strings[position]);
                        break;
                    case "II级护理":
                        startIntent(2, strings[position]);
                        break;
                    case "Ⅲ级护理":
                        startIntent(3, strings[position]);
                        break;
                    case "健康教育":
                        startIntent(4, strings[position]);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void startIntent(int position, String title) {
        Intent intent = new Intent(HuLiActivity.this, HuLiDetailsAct.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("num", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void initData() {
        rcyHuli.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rcyHighwen.setItemAnimator(new DefaultItemAnimator());
//        rcyHighwen.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(this, R.color.item_view)));
        rcyHuli.setAdapter(mAdapter = new BaseRecyclerAdapter<String>(Arrays.asList(strings), R.layout.item_huli_rcy) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, String bean, int position) {
                switch (position) {
                    case 0:
                        holder.text(R.id.item_title, bean);
//                        holder.text(R.id.item_title, bean + " (" + huLiNum.getY() + "人)");
                        break;
                    case 1:
                        holder.text(R.id.item_title, bean);
//                        holder.text(R.id.item_title, bean + " (" + huLiNum.getE() + "人)");
                        break;
                    case 2:
                        holder.text(R.id.item_title, bean);
//                        holder.text(R.id.item_title, bean + " (" + huLiNum.getS() + "人)");
                        break;
                    case 3:
                        holder.text(R.id.item_title, bean);
//                        holder.text(R.id.item_title, bean + " (" + huLiNum.getT() + "人)");
                        break;
                    case 4:
                        holder.text(R.id.item_title, bean);
//                        holder.text(R.id.item_title, bean + " (" + huLiNum.getT() + "人)");
                        break;

                    default:
                        break;
                }

                holder.text(R.id.item_content, "查询全科室所有" + bean + "的病人");
            }
        });
        initListener();
    }

    @OnClick(R.id.iv_back_include)
    public void onViewClicked() {
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(HuLiActivity.this, HomePageActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
//    private void initNework() {
//        showLoading("努力查询中请稍候...");
//        zhierCall = (new ZhierCall(progressDialog))
//                .setId(yhgh)
//                .setNumber("1100302")
//                .setMessage(NetWork.GongZuoLiang)
//                .setCanshu(bqdm)
//                .setContext(HuLiActivity.this)
//                .setPort(5000)
//                .build();
//        log.e("id=" + yhgh + "--canshu=" + bqdm);
//        zhierCall.start(new NetWork.SocketResult() {
//            @Override
//            public void success(String data) {
//                dismissLoading();
//                log.e("护理级别--" + data);
//                StringXmlParser parser = new StringXmlParser(xmlHandler2,
//                        new Class[]{HuLiJi.class, HuLiNum.class});
//                parser.parse(data);
//            }
//
//            @Override
//            public void fail(String info) {
//                dismissLoading();
//                log.e(info);
//                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    private List<HuLiJi> listHuliji = new ArrayList<>();
//    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
//        @Override
//        public void handlerMessage(Message msg) {
//            switch (msg.what) {
//                case END:
//                    initData();
//
//                    break;
//                case NODE:
//                    switch (msg.arg1) {
//                        case 0:
//                            listHuliji.add((HuLiJi) msg.obj);
//                            break;
//                        case 1:
//                            huLiNum = (HuLiNum) msg.obj;
//                            break;
//                        default:
//                            break;
//                    }
//                    break;
//                case TUPIAN:
//                    log.e("TUPIAN");
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
}
