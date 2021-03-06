package com.diabin.fastec.example;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;

import com.facebook.stetho.Stetho;
import com.flj.latte.app.Latte;
import com.flj.latte.ec.database.DatabaseManager;
import com.flj.latte.ec.icon.FontEcModule;
import com.flj.latte.net.interceptor.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by ASUS on 2020/2/10.
 */

public class ExampleApp extends Application {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//                Latte.init(this)
//                .withIcon(new FontAwesomeModule())
//                .withIcon(new FontEcModule())
//                .withApiHost("")
//                .configure();
//    }

        @Override
    public void onCreate(){
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
//                .withApiHost("http://192.168.31.236/")http://192.168.43.189/sort_list.php
                .withApiHost("http://192.168.43.189/")
                .withInterceptor(new DebugInterceptor("test100000", R.raw.test))
                .withJavascriptInterface("latte")
                .configure();
            initStetho();

            DatabaseManager.getInstance().init(this);
    }

        private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
