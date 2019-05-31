package com.weituo.jizhang.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duguodong
 * @time 2019/5/27
 * @des ${TODO}
 */
public class ScreenShotUtils {

    //截屏
    public static boolean shotScreen(Activity activity) {
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        //获取屏幕截图
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
            fos.close();

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            activity.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    //截长图（ListView）
    public static Bitmap shotLargeScreen(Activity activity, ListView listView){
        Bitmap bigBitmap = null;
        List<Bitmap> bmps = new ArrayList<>();
        ListAdapter adapter = listView.getAdapter();
        int height = 0;
        for(int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, listView);
//            itemView.measure(0,0);
            itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            itemView.layout(0, 0, itemView.getMeasuredWidth(), itemView.getMeasuredHeight());
            itemView.setDrawingCacheEnabled(true);
            itemView.buildDrawingCache();
            bmps.add(itemView.getDrawingCache());
            height += itemView.getMeasuredHeight();
        }
        bigBitmap = Bitmap.createBitmap(listView.getWidth(), height, Bitmap.Config.ARGB_4444);
        Canvas bigCanvas = new Canvas(bigBitmap);

        int iHeight = 0;
        Paint paint = new Paint();
        for(int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigCanvas.drawBitmap(bmp,0,iHeight,paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
            bmp = null;
        }
        return bigBitmap;
    }


    //保存到本地并更新系统图库
    public static void updatePhotos(Activity activity, Bitmap bigBitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(),"果冻记账");
        if(!appDir.exists()){
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bigBitmap.compress(Bitmap.CompressFormat.PNG,90,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(activity,"已保存到本地",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //保存图片后发送广播通知更新数据库
        Uri uri = Uri.fromFile(file);
        activity.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }
}
