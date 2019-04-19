package catcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2019/2/14.
 */

public class IndexFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载index.xml
        View view = inflater.inflate(R.layout.fragment_index,null);

        final TextView tv = (TextView)view.findViewById(R.id.indexText);

        final long timeInterval = 500;
        /**
         * 重点：
         * 因为Android的限定，仅能有一个UI主线程执行所有有关界面显示的操作，
         * 但是其他新生成的线程是不能轻易获取到各种UI控件的（如TextView，Button）
         * 因此只能同onClickListener和Handler之类特殊的类来通过信息交互来调用
         *
         * 比如此处的handler中，handler对象的通过接收thread线程中的数据来实现
         * 操控TextView的业务逻辑。thread线程实现定时调用，传去标记时间的参数。
         */
        final android.os.Handler handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1%7 == 0){
                    tv.setText("为您抢钱中");
                }else {
                    tv.setText(tv.getText()+"。");
                }
                super.handleMessage(msg);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                while (true){   //这是个死循环,需要在activity消失时,把flag设为false,结束循环
                    Message msg=new Message();
                    msg.arg1=count;
                    handler.sendMessage(msg);
                    count++;
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return view;
    }




}
