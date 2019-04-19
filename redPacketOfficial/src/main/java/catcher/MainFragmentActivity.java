package com.tedu.pdapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Administrator on 2019/2/14.
 */

public class MainFragmentActivity extends FragmentActivity {

    private IndexFragment indexFragment;
    private MessageFragment messageFragment = new MessageFragment();
    private MeFragment meFragment = new MeFragment();

    //fragment与button对应的数组
    private Fragment[] fragments = new Fragment[3];
    private Button[] buttons = new Button[3];

    private int buttonIndex=0;
    private int fragmentIndex = 0;

    //重写有一个参数的方法
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载布局文件
        setContentView(R.layout.main_fragment);
        //管理fragment
        FragmentManager manager = getSupportFragmentManager();
        //使用事务
        FragmentTransaction transaction = manager.beginTransaction();
        //动作1:加载indexfragment
        indexFragment = new IndexFragment();
        int containerId = R.id.fragment_container;
        transaction.add(containerId,indexFragment);
        //动作2显示indexfragment
        transaction.show(indexFragment);
        //提交事务
        transaction.commit();

        //给fragments赋值

        fragments[0] = indexFragment;
        fragments[1] = messageFragment;
        fragments[2] = meFragment;
        //给buttons赋值
        buttons[0] = (Button)findViewById(R.id.btn_main_fragment_store);
        buttons[1] = (Button)findViewById(R.id.btn_main_fragment_message);
        buttons[2] = (Button)findViewById(R.id.btn_main_fragment_me);

        //给button添加事件监听
        for(Button b:buttons){
            b.setOnClickListener(new ButtonListener());
        }

        //让第一个按钮的状态设置为选中
        buttons[fragmentIndex].setSelected(true);

    }


    class ButtonListener implements View.OnClickListener{
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {

            //判断是第几个
            switch (v.getId()){
                case R.id.btn_main_fragment_store:
                    buttonIndex = 0;
                    break;
                case R.id.btn_main_fragment_message:
                    buttonIndex = 1;
                    break;
                case R.id.btn_main_fragment_me:
                    buttonIndex = 2;
                    break;
            }
            //判断点击的是否是当前
            if(fragmentIndex != buttonIndex){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //隐藏当前fragment
                Fragment tohide = fragments[fragmentIndex];
                transaction.hide(tohide);
                //开启目标fragment
                Fragment toshow = fragments[buttonIndex];
                //fragment可能以前添加过
                if(!toshow.isAdded()){
                    int containId = R.id.fragment_container;
                    transaction.add(containId,toshow);
                }

                if(toshow == meFragment && meFragment.head != null){
                    meFragment.head.setBackground(getResources().getDrawable(R.drawable.blankhead));
                }

                transaction.show(toshow);
                //提交事务
                transaction.commit();
                //让单机的按钮高亮显示
                buttons[fragmentIndex].setSelected(false);
                buttons[buttonIndex].setSelected(true);

                //改变当前显示的界面下标
                fragmentIndex = buttonIndex;
            }
        }
    }


}
