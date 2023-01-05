package zjc.devicemanage.service.imp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zjc.devicemanage.fragment.InformationFragment;
import zjc.devicemanage.model.InformationList;
import zjc.devicemanage.service.InformationService;
import zjc.devicemanage.util.MyApplication;
import zjc.devicemanage.util.MyHttpUtil;

public class InformationServiceImp implements InformationService {
    @Override
    public void findAllInformation() {
        // 构造 findAllInformation 的 tomcat 服务请求 URL
        String findAllInformationURL = MyHttpUtil.host+"/DeviceManage/findAllInformation";
        MyHttpUtil.sendOkhttpGetRequest(findAllInformationURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zjc","web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
                MyApplication.subThreadToast("web接口服务连接失败，请确保主机ip地址是否正确，然后打开tomcat服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseJSONtoInformationList(response.body().string());
                informationFragment.showAllInformationCallback(informationListFromJson);
            }
        });
    }

    // 新增“咨询列表”属性
    private InformationList informationListFromJson;
    // 新增 parseJSONtoInformationList 方法，用于解析 tomcat 返回的 json 字符串
    private void parseJSONtoInformationList(String responseData){
        Gson gson = new Gson();
        informationListFromJson=gson.fromJson(responseData,
                new TypeToken<InformationList>(){}.getType());
        Log.i("zjc",informationListFromJson.toString());
    }

    // 新增回调类以及注入回调类的构造函数
    private InformationFragment informationFragment;
    // 注入回调类的构造函数
    public InformationServiceImp(InformationFragment informationFragment){
        this.informationFragment=informationFragment;
    }
}















