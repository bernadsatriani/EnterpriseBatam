package com.bpbatam;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bpbatam.enterprise.MainActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.AuthUser;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.net.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitControl();
        InitFolder();

        String sPassword = "";
        try {
            AppConstant.USER = "admin1";
            AppConstant.PASSWORD = "admin12345";
            sPassword = AppController.getInstance().getSHA1(AppConstant.PASSWORD);
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        JSONObject params = new JSONObject();
        try {
            params.put("hashid", AppConstant.HASHID);
            params.put("userid","admin1");
            params.put("pass",sPassword);
            params.put("reqid",AppConstant.REQID);
            params.put("device_id","");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AuthUser param = new AuthUser(AppConstant.HASHID, "admin1", sPassword, AppConstant.REQID);

        try{
            Call<AuthUser> call = NetworkManager.getNetworkService(this).loginUser(param);
            call.enqueue(new Callback<AuthUser>() {
                @Override
                public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                    int code = response.code();
                    authUser = response.body();

                    AppController.getInstance().getSessionManager().setUserAccount(authUser);
                }

                @Override
                public void onFailure(Call<AuthUser> call, Throwable t) {
                    String a = t.getMessage();
                    a = a;
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }



        try {
            sPassword = AppController.getInstance().getSHA1("admin12345");
            AppConstant.HASHID = AppController.getInstance().getHashId("admin1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    void InitControl(){
        txtUser = (EditText)findViewById(R.id.edt_username);
        txtPassword = (EditText)findViewById(R.id.edt_password);
        btnLogin = (Button)findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void InitFolder(){

        File folder;
        try{
            String sFolder = Environment.getExternalStorageDirectory().toString();

            folder = new File(sFolder + "/Batam");
            if (!folder.exists()) folder.mkdirs();

            folder = new File(sFolder+ "/Batam/Download");
            if (!folder.exists()) folder.mkdirs();
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }
}

