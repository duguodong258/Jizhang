package com.weituo.messagereceive.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.weituo.messagereceive.IMyAidlInterface;
import com.weituo.messagereceive.util.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author duguodong
 * @time 2019/5/31
 * @des 远程服务
 */
public class RemoteService extends Service {
    private MyBinder mBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                Log.i("RemoteService", "connected with " + iMyAidlInterface.getServiceName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("GD", "---------- 链接断开，重新启动 LocalService ----------");

            Date date = new Date();
            date.setTime(System.currentTimeMillis());
            String format = new SimpleDateFormat("MM-dd HH:mm:ss").format(date);
            FileUtil.saveLocal("短信App崩溃  时间: " + format);

            Toast.makeText(RemoteService.this,"链接断开，重新启动 LocalService",Toast.LENGTH_LONG).show();
            startService(new Intent(RemoteService.this,LocalService.class));
            bindService(new Intent(RemoteService.this,LocalService.class),connection, Context.BIND_IMPORTANT);


            Intent intent = getPackageManager().getLaunchIntentForPackage("com.weituo.messagereceive");
            if(intent!=null){
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        }
    };

    public RemoteService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"RemoteService 启动",Toast.LENGTH_LONG).show();
        bindService(new Intent(this,LocalService.class),connection,Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new MyBinder();
        return mBinder;
    }

    private class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public String getServiceName() throws RemoteException {
            return RemoteService.class.getName();
        }
    }
}
