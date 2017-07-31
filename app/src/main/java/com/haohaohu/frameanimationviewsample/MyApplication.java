package com.haohaohu.frameanimationviewsample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author haohao on 2017/7/31 18:44
 * @version v1.0
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
