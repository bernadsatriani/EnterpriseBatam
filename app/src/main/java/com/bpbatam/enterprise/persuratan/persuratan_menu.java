package com.bpbatam.enterprise.persuratan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.NotificationActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.ViewPagerDIsposisiMenu;
import com.bpbatam.enterprise.fragment.frag_menu;
import com.bpbatam.enterprise.fragment.frag_profile;
import com.bpbatam.enterprise.fragment.fragment_menu;
import com.bpbatam.enterprise.model.Disposisi_Notifikasi;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerPersuratanMenu;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_menu;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/14/2016.
 */

public class persuratan_menu extends AppCompatActivity {
    Fragment fragment;
    private int[] tabIcons = {
            R.drawable.home_icon,
            R.drawable.my_profile_icon,
            R.drawable.notif_icon
    };

    RelativeLayout lyHome, lyProfile, lyNotif, btnNotif;
    View view1,view2,view3;
    Disposisi_Notifikasi disposisiNotifikasi;
    TextView badge_notification_1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        InitControl();
        getNotofikasi();
        fragment = null;
        fragment = new frag_persuratan_menu();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }
    }

    void InitControl(){
        badge_notification_1 = (TextView)findViewById(R.id.badge_notification_1);
        btnNotif = (RelativeLayout)findViewById(R.id.btnNotif);
        lyHome = (RelativeLayout)findViewById(R.id.layoutHome);
        lyProfile = (RelativeLayout)findViewById(R.id.layoutProfile);
        lyNotif = (RelativeLayout)findViewById(R.id.layoutNotif);
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
                fragment = new frag_persuratan_menu();

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
}
