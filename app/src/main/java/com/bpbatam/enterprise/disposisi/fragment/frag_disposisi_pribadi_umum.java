package com.bpbatam.enterprise.disposisi.fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.PDFViewActivity_Distribusi;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiUmum;
import com.bpbatam.enterprise.fragment.Frag_Beranda_BBS;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class frag_disposisi_pribadi_umum extends Fragment{
    ImageView imgPribadi, imgUmum;
    boolean stsPribadi, stsUmum;
    FrameLayout framePribadi, frameUmum;
    Fragment fragment;

    LinearLayout layoutPribadi, layoutUmum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disposisi_pribadi_umum, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        InitControl(view);
        stsPribadi = true;
        stsUmum = false;
        FillFormPribadi();
        FillFormUmum();
    }

    void InitControl(View v){
        imgPribadi = (ImageView)v.findViewById(R.id.imgplus_pribadi);
        imgUmum = (ImageView)v.findViewById(R.id.imgplus_umum);
        layoutPribadi = (LinearLayout)v.findViewById(R.id.layout_pribadi);
        layoutUmum = (LinearLayout)v.findViewById(R.id.layout_umum);
        framePribadi = (FrameLayout)v.findViewById(R.id.frame_pribadi);
        frameUmum = (FrameLayout)v.findViewById(R.id.frame_umum);
        layoutPribadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stsPribadi){
                    stsPribadi = false;
                }else{
                    stsPribadi = true;
                }

                FillFormPribadi();
            }
        });

        layoutUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stsUmum){
                    stsUmum = false;
                }else{
                    stsUmum = true;
                }

                FillFormUmum();

            }
        });
    }

    void FillFormPribadi(){
        if (stsPribadi){
            imgPribadi.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.expand_arrow));
            framePribadi.setVisibility(View.VISIBLE);
        }else{
            imgPribadi.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.collapse_arrow));
            framePribadi.setVisibility(View.GONE);
        }

        fragment = null;
        fragment = new frag_disposisi_pribadi_new();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_pribadi, fragment);
            fragmentTransaction.commit();
        }
    }

    void FillFormUmum(){
        if (stsUmum){
            imgUmum.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.expand_arrow));
            frameUmum.setVisibility(View.VISIBLE);
        }else{
            imgUmum.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.collapse_arrow));
            frameUmum.setVisibility(View.GONE);
        }

        fragment = null;
        fragment = new frag_disposisi_umum_new();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_umum, fragment);
            fragmentTransaction.commit();
        }
    }

}
