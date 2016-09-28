package com.bpbatam;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 9/15/2016.
 */
public class AppController extends Application {
    private static AppController mInstance;
    private SessionManager sessionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sessionManager = new SessionManager(getApplicationContext());

    }

    public static Context getAppContext() {
        return mInstance;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String getStringData(Context ctx, String sData){
        String sResult = "";
        sResult = AppConstant.pref.getString(sData, AppConstant.EMPTY_STRING);
        return  sResult;
    }

    public void displayImage(Context context,String uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .into(imageView);

    }


    public void displayImagePicasso(Context context, String uri, ImageView imageView) {
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(uri)
                .into(imageView);
    }
}
