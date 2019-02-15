package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;

/**
 * Created by Administrator on 2017/8/28.
 */

public class PingTieXinXiAdapter extends BaseAdapter{
    private Context context;
    private List<Yizhu> mData;
    private LayoutInflater inflater;

    public PingTieXinXiAdapter(Context context, List<Yizhu> mData) {
        this.context = context;
        this.mData = mData;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.bryz_singleitem,parent,false);
            holder.tvYiZhu= (TextView) convertView.findViewById(R.id.nn1);
            holder.tvJiLiang= (TextView) convertView.findViewById(R.id.nn2);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvYiZhu.setText(mData.get(position).getYiZhuMC());
        holder.tvJiLiang.setText("剂量  "+mData.get(position).getJiLiang()+mData.get(position).getJiLiangDW());
        return convertView;
    }
    class ViewHolder{
        TextView tvYiZhu;
        TextView tvJiLiang;
    }
}
