package com.bpbatam.enterprise.persuratan.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerAdapterPersuratanPermohonan;

/**
 * Created by User on 9/19/2016.
 */
public class frag_persuratan_dikembalikan extends Fragment {

    ImageView imgMenu;
    CharSequence Titles[]={"Pribadi","Umum"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterPersuratanPermohonan adapter;
    TabLayout tabs;

    TextView txtLabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_persuratan_permohonan, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    void InitControl(View v){
        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        txtLabel = (TextView)v.findViewById(R.id.view2);
        txtLabel.setText("Dikembalikan");
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (TabLayout)v.findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterPersuratanPermohonan(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);
    }
}
