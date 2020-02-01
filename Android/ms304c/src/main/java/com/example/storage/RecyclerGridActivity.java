package com.example.storage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storage.adapter.BoxListAdapter;
import com.example.storage.adapter.RecyclerGridAdapter;
import com.example.storage.bean.GoodsInfo;
import com.example.storage.bean.box.BoxInfo;
import com.example.storage.bean.box.BoxResponseInfo;
import com.example.storage.bean.grid.GridInfo;
import com.example.storage.bean.grid.GridListInfo;
import com.example.storage.bean.grid.GridResponseInfo;
import com.example.storage.bean.material.MaterialteInfo;
import com.example.storage.widget.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ouyangshen on 2017/10/21.
 */
public class RecyclerGridActivity extends AppCompatActivity {

    protected static final int OK = 0;
    private ArrayList<BoxInfo> planetList; // 声明一个行星队列
    private ArrayList<GridInfo> gridList; // 声明一个行星队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid);
        initRecyclerGrid(1); // 初始化网格布局的循环视图
    }

    // 初始化网格布局的循环视图
    private void initRecyclerGrid(final int boxId) {
//        // 从布局文件中获取名叫rv_grid的循环视图
//        RecyclerView rv_grid = findViewById(R.id.rv_grid);
//        // 创建一个垂直方向的网格布局管理器
//        GridLayoutManager manager = new GridLayoutManager(this, 4);
//        // 设置循环视图的布局管理器
//        rv_grid.setLayoutManager(manager);
//        // 构建一个市场列表的网格适配器
//        RecyclerGridAdapter adapter = new RecyclerGridAdapter(this, GoodsInfo.getDefaultGrid());
//        // 设置网格列表的点击监听器
//        adapter.setOnItemClickListener(adapter);
//        // 设置网格列表的长按监听器
//        adapter.setOnItemLongClickListener(adapter);
//        // 给rv_grid设置市场网格适配器
//        rv_grid.setAdapter(adapter);
//        // 设置rv_grid的默认动画效果
//        rv_grid.setItemAnimator(new DefaultItemAnimator());
//        // 给rv_grid添加列表项之间的空白装饰
//        rv_grid.addItemDecoration(new SpacesItemDecoration(1));

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

        // 从布局文件中获取名叫rv_grid的循环视图
        RecyclerView rv_grid = findViewById(R.id.rv_grid);
        // 创建一个垂直方向的网格布局管理器
        GridLayoutManager manager = new GridLayoutManager(RecyclerGridActivity.this, 4);
        // 设置循环视图的布局管理器
        rv_grid.setLayoutManager(manager);
        // 构建一个市场列表的网格适配器
        RecyclerGridAdapter adapter = new RecyclerGridAdapter(RecyclerGridActivity.this, gridList);
        // 设置网格列表的点击监听器
        adapter.setOnItemClickListener(adapter);
        // 设置网格列表的长按监听器
        adapter.setOnItemLongClickListener(adapter);
        // 给rv_grid设置市场网格适配器
        rv_grid.setAdapter(adapter);
        // 设置rv_grid的默认动画效果
        rv_grid.setItemAnimator(new DefaultItemAnimator());
        // 给rv_grid添加列表项之间的空白装饰
        rv_grid.addItemDecoration(new SpacesItemDecoration(1));


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


}
