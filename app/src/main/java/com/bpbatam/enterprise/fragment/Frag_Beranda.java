package com.bpbatam.enterprise.fragment;

import android.graphics.Color;
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
import android.widget.LinearLayout;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.ViewPagerAdapterHome;

/**
 * Created by User on 10/3/2016.
 */
public class Frag_Beranda extends Fragment {

    ImageView imgBSS, imgPERSURATAN, imgDISPOSISI;
    FrameLayout frameBBS, framePersuratan;
    LinearLayout layoutDisposisiDetail;

    boolean stsBBS, stsPERSURATAN, stsDISPOSISI;

    //DISPOSISI----------------------------------------
    CharSequence Titles[]={"Detail","Riwayat"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterHome adapter;
    TabLayout tabs;
    Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        stsBBS = true;
        stsPERSURATAN = false;
        stsDISPOSISI = false;

        InitControl(view);
        FillFormBBS();
        FillFormPersuratan();
        FillFormDisposisi();
    }

    void InitControl(View v){
        layoutDisposisiDetail = (LinearLayout)v.findViewById(R.id.layout_disposisi_detail);
        imgBSS = (ImageView)v.findViewById(R.id.imgplus_bbs);
        imgPERSURATAN = (ImageView)v.findViewById(R.id.imgplus_persuratan);
        imgDISPOSISI = (ImageView)v.findViewById(R.id.imgplus_disposisi);
        frameBBS = (FrameLayout)v.findViewById(R.id.frame_bbs);
        framePersuratan = (FrameLayout)v.findViewById(R.id.frame_persuratan);
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (TabLayout)v.findViewById(R.id.tabs);

        adapter =  new ViewPagerAdapterHome(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        //tabs.setSelectedTabIndicatorColor(v.getResources().getColor(R.color.black));

        tabs.setupWithViewPager(pager);

        imgBSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stsBBS){
                    stsBBS = false;
                } else
                    stsBBS = true;

                FillFormBBS();
            }
        });

        imgPERSURATAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stsPERSURATAN){
                    stsPERSURATAN = false;
                }else
                    stsPERSURATAN = true;

                FillFormPersuratan();
            }
        });

        imgDISPOSISI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stsDISPOSISI){
                    stsDISPOSISI = false;
                }else
                    stsDISPOSISI = true;

                FillFormDisposisi();
            }
        });
    }

    void FillFormBBS(){
        if (stsBBS){
            imgBSS.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.minus));
            frameBBS.setVisibility(View.VISIBLE);
        }else{
            imgBSS.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus));
            frameBBS.setVisibility(View.GONE);
        }

        fragment = null;
        fragment = new Frag_Beranda_BBS();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_bbs, fragment);
            fragmentTransaction.commit();
        }
    }

    void FillFormPersuratan(){
        if (stsPERSURATAN){
            imgPERSURATAN.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.minus));
            framePersuratan.setVisibility(View.VISIBLE);
        }else{
            imgPERSURATAN.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus));
            framePersuratan.setVisibility(View.GONE);
        }

        fragment = null;
        fragment = new Frag_Beranda_PERSURATAN();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_persuratan, fragment);
            fragmentTransaction.commit();
        }
    }

    void FillFormDisposisi(){
        if (stsDISPOSISI){
            imgDISPOSISI.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.minus));
            layoutDisposisiDetail.setVisibility(View.VISIBLE);
        }else{
            imgDISPOSISI.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus));
            layoutDisposisiDetail.setVisibility(View.GONE);
        }


    }

}
