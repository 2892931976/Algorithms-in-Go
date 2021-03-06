package com.example.storage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storage.adapter.BoxListAdapter;
import com.example.storage.adapter.GridListAdapter;
import com.example.storage.adapter.MaterialListAdapter;
import com.example.storage.adapter.PlanetListAdapter;
import com.example.storage.bean.ClientRequest;
import com.example.storage.bean.Planet;
import com.example.storage.bean.box.BoxInfo;
import com.example.storage.bean.box.BoxResponseInfo;
import com.example.storage.bean.grid.GridInfo;
import com.example.storage.bean.grid.GridListInfo;
import com.example.storage.bean.grid.GridResponseInfo;
import com.example.storage.bean.material.MaterialteInfo;
import com.example.storage.bean.material.ResponseInfo;
import com.example.storage.util.GetImageByUrl;
import com.example.storage.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ouyangshen on 2017/10/7.
 */
public class BaseAdapterActivity extends AppCompatActivity implements View.OnClickListener {
    protected static final int OK = 0;
    private ArrayList<BoxInfo> planetList; // 声明一个行星队列
    private ArrayList<GridInfo> gridList; // 声明一个行星队列
    private ArrayList<MaterialteInfo> goodsList;

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);
        initPlanetSpinner();
//        initGridPlanetSpinner();
        initMaterialPlanetSpinner();

        // 下面通过七个按钮，分别演示不同拉伸类型的图片拉伸效果
        findViewById(R.id.btn_center).setOnClickListener(this);
        findViewById(R.id.btn_fitXY).setOnClickListener(this);
        findViewById(R.id.btn_fitStart).setOnClickListener(this);
        findViewById(R.id.btn_fitEnd).setOnClickListener(this);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
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

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数
                        Gson gson = new GsonBuilder().create();
                        BoxResponseInfo rinfo = gson.fromJson(str, BoxResponseInfo.class);
                        ArrayList<BoxInfo> role = rinfo.getData().getData();
                        for (int j = 0; j < role.size(); j++) {
                            BoxInfo ginfo = role.get(j);
                            planetList.add(ginfo);
                        }

                        // 构建一个行星列表的适配器
                        BoxListAdapter adapter = new BoxListAdapter(BaseAdapterActivity.this, planetList);
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

    // 初始化行星列表的下拉框
    private void initGridPlanetSpinner(final int boxId) {
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
        gridList = new ArrayList<GridInfo>();


        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数
                        Gson gson = new GsonBuilder().create();
                        GridResponseInfo rinfo = gson.fromJson(str, GridResponseInfo.class);
                        ArrayList<GridListInfo> role = rinfo.getData().getData();
                        for (int j = 0; j < role.size(); j++) {
                            GridListInfo binfo = role.get(j);
                            ArrayList<GridInfo> grids = binfo.getGridResponses();
                            for (int i = 0; i < grids.size(); i++) {
                                GridInfo ginfo = grids.get(i);
                                if (ginfo.getBoxId() == boxId) {
                                    gridList.add(ginfo);
                                }
                            }
//                            GridInfo ginfo = role.get(j);
//                            gridList.add(ginfo);
                        }

                        // 构建一个行星列表的适配器
                        GridListAdapter adapter = new GridListAdapter(BaseAdapterActivity.this, gridList);
                        // 从布局文件中获取名叫sp_planet的下拉框
                        Spinner sp = findViewById(R.id.grid_planet);
                        // 设置下拉框的标题
                        sp.setPrompt("请选择行星");
                        // 设置下拉框的列表适配器
                        sp.setAdapter(adapter);
                        // 设置下拉框默认显示第一项
                        sp.setSelection(0);
                        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
                        sp.setOnItemSelectedListener(new MyGridSelectedListener());

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

    private void initMaterialPlanetSpinner() {
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
        goodsList = new ArrayList<MaterialteInfo>();

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数

                        Gson gson = new GsonBuilder().create();
                        ResponseInfo rinfo = gson.fromJson(str, ResponseInfo.class);
                        ArrayList<MaterialteInfo> role = rinfo.getData().getData();
                        for (int j = 0; j < role.size(); j++) {
                            MaterialteInfo ginfo = role.get(j);
                            goodsList.add(ginfo);
                        }

                        // 构建一个行星列表的适配器
                        MaterialListAdapter adapter = new MaterialListAdapter(BaseAdapterActivity.this, goodsList);
                        // 从布局文件中获取名叫sp_planet的下拉框
                        Spinner sp = findViewById(R.id.material_planet);
                        // 设置下拉框的标题
                        sp.setPrompt("请选择行星");
                        // 设置下拉框的列表适配器
                        sp.setAdapter(adapter);
                        // 设置下拉框默认显示第一项
                        sp.setSelection(0);
                        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
                        sp.setOnItemSelectedListener(new MyMaterialSelectedListener());

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

        GetMaterial(handler);

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
            Toast.makeText(BaseAdapterActivity.this, "您选择的是" + planetList.get(arg2).getBoxName(), Toast.LENGTH_LONG).show();
            int boxId = planetList.get(arg2).getBoxId();
            if (boxId > 0) {
                initGridPlanetSpinner(boxId);
            }
        }

        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private class MyGridSelectedListener implements OnItemSelectedListener {
        // 选择事件的处理方法，其中arg2代表选择项的序号
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Toast.makeText(BaseAdapterActivity.this, "您选择的是" + gridList.get(arg2).getGridName(), Toast.LENGTH_LONG).show();
            et_name.setText(gridList.get(arg2).getGridName());
        }

        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private class MyMaterialSelectedListener implements OnItemSelectedListener {
        // 选择事件的处理方法，其中arg2代表选择项的序号
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Toast.makeText(BaseAdapterActivity.this, "您选择的是" + goodsList.get(arg2).getMaterialName(), Toast.LENGTH_LONG).show();
        }

        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    private void GetBox(final android.os.Handler mh) {
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

    private void GetMaterial(final android.os.Handler mh) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", "ms304w");
        params.put("name", "");
        params.put("page", "1");
        params.put("pageSize", "1000");
        params.put("status", "1");

        client.get("http://157.52.168.109:8081/v1/material", params, new TextHttpResponseHandler() {
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

    @Override
    public void onClick(View v) {  // 一旦监听到点击动作，就触发监听器的onClick方法
        if (v.getId() == R.id.btn_center) {
            // 将拉伸类型设置为“按照原尺寸居中显示”
//            iv_scale.setScaleType(ImageView.ScaleType.CENTER);
        } else if (v.getId() == R.id.btn_fitXY) {
            String name = et_name.getText().toString();
            Toast.makeText(BaseAdapterActivity.this, "您选择的grid是" + name, Toast.LENGTH_LONG).show();
            // 将拉伸类型设置为“拉伸图片使其正好填满视图（图片可能被拉伸变形）”
//            iv_scale.setScaleType(ImageView.ScaleType.FIT_XY);
        } else if (v.getId() == R.id.btn_fitStart) {
            // 将拉伸类型设置为“保持宽高比例，拉伸图片使其位于视图上方或左侧”
//            iv_scale.setScaleType(ImageView.ScaleType.FIT_START);
        } else if (v.getId() == R.id.btn_fitEnd) {
            // 将拉伸类型设置为“保持宽高比例，拉伸图片使其位于视图下方或右侧”
//            iv_scale.setScaleType(ImageView.ScaleType.FIT_END);
        }
    }
}
