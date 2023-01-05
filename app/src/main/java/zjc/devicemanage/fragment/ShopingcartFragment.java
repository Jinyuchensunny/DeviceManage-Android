package zjc.devicemanage.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import zjc.devicemanage.R;
import zjc.devicemanage.adapter.ShopingcartAdapter;
import zjc.devicemanage.model.Shopingcart;
import zjc.devicemanage.model.ShopingcartList;
import zjc.devicemanage.service.ShopingcartService;
import zjc.devicemanage.service.imp.ShopingcartServiceImp;
import zjc.devicemanage.util.MyApplication;

public class ShopingcartFragment extends Fragment {
    // 布局 fragment_shopingcart.xml 对应的视图对象
    private View fragment_shopingcartView;
    private SwipeRefreshLayout fragment_shopingcart_SRL;
    private RecyclerView fragment_shopingcart_rv;
    // 全部总金额文本控件
    private TextView fragment_shopingcart_sum_tv;
    // 全选图片控件
    private ImageView fragment_shopingcart_choiceall_iv;
    // 购物车适配器，将链接 RecyclerView 控件和数据源 shopingcartList
    private ShopingcartAdapter shopingcartAdapter;
    // 数据源，该属性必须 new 分配内存，否则后面引用到该属性时将报错
    private ShopingcartList shopingcartList = new ShopingcartList();
    // 购物车项是否被选中 Map，该属性必须 new 分配内存
    private HashMap<Integer, Boolean> choiceMap = new HashMap<Integer, Boolean>();
    // 当前用户编号，当前用户的购物车
    private String userId;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    // 是否全选所有的购物车项
     private Boolean isChoiceAll = false;

     public void initShopingcartData(){
         ShopingcartService shopingcartService = new ShopingcartServiceImp(this);
         shopingcartService.findAllShopingcart();
     }

    public ShopingcartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 步骤 1：读取布局资源文件 fragment_shopingcart.xml，并生成视图界面对象
        fragment_shopingcartView = inflater.inflate(R.layout.fragment_shopingcart, container, false);
        //步骤 2：获取滑动刷新控件、列表控件
        fragment_shopingcart_SRL = fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_SRL);
        fragment_shopingcart_rv =
                fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_rv);
        // 步骤 3：获取总金额文本控件、全选图片控件
        fragment_shopingcart_sum_tv =
                fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_sum_tv);
        fragment_shopingcart_choiceall_iv =
                fragment_shopingcartView.findViewById(R.id.fragment_shopingcart_choiceall_iv);
        //步骤 4：创建线性布局，设置列表控件对象的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_shopingcart_rv.setLayoutManager(linearLayoutManager);
        fragment_shopingcart_rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        // 步骤 5：新建购物车适配器对象，连接数据源和选项 Map
        shopingcartAdapter = new ShopingcartAdapter(shopingcartList,choiceMap);
        //步骤 6：列表控件对象绑定适配器对象
        fragment_shopingcart_rv.setAdapter(shopingcartAdapter);
        //步骤 7：执行 initShopingcartData 方法，获取远程服务器数据，并回调显示
//        userId = MyApplication.getUser_id();
        /*只有第一次创建 Fragment，需要从远程服务器获取数据。 如果后面从其他 Fragment 切换回该 Fragment 时，则不能从远程服务器获取数据， 否则用户在该 Fragment 上的操作都被还原了*/
        if (shopingcartList.getResult().size()==0){
            initShopingcartData(userId);
        }else {
            updateMoneySum();
        }
        initShopingcartData(userId);
        //步骤8：滑动刷新控件的刷新事件处理
        fragment_shopingcart_SRL.setOnRefreshListener(new SwipeRefreshLayout.
                OnRefreshListener() {
            public void onRefresh() {
                shopingcartList.getResult().clear();
                if(fragment_shopingcart_rv.getScrollState()== RecyclerView.
                        SCROLL_STATE_IDLE || !fragment_shopingcart_rv.isComputingLayout()){
                    shopingcartAdapter.notifyDataSetChanged();
                }
                // 重新获取当前用户的购物车列表项
                initShopingcartData(userId);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            // 延时1秒
                            Thread.sleep(1000);
                            handler.post(new Runnable() {
                                public void run() {
                                    // 刷新Recycler列表控件
                                    shopingcartAdapter.notifyDataSetChanged();
                                    // 下拉刷新控件暂停刷新
                                    fragment_shopingcart_SRL.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        //步骤9：全选图片按钮点击事件处理
        fragment_shopingcart_choiceall_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isChoiceAll == false){
                    isChoiceAll = true;
                    updateBatchChoiceMap(true);
                }else{
                    isChoiceAll = false;
                    updateBatchChoiceMap(false);
                }
            }
        });
        //步骤10：新增“选中”按钮的匿名接口实现类，并重载接口事件方法
        shopingcartAdapter.setOnItemChoiceListener(new ShopingcartAdapter.
                OnItemChoiceListener() {
            public void onItemChoice(int position) {
                boolean isChoiced = choiceMap.get(position);
                ShopingcartAdapter.ShopingcartViewHolder scHolder = shopingcartAdapter.
                        getShopingcartViewHolder();
                if (isChoiced){
                    choiceMap.put(position, false);
                    scHolder.shopingcart_choice_iv.setImageResource(R.drawable.choice);
                    updateMoneySum();
                    shopingcartAdapter.notifyDataSetChanged();
                }else {
                    choiceMap.put(position, true);
                    scHolder.shopingcart_choice_iv.setImageResource(R.drawable.choice);
                    updateMoneySum();
                    shopingcartAdapter.notifyDataSetChanged();
                }
            }
        });
        //步骤11：新增“增加”按钮的匿名接口实现类，并重载接口事件方法
        shopingcartAdapter.setOnItemAddListener(new ShopingcartAdapter.OnItemAddListener() {
            public void onItemAdd(int position) {
                Boolean isEnabled = choiceMap.get(position);
                // 如果未选中当前购物车项，则返回，不能操作增加按钮
                if(isEnabled == false) return;
                Shopingcart sc = shopingcartList.getResult().get(position);
                int curBuynum = new Integer(sc.getBuyNum());
                curBuynum ++;
                sc.setBuyNum(new Integer(curBuynum).toString());
                // 修改界面上的2个控件的文本值
                ShopingcartAdapter.ShopingcartViewHolder scHolder =  shopingcartAdapter.
                        getShopingcartViewHolder();
                scHolder.shopingcart_buynum_tv.setText(new Integer(curBuynum).toString());
                updateMoneySum();
                // 强制刷新界面
                shopingcartAdapter.notifyDataSetChanged();
            }
        });
        //步骤12：新增“减少”按钮的匿名接口实现类，并重载接口事件方法
        shopingcartAdapter.setOnItemSubtractionListener(new ShopingcartAdapter.
                OnItemSubtractionListener() {
            public void onItemSubtraction(int position) {
                Boolean isEnabled = choiceMap.get(position);
                // 如果未选中当前购物车项，则返回，不能操作减少按钮
                if(isEnabled == false) return;
                Shopingcart sc = shopingcartList.getResult().get(position);
                int curBuynum = new Integer(sc.getBuyNum());
                curBuynum --;
                // 如果购买数量小于0，则返回，不能继续操作减少按钮
                if(curBuynum <0) return;
                sc.setBuyNum(new Integer(curBuynum).toString());
                // 修改界面上的2个控件的文本值
                ShopingcartAdapter.ShopingcartViewHolder scHolder =  shopingcartAdapter.
                        getShopingcartViewHolder();
                scHolder.shopingcart_buynum_tv.setText(new Integer(curBuynum).toString());
                updateMoneySum();
                // 强制刷新界面
                shopingcartAdapter.notifyDataSetChanged();
            }
        });
        return fragment_shopingcartView;
    }

    public void updateMoneySum(){
         int moneySum = 0;
         List<Shopingcart> shopingcarts = shopingcartList.getResult();
         for (int i=0; i<shopingcarts.size(); i++){
             // 如果当前购物车项被选中
             Boolean isChoiced = choiceMap.get(i);
             if (isChoiced){
                 Shopingcart sc = shopingcarts.get(i);
                 int buyNum = new Integer(sc.getBuyNum());
                 int price = new Integer(sc.getDevice().getDevicePrice());
                 moneySum += new Integer(buyNum *price).intValue();
             }
        }
        fragment_shopingcart_sum_tv.setText(new Integer(moneySum).toString());
    }

    public void updateBatchChoiceMap(Boolean value){
        // 初始化购物车项是否被选中 Map，所有的购物车列表项都选中或未选中
        // 如果形参 value=true，则所有的购物车列表项都选中，否则都未选中
        Integer key = 0;
        for (Shopingcart sc: shopingcartList.getResult()){
            choiceMap.put(key,value);
            key++;
        }
        // 更改每个列表项的选择图片
        shopingcartAdapter.setChoiceMap(choiceMap);
        shopingcartAdapter.notifyDataSetChanged();
        // 更新总金额
        updateMoneySum();
    }

    // showAllShopingcartCallback回调函数，用于显示所有的购物车
    public void showAllShopingcartCallback(final ShopingcartList shopingcartListFromJson){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shopingcartList = shopingcartListFromJson;
                shopingcartAdapter.setShopingcartList(shopingcartList);
                fragment_shopingcart_rv.setAdapter(shopingcartAdapter);
                //初始化购物车项是否被选中 Map，所有的购物车列表项都未选中
                updateBatchChoiceMap(false);
                updateMoneySum();
            }
        });
    }
    public void initShopingcartData(String userId) {
        ShopingcartService shopingcartService = new ShopingcartServiceImp(this);
        shopingcartService.findAllShopingcartByUserId(userId);
    }
}
