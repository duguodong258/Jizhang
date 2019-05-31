package com.weituo.messagereceive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author duguodong
 * @time 2019/5/27
 * @des ${TODO}
 */
public class SmsReceiver extends BroadcastReceiver {

    private IMessageListener mIMessageListener;

    public void setIMessageListener(IMessageListener IMessageListener) {
        mIMessageListener = IMessageListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Log.i("GD", "...... onReceive ......");
        SmsMessage msg = null;
        if (null != bundle) {
            Log.i("GD", "...... null != bundle ......");
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());// 时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                Log.i("gd", "收到广播=======  address:" + msg.getOriginatingAddress() + "   body:" + msg.getDisplayMessageBody() + "  time:" + msg.getTimestampMillis());
//                if(msg.getDisplayMessageBody().startsWith("【Apple】") || msg.getDisplayMessageBody().startsWith("【APPLE】") || msg.getDisplayMessageBody().startsWith("[APPLE]")){
                if(msg.getDisplayMessageBody().contains("Your Apple ID Verification Code") || msg.getDisplayMessageBody().contains("您的 Apple ID 验证码")){
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("address",msg.getOriginatingAddress());
                    bundle1.putString("body",msg.getDisplayMessageBody());
                    bundle1.putString("time",msg.getTimestampMillis()+"");

                    if(mIMessageListener != null){
                        mIMessageListener.sendMsg(bundle1);
                    }
                }
            }
        }
    }

    public interface IMessageListener{
        void sendMsg(Bundle bundle);
    }
}
