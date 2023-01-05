package zjc.devicemanage.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zjc.devicemanage.R;
import zjc.devicemanage.activity.InformationDetailActivity;
import zjc.devicemanage.adapter.InformationAdapter;
import zjc.devicemanage.model.InformationList;
import zjc.devicemanage.service.InformationService;
import zjc.devicemanage.service.imp.InformationServiceImp;

public class InformationFragment extends Fragment {
    private View fragment_informationView;
    private RecyclerView fragment_information_rv;
    private InformationAdapter informationAdapter;
    private InformationList informationList = new InformationList();

    public void initInformationData(){
        InformationService informationService = new InformationServiceImp(this);
        informationService.findAllInformation();
    }

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //步骤 1：读取布局资源文件 fragment_information.xml，并生成视图界面对象
        fragment_informationView = inflater.inflate(R.layout.fragment_information,container,false);
        //步骤 2：获取列表控件对象 fragment_information_rv
        fragment_information_rv =
                fragment_informationView.findViewById(R.id.fragment_information_rv);
        //步骤 3：创建线性布局，设置列表控件对象的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragment_information_rv.setLayoutManager(linearLayoutManager);
        fragment_information_rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        //步骤 4：新建咨询适配器对象，连接数据源
        informationAdapter = new InformationAdapter(informationList);
        //步骤 5：列表控件对象绑定适配器对象
        fragment_information_rv.setAdapter(informationAdapter);
        // 步骤 6：执行 initInformationData 方法，获取远程服务器数据，并回调显示
        initInformationData();
        return fragment_informationView;
    }

    // showAllInformationCallback回调函数，用于显示所有的咨询
    public void showAllInformationCallback(final InformationList informationListFromJson){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                informationAdapter.setInformationList(informationListFromJson);
                fragment_information_rv.setAdapter(informationAdapter);
                informationAdapter.notifyDataSetChanged();
                informationAdapter.setOnItemClickListener(new InformationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int informationID) {
                        Toast.makeText(getContext(), "咨询编号" + informationID + "被点击", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InformationFragment.this.getActivity(),
                                InformationDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("informationID", informationID);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
