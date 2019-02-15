package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.YcPingGuDanAdapter;
import enjoyor.enjoyorzemobilehealth.entities.YaChuangJL;

/**
 * Created by Administrator on 2017-08-31.
 */

public class YcPingGuDanDetailActivity extends AppCompatActivity {
    private ImageView mIvBack;
    private ListView mLvSShldDetail;
    private Context context;
    private LinearLayout nodata;
    private TextView mTitle;
    private List<YaChuangJL> listYaChuangJL=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sshld_detail);
        context=this;
        initData();
        initView();
    }

    private void initData() {
        Intent intent=getIntent();
        listYaChuangJL= (List<YaChuangJL>) intent.getSerializableExtra("listYaChuangJL");
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mLvSShldDetail = (ListView) findViewById(R.id.lv_sshld_detail);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        YcPingGuDanAdapter adapter=new YcPingGuDanAdapter(context,listYaChuangJL);
        mTitle.setText("压疮评估记录");
        if(listYaChuangJL.size()==0){
            nodata.setVisibility(View.VISIBLE);
            mLvSShldDetail.setVisibility(View.GONE);
        }else{
            if(listYaChuangJL.get(0).getJLSJ().equals("")){
                nodata.setVisibility(View.VISIBLE);
                mLvSShldDetail.setVisibility(View.GONE);
            }else{
                nodata.setVisibility(View.GONE);
                mLvSShldDetail.setVisibility(View.VISIBLE);
                mLvSShldDetail.setAdapter(adapter);
                mLvSShldDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent();
                        intent.putExtra("position",position);
                        intent.putExtra("cjsj",listYaChuangJL.get(position).getJLSJ());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

            }
        }

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
