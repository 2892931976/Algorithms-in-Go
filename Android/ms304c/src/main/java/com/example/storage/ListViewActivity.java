package com.example.storage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.storage.adapter.PlanetListAdapter;
import com.example.storage.bean.Planet;
import com.example.storage.bean.order.OrderDetailInfo;
import com.example.storage.bean.order.ResponseInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

/**
 * Created by ouyangshen on 2017/10/7.
 */
public class ListViewActivity extends AppCompatActivity implements OnClickListener {
    protected static final int OK = 0;
    private final static String TAG = "ListViewActivity";
    private ListView lv_planet; // 声明一个列表视图对象
    private Drawable drawable;  // 声明一个图形对象
    private static String[] nameArray = {"", "补料", "领料", "归还", "报废", "回收"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        ArrayList<Planet> planetList = new ArrayList<Planet>();
                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数
                        Gson gson = new GsonBuilder().create();
                        ResponseInfo rinfo = gson.fromJson(str, ResponseInfo.class);
                        ArrayList<OrderDetailInfo> role = rinfo.getData().getData();
//                        OrderDetailInfo od = role.get(1);
//                        String str2 =  role.getName();
//                        Toast.makeText(ListViewActivity.this, ""+od.getMaterialName(), Toast.LENGTH_SHORT).show();
                        for (int j = 0; j < role.size(); j++) {
                            final OrderDetailInfo ginfo = role.get(j);
                            Planet pinfo = new Planet(ginfo.getName(), ginfo.getMaterialName(), ginfo.getMaterialCode(), ginfo.getMaterialSpec(), ginfo.getCreated(), ""+ ginfo.getQty(), ""+ginfo.getType());
                            planetList.add(pinfo);
                        }
                        // 构建一个行星队列的列表适配器
                        PlanetListAdapter adapter = new PlanetListAdapter(ListViewActivity.this, planetList);
                        // 从布局视图中获取名叫lv_planet的列表视图
                        lv_planet = findViewById(R.id.lv_planet);
                        // 给lv_planet设置行星列表适配器
                        lv_planet.setAdapter(adapter);
                        // 给lv_planet设置列表项的点击监听器
                        lv_planet.setOnItemClickListener(adapter);
                        // 给lv_planet设置列表项的长按监听器
                        lv_planet.setOnItemLongClickListener(adapter);
                        // 从资源文件中获取分隔线的图形对象
                        drawable = getResources().getDrawable(R.drawable.divider_red2);
                        // 初始化分隔线下拉框
                        initDividerSpinner();

                        break;
                    case 1:
//                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, SuccessActivity.class));
//                        LoginActivity.this.finish();
                        break;
                    case 2:
//                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
//                        Log.e("input error", "url为空");
                        break;
                    case 4:
//                        Toast.makeText(LoginActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        };

        client_get_order_test(handler);
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.iv_cart) { // 点击了购物车图标
//            // 跳转到购物车页面
//            Intent intent = new Intent(this, ShoppingCartActivity.class);
//            startActivity(intent);
//        }
    }

    // 初始化分隔线显示方式的下拉框
    private void initDividerSpinner() {
        ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, dividerArray);
        Spinner sp_list = findViewById(R.id.sp_list);
        sp_list.setPrompt("请选择分隔线显示方式");
        sp_list.setAdapter(dividerAdapter);
        sp_list.setOnItemSelectedListener(new DividerSelectedListener());
        sp_list.setSelection(0);
    }

    private String[] dividerArray = {
            "不显示分隔线",
            "显示分隔线"
    };

    class DividerSelectedListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            int dividerHeight = 5;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lv_planet.setDivider(drawable);  // 设置lv_planet的分隔线
            lv_planet.setDividerHeight(dividerHeight);  // 设置lv_planet的分隔线高度
            lv_planet.setPadding(0, 0, 0, 0);  // 设置lv_planet的四周空白
            lv_planet.setBackgroundColor(Color.TRANSPARENT);  // 设置lv_planet的背景颜色

            if (arg2 == 0) {  // 不显示分隔线(分隔线为null)
                lv_planet.setDivider(null);
                lv_planet.setDividerHeight(dividerHeight);
            } else if (arg2 == 1) {  // 只显示内部分隔线(后设置分隔线高度)
                lv_planet.setDivider(drawable);
                lv_planet.setDividerHeight(dividerHeight);
            }

            lv_planet.setLayoutParams(params);  // 设置lv_planet的布局参数
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void client_get_order_test(final android.os.Handler mh) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", "ms304w");
        params.put("name", "");
        params.put("page", "1");
        params.put("pageSize", "100");
        params.put("type", "0");

        client.get("http://157.52.168.109:8081/v1/order/detail/all", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {

                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("mmmm", res);  //往Bundle中存放数据
                        msg.what = OK;
                        msg.setData(bundle);//mes利用Bundle传递数据
                        mh.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                }
        );
    }
}
