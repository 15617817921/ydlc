package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.PingTieXinXiAdapter;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;

/**
 * Created by Administrator on 2017/8/28.
 */

public class PingTieXinXiActivity extends AppCompatActivity {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    private String name;
    private String bedNum;
    private ArrayList<Yizhu> mData;

    private TextView tvPatientName;
    private TextView tvBedNum;
    private TextView tvPinCi;
    private TextView tvTime;
    private TextView tvKZSJ;
    private TextView tvTZSJ;
    private ListView lvYiZhu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pingtiexinxi);
        ButterKnife.bind(this);
        init();

        getData();

        initView();

    }

    private void init() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
        tvTitleInclude.setText("瓶贴信息");
    }

    private void getData() {
        mData = new ArrayList<>();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        bedNum = intent.getStringExtra("bedNum");
        mData = (ArrayList<Yizhu>) intent.getSerializableExtra("yizhu");
    }

    private void initView() {
        tvPatientName = (TextView) findViewById(R.id.tv_ptxx_name);
        tvBedNum = (TextView) findViewById(R.id.tv_ptxx_bedNum);
        tvPinCi = (TextView) findViewById(R.id.tv_ptxx_pinci);
        tvTime = (TextView) findViewById(R.id.tv_ptxx_time);
        tvKZSJ = (TextView) findViewById(R.id.tv_ptxx_kzsj);
        tvTZSJ = (TextView) findViewById(R.id.tv_ptxx_time1);
        lvYiZhu = (ListView) findViewById(R.id.lv_ptxx_yizhu);

        ivBackInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvPatientName.setText(name);
        tvBedNum.setText(bedNum);
        tvPinCi.setText(mData.get(0).getPinCi());
        tvTime.setText(mData.get(0).getJiHuaZXSJ());
        tvKZSJ.setText(mData.get(0).getKaiZhuSJ());
        tvTZSJ.setText(mData.get(0).getJieShuSJ());
        lvYiZhu.setAdapter(new PingTieXinXiAdapter(PingTieXinXiActivity.this, mData));
    }
}
