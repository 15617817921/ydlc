package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.base.CommonAdapter;
import enjoyor.enjoyorzemobilehealth.base.ViewHolder;
import enjoyor.enjoyorzemobilehealth.entities.CbMoreBean;

/**
 * Created by Administrator on 2017/9/21.
 */

public class LvCbOtherAdapter extends CommonAdapter<CbMoreBean.Bean> {
    private List<CbMoreBean.Bean> list;

    public LvCbOtherAdapter(Context context, List<CbMoreBean.Bean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        list = mDatas;

    }

    @Override
    public void convert(ViewHolder holder, final List<CbMoreBean.Bean> datas, final String pfxx, final int position) {
        final CbMoreBean.Bean bean = datas.get(position);//iv_choose
        holder.setText(R.id.gv_name, bean.getName()).setText(R.id.gv_sorce, bean.getSorce());
        final TextView tvSorce = holder.getView(R.id.gv_sorce);
        final ImageView image = holder.getView(R.id.iv_choose);
        final RelativeLayout rl_vte = holder.getView(R.id.rl_vte);

        if (bean.isChecked()) {//状态选中
            image.setImageResource(R.drawable.btn_choose_on);
            tvSorce.setTextColor(Color.parseColor("#3f90eb"));
        } else {
            image.setImageResource(R.drawable.btn_choose_null);
            tvSorce.setTextColor(Color.parseColor("#888888"));
        }
        //展示评分信息
        for (int i = 0; i < list.size(); i++) {
            if (bean.getMsg().equals("1")) {
                image.setImageResource(R.drawable.btn_choose_on);
                tvSorce.setTextColor(Color.parseColor("#3f90eb"));
            }
        }
        rl_vte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pfxxTag = "";
                int sorce = 0;
                if (mListener != null) {
                    for (CbMoreBean.Bean bean : datas) {//全部设为未选中

                    }

                    bean.setChecked(!bean.isChecked());
                    if(bean.isChecked()){
                        bean.setMsg("1");
                    }else{
                        bean.setMsg("0");
                    }


                    String fen = "";
                    for (int i = 0; i < datas.size(); i++) {
                        pfxxTag += "#" + datas.get(i).getMsg();
                        fen = datas.get(i).getSorce();
                        if (datas.get(i).getMsg().equals("1")) {
                            sorce = Integer.parseInt(fen.substring(0, fen.length() - 1))+sorce;
                        }
                    }
                    notifyDataSetChanged();
                    Log.e("评分信息标识、分数", pfxxTag + "--" + sorce);
                }
                mListener.onCheckClick(pfxxTag,sorce,position,bean.getName());
            }
        });
    }


    public interface OnCheckClickListener {
        void onCheckClick(String pfxxTag, int sorce, int position, String content);
    }

    private LvCbAdapter.OnCheckClickListener mListener;

    public void setCheckListener(LvCbAdapter.OnCheckClickListener mListener) {
        this.mListener = mListener;
    }
}
