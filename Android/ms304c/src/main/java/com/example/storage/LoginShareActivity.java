package com.example.storage;

import com.example.storage.bean.user.ResponseInfo;
import com.example.storage.bean.user.UserInfo;
import com.example.storage.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by ouyangshen on 2017/10/1.
 */
@SuppressLint("DefaultLocale")
public class LoginShareActivity extends AppCompatActivity implements OnClickListener {
    protected static final int OK = 0;
    private RadioGroup rg_login;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private EditText et_phone;
    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;

    private int mRequestCode = 0; // 跳转页面时的请求代码
    private int mType = 0; // 用户类型
    private boolean bRemember = false; // 是否记住密码
    private String mPassword = "111111"; // 默认密码
    private String mVerifyCode; // 验证码

    private SharedPreferences mShared; // 声明一个共享参数对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_share);
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
//        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
//        btn_forget = findViewById(R.id.btn_forget);
//        ck_remember = findViewById(R.id.ck_remember);
        rg_login.setOnCheckedChangeListener(new RadioListener());
//        ck_remember.setOnCheckedChangeListener(new CheckListener());
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        et_password.addTextChangedListener(new HideTextWatcher(et_password));
//        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        initTypeSpinner();
        // 从share.xml中获取共享参数对象
        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
        // 获取共享参数中保存的手机号码
        String phone = mShared.getString("phone", "");
        // 获取共享参数中保存的密码
        String password = mShared.getString("password", "");
        int userId = mShared.getInt("userInfo", 0);

        et_phone.setText(phone); // 给手机号码编辑框填写上次保存的手机号
        et_password.setText(password); // 给密码编辑框填写上次保存的密码
    }

    // 初始化用户类型的下拉框
    private void initTypeSpinner() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, typeArray);
        typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_type = findViewById(R.id.sp_type);
        sp_type.setPrompt("请选择用户类型");
        sp_type.setAdapter(typeAdapter);
        sp_type.setSelection(mType);
        sp_type.setOnItemSelectedListener(new TypeSelectedListener());
    }

    //    private String[] typeArray = {"个人用户", "公司用户"};
    private String[] typeArray = {"个人用户"};

    // 定义用户类型的选择监听器
    class TypeSelectedListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            mType = arg2;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    // 定义登录方式的单选监听器
    private class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) {
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                ck_remember.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_verifycode) {
                tv_password.setText("　验证码：");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                ck_remember.setVisibility(View.INVISIBLE);
            }
        }
    }

    // 定义是否记住密码的勾选监听器
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.ck_remember) {
                bRemember = isChecked;
            }
        }
    }

    // 定义编辑框的文本变化监听器
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        private CharSequence mStr;

        public HideTextWatcher(EditText v) {
            super();
            mView = v;
            mMaxLength = ViewUtil.getMaxLength(v);
        }

        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }

        // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(mStr))
                return;
            // 手机号码输入达到11位，或者密码/验证码输入达到6位，都关闭输入法软键盘
            if ((mStr.length() == 11 && mMaxLength == 11) ||
                    (mStr.length() == 6 && mMaxLength == 6)) {
                ViewUtil.hideOneInputMethod(LoginShareActivity.this, mView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        String passwd = et_password.getText().toString();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(phone);
        userInfo.setPasswd(passwd);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:


//                        // 查询商品数据库中的所有商品记录
////                        ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
//                        ArrayList<MaterialteInfo> goodsList = new ArrayList<MaterialteInfo>();
//
////                        Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
////                        String str = msg.obj.toString();
////                        String str =  "{'errorCode':500,'errorMsg':'illeagle params'}";
//
                        String str = msg.getData().getString("mmmm");//接受msg传递过来的参数
//
//                        Gson gson = new Gson();
                        Gson gson = new GsonBuilder().create();
                        ResponseInfo uinfo = gson.fromJson(str, ResponseInfo.class);

                        if (uinfo.getCode() == 200) {
                            UserInfo data = uinfo.getData();
                            Toast.makeText(LoginShareActivity.this, "=====" + data.getId() + "====", Toast.LENGTH_SHORT).show();
//                            UserInfo user = uinfo.getData().getData();
//                            Toast.makeText(LoginShareActivity.this, ""+user.getId(), Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = mShared.edit(); // 获得编辑器的对象
                            editor.putInt("userInfo", data.getId()); // 添加名叫phone的手机号码
                            editor.commit(); // 提交编辑器中的修改
                            loginSuccess(); // 提示用户登录成功
                        } else {
                            Toast.makeText(LoginShareActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
//                        ArrayList<MaterialteInfo> role = rinfo.getData().getData();
////                        String str2 =  role.getName();
//                        Toast.makeText(LoginShareActivity.this, "OK:" + str, Toast.LENGTH_SHORT).show();
////                        GoodsInfo ginfo = new GoodsInfo();
////                        ginfo.setName(""+role.getName());
////                        ginfo.setDesc("魅族 PRO6S 4GB+64GB 全网通公开版 星空黑 移动联通电信4G手机");
////                        ginfo.setPrice(90000);
//                        for (int j = 0; j < role.size(); j++) {
//                            final MaterialteInfo ginfo = role.get(j);
//                            goodsList.add(ginfo);
//                        }
//                        Toast.makeText(ShoppingChannelActivity.this, ""+goodsArray.size(), Toast.LENGTH_SHORT).show();

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

        async_post_login(handler, userInfo);

//        String phone = et_phone.getText().toString();
//        if (v.getId() == R.id.btn_forget) { // 点击了“忘记密码”按钮
//            if (phone.length() < 11) { // 手机号码不足11位
//                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (rb_password.isChecked()) { // 选择了密码方式校验，此时要跳到找回密码页面
//                Intent intent = new Intent(this, LoginForgetActivity.class);
//                // 携带手机号码跳转到找回密码页面
//                intent.putExtra("phone", phone);
//                startActivityForResult(intent, mRequestCode);
//            } else if (rb_verifycode.isChecked()) { // 选择了验证码方式校验，此时要生成六位随机数字验证码
//                // 生成六位随机数字的验证码
//                mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
//                // 弹出提醒对话框，提示用户六位验证码数字
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("请记住验证码");
//                builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
//                builder.setPositiveButton("好的", null);
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        } else if (v.getId() == R.id.btn_login) { // 点击了“登录”按钮
//            if (phone.length() < 11) { // 手机号码不足11位
//                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (rb_password.isChecked()) { // 密码方式校验
//                if (!et_password.getText().toString().equals(mPassword)) {
//                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
//                } else { // 密码校验通过
//                    loginSuccess(); // 提示用户登录成功
//                }
//            } else if (rb_verifycode.isChecked()) { // 验证码方式校验
//                if (!et_password.getText().toString().equals(mVerifyCode)) {
//                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
//                } else { // 验证码校验通过
//                    loginSuccess(); // 提示用户登录成功
//                }
//            }
//        }
    }

    // 从后一个页面携带参数返回当前页面时触发
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCode && data != null) {
            // 用户密码已改为新密码，故更新密码变量
            mPassword = data.getStringExtra("new_password");
        }
    }

    // 从修改密码页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }

    // 校验通过，登录成功
    private void loginSuccess() {
        Intent intent = new Intent(LoginShareActivity.this, ShoppingChannelActivity.class);
//                    intent.putExtra("goods_id", info.rowid);
        startActivity(intent);
//        String desc = String.format("您的手机号码是%s，类型是%s。恭喜你通过登录验证，点击“确定”按钮返回上个页面",
//                et_phone.getText().toString(), typeArray[mType]);
//        // 弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
//        builder.setMessage(desc);
//        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.setNegativeButton("我再看看", null);
        AlertDialog alert = builder.create();
        alert.show();
        // 如果勾选了“记住密码”，则把手机号码和密码都保存到共享参数中
        if (bRemember) {
//            SharedPreferences.Editor editor = mShared.edit(); // 获得编辑器的对象
//            editor.putString("phone", et_phone.getText().toString()); // 添加名叫phone的手机号码
//            editor.putString("password", et_password.getText().toString()); // 添加名叫password的密码
//            editor.commit(); // 提交编辑器中的修改
        }
    }

    private void async_post_login(final android.os.Handler mh, UserInfo userInfo) {
//        String username = et_username.getText().toString();
//        String password = et_password.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();

        Gson gson = new Gson();
        String jsonObject = gson.toJson(userInfo);
        try {
            ByteArrayEntity entity = new ByteArrayEntity(jsonObject.getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getApplicationContext(), "http://192.168.1.183:8080/v1/account/login", entity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // TODO Auto-generated method stub
//            Message msg = new Message();
//            msg.what = OK;
//            msg.obj = new String(responseBody);
//            mh.sendMessage(msg);
                    String str = new String(responseBody);
//                    Toast.makeText(LoginShareActivity.this, "async:" + str, Toast.LENGTH_SHORT).show();
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("mmmm", str);  //往Bundle中存放数据
                    msg.what = OK;
                    msg.setData(bundle);//mes利用Bundle传递数据
                    mh.sendMessage(msg);
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
