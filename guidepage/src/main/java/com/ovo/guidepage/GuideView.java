package com.ovo.guidepage;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 引导按钮
 */
public class GuideView {
    private Activity activity;
    private int imageViewId;
    private int count = 0;
    private ImageView iv_view;

    private FrameLayout frameLayout;

    public GuideView(Activity activity, int imageViewId){
        this.activity = activity;
        this.imageViewId = imageViewId;
        setGuideView();
    }

    /**
     * 设置引导层
     */
    public void setGuideView(){
        if (activity == null)return;
        //获取跟视图
        View view = activity.findViewById(android.R.id.content);
        if (view == null)return;
        frameLayout = (FrameLayout) view;
        FrameLayout.LayoutParams layoutParams = new FrameLayout
                .LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        iv_view = new ImageView(activity);
        iv_view.setImageResource(imageViewId);
        iv_view.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_view.setLayoutParams(layoutParams);
        iv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count){
                    case 0:
                        iv_view.setImageResource(R.mipmap.ic_launcher_round);
                        break;
                    case 1:
                        iv_view.setImageResource(imageViewId);
                        break;
                    case 2:
                        iv_view.setImageResource(R.mipmap.ic_launcher_round);
                        break;
                }
                count ++;
            }
        });
        frameLayout.addView(iv_view);
    }

    public void removeView(){
        if (frameLayout == null || iv_view == null)return;
        frameLayout.removeView(iv_view);
    }
}
