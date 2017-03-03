package com.bpbatam.enterprise.service;

import android.app.Activity;
import android.app.Dialog;
import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.LoginActivity;
import com.bpbatam.enterprise.model.AuthUser;
import com.bpbatam.enterprise.model.Device_ID;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 3/2/2017.
 */

public class ServiceAutoLogOut extends IntentService {

    Device_ID deviceId;
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.bpbatam.enterprise.MainMenuActivity";
    public ServiceAutoLogOut() {
        super("ServiceAutoLogOut");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        String USER = "";
        Log.w("Start Service"," Cek");
        AuthUser authUser = AppController.getInstance().getSessionManager().getUserProfile();

        if (authUser != null){
            if (authUser.data !=null){
                if (authUser.data.size() > 0){
                    for (AuthUser.Datum dat : authUser.data){
                        USER = dat.user_id;
                    }
                    Log.w("USER",USER);
                    try {
                        AppConstant.HASHID = AppController.getInstance().getHashId(USER);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    Log.w("Start Service"," Cek");
                    try{
                        Device_ID param = new Device_ID(AppConstant.HASHID, USER , AppConstant.REQID);
                        Call<Device_ID> call = NetworkManager.getNetworkService().getDeviceID(param);
                        call.enqueue(new Callback<Device_ID>() {
                            @Override
                            public void onResponse(Call<Device_ID> call, Response<Device_ID> response) {
                                int code = response.code();
                                Log.w("Respone Code", String.valueOf(code));
                                if (code == 200){
                                    deviceId = response.body();
                                    if(deviceId.code.equals("00")){
                                        for (Device_ID.Datum dat : deviceId.data){
                                            if (dat.device_id.equals("-") || dat.device_id.equals(AppConstant.IMEI)){
                                                Log.w("Status"," Sama");
                                            }else{
                                                Log.w("Status"," Berbeda");
                                                publishResults("", Activity.RESULT_OK);
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Device_ID> call, Throwable t) {

                            }
                        });
                    }catch (Exception e){

                    }
                }
            }

        }


    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }




}
