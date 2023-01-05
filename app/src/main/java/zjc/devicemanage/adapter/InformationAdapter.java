package zjc.devicemanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import zjc.devicemanage.R;
import zjc.devicemanage.model.InformationList;

public class InformationAdapter extends RecyclerView.Adapter {
    private Context mContext;
    //带数据源的构造函数
    private InformationList informationList;
    public InformationAdapter(InformationList informationList) {
        this.informationList = informationList;
    }
    public void setInformationList(InformationList informationList) {
        this.informationList = informationList;
    }

    public class InformationViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout information_viewholder_ll;
        private ImageView information_viewholder_information_iv;
        private TextView information_viewholder_infocontent_tv;
        private TextView information_viewholder_infocreatedtime_tv;
        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            information_viewholder_ll = itemView.findViewById(R.id.information_viewholder_ll);
            information_viewholder_information_iv = itemView.findViewById(
                    R.id.information_viewholder_information_iv);
            information_viewholder_infocontent_tv = itemView.findViewById(
                    R.id.information_viewholder_infocontent_tv);
            information_viewholder_infocreatedtime_tv = itemView.findViewById(
                    R.id.information_viewholder_infocreatedtime_tv);
        }
    }
    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 保留 Glide 工具显示图片时所需要的上下文值 context
        mContext = parent.getContext();
        // 利用布局渲染器 LayoutInflater，生成布局资源文件 information_viewholder.xml 根视图
        View itemView =  LayoutInflater.from(mContext).inflate(R.layout.information_viewholder,parent,false);
        // 生成 information_viewholder 根视图对应的 InformationViewHolder 变量
        InformationViewHolder informationViewHolder = new InformationViewHolder(itemView);
        return informationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 强制类型转换成ViewHolder子类InformationViewHolder
        InformationViewHolder informationViewHolder  = (InformationViewHolder)holder;
        String informationImage = informationList.getResult().get(position).
                getInformationImage();
        // 利用Glide工具从服务器端装载图片
        Glide.with(mContext).load(informationImage).into(
                informationViewHolder.information_viewholder_information_iv);
        String informationContent = informationList.getResult().get(position).
                getInformationContent();
        informationViewHolder.information_viewholder_infocontent_tv.
                setText(informationContent);
        String informationCreateTime = informationList.getResult().get(position).
                getInformationCreateTime();
        informationViewHolder.information_viewholder_infocreatedtime_tv.
                setText(informationCreateTime);
        if(onItemClickListener != null){
            final int informationID = new Integer(informationList.getResult().get(position).getInformationID()).intValue();
// informationViewHolder.information_viewholder_ll是LinearLayout布局控件
// LinearLayout布局控件能够执行setOnClickListener函数，添加点击事件的处理
// onClick(int informationID)中的informationID参数是当前列表项item的咨询编号
            informationViewHolder.information_viewholder_ll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onItemClickListener.onItemClick(informationID);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return informationList.getResult().size();
    }
    public interface OnItemClickListener{
        void onItemClick(int informationID);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
