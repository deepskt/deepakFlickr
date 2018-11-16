package com.deepak.androidflickr;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.deepak.androidflickr.manager.DBManager;
import com.deepak.androidflickr.util.Tracer;

import java.io.IOException;

public class FlickrApplication extends MultiDexApplication {
    private static final String TAG = Config.logger + FlickrApplication.class.getSimpleName();
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Tracer.debug(TAG," onCreate "+" ");
        sAppContext = getApplicationContext();
        DBManager.init();
    }

    public static Context getsAppContext(){
        return sAppContext;
    }
}
