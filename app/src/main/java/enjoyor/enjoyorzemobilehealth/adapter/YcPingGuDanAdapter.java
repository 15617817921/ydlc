package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.YaChuangJL;

/**
 * Created by Administrator on 2017-08-31.
 */

public class YcPingGuDanAdapter extends BaseAdapter{
    private final Context context;
    private final List<YaChuangJL> data;
    private final LayoutInflater inflater;

    public YcPingGuDanAdapter(Context context, List<YaChuangJL> data){
        this.context=context;
        this.data=data;
        inflater= LayoutInflater.from(context);

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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.sshld_detail_item,parent,false);

            holder.tvDate= (TextView) convertView.findViewById(R.id.tv_detail_date);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        YaChuangJL bean=data.get(position);
        holder.tvDate.setText(bean.getJLSJ());
        return convertView;
    }
    class ViewHolder{
        TextView tvDate;
    }
}
