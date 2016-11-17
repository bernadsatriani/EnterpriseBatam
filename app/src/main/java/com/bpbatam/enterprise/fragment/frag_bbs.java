package com.bpbatam.enterprise.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.adapter.ViewPagerAdapterBBS;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_Insert;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerAdapterPersuratanPermohonan;

import org.json.JSONObject;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class frag_bbs extends Fragment {

    int CODE_FILE = 45;
    ImageView imgMenu;
    CharSequence Titles[]={"Daftar Pesan","Semua Pesan"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterBBS adapter;
    TabLayout tabs;


    SimpleAdapter adpGridView;

    BBS_CATEGORY bbs_category;
    BBS_Insert bbs_insert;
    String[] lstCategory;
    String sCategoryID = "";

    EditText txtJudul, txtIsi;
    LinearLayout layout_button_kembali;
    String sFile_Size, sFile_Type, sBBS_id, sFile_Path;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bbs, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);

    }

    void InitControl(View v){
        txtJudul = (EditText)v.findViewById(R.id.text_judul);
        txtIsi = (EditText)v.findViewById(R.id.text_isi);
        layout_button_kembali = (LinearLayout) v.findViewById(R.id.layout_button_kembali);


        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (TabLayout)v.findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterBBS(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setSelectedTabIndicatorColor(getActivity().getResources().getColor(R.color.colorSearch));
        tabs.setupWithViewPager(pager);
    }

}
