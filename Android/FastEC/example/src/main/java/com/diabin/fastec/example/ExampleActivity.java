package com.diabin.fastec.example;

//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.ActionBar;
//import android.widget.Toast;

import com.flj.latte.activities.ProxyActivity;
//import com.flj.latte.app.Latte;
import com.flj.latte.delegates.LatteDelegate;
//import com.flj.latte.ec.main.EcBottomDelegate;
//import com.flj.latte.ec.sign.ISignListener;
//import com.flj.latte.ec.sign.SignInDelegate;
//import com.flj.latte.ui.launcher.ILauncherListener;
//import com.flj.latte.ui.launcher.OnLauncherFinishTag;
//
//import cn.jpush.android.api.JPushInterface;
//import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

}
