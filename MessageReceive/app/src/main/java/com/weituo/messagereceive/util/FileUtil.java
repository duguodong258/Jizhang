package com.weituo.messagereceive.util;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author duguodong
 * @time 2019/5/31
 * @des ${TODO}
 */
public class FileUtil {



    /**
     * 写入内容到本地
     * @param content 内容
     */
    public static void saveLocal(String content){
        try {
            File fireDir = new File(Environment.getExternalStorageDirectory(), "短信上报");
            if(!fireDir.exists()){
                fireDir.mkdir();
            }

            File file = new File(fireDir, "log.txt");
            content = content + "\n";
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
