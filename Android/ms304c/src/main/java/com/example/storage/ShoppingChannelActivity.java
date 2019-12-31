package com.example.storage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.example.storage.bean.CartInfo;
import com.example.storage.bean.ClientRequest;
import com.example.storage.bean.material.ResponseInfo;
import com.example.storage.bean.material.DataListInfo;
import com.example.storage.bean.material.MaterialteInfo;
import com.example.storage.database.CartDBHelper;
import com.example.storage.database.GoodsDBHelper;
import com.example.storage.provider.SPUtils;
import com.example.storage.util.DateUtil;
import com.example.storage.util.GetImageByUrl;
import com.example.storage.util.SharedUtil;
import com.example.storage.util.Utils;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

/**
 * Created by ouyangshen on 2017/10/1.
 */
@SuppressLint("SetTextI18n")
public class ShoppingChannelActivity extends AppCompatActivity implements OnClickListener {
    protected static final int OK = 0;
    private TextView tv_count;
    private LinearLayout ll_channel;
    private int mCount; // 购物车中的商品数量
    private GoodsDBHelper mGoodsHelper; // 声明一个商品数据库的帮助器对象
    private CartDBHelper mCartHelper; // 声明一个购物车数据库的帮助器对象
    private Context mContext;
    private ArrayList<MaterialteInfo> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        ll_channel = findViewById(R.id.ll_channel);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        tv_title.setText("物料列表");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cart) { // 点击了购物车图标
            // 跳转到购物车页面
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        }
    }

    // 把指定编号的商品添加到购物车
    private void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        // 把购物车中的商品数量写入共享参数
        SharedUtil.getIntance(this).writeShared("count", "" + mCount);
        // 根据商品编号查询购物车数据库中的商品记录
        CartInfo info = mCartHelper.queryByGoodsId(goods_id);
        if (info != null) { // 购物车已存在该商品记录
            info.count++; // 该商品的数量加一
            info.update_time = DateUtil.getNowDateTime("");
            // 更新购物车数据库中的商品记录信息
            mCartHelper.update(info);
        } else { // 购物车不存在该商品记录
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            // 往购物车数据库中添加一条新的商品记录
            mCartHelper.insert(info);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getIntance(this).readShared("count", "0"));
        tv_count.setText("" + mCount);
        // 获取商品数据库的帮助器对象
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        // 打开商品数据库的读连接
        mGoodsHelper.openReadLink();
        // 获取购物车数据库的帮助器对象
        mCartHelper = CartDBHelper.getInstance(this, 1);
        // 打开购物车数据库的写连接
        mCartHelper.openWriteLink();
        // 展示商品列表
        showGoods();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 关闭商品数据库的数据库连接
        mGoodsHelper.closeLink();
        // 关闭购物车数据库的数据库连接
        mCartHelper.closeLink();
    }

    private LayoutParams mFullParams, mHalfParams;

    private void showGoods() {
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
        mFullParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // mHalfParams这个布局参数的宽度与其它布局平均分
        mHalfParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        // 给mHalfParams设置四周的空白距离
        mHalfParams.setMargins(Utils.dip2px(ShoppingChannelActivity.this, 2), Utils.dip2px(ShoppingChannelActivity.this, 2), Utils.dip2px(ShoppingChannelActivity.this, 2), Utils.dip2px(ShoppingChannelActivity.this, 2));


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
                            TextView tv_name = new TextView(ShoppingChannelActivity.this);
                            tv_name.setLayoutParams(mFullParams);
                            tv_name.setGravity(Gravity.CENTER);
                            tv_name.setText("" + info.getMaterialName());
                            tv_name.setTextColor(Color.BLACK);
                            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                            ll_goods.addView(tv_name);
                            // 添加商品小图
                            ImageView iv_thumb = new ImageView(ShoppingChannelActivity.this);
                            iv_thumb.setLayoutParams(new LayoutParams(
                                    LayoutParams.MATCH_PARENT, Utils.dip2px(ShoppingChannelActivity.this, 150)));
                            iv_thumb.setScaleType(ScaleType.FIT_CENTER);
//                            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
                            GetImageByUrl getImageByUrl = new GetImageByUrl();
                            if (info.getImg() != "") {
                                getImageByUrl.setImage(iv_thumb, info.getImg());
                            }
                            iv_thumb.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
//                                    intent.putExtra("goods_id", info.rowid);
                                    startActivity(intent);
                                }
                            });
                            ll_goods.addView(iv_thumb);
                            // 添加商品价格
                            LinearLayout ll_bottom = newLinearLayout(LinearLayout.HORIZONTAL, 0);
                            TextView tv_price = new TextView(ShoppingChannelActivity.this);
                            tv_price.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
                            tv_price.setGravity(Gravity.CENTER);
                            tv_price.setText("" + info.getMaterialCode());
                            tv_price.setTextColor(Color.RED);
                            tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            ll_bottom.addView(tv_price);
                            // 添加购物车按钮
                            Button btn_add = new Button(ShoppingChannelActivity.this);
                            btn_add.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3));
                            btn_add.setGravity(Gravity.CENTER);
                            btn_add.setText("开门");
                            btn_add.setTextColor(Color.BLACK);
                            btn_add.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            btn_add.setOnClickListener(new OnClickListener() {
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
                                    Toast.makeText(ShoppingChannelActivity.this,
                                            "已添加2222部" + info.getMaterialId() + "到购物车", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ShoppingChannelActivity.this,
                                            "已添加一部" + userId + "到购物车", Toast.LENGTH_SHORT).show();
//                                    async_post_test(clientRequest);
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

        client_get_test(handler);

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

    private void client_get_test(final android.os.Handler mh) {
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
        params.put("name", "");
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
                    Toast.makeText(ShoppingChannelActivity.this, "" + str, Toast.LENGTH_SHORT).show();

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
