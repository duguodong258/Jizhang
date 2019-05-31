package com.weituo.messagereceive.adapter;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weituo.messagereceive.R;
import com.weituo.messagereceive.model.MsgModel;

import java.util.List;
import java.util.Random;


/**
 * @author duguodong
 * @time 2019/5/17
 * @des ${TODO}
 */
public class ListAdapter extends BaseAdapter {
    private List<MsgModel> mDatas;
    private int[] icons = {
            R.drawable.aa2, R.drawable.aa3, R.drawable.aa4, R.drawable.aa5, R.drawable.aa6, R.drawable.aa7,R.drawable.aa8,
            R.drawable.aa9, R.drawable.aa10, R.drawable.aa11, R.drawable.aa12, R.drawable.aa13, R.drawable.aa14,R.drawable.aa15,
            R.drawable.aa16, R.drawable.aa17, R.drawable.aa18, R.drawable.aa19, R.drawable.aa20, R.drawable.aa21,R.drawable.aa22,
    };

    public ListAdapter(List<MsgModel> datas) {
        mDatas = datas;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, null);
            holder.ivHead = convertView.findViewById(R.id.iv_head);
            holder.tvCode = convertView.findViewById(R.id.tv_code);
            holder.tvFrom = convertView.findViewById(R.id.tv_from);
            holder.tvMy = convertView.findViewById(R.id.tv_my);
            holder.tvDate = convertView.findViewById(R.id.tv_date);
            holder.tvId = convertView.findViewById(R.id.tv_id);
            holder.llRoot = convertView.findViewById(R.id.ll_root);


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MsgModel msgModel = mDatas.get(position);
        holder.tvCode.setText(msgModel.getCode());
        holder.tvFrom.setText(msgModel.getFrom_phone());
        holder.tvMy.setText(msgModel.getMy_phone());
        holder.tvDate.setText(msgModel.getTime().substring(5,msgModel.getTime().length()));
        holder.tvId.setText(mDatas.size() - position + "");
        if(position % 2 == 1){
            holder.llRoot.setBackgroundColor(parent.getResources().getColor(R.color.trans_parent_30));
        }


        int index = new Random().nextInt(21);
//        holder.ivHead.setImageResource(icons[index]);
        final ViewHolder finalHolder = holder;
        Glide.with(parent.getContext()).load(icons[index]).asBitmap().centerCrop().into(new BitmapImageViewTarget(finalHolder.ivHead){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(parent.getContext().getResources(), resource);
                roundedBitmapDrawable.setCornerRadius(10);
                finalHolder.ivHead.setImageDrawable(roundedBitmapDrawable);
            }
        });

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
        private ImageView ivHead;
        private TextView tvId;
        private TextView tvCode;
        private TextView tvFrom;
        private TextView tvMy;
        private TextView tvDate;
        private LinearLayout llRoot;
    }
}
