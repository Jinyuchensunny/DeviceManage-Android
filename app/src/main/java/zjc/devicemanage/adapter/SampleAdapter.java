package zjc.devicemanage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import zjc.devicemanage.R;

public class SampleAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View itemView=null;
        switch (viewType){
            case ItemType1:
                itemView=
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_viewholder1,parent,false);
                viewHolder=new SampleViewHolder1(itemView);
                break;
            case ItemType2:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_viewholder2,parent,false);
                viewHolder = new SampleViewHolder2(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // position的取值依次为[0,1,...,14]，总数为 15 项
        // 根据当前项编号 position，得到该项在洲数组 continents 中的序号
        final int continentNum = position / 5;
        // 根据当前项编号 position，得到该项在国家数组 countries 中的序号
        final int countryNum = position - continentNum -1;

        // 第一种视图类型的单元项
        if (position%5==0){
            // 强制类型转换成 ViewHolder 子类 SampleViewHolder1
            SampleViewHolder1 sampleViewHolder1 = (SampleViewHolder1)holder;
            sampleViewHolder1.sample_viewholder1_continent_tv.setText(continents.get(continentNum));
            // 给 sample_viewholder1_ll 布局控件添加事件监听处理
            if (onItemClickListener!=null){
                sampleViewHolder1.sample_viewholder1_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position,continentNum);
                    }
                });
            }
        }else {
            //  第二种视图类型的单元项
            SampleViewHolder2 sampleViewHolder2 = (SampleViewHolder2)holder;
            sampleViewHolder2.sample_viewholder2_country_tv.setText(countries.get(countryNum));
            sampleViewHolder2.sample_viewholder2_capital_tv.setText(capitals.get(countryNum));
            switch (countryNum){
                case 0:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.china);
                    break;
                case 1:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.yindu);
                    break;
                case 2:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.hanguo);
                    break;
                case 3:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.riben);
                    break;
                case 4:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.yingguo);
                    break;
                case 5:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.faguo);
                    break;
                case 6:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.deguo);
                    break;
                case 7:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.xibanya);
                    break;
                case 8:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.meiguo);
                    break;
                case 9:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.jianada);
                    break;
                case 10:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.guba);
                    break;
                case 11:
                    sampleViewHolder2.sample_viewholder2_country_iv.setImageResource(R.drawable.moxige);
                    break;
            }
            // 给 sample_viewholder1_ll 布局控件添加事件监听处理
            if (onItemClickListener!=null){
                sampleViewHolder2.sample_viewholder2_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position, continentNum, countryNum);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%5==0){
            return ItemType1;
        }else {
            return ItemType2;
        }
    }

    @Override
    public int getItemCount() {
        return continents.size()+countries.size();
    }

    //9.10
    public class SampleViewHolder1 extends RecyclerView.ViewHolder{
        private LinearLayout sample_viewholder1_ll;
        private TextView sample_viewholder1_continent_tv;
        public SampleViewHolder1(@NonNull View itemView) {
            super(itemView);
            sample_viewholder1_ll=itemView.findViewById(R.id.sample_viewholder_ll);
            sample_viewholder1_continent_tv=
                    itemView.findViewById(R.id.sample_viewholder1_continent_tv);
        }
    }

    public class SampleViewHolder2 extends RecyclerView.ViewHolder{
        private LinearLayout sample_viewholder2_ll;
        private ImageView sample_viewholder2_country_iv;
        private TextView sample_viewholder2_country_tv;
        private TextView sample_viewholder2_capital_tv;
        public SampleViewHolder2(@NonNull View itemView) {
            super(itemView);
            sample_viewholder2_ll=itemView.findViewById(R.id.sample_viewholder2_ll);
            sample_viewholder2_country_iv=
                    itemView.findViewById(R.id.sample_viewholder2_country_iv);
            sample_viewholder2_country_tv = itemView.findViewById(R.id.sample_viewholder2_country_tv);
            sample_viewholder2_capital_tv = itemView.findViewById(R.id. sample_viewholder2_capital_tv);
        }
    }
    private List<String> continents;// 洲名列表
    private List<String> countries; // 国家列表
    private List<String> capitals; // 首都列表

    public SampleAdapter(List<String> continents, List<String> countries, List<String> capitals) {
        this.continents = continents;
        this.countries = countries;
        this.capitals = capitals;
    }

    private final int ItemType1 = 1;
    private final int ItemType2 = 2;

    // item 的回调接口，position 是列表项序号，continentNum 和 countryNum 分别是点击的列 表项 item 所属的 continents 数组和 countries 数组中的序号值
    public interface OnItemClickListener{
        // 点击单元项是国家类型时调用
        void onItemClick(int position, int continentNum, int countryNum);
        // 点击单元项是洲类型时调用
        void onItemClick(int position, int continentNum);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
