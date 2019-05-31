package com.weituo.messagereceive.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weituo.messagereceive.R;
import com.weituo.messagereceive.service.LocalService;
import com.weituo.messagereceive.service.RemoteService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * @author duguodong
 * @time 2019/5/28
 * @des ${TODO}
 */
public class BActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private Button btnStartLocal;
    private Button btnStopLocal;
    private Button btnStopRemote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        tv =  findViewById(R.id.tv);

        btnStartLocal = findViewById(R.id.btn_start_local);
        btnStopLocal = findViewById(R.id.btn_stop_local);
        btnStopRemote = findViewById(R.id.btn_stop_remote);
        btnStartLocal.setOnClickListener(this);
        btnStopLocal.setOnClickListener(this);
        btnStopRemote.setOnClickListener(this);


        // 获取ActivityManager
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
            Log.i("GD", "进程process" + info.processName);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_local :
                startService(new Intent(this, LocalService.class));//开启服务 提升进程优先级
                saveLocal("傲视科技第六感和建行卡黄沙古渡林坤");
                saveLocal("静安寺看到过回拉萨的合格率卡收到货高科技啊");
                break;
            case R.id.btn_stop_local :
                stopService(new Intent(this, LocalService.class));//关闭本地服务
                break;
            case R.id.btn_stop_remote :
                stopService(new Intent(this, RemoteService.class));//关闭本地服务
                break;
        }
    }



    /**
     * 写入内容到本地
     * @param content 内容
     */
    private void saveLocal(String content){
        try {
            File fireDir = new File(Environment.getExternalStorageDirectory(), "短信上报");
            if(!fireDir.exists()){
                fireDir.mkdir();
            }

            File file = new File(fireDir, "短信上报.txt");
            if(file.exists()){ //如果已经存在
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write(content.getBytes());
                randomAccessFile.close();
            }else{
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
