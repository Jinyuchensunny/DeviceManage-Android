package zjc.devicemanage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import zjc.devicemanage.R;
import zjc.devicemanage.model.Device;
import zjc.devicemanage.model.ShopingcartList;

public class ShopingcartAdapter extends RecyclerView.Adapter {
    // 新增数据源属性（购物车列表）
    private ShopingcartList shopingcartList;
    public void setShopingcartList(ShopingcartList shopingcartList) {
        this.shopingcartList = shopingcartList;
    }

    // 新增购物车列表项是否选中的哈希变量
    private HashMap<Integer, Boolean> choiceMap;
    public void setChoiceMap(HashMap<Integer, Boolean> choiceMap) {
        this.choiceMap = choiceMap;
    }

    public ShopingcartAdapter(ShopingcartList shopingcartList, HashMap<Integer, Boolean> choiceMap) {
        this.shopingcartList = shopingcartList;
        this.choiceMap = choiceMap;
    }

    // 新增用于访问适配器对应的 ViewHolder 对象
    private ShopingcartViewHolder shopingcartViewHolder;
    public ShopingcartViewHolder getShopingcartViewHolder() {
        return shopingcartViewHolder;
    }
    public void setShopingcartViewHolder(ShopingcartViewHolder shopingcartViewHolder) {
        this.shopingcartViewHolder = shopingcartViewHolder;
    }

    public interface OnItemChoiceListener{
        void onItemChoice(int position);
    }
    private OnItemChoiceListener onItemChoiceListener;
    public void setOnItemChoiceListener(OnItemChoiceListener onItemChoiceListener) {
        this.onItemChoiceListener = onItemChoiceListener;
    }

    public interface OnItemAddListener{
        void onItemAdd(int position);
    }
    private OnItemAddListener onItemAddListener;
    public void setOnItemAddListener(OnItemAddListener onItemAddListener) {
        this.onItemAddListener = onItemAddListener;
    }

    public interface OnItemSubtractionListener{
        void onItemSubtraction(int position);
    }
    private OnItemSubtractionListener onItemSubtractionListener;
    public void setOnItemSubtractionListener(OnItemSubtractionListener onItemSubtractionListener) {
        this.onItemSubtractionListener = onItemSubtractionListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 利用布局渲染器 LayoutInflater，生成布局资源文件 shopingcart_viewholder.xml 根视图
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.shopingcart_viewholder,
                        parent,false);
        // 生成 shopingcart_viewholder 根视图对应的 ShopingcartViewHolder 变量
        shopingcartViewHolder = new ShopingcartViewHolder(itemView);
        return shopingcartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // 强制类型转换成 ViewHolder 子类 ShopingcartViewHolder
        ShopingcartViewHolder shopingcartViewHolder = (ShopingcartViewHolder)holder;
        Device device = shopingcartList.getResult().get(position).getDevice();
        String buyNum = shopingcartList.getResult().get(position).getBuyNum();
        // 读取 device 对象的属性
        String deviceName = device.getDeviceName();
        String devicePrice = device.getDevicePrice();
        // 将 3 个属性值显示到 3 个 TextView 控件上
        shopingcartViewHolder.shopingcart_devicename_tv.setText(deviceName);
        shopingcartViewHolder.shopingcart_buynum_tv.setText(buyNum);
        shopingcartViewHolder.shopingcart_deviceprice_tv.setText(devicePrice);
        // 根据 choiceMap，确定图片控件 shopingcart_choice_iv 显示图片是 choice.jpg 还是 choiced.jpg
        if(choiceMap.get(position)){
            shopingcartViewHolder.shopingcart_choice_iv.setImageResource(R.drawable.choiced);
        }else {
            shopingcartViewHolder.shopingcart_choice_iv.setImageResource(R.drawable.choice);
        }
        // 根据设备的名称，确定图片控件 shopingcart_deviceimage_iv 显示的图片
        switch (deviceName){
            case "打印机":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource( R.drawable.printer);
                break;
            case "耳机":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource( R.drawable.earphone);
                break;
            case "鼠标":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource( R.drawable.mouse);
                break;
            case "笔记本电脑":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource( R.drawable.computer);
                break;
            case "U盘":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource( R.drawable.udisk);
                break;
            case "头盔":
                shopingcartViewHolder.shopingcart_deviceimage_iv.setImageResource( R.drawable.helmet);
                break;
        }
        // 处理选中图片按钮的点击事件
        if(onItemChoiceListener != null) {
            shopingcartViewHolder.shopingcart_choice_iv.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            onItemChoiceListener.onItemChoice(position);
                        }
                    });
        }
        // 处理增加图片的点击事件
        if(onItemAddListener != null) {
            shopingcartViewHolder.shopingcart_add_iv.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            onItemAddListener.onItemAdd(position);
                        }
                    });
        }
        // 处理减少图片的点击事件
        if(onItemSubtractionListener != null) {
            shopingcartViewHolder.shopingcart_subtraction_iv.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            onItemSubtractionListener.onItemSubtraction(position);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return shopingcartList.getResult().size();
    }

    public class ShopingcartViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout shopingcart_viewholder_ll;
        public LinearLayout shopingcart_choice_ll;
        public ImageView shopingcart_choice_iv;
        public ImageView shopingcart_deviceimage_iv;
        public TextView shopingcart_devicename_tv;
        public TextView shopingcart_deviceprice_tv;
        public ImageView shopingcart_subtraction_iv;
        public TextView shopingcart_buynum_tv;
        public ImageView shopingcart_add_iv;

        public ShopingcartViewHolder(@NonNull View itemView) {
            super(itemView);
            shopingcart_viewholder_ll = itemView.findViewById(R.id.shopingcart_viewholder_ll);
            shopingcart_choice_ll = itemView.findViewById(R.id.shopingcart_choice_ll);
            shopingcart_choice_iv = itemView.findViewById(R.id.shopingcart_choice_iv);
            shopingcart_deviceimage_iv = itemView.findViewById(
                    R.id.shopingcart_deviceimage_iv);
            shopingcart_devicename_tv = itemView.findViewById(R.id.shopingcart_devicename_tv);
            shopingcart_deviceprice_tv = itemView.findViewById(R.id.shopingcart_deviceprice_tv);
            shopingcart_subtraction_iv = itemView.findViewById(R.id.shopingcart_subtraction_iv);
            shopingcart_buynum_tv = itemView.findViewById(R.id.shopingcart_buynum_tv);
            shopingcart_add_iv = itemView.findViewById(R.id.shopingcart_add_iv);
        }
    }
}
