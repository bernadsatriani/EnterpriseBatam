package com.bpbatam.enterprise.bbs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.fragment.frag_bbs;

/**
 * Created by setia.n on 11/14/2016.
 */

public class bbs_menu extends AppCompatActivity {
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
        fragment = new frag_bbs();

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
        view2.setBackgroundResource( R.color.colorSelectButton );
        view3.setBackgroundResource( R.color.colorSelectButton );

        lyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.white );
                view2.setBackgroundResource( R.color.colorSelectButton );
                view3.setBackgroundResource( R.color.colorSelectButton );

                fragment = null;
                fragment = new frag_bbs();

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
                view1.setBackgroundResource( R.color.colorSelectButton );
                view2.setBackgroundResource( R.color.white );
                view3.setBackgroundResource( R.color.colorSelectButton );
            }
        });

        lyNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.colorSelectButton );
                view2.setBackgroundResource( R.color.colorSelectButton );
                view3.setBackgroundResource( R.color.white );
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setBackgroundResource( R.color.colorSelectButton );
                view2.setBackgroundResource( R.color.colorSelectButton );
                view3.setBackgroundResource( R.color.white );
            }
        });
    }

}
