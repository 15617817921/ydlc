package enjoyor.enjoyorzemobilehealth.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/8/21.
 */

public class OperationHuLiJiLuFormActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_operation_hld);
    }
}
