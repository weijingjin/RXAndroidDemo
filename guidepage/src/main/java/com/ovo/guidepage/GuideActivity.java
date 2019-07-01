package com.ovo.guidepage;

import android.app.Activity;
import android.os.Bundle;

/**
 * 设置引导页
 */
public class GuideActivity extends Activity {

    private GuideView guideView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        guideView = new GuideView(this, R.mipmap.ic_launcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        guideView.removeView();
    }
}
