package com.bpbatam.enterprise.disposisi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.ViewPagerMenu;
import com.bpbatam.enterprise.disposisi.adapter.ViewPagerDIsposisiMenu;
import com.bpbatam.enterprise.disposisi.disposisi_folder_pribadi_umum;

import ui.QuickAction.ActionItem;
import ui.QuickAction.QuickAction;

/**
 * Created by setia.n on 11/14/2016.
 */

public class frag_disposisi_menu extends Fragment {
    TextView txtLabel,  txtRiwayat, txtFolder;

    FrameLayout frameLayout;
    RelativeLayout lyRiwayat, lyFolder;
    Fragment fragment;
    View line1, line2;
    Toolbar toolbar;
    String statusPesan;

    ImageView imgMenu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_disposisi, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        InitControl(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = null;
        fragment = new frag_disposisi_pribadi_umum();

        line1.setBackgroundResource( R.color.colorBar );
        line2.setBackgroundResource( R.color.grey_s );

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content1, fragment);
            fragmentTransaction.commit();
        }
    }



    void InitControl(View v){
        toolbar = (Toolbar)v.findViewById(R.id.tool_bar);
        line1 = (View)v.findViewById(R.id.line4);
        line2 = (View)v.findViewById(R.id.line5);
        /*imgMenu = (ImageView)v.findViewById(R.id.img_menu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickAction.show(view);
            }
        });*/

        txtLabel = (TextView)v.findViewById(R.id.textLabel);
        //txtPermohonan = (TextView)v.findViewById(R.id.text_permohonan);
        //txtDalamSurat = (TextView)v.findViewById(R.id.text_dalamsurat);
        txtRiwayat = (TextView)v.findViewById(R.id.text_riwayat);
        txtFolder = (TextView)v.findViewById(R.id.text_folder);
        frameLayout = (FrameLayout)v.findViewById(R.id.frame_content);
        //lyPermohonan = (RelativeLayout)v.findViewById(R.id.layout_permohonan);
        //lyDalamSurat = (RelativeLayout)v.findViewById(R.id.layout_dalamsurat);
        lyRiwayat = (RelativeLayout)v.findViewById(R.id.layout_riwayat);
        lyFolder = (RelativeLayout)v.findViewById(R.id.layout_folder);


        txtLabel.setText("DISPOSISI");

        //txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.black));
        //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));

        /*lyPermohonan.setOnClickListener(new View.OnClickListener() {
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
        });*/

        /*lyDalamSurat.setOnClickListener(new View.OnClickListener() {
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
        });*/

        lyRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "RIWAYAT (01/08)";
                fragment = null;
                fragment = new frag_disposisi_riwayat();

                line1.setBackgroundResource( R.color.grey_s );
                line2.setBackgroundResource( R.color.colorBar );
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
                //txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtRiwayat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.black));
                AppConstant.ACTIVITY_FROM = "FOLDER (01/08)";
                fragment = null;
                fragment = new frag_disposisi_pribadi_umum();
                line1.setBackgroundResource( R.color.colorBar );
                line2.setBackgroundResource( R.color.grey_s );
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_search2, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv =(SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setQueryHint("Search Surat...");
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
        }else if (id == R.id.action_menu) {

            return true;
        }else if (id == R.id.item1) {
            Intent mIntent = new Intent(getActivity(), disposisi_folder_pribadi_umum.class);
            mIntent.putExtra("ID",AppConstant.PILIH_PESAN);
            startActivity(mIntent);
        }else if (id == R.id.item2) {
            Intent mIntent = new Intent(getActivity(), disposisi_folder_pribadi_umum.class);
            mIntent.putExtra("ID",AppConstant.SEMUA_PESAN);
            startActivity(mIntent);
        }



        return super.onOptionsItemSelected(item);
    }

}
