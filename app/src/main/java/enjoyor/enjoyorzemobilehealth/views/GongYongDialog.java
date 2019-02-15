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

/**
 * Created by Administrator on 2017-08-29.
 */

public class GongYongDialog extends Dialog implements View.OnClickListener{
    private final Context context;
    private final String lx;
    private final String[] data;
    List<String> tempList= new ArrayList<String>();
    private FlowLayout listssxz;
    private TextView tv_title;
    private Dialogcallback dialogcallback;
    public GongYongDialog(Context context, String[] data, String lx,Dialogcallback dialogcallback) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.dialog_xhcx);
        this.context =context;
        this.dialogcallback = dialogcallback;
        this.data=data;
        this.lx = lx;
        tv_title = (TextView) findViewById(R.id.tv_title);
        listssxz = (FlowLayout) findViewById(R.id.listxhxz);
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


        for(String i:data){

                tempList.add(i);

        }
        listssxz.addData(tempList,lx);
        listssxz.setFlowLayoutListener(new FlowLayout.FlowLayoutListener() {
            @Override
            public void onItemClick(View view, int poition) {
                //activity.getShouShuMC(tempList.get(poition));
                dialogcallback.dialogdo(data[poition]);
              //  listener.OnCenterItemClick(view,poition);
                dismiss();
            }
        });
    }
    //设置回调接口，使对话框选中的值可传给Activity
    public interface Dialogcallback {
        public void dialogdo(String string);
    }
    public void setDialogCallback(Dialogcallback dialogcallback) {
        this.dialogcallback = dialogcallback;
    }

    /*private OnCenterItemClickListener listener;
    private String[] list;
    public interface OnCenterItemClickListener {
        void OnCenterItemClick(View view,int poition);
    }
    public void setOnCenterItemClickListener(String[] data,OnCenterItemClickListener listener) {
        this.list=data;
        this.listener = listener;
    }*/
    @Override
    public void onClick(View v) {

    }
    public void setTexttitle(String str){
        tv_title.setText(str);
    }

}
