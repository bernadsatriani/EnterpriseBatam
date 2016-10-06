package com.bpbatam;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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



    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                //hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getDateTime() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        AppConstant.REQID = dateFormat.format(date) + getTime();
        return AppConstant.REQID;
    }

    public String getTime() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String sHour = String.valueOf(hour);
        String sMinute= String.valueOf(minute);
        String sSecond = String.valueOf(second);

        if (sHour.length() < 2) sHour = "0" + sHour;
        if (sMinute.length() < 2) sMinute = "0" + sMinute;
        if (sSecond.length() < 2) sSecond = "0" + sSecond;

        return sHour + sMinute + sSecond;
    }

    public String getHasPassword(String Password) throws NoSuchAlgorithmException {
        return getSHA1(Password+"S.1bc29400");
    }

    public String getSHA1(String input)throws NoSuchAlgorithmException{

        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public String getHashId(String sUserId, String sPassword)throws NoSuchAlgorithmException{
        AppConstant.REQID = getDateTime();

        String sResult = getSHA1(getDateTime() + sUserId + "bc12b22e93149c175cc034f69b031f35"+getSHA1(sPassword+"S.1bc29400"));

        return sResult;
    }

}
