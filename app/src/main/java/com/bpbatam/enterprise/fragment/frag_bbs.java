package com.bpbatam.enterprise.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.BBS_add_berita;
import com.bpbatam.enterprise.bbs.adapter.ViewPagerAdapterBBS;
import com.bpbatam.enterprise.bbs.fragment.Frag_bbs_daftar_pesanan;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_Insert;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerAdapterPersuratanPermohonan;

import org.json.JSONObject;
import org.w3c.dom.Text;

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
    TextView textLabel, text_tulis_pesan;
    //LinearLayout layout_button_kembali;
    String sFile_Size, sFile_Type, sBBS_id, sFile_Path;
    Uri uri;
    Toolbar toolbar;
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
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }*/



    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        /*inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv =(SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setQueryHint("Search Berita...");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");

                //FillGrid(newText);
                return false;
            }
        });
*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }else if (id == android.R.id.home) {
            getActivity().finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    void InitControl(View v){
        toolbar = (Toolbar)v.findViewById(R.id.tool_bar);

        text_tulis_pesan  = (TextView)v.findViewById(R.id.text_tulis_pesan);
        textLabel = (TextView)v.findViewById(R.id.textLabel);
        txtJudul = (EditText)v.findViewById(R.id.text_judul);
        txtIsi = (EditText)v.findViewById(R.id.text_isi);
        //layout_button_kembali = (LinearLayout) v.findViewById(R.id.layout_button_kembali);

        textLabel.setText("BBS");

        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (TabLayout)v.findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterBBS(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setSelectedTabIndicatorColor(getActivity().getResources().getColor(R.color.colorBar));
        tabs.setupWithViewPager(pager);

        text_tulis_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), BBS_add_berita.class);
                startActivity(mIntent);
            }
        });
    }



}
