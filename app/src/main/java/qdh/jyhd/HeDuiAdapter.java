package qdh.jyhd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.utlis.StringUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtil;
import my_network.MyLogger;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HeDuiAdapter extends BaseAdapter {
    private Context context;
    private List<JianYanJG> data;
    private ZhierCall zhierCall;
    private MyLogger log;
    private String zyid,userName,userID;

    private String tiaoMa="";
    private ProgressDialog dialog;

    public HeDuiAdapter(Context context, List<JianYanJG> data, String zyid, String tiaoMa, String userName, String userID) {
        this.context = context;
        this.data = data;
        this.zyid = zyid;
        this.tiaoMa = tiaoMa;
        this.userName = userName;
        this.userID = userID;
        log=MyLogger.kLog();
        log.e( zyid + "¤" + tiaoMa + "¤" + userName + "¤" + userID);
    }
    public HeDuiAdapter(Context context, List<JianYanJG> data) {
        this.context = context;
        this.data = data;
        log=MyLogger.kLog();
    }

    @Override
    public int getCount() {
        log.e(  data.size() +"");
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        ViewHolder vHolder = null;
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.jyjg_bgd_item, null);
            vHolder.lx = (TextView) convertView.findViewById(R.id.lx);
            vHolder.jysj = (TextView) convertView.findViewById(R.id.jysj);
            vHolder.jybt = (LinearLayout) convertView.findViewById(R.id.jybt);
            vHolder.ll_time_name = (LinearLayout) convertView.findViewById(R.id.ll_time_name);//核对时间
            vHolder.tv_hedui_time = (TextView) convertView.findViewById(R.id.tv_hedui_time);//核对时间
            vHolder.tv_zhixingren = (TextView) convertView.findViewById(R.id.tv_zhixingren);//核对人
            vHolder.tv_hd_beizhu = (TextView) convertView.findViewById(R.id.tv_hd_beizhu);//备注
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        vHolder.jybt.setVisibility(View.GONE);
        final JianYanJG jyjg = (JianYanJG) data.get(position);
        vHolder.lx.setText(jyjg.getJianChaMC());
        if (!StringUtils.stringNull(jyjg.getZXSJ()).equals("")) {
            vHolder.ll_time_name.setVisibility(View.VISIBLE);
            vHolder.tv_hedui_time.setText("核对时间:" + jyjg.getZXSJ());
            vHolder.tv_zhixingren.setText("核对人:" + jyjg.getZXGH());
        }else {
            vHolder.ll_time_name.setVisibility(View.GONE);
        }
        vHolder.tv_hd_beizhu.setText("备注:" + jyjg.getBZXX1());

        vHolder.jysj.setText(jyjg.getJianYanSJ());
        if (jyjg.getBeiZhu().equals("1")) {
            vHolder.lx.setTextColor(Color.BLUE);
            vHolder.jysj.setTextColor(Color.BLUE);
        } else {
            vHolder.lx.setTextColor(Color.BLACK);
            vHolder.jysj.setTextColor(Color.BLACK);
        }
        vHolder.tv_hd_beizhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!jyjg.getBeiZhu().equals("1")){
//                    ToastUtil.showLong("未核对，不能备注");
//                }else {
                    showdialog(jyjg);
//                }
            }
        });
        return convertView;
    }

    private String[] shuju = new String[]{"拒测", "抽血失败", "未能留便","条码损坏重新打印","其他原因"};

    private void showdialog(final JianYanJG bean) {
        new AlertDialog.Builder(context).setTitle("备注原因")
                .setItems(shuju, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = shuju[which];
                        bean.setBZXX1(s);
                        notifyDataSetChanged();
                        shangchuang(s);
//                        onClickMyTextView.myTextViewClick(s);
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void shangchuang(String beizhu) {
        showLoading("备注中...");
        tiaoMa= MyApplication.getInstance().getHedui_tiaoMa();
        String canshu = zyid + "¤" + tiaoMa + "¤" + userName + "¤" + userID+"¤"+beizhu;
        log.e(canshu);
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("0500002")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(context)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                dismissLoading();
                ToastUtil.showLong("备注成功");
                log.e(data);
//                String date = DateUtil.getInstance().getDate();
            }

            @Override
            public void fail(String info) {
                dismissLoading();
                ToastUtil.showLong("备注失败");
                log.e(info);
            }
        });
    }
    private void showLoading(String content) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(content);
        dialog.show();
    }
    private void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    static class ViewHolder {
        TextView lx;
        TextView jysj;
        TextView tv_zhixingren;
        TextView tv_hedui_time;
        TextView tv_hd_beizhu;
        LinearLayout jybt;
        LinearLayout ll_time_name;
    }

    private onClickMyTextView onClickMyTextView;

    //接口回调
    public interface onClickMyTextView {
        void myTextViewClick(String beizhu);
    }

    public void setOnClickMyTextView(onClickMyTextView onClickMyTextView) {
        this.onClickMyTextView = onClickMyTextView;
    }

}
