package com.bpbatam;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.enterprise.MainActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.AuthUser;
import com.bpbatam.enterprise.model.UpdateDeviceId;
import com.bpbatam.enterprise.model.net.NetworkManager;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText txtUser,
        txtPassword;

    Button btnLogin;

    AuthUser authUser;
    UpdateDeviceId updateDeviceId;

    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);

        InitControl();
        InitFolder();

    }

    void InitControl(){
        txtUser = (EditText)findViewById(R.id.edt_username);
        txtPassword = (EditText)findViewById(R.id.edt_password);
        btnLogin = (Button)findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();*/

                ValidasiLogin();
            }
        });
    }

    void ValidasiLogin(){
        progress = ProgressDialog.show(this, "Information",
                "Checking data", true);

        String sPassword = "";
        try {
            AppConstant.USER = txtUser.getText().toString().trim();
            AppConstant.PASSWORD = txtPassword.getText().toString().trim();
            sPassword = AppController.getInstance().md5(AppConstant.PASSWORD);
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        JSONObject params = new JSONObject();
        try {
            params.put("hashid", AppConstant.HASHID);
            params.put("userid",AppConstant.USER );
            params.put("pass",sPassword);
            params.put("reqid",AppConstant.REQID);
            params.put("device_id","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AuthUser param = new AuthUser(AppConstant.HASHID, AppConstant.USER , sPassword, AppConstant.REQID);

        try{
            Call<AuthUser> call = NetworkManager.getNetworkService(this).loginUser(param);
            call.enqueue(new Callback<AuthUser>() {
                @Override
                public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                    int code = response.code();
                    if (code == 200){
                        progress.dismiss();
                        authUser = response.body();
                        AppController.getInstance().getSessionManager().setUserAccount(authUser);

                        if (authUser.code.equals("95")){
                            CustomeDialog();
                        }else{
                            fUpdateDeviceID();
                            Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        progress.dismiss();
                    }


                }

                @Override
                public void onFailure(Call<AuthUser> call, Throwable t) {
                    progress.dismiss();
                    String a = t.getMessage();
                    a = a;
                }
            });
        }catch (Exception e){
            progress.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    void fUpdateDeviceID(){
        try {
            AppConstant.USER = txtUser.getText().toString().trim();
            AppConstant.PASSWORD = txtPassword.getText().toString().trim();
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        UpdateDeviceId params = new UpdateDeviceId(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, AppConstant.IMEI);

        try{
            Call<UpdateDeviceId> call = NetworkManager.getNetworkService(this).updateDeviceID(params);
            call.enqueue(new Callback<UpdateDeviceId>() {
                @Override
                public void onResponse(Call<UpdateDeviceId> call, Response<UpdateDeviceId> response) {
                    int code = response.code();
                    if (code == 200){
                        updateDeviceId = response.body();
                    }
                }

                @Override
                public void onFailure(Call<UpdateDeviceId> call, Throwable t) {
                    String a = t.getMessage();
                    a = a;
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //dialog.setTitle("Title...");

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void InitFolder(){

        File folder;
        try{
            String sFolder = Environment.getExternalStorageDirectory().toString();

            folder = new File(sFolder + "/Batam");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Batam/Enterprise");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Batam/Enterprise/Download");
            if (!folder.exists()) folder.mkdirs();
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }
}

