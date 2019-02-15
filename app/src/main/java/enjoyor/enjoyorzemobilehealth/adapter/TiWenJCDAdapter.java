package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.TiWenJCD;


public class TiWenJCDAdapter extends BaseAdapter{
    private final Context context;
    private final List<TiWenJCD> data;
    private LayoutInflater mLayoutInflater;
    public TiWenJCDAdapter(Context context, List<TiWenJCD> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vHolder = new ViewHolder();
        if (convertView == null) {
            System.out.print("loguin50:" + "\n");
            convertView = mLayoutInflater.from(context).inflate(R.layout.activity_tiwenjcd_item, null);
            vHolder.ycsj = (TextView) convertView.findViewById(R.id.ycsj);
            vHolder.dsjd = (TextView) convertView.findViewById(R.id.dsjd);
            //vHolder.sss=(TextView)convertView.findViewById(R.id.sss);
            //vHolder.zd=(TextView)convertView.findViewById(R.id.zd);
            vHolder.br = (TextView) convertView.findViewById(R.id.br);
            vHolder.ch = (TextView) convertView.findViewById(R.id.ch);
            System.out.print("loguin502:" + "\n");

            convertView.setTag(vHolder);
        } else {
            vHolder = (TiWenJCDAdapter.ViewHolder) convertView.getTag();
        }
        TiWenJCD sscx = (TiWenJCD) data.get(position);

        vHolder.ycsj.setText(sscx.getYLSJ());
        vHolder.dsjd.setText(sscx.getSJ());
        vHolder.br.setText(sscx.getXM());
        vHolder.ch.setText(sscx.getCH() + "床");

        return convertView;
    }
    static class ViewHolder{
        TextView br;
        TextView ycsj;
        TextView dsjd;
        TextView ch;
    }
}
