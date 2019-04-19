package com.tedu.pdapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isAccessibilitySettingsOn(this)){
            //如果辅助功能已经开启了，则直接准备3秒后跳转到fragment主界面
            ToLoginThread toLoginThread = new ToLoginThread();
            toLoginThread.start();
        }else{
            //如果辅助功能没有开启，则应该弹出对话框，随后跳转到设置界面
            popDialog();
        }

    }

    //弹出对话框
    public void popDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("开启辅助功能");
        builder.setMessage("想抢红包的小朋友需要开启一下辅助功能哦，我们马上跳转过去！（点击红包助手->点击开启->完成）");
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toSwitchAccessOn();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    /**
     * 使用内部类定义一个线程,实现跳转登录的操作
     */
    class ToLoginThread extends Thread{
        @Override
        public void run() {
            try{
                sleep(3000);
                //从mainActivity跳到指定activity
                Intent intent = new Intent(MainActivity.this,MainFragmentActivity.class);
                startActivity(intent);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 检验我们的软件的辅助功能是否被开启，如果没有被开启，
     * 则跳转到设置里进行开启。
     * @param mContext
     * @return
     */
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + TestService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.i("tag","accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e("tag", "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.i("tag", "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.i("tag", "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.i("tag", "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //打开辅助功能的方法
    private void toSwitchAccessOn(){
        Intent intent =  new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true) {
            if(isAccessibilitySettingsOn(this)){
                Log.v("open","辅助功能开启了！！");
                ToFgThread toFgThread = new ToFgThread();
                toFgThread.start();
                //此处千万要记得break停止循环，否则死循环死机！！
                break;
            };
        }
    }

    class ToFgThread extends Thread{
        @Override
        public void run() {
            try{
                        Intent fgintent = new Intent(getBaseContext(),MainFragmentActivity.class);
                        startActivity(fgintent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
