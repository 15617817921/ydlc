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

/**
 * Created by Administrator on 2017/8/28.
 */

public class ZhiXingJiLuAdapter extends BaseAdapter {
    private Context context;
    private List<ZhiXingJL> mData;
    private LayoutInflater inflater;

    public ZhiXingJiLuAdapter(Context context, List<ZhiXingJL> mData) {
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_zhixingjilu, parent, false);
            holder.tvZhiXingState = (TextView) convertView.findViewById(R.id.tv_zxjl_state);
            holder.tvZhiXingRen = (TextView) convertView.findViewById(R.id.tv_zxjl_caozuoren);
            holder.tvZhiXingSJ = (TextView) convertView.findViewById(R.id.tv_zxjl_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String zxzt = mData.get(position).getYiZhuZXZT();
        switch (zxzt) {
            case "6":
                zxzt = "开始";
                break;
            case "3":
                zxzt = "暂停";
                break;
            case "5":
                zxzt = "继续";
                break;
            case "1":
                zxzt = "结束";
                break;
            case "2":
                zxzt = "异常中断";
                break;
            case "4":
                zxzt = "停用";
                break;
            case "0":
                zxzt = "未执行";
                break;
            default:
                break;
        }
        holder.tvZhiXingState.setText("执行状态:  " + zxzt);
        holder.tvZhiXingRen.setText("执行人:  " + mData.get(position).getZhiXingRen());
        holder.tvZhiXingSJ.setText("执行时间:  " + mData.get(position).getZhiXingSJ());
        return convertView;
    }

    class ViewHolder {
        TextView tvZhiXingState;
        TextView tvZhiXingRen;
        TextView tvZhiXingSJ;
    }
}
