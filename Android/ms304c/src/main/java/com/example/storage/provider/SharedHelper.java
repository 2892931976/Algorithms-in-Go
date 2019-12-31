package com.example.storage.provider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
/**
 * Created by Administrator on 2019/12/28.
 */

public class SharedHelper {

    private Context mContext;
    public static final String FILE_NAME3 = "mysp4";
    public SharedPreferences mShared;
    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }

    public SharedPreferences getUserSp(Context mContext) {
        if (mShared==null){
            mShared = mContext.getSharedPreferences(FILE_NAME3, Context.MODE_PRIVATE);
        }
        return mShared;
    }


    //定义一个保存数据的方法
//    public void save(String username, String passwd) {
//        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("username", username);
//        editor.putString("passwd", passwd);
//        editor.commit();
//        Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
//    }

    public void putInt(SharedPreferences sp, String key, int obj) {
//        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME3, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, obj);
        editor.commit();
                Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }

    public int getInt(SharedPreferences sp, String key, int defaultObj) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
//        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME3, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultObj);

    }


    //定义一个读取SP文件的方法
//    public Map<String, String> read() {
//        Map<String, String> data = new HashMap<String, String>();
//        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
//        data.put("username", sp.getString("username", ""));
//        data.put("passwd", sp.getString("passwd", ""));
//        return data;
//    }
}
