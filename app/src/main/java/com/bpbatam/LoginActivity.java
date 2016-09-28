package com.bpbatam;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bpbatam.enterprise.MainActivity;
import com.bpbatam.enterprise.R;

import java.io.File;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText txtUser,
        txtPassword;

    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

