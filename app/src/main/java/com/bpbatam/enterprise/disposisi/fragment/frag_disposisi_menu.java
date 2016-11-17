package com.bpbatam.enterprise.disposisi.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.ViewPagerMenu;
import com.bpbatam.enterprise.disposisi.adapter.ViewPagerDIsposisiMenu;

/**
 * Created by setia.n on 11/14/2016.
 */

public class frag_disposisi_menu extends Fragment {

    TextView txtLabel, txtPermohonan, txtDalamSurat, txtRiwayat, txtFolder;

    FrameLayout frameLayout;
    RelativeLayout lyPermohonan, lyDalamSurat, lyRiwayat, lyFolder, lyMenu;
    Fragment fragment;
    ImageView imgPilih, imgBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_disposisi, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        InitControl(view);
        imgPilih.setVisibility(View.GONE);
        fragment = null;
        fragment = new frag_disposisi_permohonan();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content1, fragment);
            fragmentTransaction.commit();
        }
    }

    void InitControl(View v){
        imgPilih = (ImageView)v.findViewById(R.id.imgPilih);
        imgBack = (ImageView)v.findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        lyMenu = (RelativeLayout)v.findViewById(R.id.layout_menu);
        lyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        txtLabel = (TextView)v.findViewById(R.id.textLabel);
        txtPermohonan = (TextView)v.findViewById(R.id.text_permohonan);
        txtDalamSurat = (TextView)v.findViewById(R.id.text_dalamsurat);
        txtRiwayat = (TextView)v.findViewById(R.id.text_riwayat);
        txtFolder = (TextView)v.findViewById(R.id.text_folder);
        frameLayout = (FrameLayout)v.findViewById(R.id.frame_content);
        lyPermohonan = (RelativeLayout)v.findViewById(R.id.layout_permohonan);
        lyDalamSurat = (RelativeLayout)v.findViewById(R.id.layout_dalamsurat);
        lyRiwayat = (RelativeLayout)v.findViewById(R.id.layout_riwayat);
        lyFolder = (RelativeLayout)v.findViewById(R.id.layout_folder);


        txtLabel.setText("Disposisi");

        txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.black));
        txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));

        lyPermohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "PERMOHONAN (01/08)";
                imgPilih.setVisibility(View.GONE);
                fragment = null;
                fragment = new frag_disposisi_permohonan();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        lyDalamSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "DALAM PROSES (01/08)";
                imgPilih.setVisibility(View.GONE);
                fragment = null;
                fragment = new frag_disposisi_dalamproses();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        lyRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "RIWAYAT (01/08)";
                imgPilih.setVisibility(View.GONE);
                fragment = null;
                fragment = new frag_disposisi_riwayat();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        lyFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.black));
                AppConstant.ACTIVITY_FROM = "FOLDER (01/08)";
                imgPilih.setVisibility(View.VISIBLE);
                fragment = null;
                fragment = new frag_disposisi_pribadi_umum();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }
}
