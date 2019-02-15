package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.ChuangWeiZH;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ChuangWeiZHAdapter extends BaseAdapter {
    private final Context context;
    private final List<ChuangWeiZH> data;
    private LayoutInflater mLayoutInflater;

    public ChuangWeiZHAdapter(Context context, List<ChuangWeiZH> data){
        this.context=context;
        this.data=data;
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
        ViewHolder vHolder=new ViewHolder();
        if(convertView==null){
            convertView=mLayoutInflater.from(context).inflate(R.layout.activity_chuangweiliuzhuan_item,null);
            vHolder.zhcz=(TextView)convertView.findViewById(R.id.zhcz);
            vHolder.zhhs=(TextView)convertView.findViewById(R.id.zhhs);
            vHolder.zhsj=(TextView)convertView.findViewById(R.id.zhsj);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        ChuangWeiZH bean=data.get(position);
        vHolder.zhcz.setText(bean.getBingQuMC());
        vHolder.zhhs.setText(bean.getZHRen());
        vHolder.zhsj.setText(bean.getZHSJ());

        return convertView;
    }
    static class ViewHolder{
        TextView zhcz;
        TextView zhhs;
        TextView zhsj;
    }
}
