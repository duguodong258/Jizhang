package com.weituo.messagereceive.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author duguodong
 * @time 2019/5/27
 * @des ${TODO}
 */
public class HttpUtils {

    public void requestGet(){
        try {
            URL url = new URL("");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            connection.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            connection.setReadTimeout(5 * 1000);
            // 设置为Post请求
            connection.setRequestMethod("POST");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            connection.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            connection.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            connection.connect();
            // 判断请求是否成功
            if (connection.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(connection.getInputStream());
                Log.e("GD", "Get方式请求成功，result--->" + result);
            } else {
                Log.e("GD", "Get方式请求失败");
            }
            // 关闭连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void requestPost(HashMap<String, String> paramsMap,IHttpCallback httpCallback) {
        try {
            String baseUrl = "http://manager.server.iosinstall.langxingtiyu.com/mobile/captcha/receive";
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key,  URLEncoder.encode(paramsMap.get(key),"utf-8")));
                pos++;
            }
            String params =tempParams.toString();
            // 请求的参数转换为byte数组
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Content-Type", "application/json");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.e("GD", "Post方式请求成功，result--->" + result);
                httpCallback.callback(result);
            } else {
                Log.e("GD", "Post方式请求失败: " + urlConn.getResponseMessage());
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e("GD", e.toString());
        }
    }


    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("GD", e.toString());
            return null;
        }
    }


    public interface IHttpCallback{
        void callback(String json);
    }
}
