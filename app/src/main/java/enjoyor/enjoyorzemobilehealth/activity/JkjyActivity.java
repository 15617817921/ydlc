package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.home.HomePageActivity;

/**
 * Created by Administrator on 2017/7/24.
 */

public class JkjyActivity extends BaseActivity {
    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;

    private LinearLayout ryjd;
    private LinearLayout zyjd;
    private LinearLayout cyzd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkjy_login);
        ButterKnife.bind(this);
        init();
        defineData();
        clickData();
    }

    private void init() {
        tvTitleInclude.setText("健康教育");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }

    private void defineData() {
        ryjd = (LinearLayout) findViewById(R.id.ryjd);
        zyjd = (LinearLayout) findViewById(R.id.zyjd);
        cyzd = (LinearLayout) findViewById(R.id.cyzd);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(JkjyActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

//        if (android.os.Build.MODEL.equals("m80")||android.os.Build.MODEL.equals("m80s")) {
//            if (scanInf.onKeyDown(keyCode, event, HeDuiActivity.this.getClass().getName())) {
//
//                return true;
//
//            }
//        }

        return super.onKeyDown(keyCode, event);
    }

    private void clickData() {
        ryjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JkjyActivity.this, RyjdActivity.class);
                intent.putExtra("eduStyle", "入院阶段");
                intent.putExtra("eduStyleID", "1");
                startActivity(intent);
            }
        });
        zyjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JkjyActivity.this, RyjdActivity.class);
                intent.putExtra("eduStyle", "住院阶段");
                intent.putExtra("eduStyleID", "2");
                startActivity(intent);
            }
        });
        cyzd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JkjyActivity.this, RyjdActivity.class);
                intent.putExtra("eduStyle", "出院指导");
                intent.putExtra("eduStyleID", "3");
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.iv_back_include)
    public void onViewClicked() {
        startActivity(new Intent(JkjyActivity.this, HomePageActivity.class));
        finish();
    }
}
