package enjoyor.enjoyorzemobilehealth.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.SsHuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.entities.SSCX;

/**
 * Created by Administrator on 2017-08-22.
 */

public class ShoushuMCDialog extends Dialog{
    private final Context context;
    private final List<SSCX> data;
    private final String lx;
    private List<String> names = new ArrayList<>();
    List<String> tempList= new ArrayList<String>();
    private FlowLayout listssxz;
    private TextView tv_title;
    private SsHuLiDanActivity activity;


    public ShoushuMCDialog(Context context, List<SSCX> data, String lx) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.dialog_xhcx);
        this.context =context;
        this.data=data;
        this.lx = lx;
        tv_title = (TextView) findViewById(R.id.tv_title);
        listssxz = (FlowLayout) findViewById(R.id.listxhxz);
        activity = (SsHuLiDanActivity)context;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置为居中
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置dialog宽度为屏幕的4/5
        /*lp.height = (int) (display.getHeight());*/
        lp.y = 0;
        getWindow().setAttributes(lp);
        // setCanceledOnTouchOutside(false);// 点击Dialog外部消失
        setCancelable(true);
        tv_title.setText("请选择手术名称");
        for(int i=0;i<data.size();i++){
            names.add(data.get(i).getShouSuMC());
        }

        for(String i:names){
            if(!tempList.contains(i)){
                tempList.add(i);
            }
        }
        listssxz.addData(tempList,lx);
        listssxz.setFlowLayoutListener(new FlowLayout.FlowLayoutListener() {
            @Override
            public void onItemClick(View view, int poition) {
                activity.getShouShuMC(tempList.get(poition));
                dismiss();
            }
        });
    }
}
