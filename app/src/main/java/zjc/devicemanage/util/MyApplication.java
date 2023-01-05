package zjc.devicemanage.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class MyApplication extends Application {
    //共享偏好对象
    private static SharedPreferences pref;
    //共享编号编辑器对象
    private static SharedPreferences.Editor editor;
    //上下文环境变量，用于多个活动之间的共享变量
    private static Context context;
    //登录用户编号
//    private static String user_id;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

//    public static String getUser_id() {
//        return user_id;
//    }
//
//    public static void setUser_id(String user_id) {
//        MyApplication.user_id = user_id;
//        editor.putString("user_id",user_id);
//        editor.apply();
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        pref= PreferenceManager.getDefaultSharedPreferences(context);
        editor=pref.edit();
        editor.apply();
//        user_id=pref.getString("user_id","");
    }

    public static void subThreadToast(String message){
        Looper.prepare();
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        Looper.loop();
    }
}
