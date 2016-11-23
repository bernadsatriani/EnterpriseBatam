package com.bpbatam.enterprise;

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

import com.bpbatam.enterprise.adapter.ViewPagerMenu;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_pribadi_umum;
import com.bpbatam.enterprise.fragment.frag_menu;
import com.bpbatam.enterprise.fragment.frag_profile;
import com.bpbatam.enterprise.fragment.fragment_menu;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        InitControl();
        fragment = null;
        fragment = new fragment_menu();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }
    }

    void InitControl(){
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
}
