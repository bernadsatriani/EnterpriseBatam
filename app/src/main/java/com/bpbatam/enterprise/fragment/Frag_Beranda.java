package com.bpbatam.enterprise.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bpbatam.enterprise.FragmentLifecycle;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.ViewPagerAdapterHome;

/**
 * Created by User on 10/3/2016.
 */
public class Frag_Beranda extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ImageView imgBSS, imgPERSURATAN, imgDISPOSISI;
    FrameLayout frameBBS, framePersuratan, frmDisposisi;
    LinearLayout layoutDisposisiDetail, layoutBBS, layoutPERSURATAN, layoutDISPOSISI;

    boolean stsBBS, stsPERSURATAN, stsDISPOSISI;

    //DISPOSISI----------------------------------------
    CharSequence Titles[]={"Detail","Riwayat"};
    int Numboftabs =2;

    Fragment fragment;
    SwipeRefreshLayout swipeRefreshLayout;

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
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        layoutBBS = (LinearLayout)v.findViewById(R.id.layout_bbs);
        layoutPERSURATAN = (LinearLayout)v.findViewById(R.id.layout_persuratan);
        layoutDISPOSISI = (LinearLayout)v.findViewById(R.id.layout_disposisi);

        imgBSS = (ImageView)v.findViewById(R.id.imgplus_bbs);
        imgPERSURATAN = (ImageView)v.findViewById(R.id.imgplus_persuratan);
        imgDISPOSISI = (ImageView)v.findViewById(R.id.imgplus_disposisi);
        frameBBS = (FrameLayout)v.findViewById(R.id.frame_bbs);
        framePersuratan = (FrameLayout)v.findViewById(R.id.frame_persuratan);
        frmDisposisi = (FrameLayout)v.findViewById(R.id.frame_diposisi);

        layoutBBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stsBBS){
                    stsBBS = false;
                } else
                    stsBBS = true;

                FillFormBBS();
            }
        });

        layoutPERSURATAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stsPERSURATAN){
                    stsPERSURATAN = false;
                }else
                    stsPERSURATAN = true;

                FillFormPersuratan();
            }
        });

        layoutDISPOSISI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stsDISPOSISI){
                    stsDISPOSISI = false;
                }else
                    stsDISPOSISI = true;

                FillFormDisposisi();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorSearch),
                getActivity().getResources().getColor(R.color.Green),
                getActivity().getResources().getColor(R.color.b7_orange),
                getActivity().getResources().getColor(R.color.red));
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
            frmDisposisi.setVisibility(View.VISIBLE);
        }else{
            imgDISPOSISI.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plus));
            frmDisposisi.setVisibility(View.GONE);
        }

        fragment = null;
        fragment = new Frag_Beranda_DISPOSISI_riwayat();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_diposisi, fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onRefresh() {
        stsBBS = true;
        stsPERSURATAN = false;
        stsDISPOSISI = false;

        FillFormBBS();
        FillFormPersuratan();
        FillFormDisposisi();
        swipeRefreshLayout.setRefreshing(false);
    }
}
