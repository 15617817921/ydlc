package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.ZhiXingJL;

public class XHuiAdapter extends BaseAdapter {
    private Context context;
    private List<ZhiXingJL> data;
    private LayoutInflater mLayoutInflater;

    public XHuiAdapter(Context context, List<ZhiXingJL> data){
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
            convertView = mLayoutInflater.from(context).inflate(R.layout.item_xunhui_clv,null);
            vHolder.tv_zxZhuangT=(TextView)convertView.findViewById(R.id.tv_zxZhuangT);
            vHolder.tv_xunhuitime=(TextView)convertView.findViewById(R.id.tv_xunhuitime);
            vHolder.tv_xhHushi=(TextView)convertView.findViewById(R.id.tv_xhHushi);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }

        ZhiXingJL xhjl= data.get(position);
        vHolder.tv_zxZhuangT.setText(xhjl.getCishu());
        vHolder.tv_xunhuitime.setText(xhjl.getZhiXingSJ());
        vHolder.tv_xhHushi.setText(xhjl.getZhiXingRen());
        return convertView;
    }
    static class  ViewHolder{
        TextView tv_zxZhuangT;
        TextView tv_xunhuitime;
        TextView tv_xhHushi;
    }
}
