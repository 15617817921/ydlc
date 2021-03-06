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

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.ShouShuhldDetailAdapter;
import enjoyor.enjoyorzemobilehealth.entities.ShuHouLD;

/**
 * Created by Administrator on 2017-08-24.
 */

public class SsHuLiDanDtailActivity extends AppCompatActivity {
    private ImageView mIvBack;
    private ListView mLvSShldDetail;
    private Context context;
    private LinearLayout nodata;
    private List<ShuHouLD> listShuHouLD=new ArrayList<>();
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
        listShuHouLD= (List<ShuHouLD>) intent.getSerializableExtra("listShuHouLD");
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mLvSShldDetail = (ListView) findViewById(R.id.lv_sshld_detail);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        ShouShuhldDetailAdapter adapter=new ShouShuhldDetailAdapter(context,listShuHouLD);
        if(listShuHouLD.size()==0){
            nodata.setVisibility(View.VISIBLE);
            mLvSShldDetail.setVisibility(View.GONE);
        }else{
            if(listShuHouLD.get(0).getJLSJ().equals("")){
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
                        intent.putExtra("cjsj",listShuHouLD.get(position).getJLSJ());
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
