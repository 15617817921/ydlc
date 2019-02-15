package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.Yizhu;

/**
 * Created by Administrator on 2017/9/12.
 */

public class PopUpWindowWithNoItemClick extends PopupWindow{
    private Context context;
    private List<Yizhu> mList;
    private View view;
    private LayoutInflater inflater;
    private ListView mListView;

    public PopUpWindowWithNoItemClick(Context context, List<Yizhu> mList) {
        this.context=context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        view = inflater.inflate(R.layout.list_popupwindow, null);
        mListView = (ListView) view.findViewById(R.id.lv_pop_content);
        mListView.setAdapter(new MyAdapter());

        this.setContentView(this.view);
        this.setWidth(500);
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(600);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffF8F8F8")));
        this.setOutsideTouchable(true);
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
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
                convertView=inflater.inflate(R.layout.item_popwindow_bryz,null);
                holder.yiZhuName= (TextView) convertView.findViewById(R.id.tv_yzmc);
                holder.jiLiang= (TextView) convertView.findViewById(R.id.tv_jiliang);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.yiZhuName.setText(mList.get(position).getYiZhuMC());
            holder.jiLiang.setText("剂量  "+mList.get(position).getJiLiang()+mList.get(position).getJiLiangDW());
            return convertView;
        }
        class ViewHolder{
            TextView yiZhuName;
            TextView jiLiang;
        }
    }

}
