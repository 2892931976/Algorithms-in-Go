package com.example.storage;

import com.example.storage.bean.UserInfo;
import com.example.storage.database.UserDBHelper;
import com.example.storage.util.DateUtil;
import com.example.storage.util.ViewUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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

/**
 * Created by ouyangshen on 2017/10/1.
 */
@SuppressLint("DefaultLocale")
public class LoginSQLiteActivity extends AppCompatActivity implements OnClickListener, OnFocusChangeListener {
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
    private UserDBHelper mHelper; // 声明一个用户数据库帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sqlite);
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        rg_login.setOnCheckedChangeListener(new RadioListener());
        ck_remember.setOnCheckedChangeListener(new CheckListener());
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        et_password.addTextChangedListener(new HideTextWatcher(et_password));
        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        initTypeSpinner();
        // 给密码编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        et_password.setOnFocusChangeListener(this);
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

    private String[] typeArray = {"个人用户", "公司用户"};
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
                ViewUtil.hideOneInputMethod(LoginSQLiteActivity.this, mView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (v.getId() == R.id.btn_forget) { // 点击了“忘记密码”按钮
            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) { // 选择了密码方式校验，此时要跳到找回密码页面
                Intent intent = new Intent(this, LoginForgetActivity.class);
                // 携带手机号码跳转到找回密码页面
                intent.putExtra("phone", phone);
                startActivityForResult(intent, mRequestCode);
            } else if (rb_verifycode.isChecked()) { // 选择了验证码方式校验，此时要生成六位随机数字验证码
                // 生成六位随机数字的验证码
                mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                // 弹出提醒对话框，提示用户六位验证码数字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else if (v.getId() == R.id.btn_login) { // 点击了“登录”按钮
            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) { // 密码方式校验
                if (!et_password.getText().toString().equals(mPassword)) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                } else { // 密码校验通过
                    loginSuccess(); // 提示用户登录成功
                }
            } else if (rb_verifycode.isChecked()) { // 验证码方式校验
                if (!et_password.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else { // 验证码校验通过
                    loginSuccess(); // 提示用户登录成功
                }
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        mHelper = UserDBHelper.getInstance(this, 2);
        // 恢复页面，则打开数据库连接
        mHelper.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        mHelper.closeLink();
    }

    // 校验通过，登录成功
    private void loginSuccess() {
        String desc = String.format("您的手机号码是%s，类型是%s。恭喜你通过登录验证，点击“确定”按钮返回上个页面",
                et_phone.getText().toString(), typeArray[mType]);
        // 弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog alert = builder.create();
        alert.show();
        // 如果勾选了“记住密码”，则把手机号码和密码保存为数据库的用户表记录
        if (bRemember) {
            // 创建一个用户信息实体类
            UserInfo info = new UserInfo();
            info.phone = et_phone.getText().toString();
            info.password = et_password.getText().toString();
            info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
            // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
            mHelper.insert(info);
        }
    }

    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    // 为什么光标进入密码框事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String phone = et_phone.getText().toString();
        // 判断是否是密码编辑框发生焦点变化
        if (v.getId() == R.id.et_password) {
            // 用户已输入手机号码，且密码框获得焦点
            if (phone.length() > 0 && hasFocus) {
                // 根据手机号码到数据库中查询用户记录
                UserInfo info = mHelper.queryByPhone(phone);
                if (info != null) {
                    // 找到用户记录，则自动在密码框中填写该用户的密码
                    et_password.setText(info.password);
                }
            }
        }
    }

}
