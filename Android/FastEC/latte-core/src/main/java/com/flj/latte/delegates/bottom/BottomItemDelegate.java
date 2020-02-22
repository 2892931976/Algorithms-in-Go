package com.flj.latte.delegates.bottom;

import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.R;
import com.flj.latte.app.Latte;
import com.flj.latte.delegates.LatteDelegate;

/**
 * Created by 傅令杰
 */

public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener {
    // 再点一次退出程序时间设置
//    private static final long WAIT_TIME = 2000L;
//    private long TOUCH_TIME = 0;

    private static final int EXIT_TIME = 2000;
    private long mExitTime = 0;

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME){
                mExitTime = System.currentTimeMillis();
                Toast.makeText(getContext(), "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
            }else{
                _mActivity.finish();
                mExitTime=0;
            }
            return true;
        }
        return false;
    }

//    @Override
//    public boolean onBackPressedSupport() {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            _mActivity.finish();
//        } else {
//            TOUCH_TIME = System.currentTimeMillis();
//            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
}
