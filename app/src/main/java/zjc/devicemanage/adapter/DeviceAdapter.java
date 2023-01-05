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

public class DeviceAdapter extends RecyclerView.Adapter {
    private DeviceList deviceList = new DeviceList();
    public DeviceAdapter(DeviceList deviceList) {
        this.deviceList = deviceList;
    }
    public void setDeviceList(DeviceList deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 利用布局渲染器 LayoutInflater，生成布局资源文件 device_viewholder.xml 根视图
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.device_viewholder,
                        parent,false);
        // 生成 device_viewholder 根视图对应的 DeviceViewHolder 变量 deviceViewHolder
        DeviceViewHolder deviceViewHolder = new DeviceViewHolder(itemView);
        return deviceViewHolder;
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout device_viewholder_ll;
        private ImageView device_viewholder_device_iv;
        private TextView device_viewholder_devicename_tv;
        private TextView device_viewholder_deviceprice_tv;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            device_viewholder_ll = itemView.findViewById(R.id.device_viewholder_ll);
            device_viewholder_device_iv = itemView.findViewById(
                    R.id.device_viewholder_device_iv);
            device_viewholder_devicename_tv = itemView.findViewById(
                    R.id.device_viewholder_devicename_tv);
            device_viewholder_deviceprice_tv = itemView.findViewById(
                    R.id.device_viewholder_deviceprice_tv);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 强制类型转换成 ViewHolder 子类 DeviceViewHolder
        DeviceViewHolder deviceViewHolder = (DeviceViewHolder) holder;
        String deviceName = deviceList.getResult().get(position).getDeviceName();
        deviceViewHolder.device_viewholder_devicename_tv.setText(deviceName);
        String devicePrice = deviceList.getResult().get(position).getDevicePrice();
        deviceViewHolder.device_viewholder_deviceprice_tv.setText(devicePrice);
        switch (deviceName) {
            case "打印机":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(
                        R.drawable.printer);
                break;
            case "耳机":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(
                        R.drawable.earphone);
                break;
            case "鼠标":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(
                        R.drawable.mouse);
                break;
            case "笔记本电脑":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(
                        R.drawable.computer);
                break;
            case "U盘":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(
                        R.drawable.udisk);
                break;
            case "头盔":
                deviceViewHolder.device_viewholder_device_iv.setImageResource(
                        R.drawable.helmet);
                break;
        }
        if (onItemClickListener != null) {
            final int deviceID = new Integer(deviceList.getResult().get(position).getDeviceID()).intValue();
            // deviceViewHolder.device_viewholder_ll是LinearLayout布局控件
            // LinearLayout布局控件能够执行setOnClickListener函数，添加点击事件处理
            // onClick(int deviceID)中的deviceID参数是当前列表项item的设备编号
            deviceViewHolder.device_viewholder_ll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onItemClickListener.onItemClick(deviceID);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return deviceList.getResult().size();
    }

    public interface OnItemClickListener{
        void onItemClick(int deviceID);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
