package zjc.devicemanage.service.imp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.model.DeviceClassList;
import zjc.devicemanage.model.DeviceList;
import zjc.devicemanage.service.DeviceService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class DeviceServiceImp implements DeviceService {
    // 新增回调类以及注入回调类的构造函数
    // 添加回调类
    private HomeFragment homeFragment;
    // 注入回调类的构造函数
    public DeviceServiceImp(HomeFragment homeFragment){
        this.homeFragment=homeFragment;
    }

    @Override
    public void findAllDevice() {
        // 构造 findAllDevice 的 tomcat 服务请求 URL
//        String findAllDeviceURL = MyHttpUtil.host + "/DeviceManage/findAllDevice";
        String findAllDeviceURL = MyHttpUtil.host + "/devicemanagespringboot/findAllDevice";
        MyHttpUtil.sendOkhttpGetRequest(findAllDeviceURL, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确， 然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机 ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoDeviceList(response.body().string());
                homeFragment.showAllDeviceCallback(deviceListFromJson);
            }
        });
    }

    @Override
    public void findDeviceByDeviceClassId(int deviceClassId) {
        // 构造 findDeviceByDeviceClassId 的 tomcat 服务请求 URL
        String findDeviceByDeviceClassId = MyHttpUtil.host + "/DeviceManage/findDeviceByDeviceClassId?deviceClassId=" + new Integer(deviceClassId).toString();
        MyHttpUtil.sendOkhttpGetRequest(findDeviceByDeviceClassId, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确， 然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip 地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoDeviceList(response.body().string());
                homeFragment.showDeviceByDeviceClassIdCallback(deviceListFromJson);
            }
        });
    }

    // 新增“设备列表”属性
    private DeviceList deviceListFromJson;
    // 新增 parseJSONtoDeviceList 方法，用于解析 tomcat 返回的 json 字符串
    private void parseJSONtoDeviceList(String responseData){
        Gson gson=new Gson();
        deviceListFromJson = gson.fromJson(responseData, new TypeToken<DeviceList>(){}.getType());
        Log.i("zjc",deviceListFromJson.toString());
    }
}
