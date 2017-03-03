package com.bpbatam.enterprise;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.LoginActivity;
import com.bpbatam.enterprise.disposisi.disposisi_lihat_surat;
import com.bpbatam.enterprise.fragment.frag_profile;
import com.bpbatam.enterprise.fragment.fragment_menu;
import com.bpbatam.enterprise.model.Disposisi_Notifikasi;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.persuratan_lihat_surat;
import com.bpbatam.enterprise.service.AlarmReceiver;
import com.bpbatam.enterprise.service.ServiceAutoLogOut;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/13/2016.
 */

public class MainMenuActivity extends AppCompatActivity{

    Fragment fragment;
    private int[] tabIcons = {
            R.drawable.home_icon,
            R.drawable.my_profile_icon,
            R.drawable.notif_icon
    };

    RelativeLayout lyHome, lyProfile, lyNotif, btnNotif;
    View view1,view2,view3;
    Disposisi_Notifikasi disposisiNotifikasi;
    TextView badge_notification_1;

    String doc_id = "", type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        InitControl();
        StartService();
        getNotofikasi();

        try {
            doc_id = getIntent().getExtras().getString("doc_id");
        }catch (Exception e){
            doc_id = "0";
        }

        try {
            type = getIntent().getExtras().getString("type");
        }catch (Exception e){
            type = "";
        }


        fragment = null;
        fragment = new fragment_menu();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }
        Intent intent;

        switch (type.toUpperCase()){
            case "DISPOS":
                AppConstant.EMAIL_ID = Integer.parseInt(doc_id);
                AppConstant.DISPO_ID = doc_id;
                intent = new Intent(getBaseContext(), disposisi_lihat_surat.class);
                startActivity(intent);
                break;
            case "MAIL":
                AppConstant.EMAIL_ID = Integer.parseInt(doc_id);
                intent = new Intent(getBaseContext(), persuratan_lihat_surat.class);
                startActivity(intent);
                break;
        }
    }

    void InitControl(){
        badge_notification_1 = (TextView)findViewById(R.id.badge_notification_1);
        lyHome = (RelativeLayout)findViewById(R.id.layoutHome);
        lyProfile = (RelativeLayout)findViewById(R.id.layoutProfile);
        lyNotif = (RelativeLayout)findViewById(R.id.layoutNotif);
        btnNotif = (RelativeLayout)findViewById(R.id.btnNotif);
        view1 = (View)findViewById(R.id.view1);
        view2 = (View)findViewById(R.id.view2);
        view3 = (View)findViewById(R.id.view3);

        view1.setBackgroundResource( R.color.white );
        view2.setBackgroundResource( R.color.colorBar );
        view3.setBackgroundResource( R.color.colorBar );

        lyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.white );
                view2.setBackgroundResource( R.color.colorBar );
                view3.setBackgroundResource( R.color.colorBar );

                fragment = null;
                fragment = new fragment_menu();

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        lyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.colorBar );
                view2.setBackgroundResource( R.color.white );
                view3.setBackgroundResource( R.color.colorBar );
                fragment = null;
                fragment = new frag_profile();

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        lyNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.colorBar );
                view2.setBackgroundResource( R.color.colorBar );
                view3.setBackgroundResource( R.color.white );
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.colorBar );
                view2.setBackgroundResource( R.color.colorBar );
                view3.setBackgroundResource( R.color.white );
                badge_notification_1.setVisibility(View.GONE);
                fragment = null;
                fragment = new NotificationActivity();

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    void getNotofikasi(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Disposisi_Notifikasi param = new Disposisi_Notifikasi(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID);

            Call<Disposisi_Notifikasi> call = NetworkManager.getNetworkService(this).getNotifikasi(param);
            call.enqueue(new Callback<Disposisi_Notifikasi>() {
                @Override
                public void onResponse(Call<Disposisi_Notifikasi> call, Response<Disposisi_Notifikasi> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiNotifikasi = response.body();
                        if (disposisiNotifikasi.code.equals("00")){
                            if (disposisiNotifikasi.data.size() > 0){
                                badge_notification_1.setVisibility(View.VISIBLE);
                                badge_notification_1.setText(String.valueOf(disposisiNotifikasi.data.size()));
                            }else{
                                badge_notification_1.setText("0");
                                badge_notification_1.setVisibility(View.GONE);
                            }

                        }else{
                            badge_notification_1.setText("0");
                            badge_notification_1.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Notifikasi> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ServiceAutoLogOut.NOTIFICATION));
        if (AppConstant.EXIT) finish();
    }

    void StartService(){
        //ServiceAutoLogout==================================================
        Intent myIntent = new Intent(getBaseContext(),
                AlarmReceiver.class);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(getBaseContext(),
                222222, myIntent, 0);

        AlarmManager alarmManager
                = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        long interval = (10 * 60) * 1000; //
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), interval, pendingIntent);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(ServiceAutoLogOut.FILEPATH);
                int resultCode = bundle.getInt(ServiceAutoLogOut.RESULT);
                if (resultCode == RESULT_OK) {
                    CustomeDialog();
                }
            }
        }
    };


    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        txtDismis.setText("OK");
        txtDialog.setText("Anda telah logout");
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregisterReceiver(receiver);
                dialog.dismiss();

                finish();
                Intent mIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(mIntent);
            }
        });

        dialog.show();
    }

}
