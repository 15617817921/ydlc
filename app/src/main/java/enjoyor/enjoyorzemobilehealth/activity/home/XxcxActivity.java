package enjoyor.enjoyorzemobilehealth.activity.home;

/**
 * Created by Administrator on 2017/7/14.
 */

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
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.xxcx.BrxxcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.xxcx.HighTiwenActivity;
import enjoyor.enjoyorzemobilehealth.activity.xxcx.JyjgcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.xxcx.SsjlcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.xxcx.yinshiActivity;

public class XxcxActivity extends BaseActivity {


    @BindView(R.id.iv_back_include)
    ImageView ivBackInclude;
    @BindView(R.id.ll_brxxcx)
    LinearLayout llBrxxcx;
    @BindView(R.id.ll_jyjgcx)
    LinearLayout llJyjgcx;
    @BindView(R.id.ll_ssjlcx)
    LinearLayout llSsjlcx;
    @BindView(R.id.ll_yinshicx)
    LinearLayout llYinshicx;
    @BindView(R.id.ll_gtwckan)
    LinearLayout llGtwckan;
    @BindView(R.id.tv_title_include)
    TextView tvTitleInclude;
    @BindView(R.id.ll_hightwckan)
    LinearLayout llHightwckan;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xxcx);
        ButterKnife.bind(this);
        init();
        initHandler(this);
        initLisetener();
    }

    private void init() {
        tvTitleInclude.setText("信息查询");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.my_bule), 0);
    }

    @OnClick({R.id.iv_back_include, R.id.ll_brxxcx, R.id.ll_jyjgcx, R.id.ll_ssjlcx, R.id.ll_yinshicx, R.id.ll_gtwckan, R.id.ll_hightwckan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_include:
                startActivity(new Intent(XxcxActivity.this, HomePageActivity.class));
                finish();
                break;
            case R.id.ll_brxxcx:
                startActivity(new Intent(XxcxActivity.this, BrxxcxActivity.class));
                break;
            case R.id.ll_jyjgcx:
                startActivity(new Intent(XxcxActivity.this, JyjgcxActivity.class));
                break;
            case R.id.ll_ssjlcx:
                startActivity(new Intent(XxcxActivity.this, SsjlcxActivity.class));
                break;
            case R.id.ll_yinshicx:
                startActivity(new Intent(XxcxActivity.this, yinshiActivity.class));
                break;
            case R.id.ll_gtwckan:
                intent = new Intent(XxcxActivity.this, HighTiwenActivity.class);
                intent.putExtra("wendu", "37.5");
                startActivity(intent);
                break;
            case R.id.ll_hightwckan:
                intent = new Intent(XxcxActivity.this, HighTiwenActivity.class);
                intent.putExtra("wendu", "38");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initLisetener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(XxcxActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}

