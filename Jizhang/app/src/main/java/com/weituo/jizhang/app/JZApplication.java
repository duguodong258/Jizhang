package com.weituo.jizhang.app;

import android.app.Application;

import com.zhy.changeskin.SkinManager;

import cn.leo.messenger.MagicMessenger;

/**
 * @author duguodong
 * @time 2019/5/24
 * @des ${TODO}
 */
public class JZApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //换肤sdk初始化
        SkinManager.getInstance().init(this);

        MagicMessenger.init(this);
        MagicMessenger.bindOtherAPP(this,"com.weituo.messagereceive");
    }
}
