package zjc.devicemanage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zjc.devicemanage.R;
import zjc.devicemanage.adapter.DeviceAdapter;
import zjc.devicemanage.adapter.DeviceClassAdapter;
import zjc.devicemanage.adapter.EmbedDeviceAdapter;
import zjc.devicemanage.model.DeviceClassList;
import zjc.devicemanage.model.DeviceList;
import zjc.devicemanage.service.DeviceClassService;
import zjc.devicemanage.service.DeviceService;
import zjc.devicemanage.service.ShopingcartService;
import zjc.devicemanage.service.imp.DeviceClassServiceImp;
import zjc.devicemanage.service.imp.DeviceServiceImp;
import zjc.devicemanage.service.imp.ShopingcartServiceImp;

public class HomeFragment extends Fragment {
    private View fragment_homeView;
    private Banner banner;
    private RecyclerView fragment_home_recyclerView;

    private DeviceClassAdapter deviceClassAdapter;
    private DeviceAdapter deviceAdapter;
    private View embeddevice_viewholder1View;
    private RecyclerView embeddevice_viewholder1_recyclerView;
    private EmbedDeviceAdapter embedDeviceAdapter;


    public HomeFragment() {

    }

    private  void initBanner(){
        List images = new ArrayList();
        images.add(R.drawable.bannerimg1);
        images.add(R.drawable.bannerimg2);
        images.add(R.drawable.bannerimg3);
        images.add(R.drawable.bannerimg4);
        images.add(R.drawable.bannerimg5);
        // 在fragment_homeView中利用findViewById函数获得Banner对象
        banner = fragment_homeView.findViewById(R.id.home_banner);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // 利用Glide加载图片
                Glide.with(getContext()).load(path).into(imageView);
            }
        });
        banner.setImages(images);
        banner.start();
    }

    private DeviceList deviceList = new DeviceList();
    private DeviceClassList deviceClassList = new DeviceClassList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 步骤 1：
        // 读取布局资源文件 embeddevice_viewholder1.xml，并生成视图界面对象
        embeddevice_viewholder1View = inflater.inflate(R.layout.embeddevice_viewholder1,container
                ,false);
        // 生成设备列表适配器的第一种单元项视图界面
        embeddevice_viewholder1_recyclerView=
                embeddevice_viewholder1View.findViewById(R.id.embeddevice_viewholder1_deviceclass_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        embeddevice_viewholder1_recyclerView.setLayoutManager(gridLayoutManager);
        // 生成设备分类列表适配器 DeviceClassAdapter
        deviceClassAdapter=new DeviceClassAdapter(deviceClassList);
        // 步骤 2：
        // 将设备分类列表视图 RecyclerView 控件绑定适配器
        embeddevice_viewholder1_recyclerView.setAdapter(deviceClassAdapter);
        // 步骤 3：
        // 读取布局资源文件 fragment_home.xml，并生成视图界面对象
        fragment_homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initBanner();
        // 生成设备列表视图 recyclerView
        fragment_home_recyclerView = fragment_homeView.findViewById(R.id.fragment_home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_home_recyclerView.setLayoutManager(linearLayoutManager);
        fragment_home_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // 生成设备列表适配器 EmbedDeviceAdapter
        embedDeviceAdapter = new EmbedDeviceAdapter(deviceList);
        // 必须设置 embedDeviceAdapter 适配器的第一种单元项视图，否则将报错
        embedDeviceAdapter.setRecyclerViewDeviceClass(embeddevice_viewholder1View);
        // 步骤 4：
        // 将设备列表视图控件 RecyclerView 绑定适配器
         fragment_home_recyclerView.setAdapter(embedDeviceAdapter);
        // 步骤 5：
        // 调用 initDeviceClassData，实现设备分类列表视图的数据显示
        initDeviceClassData();
        return fragment_homeView;
        // 生成设备列表视图 recyclerView
//        fragment_homeView = inflater.inflate(R.layout.fragment_home,container,false);
//        fragment_home_recyclerView = fragment_homeView.findViewById(R.id.fragment_home_recyclerView);

//        fragment_home_recyclerView.setLayoutManager(gridLayoutManager);
//        initUsers();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        fragment_home_recyclerView.setLayoutManager(linearLayoutManager);
//        fragment_home_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        initContinentsAndCountries();
//        initDeviceClassData();
//        initDeviceData();
    }

    public void initDeviceData(int deviceClassID){
        DeviceService deviceService = new DeviceServiceImp(this);
        deviceService.findDeviceByDeviceClassId(deviceClassID);
    }

    public void initDeviceClassData(){
        DeviceClassService deviceClassService = new DeviceClassServiceImp(this);
        deviceClassService.findAllDeviceClass();
    }

    public void showAllDeviceCallback(final DeviceList deviceListFromJson){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deviceAdapter = new DeviceAdapter(deviceListFromJson);
                fragment_home_recyclerView.setAdapter(deviceAdapter);
                deviceAdapter.notifyDataSetChanged();
                deviceAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int deviceID) {
                        Toast.makeText(fragment_home_recyclerView.getContext(),"设备编号"+deviceID+
                                "被点击",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    // showAllDeviceClassCallback回调函数，用于显示所有的设备分类
    public void showAllDeviceClassCallback(final DeviceClassList deviceClassListFromJson){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deviceClassAdapter.setDeviceClassList(deviceClassListFromJson);
//                fragment_home_recyclerView.setAdapter(deviceClassAdapter);
                deviceClassAdapter.notifyDataSetChanged();
                deviceClassAdapter.setOnItemClickListener(new DeviceClassAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int deviceClassID) {
                        Toast.makeText(fragment_home_recyclerView.getContext(), "设备分类编号" +deviceClassID + "被点击",Toast.LENGTH_LONG).show();
                        initDeviceData(deviceClassID);
                    }
                });
            }
        }
        );
    }

    // showDeviceByDeviceClassIdCallback回调函数，显示指定设备分类编号的所有设备对象
    public void showDeviceByDeviceClassIdCallback(final DeviceList deviceListFromJson){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                embedDeviceAdapter.setDeviceList(deviceListFromJson);
                embedDeviceAdapter.notifyDataSetChanged();
                embedDeviceAdapter.setOnItemClickListener(new EmbedDeviceAdapter.OnItemClickListener() {
                    public void onItemClick(int deviceID) {
                        Toast.makeText(getContext(), "设备编号" + deviceID + "被点击", Toast.LENGTH_LONG).show();
                    }
                });
                embedDeviceAdapter.setOnAddShopingcartClickListener(new EmbedDeviceAdapter.OnAddShopingcartClickListener() {
                    public void onAddShopingcartClick(int deviceID) {
                        ShopingcartService shopingcartService = new ShopingcartServiceImp(HomeFragment.this);
//                        String userId = MyApplication.getUser_id();
//                        shopingcartService.addShopingcart(new Integer(deviceID).toString(), "1", userId);
                    }
                });
            }
        });
    }

    public void showAddShopingcartCallback(final String addDeviceID){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "设备编号"+ addDeviceID + "加入购物车成功！",Toast.LENGTH_LONG).show();
            }
        });
    }
}
