package qdh.jyhd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;


/**
 * Created by Administrator on 2017/10/12.
 */

public class HeDuiJLAdapter extends BaseAdapter{
    private Context context;
    private List<JianYanJGMX> data;
    private LayoutInflater mLayoutInflater;

    public HeDuiJLAdapter(Context context, List<JianYanJGMX> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = new ViewHolder();
        if(convertView==null){
            convertView = mLayoutInflater.from(context).inflate(R.layout.activity_heduijl_item,null);
            vHolder.hdmc=(TextView)convertView.findViewById(R.id.hdmc);
            vHolder.hdr=(TextView)convertView.findViewById(R.id.hdr);
            vHolder.zxsj=(TextView)convertView.findViewById(R.id.zxsj);
            vHolder.xm=(TextView)convertView.findViewById(R.id.xm);
            vHolder.ch=(TextView)convertView.findViewById(R.id.ch);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        JianYanJGMX bean= data.get(position);
        String chuangweihao="";
        MyApplication instance = MyApplication.getInstance();
        for(int i=0;i<instance.getListBRLB().size();i++){
            if(bean.getBingRenZYID().equals(instance.getListBRLB().get(i).getBINGRENZYID())){
                chuangweihao=instance.getListBRLB().get(i).getCHUANGWEIHAO();
                break;
            }
        }
        vHolder.ch.setText(chuangweihao);
        vHolder.xm.setText(bean.getXingMing());
        vHolder.hdmc.setText(bean.getJianYanMC());
        vHolder.hdr.setText(bean.getYongHuMC());
        vHolder.zxsj.setText(bean.getZhiXingSJ());
        return convertView;
    }
    static class  ViewHolder{
        TextView hdmc;
        TextView hdr;
        TextView zxsj;
        TextView xm;
        TextView ch;
    }
}
