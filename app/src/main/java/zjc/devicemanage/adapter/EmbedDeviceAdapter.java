package zjc.devicemanage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import zjc.devicemanage.R;
import zjc.devicemanage.model.DeviceList;

public class EmbedDeviceAdapter extends RecyclerView.Adapter {
    private DeviceList deviceList;
    public EmbedDeviceAdapter(DeviceList deviceList) {
        this.deviceList = deviceList;
    }
    public void setDeviceList(DeviceList deviceList) {
        this.deviceList = deviceList;
    }

    public interface OnAddShopingcartClickListener {
        void onAddShopingcartClick(int deviceID);
    }
    private OnAddShopingcartClickListener onAddShopingcartClickListener;
    public void setOnAddShopingcartClickListener(OnAddShopingcartClickListener
                                                         onAddShopingcartClickListener) {
        this.onAddShopingcartClickListener = onAddShopingcartClickListener;
    }

    private View recyclerViewDeviceClass;
    public void setRecyclerViewDeviceClass(View recyclerViewDeviceClass) {
        this.recyclerViewDeviceClass = recyclerViewDeviceClass;
    }
    // 第一种单元项的视图 View(embeddevice_viewholder1.xml)，不需要定义子视图控件
    public class EmbedDeviceViewHolder1 extends RecyclerView.ViewHolder{
        public EmbedDeviceViewHolder1(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class EmbedDeviceViewHolder2 extends RecyclerView.ViewHolder{
        private LinearLayout embeddevice_viewholder2_ll;
        private ImageView embeddevice_viewholder2_device_iv;
        private TextView embeddevice_viewholder2_devicename_tv;
        private TextView embeddevice_viewholder2_deviceprice_tv;
        private ImageView embeddevice_viewholder2_addtoshopingcart;
        public EmbedDeviceViewHolder2(@NonNull View itemView) {
            super(itemView);
            embeddevice_viewholder2_ll = itemView.findViewById(R.id.embeddevice_viewholder2_ll);
            embeddevice_viewholder2_device_iv = itemView.findViewById(R.id.embeddevice_viewholder2_device_iv);
            embeddevice_viewholder2_devicename_tv = itemView.findViewById(R.id.embeddevice_viewholder2_devicename_tv);
            embeddevice_viewholder2_deviceprice_tv = itemView.findViewById(R.id.embeddevice_viewholder2_deviceprice_tv);
            embeddevice_viewholder2_addtoshopingcart =
                    itemView.findViewById(R.id.embeddevice_viewholder2_addtoshopingcart);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        // view是单元项所显示的界面视图
        View view = null;
        switch (viewType){
        // 第一种单元项，该种单元项就是 embeddevice_viewholder1.xml 界面
            case ItemType1:
                 view = recyclerViewDeviceClass;
                 viewHolder = new EmbedDeviceViewHolder1(view);
                 break;
        // 第二种单元项，该种单元项就是 embeddevice_viewholder2.xml 界面
            case ItemType2:
        // parent 是 RecyclerView 控件对象
        // 利用布局渲染器 LayoutInflater，读取布局资源文件 embeddevice_view holder2.xml，生成对应的视图 view
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.embeddevice_viewholder2,parent,false);
                viewHolder=new EmbedDeviceViewHolder2(view);
                break;
         }
           return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 如果是第一个单元项，则不需要操作 ViewHolder 中的控件显示内容
        if (position == 0) {
            return;
        }else {
            // 其他单元项，则需要操作 ViewHolder 中的控件显示内容
            EmbedDeviceViewHolder2 embedDeviceViewHolder2 = (EmbedDeviceViewHolder2)holder;
            String deviceName = deviceList.getResult().get(position - 1).getDeviceName();
            embedDeviceViewHolder2.embeddevice_viewholder2_devicename_tv.setText(deviceName);
            String devicePrice = deviceList.getResult().get(position - 1).getDevicePrice();
            embedDeviceViewHolder2.embeddevice_viewholder2_deviceprice_tv.setText(devicePrice);
            switch (deviceName){
                case "打印机":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.printer);
                    break;
                case "耳机":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.earphone);
                    break;
                case "鼠标":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.mouse);
                    break;
                case "笔记本电脑":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.computer);
                    break;
                case "U盘":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.udisk);
                    break;
                case "头盔":
                    embedDeviceViewHolder2.embeddevice_viewholder2_device_iv.setImageResource(R.drawable.helmet);
                    break;
            }
            if (onItemClickListener != null) {
                final int deviceID = new Integer(deviceList.getResult().get(position-1).getDeviceID()).intValue();
                embedDeviceViewHolder2.embeddevice_viewholder2_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(deviceID);
                    }
                });
            }
            // 加入购物车图片控件的点击处理
            if (onAddShopingcartClickListener != null) {
                final int deviceID = new Integer(deviceList.getResult().get(position-1).getDeviceID()).intValue();
                embedDeviceViewHolder2.embeddevice_viewholder2_addtoshopingcart.
                        setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                onAddShopingcartClickListener.onAddShopingcartClick(deviceID);
                            }
                        });
            }
        }
    }



    public interface OnItemClickListener{
        void onItemClick(int deviceID);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return 1+deviceList.getResult().size();
    }

    private final int ItemType1 = 1;
    private final int ItemType2 = 2;
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return ItemType1;
        }else {
            return ItemType2;
        }
    }
}
