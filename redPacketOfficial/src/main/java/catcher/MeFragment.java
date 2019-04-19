package catcher;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2019/2/14.
 */

public class MeFragment extends Fragment {

    public ImageView head;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,null);

        head = (ImageView) view.findViewById(R.id.headImage);

        head.setBackground(getResources().getDrawable(R.drawable.blankhead));

        head.setOnClickListener(new View.OnClickListener() {

            int count = 1;
            public void onClick(View v) {
                if(count%2==1) {
                    head.setBackground(getResources().getDrawable(R.drawable.kevin));
                }else {
                    head.setBackground(getResources().getDrawable(R.drawable.blankhead));
                }
                count++;
            }
        });
        return view;
    }

}
