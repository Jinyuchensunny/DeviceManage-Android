package zjc.devicemanage.activity;

import androidx.appcompat.app.AppCompatActivity;
import zjc.devicemanage.R;
import zjc.devicemanage.util.MyHttpUtil;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InformationDetailActivity extends AppCompatActivity {
    private WebView activity_infomation_detail_wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        activity_infomation_detail_wv=findViewById(R.id.activity_infomation_detail_wv);
        //通过 Bundle 获得咨询编号参数
        Bundle bundle = getIntent().getExtras();
        int informationID = bundle.getInt("informationID");
        //设置 WebView 控件的 3 个操作
        activity_infomation_detail_wv.getSettings().setJavaScriptEnabled(true);
        activity_infomation_detail_wv.setWebViewClient(new WebViewClient());
        String showInformationURL = MyHttpUtil.host +
                "/DeviceManage/showInformationByIdFromAppPortol?infoId=";
        activity_infomation_detail_wv.loadUrl(showInformationURL+informationID);
    }
}
