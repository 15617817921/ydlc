package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.ShouShuXX;


public class SscxAdapter extends BaseAdapter{
    private final Context context;
    private final List<ShouShuXX> data;
    private LayoutInflater mLayoutInflater;
    public SscxAdapter(Context context, List<ShouShuXX> data){
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
            System.out.print("loguin50:"+"\n");
            convertView = mLayoutInflater.from(context).inflate(R.layout.activity_ssjlcx_item,null);
            vHolder.sj=(TextView)convertView.findViewById(R.id.sj);
            vHolder.ssmc=(TextView)convertView.findViewById(R.id.ssmc);
            vHolder.sss=(TextView)convertView.findViewById(R.id.sss);
            vHolder.zd=(TextView)convertView.findViewById(R.id.zd);
            vHolder.br=(TextView)convertView.findViewById(R.id.br);
            vHolder.ch=(TextView)convertView.findViewById(R.id.ch);
            System.out.print("loguin502:"+"\n");

            convertView.setTag(vHolder);
        }else{
            vHolder = (SscxAdapter.ViewHolder) convertView.getTag();
        }
        ShouShuXX sscx= (ShouShuXX)data.get(position);
        if(sscx.getAPRQ().trim().length() > 0) {
            String shijan = sscx.getAPRQ().substring(2, sscx.getAPRQ().length() - 3);
            vHolder.sj.setText(shijan);
            vHolder.ssmc.setText(sscx.getSSMC());
            vHolder.sss.setText("");//sscx.getShouShuShi());
            vHolder.zd.setText("");//sscx.getShouShuRY());
            vHolder.br.setText(sscx.getXINGMING());
            vHolder.ch.setText(sscx.getCHUANGWEIHAO() + "床");
        }
        return convertView;
    }
    static class ViewHolder{
        TextView br;
        TextView sj;
        TextView ssmc;
        TextView sss;
        TextView zd;
        TextView ch;
    }
}
