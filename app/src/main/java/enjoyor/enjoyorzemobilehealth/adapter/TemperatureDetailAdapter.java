package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.SMTZSJ;

/**
 * Created by Administrator on 2017/6/14.
 */

public class TemperatureDetailAdapter extends BaseAdapter{
    private Context context;
    private List<SMTZSJ> mTiWenList;
    private LayoutInflater inflater;

    public TemperatureDetailAdapter(Context context, List<SMTZSJ> mTiWenList) {
        this.context = context;
        this.mTiWenList = mTiWenList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTiWenList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTiWenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_smtzmx_detail,null);
            holder.tvRQ= (TextView) convertView.findViewById(R.id.tv_temper_detail_rq);
            holder.tvSJ= (TextView) convertView.findViewById(R.id.tv_temper_detail_sj);
            holder.ivShow= (TextView) convertView.findViewById(R.id.tv_temper_detail_lx);
            holder.tvTemperatureValue= (TextView) convertView.findViewById(R.id.tv_temper_detail_value);
            holder.tvDanWei = (TextView)convertView.findViewById(R.id.tv_temper_detail_danwei);
            holder.line=convertView.findViewById(R.id.vertical_line);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        SMTZSJ tiWen=mTiWenList.get(position);
        if (tiWen.getSHIJIAN().trim().length() > 0) {
            holder.tvRQ.setText(tiWen.getSHIJIAN().split(" ")[0]);
            holder.tvSJ.setText(tiWen.getSHIJIAN().split(" ")[1]);
            holder.tvTemperatureValue.setText(tiWen.getSHUZHI());
            holder.ivShow.setText(tiWen.getLEIXING());
            holder.tvDanWei.setText(tiWen.getDANWEI());
            if (position == mTiWenList.size() - 1) {
                holder.line.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvRQ;
        TextView tvSJ;
        TextView ivShow;
        TextView tvTemperatureValue;
        TextView tvDanWei;
        View line;
    }
}
