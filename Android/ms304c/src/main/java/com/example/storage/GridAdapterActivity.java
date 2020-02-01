package com.example.storage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storage.adapter.BoxListAdapter;
import com.example.storage.bean.box.BoxInfo;
import com.example.storage.bean.box.BoxResponseInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ouyangshen on 2017/10/7.
 */
public class GridAdapterActivity extends AppCompatActivity {
    protected static final int OK = 0;
    private ArrayList<BoxInfo> planetList; // 声明一个行星队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);
        initPlanetSpinner();
    }

    // 初始化行星列表的下拉框
    private void initPlanetSpinner() {
        // 获取默认的行星队列，即水星、金星、地球、火星、木星、土星
//        planetList = BoxInfo.getDefaultList();
//        // 构建一个行星列表的适配器
//        BoxListAdapter adapter = new BoxListAdapter(this, planetList);
//        // 从布局文件中获取名叫sp_planet的下拉框
//        Spinner sp = findViewById(R.id.sp_planet);
//        // 设置下拉框的标题
//        sp.setPrompt("请选择行星");
//        // 设置下拉框的列表适配器
//        sp.setAdapter(adapter);
//        // 设置下拉框默认显示第一项
//        sp.setSelection(0);
//        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
//        sp.setOnItemSelectedListener(new MySelectedListener());

                // 获取默认的行星队列，即水星、金星、地球、火星、木星、土星
        planetList = new ArrayList<BoxInfo>();
//        BoxInfo ginfo2 = new BoxInfo("XXX", "水星是太阳系八大行星最内侧也是最小的一颗行星，也是离太阳最近的行星");
//        planetList.add(ginfo2);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

//                                BoxInfo ginfo2 = new BoxInfo("XXX", "水星是太阳系八大行星最内侧也是最小的一颗行星，也是离太阳最近的行星");
//        planetList.add(ginfo2);

                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数
                        Gson gson = new GsonBuilder().create();
                        BoxResponseInfo rinfo = gson.fromJson(str, BoxResponseInfo.class);
                        ArrayList<BoxInfo> role = rinfo.getData().getData();
                        for (int j = 0; j < role.size(); j++) {
                            BoxInfo ginfo = role.get(j);
                            planetList.add(ginfo);
                        }

                        // 构建一个行星列表的适配器
                        BoxListAdapter adapter = new BoxListAdapter(GridAdapterActivity.this, planetList);
                        // 从布局文件中获取名叫sp_planet的下拉框
                        Spinner sp = findViewById(R.id.sp_planet);
                        // 设置下拉框的标题
                        sp.setPrompt("请选择行星");
                        // 设置下拉框的列表适配器
                        sp.setAdapter(adapter);
                        // 设置下拉框默认显示第一项
                        sp.setSelection(0);
                        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
                        sp.setOnItemSelectedListener(new MySelectedListener());

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

        GetBox(handler);

//        // 构建一个行星列表的适配器
//        BoxListAdapter adapter = new BoxListAdapter(this, planetList);
//        // 从布局文件中获取名叫sp_planet的下拉框
//        Spinner sp = findViewById(R.id.sp_planet);
//        // 设置下拉框的标题
//        sp.setPrompt("请选择行星");
//        // 设置下拉框的列表适配器
//        sp.setAdapter(adapter);
//        // 设置下拉框默认显示第一项
//        sp.setSelection(0);
//        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
//        sp.setOnItemSelectedListener(new MySelectedListener());

    }

    // 定义一个选择监听器，它实现了接口OnItemSelectedListener
    private class MySelectedListener implements OnItemSelectedListener {
        // 选择事件的处理方法，其中arg2代表选择项的序号
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Toast.makeText(GridAdapterActivity.this, "您选择的是" + planetList.get(arg2).getBoxName(), Toast.LENGTH_LONG).show();
        }

        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {}
    }


    private void GetBox(final Handler mh) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", "ms304w");
        params.put("name", "");
        params.put("page", "1");
        params.put("pageSize", "1000");
        params.put("status", "1");
        params.put("type", "1");

        client.get("http://157.52.168.109:8080/v1/material/box", params, new TextHttpResponseHandler() {
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
