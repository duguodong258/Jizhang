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

/**
 * @author duguodong
 * @time 2019/5/31
 * @des 本地服务
 */
public class LocalService extends Service {

    private MyBinder mBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                Log.i("GD", "connected with " + iMyAidlInterface.getServiceName());
                Log.i("LocalService", "connected with " + iMyAidlInterface.getServiceName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("GD", "---------- 链接断开，重新启动 RemoteService ----------");
            Toast.makeText(LocalService.this,"链接断开，重新启动 RemoteService",Toast.LENGTH_LONG).show();
            startService(new Intent(LocalService.this,RemoteService.class));
            bindService(new Intent(LocalService.this,RemoteService.class),connection, Context.BIND_IMPORTANT);
        }
    };

    public LocalService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("GD", "---------- LocalService onStartCommand----------");
        Toast.makeText(this,"LocalService 启动",Toast.LENGTH_LONG).show();
        startService(new Intent(LocalService.this,RemoteService.class));
        bindService(new Intent(this,RemoteService.class),connection, Context.BIND_IMPORTANT);

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.i("GD", "LocalService");
//            }
//        }, 1000, 1000);
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
            return LocalService.class.getName();
        }

    }
}
