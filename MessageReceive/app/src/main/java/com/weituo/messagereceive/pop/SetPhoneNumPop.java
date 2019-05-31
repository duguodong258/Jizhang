package com.weituo.messagereceive.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weituo.messagereceive.R;

/**
 * @author duguodong
 * @time 2019/5/30
 * @des ${TODO}
 */
public class SetPhoneNumPop extends PopupWindow implements View.OnClickListener {
    private EditText etNumber;
    private Button btnSure;
    private ICallback mICallback;
    private final TextView mTvCurrentNum;


    public SetPhoneNumPop(Context context, String line1Number, ICallback callback) {
        super(context);
        mICallback = callback;
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);

        mTvCurrentNum = contentView.findViewById(R.id.tv_current_num);
        etNumber = contentView.findViewById(R.id.et_number);
        btnSure = contentView.findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        mTvCurrentNum.setText("当前号码: " + line1Number);

        setContentView(contentView);
        setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure :
                if(mICallback != null){
                    mICallback.getSettingPhoneNum(etNumber.getText().toString());
                }
                break;
        }
    }

    public interface ICallback {
        void getSettingPhoneNum(String phoneNum);
    }
}
