package com.weituo.jizhang.popwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.weituo.jizhang.R;

/**
 * @author duguodong
 * @time 2019/5/21
 * @des ${TODO}
 */
public class MyPopupwindow extends PopupWindow {
    private Context mContext;

    public MyPopupwindow(Context context) {
        super(context);
        mContext = context;
    }

    public MyPopupwindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MyPopupwindow(View contentView) {
        super(contentView);
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_layout, null);
        setContentView(view);
    }

}
