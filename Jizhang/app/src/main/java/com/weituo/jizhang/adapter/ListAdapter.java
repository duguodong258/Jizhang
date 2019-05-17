package com.weituo.jizhang.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weituo.jizhang.R;
import com.weituo.jizhang.model.DayModel;

import java.util.List;

/**
 * @author duguodong
 * @time 2019/5/17
 * @des ${TODO}
 */
public class ListAdapter extends BaseAdapter {
    private List<DayModel> mDatas;

    public ListAdapter(List<DayModel> datas) {
        mDatas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, null);
            holder.tvLaunch = convertView.findViewById(R.id.tv_launch);
            holder.tvDinner = convertView.findViewById(R.id.tv_dinner);
            holder.tvOther = convertView.findViewById(R.id.tv_other);
            holder.tvTotal = convertView.findViewById(R.id.tv_total);
            holder.tvRemark = convertView.findViewById(R.id.tv_remark);
            holder.llRemark = convertView.findViewById(R.id.ll_remark);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        DayModel dayModel = mDatas.get(position);
        holder.tvLaunch.setText(dayModel.getLaunch() + "￥");
        holder.tvDinner.setText(dayModel.getDinner() + "￥");
        holder.tvOther.setText(dayModel.getOther() == 0 ? "00￥": dayModel.getOther() + "￥");
        holder.tvTotal.setText(dayModel.getTotal() + "￥");
        if(TextUtils.isEmpty(dayModel.getRemark())){
            holder.llRemark.setVisibility(View.GONE);
            holder.tvOther.setVisibility(View.INVISIBLE);
        }else{
            holder.tvRemark.setText(dayModel.getRemark());
            holder.llRemark.setVisibility(View.GONE);
        }

        return convertView;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        private TextView tvLaunch;
        private TextView tvDinner;
        private TextView tvOther;
        private TextView tvTotal;
        private TextView tvRemark;
        private LinearLayout llRemark;
    }
}
