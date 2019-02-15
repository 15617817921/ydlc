package qdh.wfsx;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import qdh.jyhd.JianYanJG;

/**
 * Created by Administrator on 2017/10/12.
 */

public class WFSXHeDuiAdapter extends BaseAdapter{
    private Context context;
    private List<JianYanJG> data;



    public WFSXHeDuiAdapter(Context context, List<JianYanJG> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        System.out.print("helloqqq" + data.size() + "\n");
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
        Log.d("login34", "进入适配器");
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.wf_sx, null);
            vHolder.yizhu1=(ImageView)convertView.findViewById(R.id.yizhu1);
            vHolder.yizhu2 = (TextView) convertView.findViewById(R.id.yizhu2);
            vHolder.lx = (TextView) convertView.findViewById(R.id.lx);
            //vHolder.jl = (TextView) convertView.findViewById(R.id.jl);
            vHolder.zxz = (TextView) convertView.findViewById(R.id.zxz);
            vHolder.zxsj = (TextView) convertView.findViewById(R.id.zxsj);
            vHolder.fhz = (TextView) convertView.findViewById(R.id.fhz);
            vHolder.fhsj = (TextView) convertView.findViewById(R.id.fhsj);
            vHolder.jsz = (TextView) convertView.findViewById(R.id.jsz);
            vHolder.jssj = (TextView) convertView.findViewById(R.id.jssj);

            //vHolder.jybt = (LinearLayout) convertView.findViewById(R.id.jybt);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
       // vHolder.jybt.setVisibility(View.GONE);
        JianYanJG jyjg = (JianYanJG) data.get(position);
        vHolder.lx.setText(jyjg.getJianChaMC());
        vHolder.zxz.setText(jyjg.getZXGH());
        vHolder.zxsj.setText(jyjg.getZXSJ());
        vHolder.fhz.setText(jyjg.getZXGH1());
        vHolder.fhsj.setText(jyjg.getZXSJ1());
        vHolder.jsz.setText(jyjg.getJSGH());
        vHolder.jssj.setText(jyjg.getJSSJ());
        if (jyjg.getBeiZhu().equals("1"))
        {
           vHolder.yizhu1.setImageResource(R.drawable.icon_kaishi3x);
           vHolder.yizhu2.setText("未复核");
        }
        else if(jyjg.getBeiZhu().equals("2")){
            vHolder.yizhu1.setImageResource(R.drawable.icon_kaishi3x);
            vHolder.yizhu2.setText("开始");
        }else if(jyjg.getBeiZhu().equals("3")){
            vHolder.yizhu1.setImageResource(R.drawable.icon_jieshu3x);
            vHolder.yizhu2.setText("结束");
        }else {
            vHolder.yizhu1.setImageResource(R.drawable.wzx);
            vHolder.yizhu2.setText("未执行");
        }


        return convertView;
    }

    static class ViewHolder {
        ImageView yizhu1;
        TextView yizhu2;
        TextView lx;
        TextView jl;
        TextView zxz;
        TextView zxsj;
        TextView fhz;
        TextView fhsj;
        TextView jsz;
        TextView jssj;
        //LinearLayout jybt;
    }
}
