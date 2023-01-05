package zjc.devicemanage.service.imp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.fragment.ShopingcartFragment;
import zjc.devicemanage.model.ShopingcartList;
import zjc.devicemanage.service.ShopingcartService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class ShopingcartServiceImp implements ShopingcartService {
    private HomeFragment homeFragment;
    public ShopingcartServiceImp(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    @Override
    public void findAllShopingcart() {
        // 构造 findAllShopingcart 的 tomcat 服务请求 URL
        String findAllShopingcartURL= MyHttpUtil.host+"/DeviceManage/findAllShopingcart";
        MyHttpUtil.sendOkhttpGetRequest(findAllShopingcartURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoInformationList(response.body().string());
                shopingcartFragment.showAllShopingcartCallback(shopingcartListFromJson);
            }
        });
    }

    @Override
    public void findAllShopingcartByUserId(String userId) {
        // 构造 findAllShopingcartByUserId 的 tomcat 服务请求 URL
        String findAllShopingcartByUserId= MyHttpUtil.host + "/DeviceManage/findAllShopingcartByUserId?userId="+userId;
        MyHttpUtil.sendOkhttpGetRequest(findAllShopingcartByUserId, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoInformationList(response.body().string());
                shopingcartFragment.showAllShopingcartCallback(
                        shopingcartListFromJson);
            }
        });

    }

    @Override
    public void addShopingcart(final String addDeviceID, String addBuyNum, String addUserID) {
        // 构造addShopingcart的tomcat服务请求URL
        String addShopingcart = MyHttpUtil.host + "/DeviceManage/addShopingcart?addDeviceID=" + addDeviceID+ "&addBuyNum=" + addBuyNum
                + "&addUserID=" + addUserID;
        MyHttpUtil.sendOkhttpGetRequest(addShopingcart, new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }
            public void onResponse(Call call, Response response) throws IOException {
                homeFragment.showAddShopingcartCallback(addDeviceID);
            }
        });
    }

    // 新增“购物车列表”属性
    private ShopingcartList shopingcartListFromJson;
    // 新增 parseJSONtoInformationList 方法，用于解析 tomcat 返回的 json 字符串
    private void parseJSONtoInformationList(String responseData){
        Gson gson = new Gson();
        shopingcartListFromJson=gson.fromJson(responseData,
                new TypeToken<ShopingcartList>(){}.getType());
        Log.i("zjc",shopingcartListFromJson.toString());
    }

    // 新增回调类以及注入回调类的构造函数
    private ShopingcartFragment shopingcartFragment;
    // 注入回调类的构造函数
    public ShopingcartServiceImp(ShopingcartFragment shopingcartFragment){
        this.shopingcartFragment=shopingcartFragment;
    }
}
