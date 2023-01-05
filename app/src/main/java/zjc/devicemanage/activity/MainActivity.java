package zjc.devicemanage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import zjc.devicemanage.R;
import zjc.devicemanage.fragment.HomeFragment;
import zjc.devicemanage.fragment.InformationFragment;
import zjc.devicemanage.fragment.MineFragment;
import zjc.devicemanage.fragment.ShopingcartFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        initControlsEvent();
        loadFragment(homeFragment);
    }

    // 标题文本控件
    private TextView mainTitle_tv;
    // 4 个 Fragment 控件
     private HomeFragment homeFragment;
     private InformationFragment informationFragment;
     private ShopingcartFragment shopingcartFragment;
     private MineFragment mineFragment;
    // 4 个标签 Tab 布局控件
     private LinearLayout tab_home_ll;
     private LinearLayout tab_information_ll;
     private LinearLayout tab_shopingcart_ll;
     private LinearLayout tab_mine_ll;
    // 4个图片按钮控件
     private ImageButton tab_home_ib;
     private ImageButton tab_information_ib;
     private ImageButton tab_shopingcart_ib;
     private ImageButton tab_mine_ib;
    // 4个文本控件
     private TextView tab_home_tv;
     private TextView tab_information_tv;
     private TextView tab_shopingcart_tv;
     private TextView tab_mine_tv;

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 用 4 种 fragment 视图控件替换 activity_main.xml 中的 FrameLayout 控件
        fragmentTransaction.replace(R.id.main_fl, fragment);
        fragmentTransaction.commit();
        // 根据不同的 Fragment 显示不同的标题、点击图片和文字变色
        if (fragment instanceof HomeFragment){
            mainTitle_tv.setText("首页");
            tab_home_ib.setImageResource(R.drawable.home_click);
            tab_home_tv.setTextColor(Color.parseColor("#00BFFF"));
        }else if (fragment instanceof InformationFragment){
            mainTitle_tv.setText("咨询");
            tab_information_ib.setImageResource(R.drawable.information_click);
            tab_information_tv.setTextColor(Color.parseColor("#00BFFF"));
        }else if (fragment instanceof ShopingcartFragment){
            mainTitle_tv.setText("购物车");
            tab_shopingcart_ib.setImageResource(R.drawable.shoppingcart_click);
            tab_shopingcart_tv.setTextColor(Color.parseColor("#00BFFF"));
        }else if (fragment instanceof MineFragment){
            mainTitle_tv.setText("我");
            tab_mine_ib.setImageResource(R.drawable.mine_click);
            tab_mine_tv.setTextColor(Color.parseColor("#00BFFF"));
        }
    }

    private void initControls(){
        // 载入控件
         mainTitle_tv = findViewById(R.id.mainTitle_tv);
         tab_home_ll = findViewById(R.id.tab_home_ll);
         tab_information_ll = findViewById(R.id.tab_information_ll);
         tab_shopingcart_ll = findViewById(R.id.tab_shopingcart_ll);
         tab_mine_ll = findViewById(R.id.tab_mine_ll);
         tab_home_ib = findViewById(R.id.tab_home_ib);
         tab_information_ib = findViewById(R.id.tab_information_ib);
         tab_shopingcart_ib = findViewById(R.id.tab_shopingcart_ib);
         tab_mine_ib = findViewById(R.id.tab_mine_ib);
         tab_home_tv = findViewById(R.id.tab_home_tv);
         tab_information_tv = findViewById(R.id.tab_information_tv);
         tab_shopingcart_tv = findViewById(R.id.tab_shopingcart_tv);
         tab_mine_tv = findViewById(R.id.tab_mine_tv);
        // 生成 4 个 Fragment 对象
         homeFragment = new HomeFragment();
         informationFragment = new InformationFragment();
         shopingcartFragment = new ShopingcartFragment();
         mineFragment = new MineFragment();
    }

    //新增 resetImageAndTextColor 函数，用于重置 4 个按钮图片和文本颜色
    private void resetImageAndTextColor(){
        // 重置首页按钮图片和文本颜色
         tab_home_ib.setImageResource(R.drawable.home);
         tab_home_tv.setTextColor(Color.parseColor("#272727"));
        // 重置咨询按钮图片和文本颜色
         tab_information_ib.setImageResource(R.drawable.information);
         tab_information_tv.setTextColor(Color.parseColor("#272727"));
         // 重置购物车按钮图片和文本颜色
         tab_shopingcart_ib.setImageResource(R.drawable.shoppingcart);
         tab_shopingcart_tv.setTextColor(Color.parseColor("#272727"));
        // 重置我按钮图片和文本颜色
         tab_mine_ib.setImageResource(R.drawable.mine);
         tab_mine_tv.setTextColor(Color.parseColor("#272727"));
    }

    //重载 onClick 函数 对 4 个不同的布局控件，进行点击处理
    @Override
    public void onClick(View v) {
        resetImageAndTextColor();
        switch (v.getId()){
            case R.id.tab_home_ll:
                loadFragment(homeFragment);
                break;
                case R.id.tab_information_ll:
                    loadFragment(informationFragment);
                    break;
                    case R.id.tab_shopingcart_ll:
                        loadFragment(shopingcartFragment);
                        break;
                        case R.id.tab_mine_ll:
                            loadFragment(mineFragment);
                            break;
        }
    }

    //将 4 个布局控件的点击事件都交由 MainActivity 的 onClick 函数处理
    private void initControlsEvent(){
        tab_home_ll.setOnClickListener(this);
        tab_information_ll.setOnClickListener(this);
        tab_shopingcart_ll.setOnClickListener(this);
        tab_mine_ll.setOnClickListener(this);
    }
}
