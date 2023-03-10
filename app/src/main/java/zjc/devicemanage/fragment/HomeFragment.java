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
        // ???fragment_homeView?????????findViewById????????????Banner??????
        banner = fragment_homeView.findViewById(R.id.home_banner);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // ??????Glide????????????
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
        // ?????? 1???
        // ???????????????????????? embeddevice_viewholder1.xml??????????????????????????????
        embeddevice_viewholder1View = inflater.inflate(R.layout.embeddevice_viewholder1,container
                ,false);
        // ????????????????????????????????????????????????????????????
        embeddevice_viewholder1_recyclerView=
                embeddevice_viewholder1View.findViewById(R.id.embeddevice_viewholder1_deviceclass_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        embeddevice_viewholder1_recyclerView.setLayoutManager(gridLayoutManager);
        // ????????????????????????????????? DeviceClassAdapter
        deviceClassAdapter=new DeviceClassAdapter(deviceClassList);
        // ?????? 2???
        // ??????????????????????????? RecyclerView ?????????????????????
        embeddevice_viewholder1_recyclerView.setAdapter(deviceClassAdapter);
        // ?????? 3???
        // ???????????????????????? fragment_home.xml??????????????????????????????
        fragment_homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initBanner();
        // ???????????????????????? recyclerView
        fragment_home_recyclerView = fragment_homeView.findViewById(R.id.fragment_home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_home_recyclerView.setLayoutManager(linearLayoutManager);
        fragment_home_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // ??????????????????????????? EmbedDeviceAdapter
        embedDeviceAdapter = new EmbedDeviceAdapter(deviceList);
        // ???????????? embedDeviceAdapter ??????????????????????????????????????????????????????
        embedDeviceAdapter.setRecyclerViewDeviceClass(embeddevice_viewholder1View);
        // ?????? 4???
        // ??????????????????????????? RecyclerView ???????????????
         fragment_home_recyclerView.setAdapter(embedDeviceAdapter);
        // ?????? 5???
        // ?????? initDeviceClassData????????????????????????????????????????????????
        initDeviceClassData();
        return fragment_homeView;
        // ???????????????????????? recyclerView
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
                        Toast.makeText(fragment_home_recyclerView.getContext(),"????????????"+deviceID+
                                "?????????",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    // showAllDeviceClassCallback????????????????????????????????????????????????
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
                        Toast.makeText(fragment_home_recyclerView.getContext(), "??????????????????" +deviceClassID + "?????????",Toast.LENGTH_LONG).show();
                        initDeviceData(deviceClassID);
                    }
                });
            }
        }
        );
    }

    // showDeviceByDeviceClassIdCallback??????????????????????????????????????????????????????????????????
    public void showDeviceByDeviceClassIdCallback(final DeviceList deviceListFromJson){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                embedDeviceAdapter.setDeviceList(deviceListFromJson);
                embedDeviceAdapter.notifyDataSetChanged();
                embedDeviceAdapter.setOnItemClickListener(new EmbedDeviceAdapter.OnItemClickListener() {
                    public void onItemClick(int deviceID) {
                        Toast.makeText(getContext(), "????????????" + deviceID + "?????????", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "????????????"+ addDeviceID + "????????????????????????",Toast.LENGTH_LONG).show();
            }
        });
    }
}
