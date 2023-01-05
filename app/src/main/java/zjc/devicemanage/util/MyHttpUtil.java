package zjc.devicemanage.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyHttpUtil {
    public static String host = "http://10.248.212.126:8080";

    //sendOkhttpPostRequest用来向服务器发送Post请求
    public static void sendOkhttpPostRequest(String address, RequestBody requestBody,
                                             Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    //sendOkhttpGetRequest用来向服务器发送Post请求Get请求
    public static void sendOkhttpGetRequest(String address,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
