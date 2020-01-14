package com.example.storage;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storage.bean.ClientRequest;
import com.example.storage.bean.material.MaterialteInfo;
import com.example.storage.bean.material.ResponseInfo;
import com.example.storage.util.GetImageByUrl;
import com.example.storage.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by ouyangshen on 2017/10/21.
 */
@SuppressLint("SetTextI18n")
public class SearchResultActvity extends AppCompatActivity {
    private static final String TAG = "SearchResultActvity";
    private TextView tv_search_result;
    private LinearLayout ll_channel;
    private ArrayList<MaterialteInfo> goodsList;
    protected static final int OK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        // 从布局文件中获取名叫tl_result的工具栏
        Toolbar tl_result = findViewById(R.id.tl_result);
        // 设置工具栏的背景
        tl_result.setBackgroundResource(R.color.blue_light);
        // 设置工具栏的标志图片
        tl_result.setLogo(R.drawable.ic_app);
        // 设置工具栏的标题文字
        tl_result.setTitle("搜索结果页");
        // 设置工具栏的导航图标
        tl_result.setNavigationIcon(R.drawable.ic_back);
        // 使用tl_result替换系统自带的ActionBar
        setSupportActionBar(tl_result);
//        tv_search_result = findViewById(R.id.tv_search_result);
        ll_channel = findViewById(R.id.ll_channel2);
        // 执行搜索查询操作
        doSearchQuery(getIntent());
    }

    // 解析搜索请求页面传来的搜索信息，并据此执行搜索查询操作
    private void doSearchQuery(Intent intent) {
        if (intent != null) {
            // 如果是通过ACTION_SEARCH来调用，即为搜索框来源
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                // 获取额外的搜索数据
                Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
                String value = bundle.getString("hi");
                // 获取实际的搜索文本
                String queryString = intent.getStringExtra(SearchManager.QUERY);
//                tv_search_result.setText("您输入的搜索文字是："+queryString+", 额外信息："+value);
                showGoods(queryString, value);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_null.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_null, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // 点击了工具栏左边的返回箭头
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private LinearLayout.LayoutParams mFullParams, mHalfParams;

    private void showGoods(String queryString, String value) {
//        // 移除线性布局ll_channel下面的所有子视图
//        ll_channel.removeAllViews();
//        // mFullParams这个布局参数的宽度占了一整行
//        mFullParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        // mHalfParams这个布局参数的宽度与其它布局平均分
//        mHalfParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
//        // 给mHalfParams设置四周的空白距离
//        mHalfParams.setMargins(Utils.dip2px(this, 2), Utils.dip2px(this, 2), Utils.dip2px(this, 2), Utils.dip2px(this, 2));
//        // 创建一行的线性布局
//        LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
//        // 查询商品数据库中的所有商品记录
//        ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");

        // 移除线性布局ll_channel下面的所有子视图
        ll_channel.removeAllViews();
        // mFullParams这个布局参数的宽度占了一整行
        mFullParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // mHalfParams这个布局参数的宽度与其它布局平均分
        mHalfParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        // 给mHalfParams设置四周的空白距离
        mHalfParams.setMargins(Utils.dip2px(SearchResultActvity.this, 2), Utils.dip2px(SearchResultActvity.this, 2), Utils.dip2px(SearchResultActvity.this, 2), Utils.dip2px(SearchResultActvity.this, 2));


        goodsList = new ArrayList<MaterialteInfo>();
//        LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

//                        // 移除线性布局ll_channel下面的所有子视图
//                        ll_channel.removeAllViews();
//                        // mFullParams这个布局参数的宽度占了一整行
//                        mFullParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//                        // mHalfParams这个布局参数的宽度与其它布局平均分
//                        mHalfParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
//                        // 给mHalfParams设置四周的空白距离
//                        mHalfParams.setMargins(Utils.dip2px(ShoppingChannelActivity.this, 2), Utils.dip2px(ShoppingChannelActivity.this, 2), Utils.dip2px(ShoppingChannelActivity.this, 2), Utils.dip2px(ShoppingChannelActivity.this, 2));
//                        // 创建一行的线性布局
                        LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
                        // 查询商品数据库中的所有商品记录
//                        ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
//                        ArrayList<MaterialteInfo> goodsList = new ArrayList<MaterialteInfo>();

//                        Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
//                        String str = msg.obj.toString();
//                        String str =  "{'errorCode':500,'errorMsg':'illeagle params'}";

                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数

//                        Gson gson = new Gson();
                        Gson gson = new GsonBuilder().create();
                        ResponseInfo rinfo = gson.fromJson(str, ResponseInfo.class);
                        ArrayList<MaterialteInfo> role = rinfo.getData().getData();
//                        String str2 =  role.getName();
//                        Toast.makeText(ShoppingChannelActivity.this, ""+role.getName(), Toast.LENGTH_SHORT).show();
//                        GoodsInfo ginfo = new GoodsInfo();
//                        ginfo.setName(""+role.getName());
//                        ginfo.setDesc("魅族 PRO6S 4GB+64GB 全网通公开版 星空黑 移动联通电信4G手机");
//                        ginfo.setPrice(90000);
                        for (int j = 0; j < role.size(); j++) {
                            MaterialteInfo ginfo = role.get(j);
                            goodsList.add(ginfo);
                        }
//                        Toast.makeText(ShoppingChannelActivity.this, ""+goodsArray.size(), Toast.LENGTH_SHORT).show();

                        int i = 0;
                        for (; i < goodsList.size(); i++) {
                            final MaterialteInfo info = goodsList.get(i);
                            // 创建一个商品项的垂直线性布局，从上到下依次列出商品标题、商品图片、商品价格
                            LinearLayout ll_goods = newLinearLayout(LinearLayout.VERTICAL, 1);
                            ll_goods.setBackgroundColor(Color.WHITE);
                            // 添加商品标题
                            TextView tv_name = new TextView(SearchResultActvity.this);
                            tv_name.setLayoutParams(mFullParams);
                            tv_name.setGravity(Gravity.CENTER);
                            tv_name.setText("" + info.getMaterialName());
                            tv_name.setTextColor(Color.BLACK);
                            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                            ll_goods.addView(tv_name);
                            // 添加商品小图
                            ImageView iv_thumb = new ImageView(SearchResultActvity.this);
                            iv_thumb.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(SearchResultActvity.this, 150)));
                            iv_thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
                            GetImageByUrl getImageByUrl = new GetImageByUrl();
                            if (info.getImg() != "") {
                                getImageByUrl.setImage(iv_thumb, info.getImg());
                            }
                            iv_thumb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(SearchResultActvity.this, ShoppingDetailActivity.class);
//                                    intent.putExtra("goods_id", info.rowid);
                                    startActivity(intent);
                                }
                            });
                            ll_goods.addView(iv_thumb);
                            // 添加商品价格
                            LinearLayout ll_bottom = newLinearLayout(LinearLayout.HORIZONTAL, 0);
                            TextView tv_price = new TextView(SearchResultActvity.this);
                            tv_price.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
                            tv_price.setGravity(Gravity.CENTER);
                            tv_price.setText("" + info.getMaterialCode());
                            tv_price.setTextColor(Color.RED);
                            tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            ll_bottom.addView(tv_price);
                            // 添加购物车按钮
                            Button btn_add = new Button(SearchResultActvity.this);
                            btn_add.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
                            btn_add.setGravity(Gravity.CENTER);
                            btn_add.setText("开门");
                            btn_add.setTextColor(Color.BLACK);
                            btn_add.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            btn_add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //            jsonParams.put("orderId", 0);
//            jsonParams.put("userId", 564);
//            jsonParams.put("type", 2);
//            jsonParams.put("materialId", 9);
//            jsonParams.put("operation", 1);
//            jsonParams.put("openWay", 2);
                                    ClientRequest clientRequest = new ClientRequest();
                                    SharedPreferences mShared = getSharedPreferences("share_login", MODE_PRIVATE);
                                    int userId = mShared.getInt("userInfo", 0);
                                    clientRequest.setUserId(userId);
                                    clientRequest.setType(2);
                                    clientRequest.setMaterialId(info.getMaterialId());
                                    clientRequest.setOperation(1);
                                    clientRequest.setOpenWay(2);
                                    Toast.makeText(SearchResultActvity.this,
                                            "已添加2222部" + info.getMaterialId() + "到购物车", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SearchResultActvity.this,
                                            "已添加一部" + userId + "到购物车", Toast.LENGTH_SHORT).show();
                                    async_post_test(clientRequest);
//                                    addToCart(info.rowid);
//                                    Toast.makeText(ShoppingChannelActivity.this,
//                                            "已添加一部" + info.getName() + "到购物车", Toast.LENGTH_SHORT).show();
                                }
                            });
                            ll_bottom.addView(btn_add);
                            ll_goods.addView(ll_bottom);
                            // 把商品项添加到该行上
                            ll_row.addView(ll_goods);
//                            Toast.makeText(ShoppingChannelActivity.this, ""+info.getMaterialName(), Toast.LENGTH_SHORT).show();
                            // 每行放两个商品项，所以放满两个商品后，就要重新创建下一行的线性视图
                            if (i % 2 == 1) {
                                ll_channel.addView(ll_row);
                                ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
                            }

                            if (goodsList.size() == i + 1) {
//                                ll_channel.addView(ll_row);
                                if (i % 2 == 0) {
                                    ll_row.addView(newLinearLayout(LinearLayout.VERTICAL, 1));
                                    ll_channel.addView(ll_row);
                                }
                            }
                        }
//                         最后一行只有一个商品项，则补上一个空白格，然后把最后一行添加到ll_channel
                        if (i % 2 == 0) {
                            ll_row.addView(newLinearLayout(LinearLayout.VERTICAL, 1));
                            ll_channel.addView(ll_row);
                        }

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

        client_get_test(handler, queryString);

//        int i = 0;
//        for (; i < goodsArray.size(); i++) {
//            final GoodsInfo info = goodsArray.get(i);
//            // 创建一个商品项的垂直线性布局，从上到下依次列出商品标题、商品图片、商品价格
//            LinearLayout ll_goods = newLinearLayout(LinearLayout.VERTICAL, 1);
//            ll_goods.setBackgroundColor(Color.WHITE);
//            // 添加商品标题
//            TextView tv_name = new TextView(this);
//            tv_name.setLayoutParams(mFullParams);
//            tv_name.setGravity(Gravity.CENTER);
//            tv_name.setText(info.name);
//            tv_name.setTextColor(Color.BLACK);
//            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
//            ll_goods.addView(tv_name);
//            // 添加商品小图
//            ImageView iv_thumb = new ImageView(this);
//            iv_thumb.setLayoutParams(new LayoutParams(
//                    LayoutParams.MATCH_PARENT, Utils.dip2px(this, 150)));
//            iv_thumb.setScaleType(ScaleType.FIT_CENTER);
//            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
//            iv_thumb.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
//                    intent.putExtra("goods_id", info.rowid);
//                    startActivity(intent);
//                }
//            });
//            ll_goods.addView(iv_thumb);
//            // 添加商品价格
//            LinearLayout ll_bottom = newLinearLayout(LinearLayout.HORIZONTAL, 0);
//            TextView tv_price = new TextView(this);
//            tv_price.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
//            tv_price.setGravity(Gravity.CENTER);
//            tv_price.setText("" + (int) info.price);
//            tv_price.setTextColor(Color.RED);
//            tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//            ll_bottom.addView(tv_price);
//            // 添加购物车按钮
//            Button btn_add = new Button(this);
//            btn_add.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3));
//            btn_add.setGravity(Gravity.CENTER);
//            btn_add.setText("加入购物车");
//            btn_add.setTextColor(Color.BLACK);
//            btn_add.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//            btn_add.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    addToCart(info.rowid);
//                    Toast.makeText(ShoppingChannelActivity.this,
//                            "已添加一部" + info.name + "到购物车", Toast.LENGTH_SHORT).show();
//                }
//            });
//            ll_bottom.addView(btn_add);
//            ll_goods.addView(ll_bottom);
//            // 把商品项添加到该行上
//            ll_row.addView(ll_goods);
//            // 每行放两个商品项，所以放满两个商品后，就要重新创建下一行的线性视图
//            if (i % 2 == 1) {
//                ll_channel.addView(ll_row);
//                ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
//            }
//        }
//        // 最后一行只有一个商品项，则补上一个空白格，然后把最后一行添加到ll_channel
//        if (i % 2 == 0) {
//            ll_row.addView(newLinearLayout(LinearLayout.VERTICAL, 1));
//            ll_channel.addView(ll_row);
//        }
    }

    // 创建一个线性视图的框架
    private LinearLayout newLinearLayout(int orientation, int weight) {
        LinearLayout ll_new = new LinearLayout(this);
        ll_new.setLayoutParams((weight == 0) ? mFullParams : mHalfParams);
        ll_new.setOrientation(orientation);
        return ll_new;
    }

    private void client_get_test(final android.os.Handler mh, String queryString) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                // TODO Auto-generated method stub
////                String username = et_username.getText().toString();
////                String password = et_password.getText().toString();
////                http://192.168.1.174:8081/v1/material?tag=ms304w&name=&page=1&pageSize=5&status=1
//
//                HttpClient client = new DefaultHttpClient();
////                HttpGet get = new HttpGet("http://192.168.1.174:8090/v1/permission/role/437");
//                HttpGet get = new HttpGet("http://192.168.1.183:8081/v1/material?tag=ms304w&name=&page=1&pageSize=100&status=1");
//
//                try {
//                    HttpResponse response = client.execute(get);
//                    if(response.getStatusLine().getStatusCode() == 200){
//                        HttpEntity entity = response.getEntity();
//                        String result = EntityUtils.toString(entity, "utf-8");
//                        Message msg = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("mmmm",result);  //往Bundle中存放数据
//                        msg.what = OK;
////                        msg.obj = result;
//
//                        msg.setData(bundle);//mes利用Bundle传递数据
//                        mh.sendMessage(msg);
//
//                    }
//
//                } catch (ClientProtocolException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", "ms304w");
        params.put("name", queryString);
        params.put("page", "1");
        params.put("pageSize", "100");
        params.put("status", "1");

        client.get("http://192.168.1.183:8081/v1/material", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
//                        Gson gson = new GsonBuilder().create();
                        // Define Response class to correspond to the JSON response returned
//                        gson.fromJson(res, Response.class);

                        //   public void onSuccess(int statusCode, Header[] headers, String response) {
                        //      Gson gson = new GsonBuilder().create();
                        //      Movie movie = gson.fromJson(response, Movie.class);
                        // }
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

    private void async_post_test(ClientRequest clientRequest) {
//        String username = et_username.getText().toString();
//        String password = et_password.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();

        Gson gson = new Gson();
        String jsonObject = gson.toJson(clientRequest);
        try {
            ByteArrayEntity entity = new ByteArrayEntity(jsonObject.getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getApplicationContext(), "http://192.168.1.183:8080/v1/ms304w/open", entity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // TODO Auto-generated method stub
//            Message msg = new Message();
//            msg.what = OK;
//            msg.obj = new String(responseBody);
//            mh.sendMessage(msg);
                    String str = new String(responseBody);
                    Toast.makeText(SearchResultActvity.this, "" + str, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] responseBody, Throwable error) {
                    // TODO Auto-generated method stub
//            Message msg = new Message();
//            msg.what = OK;
//            msg.obj = new String(responseBody);
//            mh.sendMessage(msg);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        RequestParams params = new RequestParams();
//        params.add("username", username);
//        params.add("password", password);
//        RequestParams params = new RequestParams();
//        params.setUseJsonStreamer(true);
//        params.put("OrderId", 0);
//        int ui = 564;
//        params.put("userId", ui);
//        params.put("type", 2);
//        params.put("materialId", 9);
//        params.put("operation", 1);
//        params.put("openWay", 2);
//        params.setUseJsonStreamer(true);

//        JsonObject jo = Json.createObjectBuilder()
//                .add("data", 2376845)
//                .add("data2", 12545)
//                .add("array",Json.createArrayBuilder()
//                        .add(Json.createObjectBuilder().add("data3", "2013-01-10").add("data4", 23532).build())
//                        .add(Json.createObjectBuilder().add("data3", "2013-01-11").add("data4", 523526).build()))
//                .build();
//        ScaanRestClient restClient = new ScaanRestClient(getApplicationContext());
//        JsonObject jo = Json.createObjectBuilder()
//        JSONObject jsonParams = new JSONObject();
//
//        try {
//            jsonParams.put("orderId", 0);
//            jsonParams.put("userId", 564);
//            jsonParams.put("type", 2);
//            jsonParams.put("materialId", 9);
//            jsonParams.put("operation", 1);
//            jsonParams.put("openWay", 2);
////            StringEntity entity = new StringEntity(jsonParams.toString());
////            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//            ByteArrayEntity entity = new ByteArrayEntity(jsonParams.toString().getBytes("UTF-8"));
//            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//            client.post(getApplicationContext(), "http://192.168.1.183:8080/v1/ms304w/open", entity, "application/json", new AsyncHttpResponseHandler() {
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    // TODO Auto-generated method stub
////            Message msg = new Message();
////            msg.what = OK;
////            msg.obj = new String(responseBody);
////            mh.sendMessage(msg);
//                    String str = new String(responseBody);
//                    Toast.makeText(ShoppingChannelActivity.this, "" + str, Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers,
//                                      byte[] responseBody, Throwable error) {
//                    // TODO Auto-generated method stub
////            Message msg = new Message();
////            msg.what = OK;
////            msg.obj = new String(responseBody);
////            mh.sendMessage(msg);
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        jsonParams.put("", 564);

//        StringEntity entity = new StringEntity(jsonParams.toString());
//        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        client.post("http://192.168.1.183:8080/v1/ms304w/open", entity , new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody){
//            // TODO Auto-generated method stub
////            Message msg = new Message();
////            msg.what = OK;
////            msg.obj = new String(responseBody);
////            mh.sendMessage(msg);
//                String str = new String(responseBody);
//                Toast.makeText(ShoppingChannelActivity.this, ""+str, Toast.LENGTH_SHORT).show();
//
//            }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers,
//        byte[] responseBody, Throwable error) {
//            // TODO Auto-generated method stub
////            Message msg = new Message();
////            msg.what = OK;
////            msg.obj = new String(responseBody);
////            mh.sendMessage(msg);
//        }
//    });
    }

}
