package com.bpbatam.enterprise.persuratan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.disposisi_folder_pribadi_umum;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_dalamproses;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_permohonan;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_pribadi_umum;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_riwayat;
import com.bpbatam.enterprise.persuratan.persuratan_folder_pribadi_umum;
import com.bpbatam.enterprise.persuratan.persuratan_status_surat;

/**
 * Created by setia.n on 11/14/2016.
 */

public class frag_persuratan_menu extends Fragment {

    TextView txtLabel, txtPermohonan, txtDraft, txtFolder, txtStatusSurat;

    FrameLayout frameLayout;
    RelativeLayout lyPermohonan, lyDikembalikan, lySimpan, lyFolder;
    //ImageView imgPilih, imgBack;
    Fragment fragment;
    Toolbar toolbar;
    View line1, line2, line3, line4;
    int iPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_persuratan, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        InitControl(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        line1.setBackgroundResource( R.color.colorBar );
        line2.setBackgroundResource( R.color.grey_s );
        line3.setBackgroundResource( R.color.grey_s );
        line4.setBackgroundResource( R.color.grey_s );

        iPosition = 1;
        //imgPilih.setVisibility(View.GONE);
        fragment = null;
        fragment = new frag_persuratan_pribadi_umum();

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
        line3 = (View)v.findViewById(R.id.line6);
        line4 = (View)v.findViewById(R.id.line7);
        /*imgBack = (ImageView)v.findViewById(R.id.img_back);
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
        imgPilih = (ImageView)v.findViewById(R.id.imgPilih);*/
        txtLabel = (TextView)v.findViewById(R.id.textLabel);
        txtPermohonan = (TextView)v.findViewById(R.id.text_permohonan);

        txtDraft = (TextView)v.findViewById(R.id.text_dikembalikan);
        txtStatusSurat = (TextView)v.findViewById(R.id.text_simpan);
        txtFolder = (TextView)v.findViewById(R.id.text_folder);
        frameLayout = (FrameLayout)v.findViewById(R.id.frame_content);
        lyPermohonan = (RelativeLayout)v.findViewById(R.id.layout_permohonan);
        //lyDalamSurat = (RelativeLayout)v.findViewById(R.id.layout_dalamsurat);
        lyDikembalikan = (RelativeLayout)v.findViewById(R.id.layout_dikembalikan);
        lySimpan = (RelativeLayout)v.findViewById(R.id.layout_simpan);
        lyFolder = (RelativeLayout)v.findViewById(R.id.layout_folder);


        txtLabel.setText("PERSURATAN");

        txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
        //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtDraft.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtStatusSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtFolder.setTextColor(getActivity().getResources().getColor(R.color.black));

        lyPermohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.black));
                //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDraft.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtStatusSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                line1.setBackgroundResource( R.color.grey_s );
                line2.setBackgroundResource( R.color.grey_s );
                line3.setBackgroundResource( R.color.colorBar );
                line4.setBackgroundResource( R.color.grey_s );
                iPosition = 3;
                AppConstant.ACTIVITY_FROM = "PERMOHONAN (01/08)";
                fragment = null;
                fragment = new frag_persuratan_permohonan();

                //imgPilih.setVisibility(View.GONE);
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        /*lyDalamSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtDraft.setTextColor(getActivity().getResources().getColor(R.color.grey));
                //txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "DALAM PROSES (01/08)";
                //imgPilih.setVisibility(View.GONE);
                fragment = null;
                fragment = new frag_persuratan_dalam_proses();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });*/

        lyDikembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDraft.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtStatusSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                line1.setBackgroundResource( R.color.grey_s );
                line2.setBackgroundResource( R.color.colorBar );
                line3.setBackgroundResource( R.color.grey_s );
                line4.setBackgroundResource( R.color.grey_s );
                iPosition = 2;
                AppConstant.ACTIVITY_FROM = "DRAFT (01/08)";
                fragment = null;
                fragment = new frag_persuratan_draft();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        lySimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDraft.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtStatusSurat.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                iPosition = 4;
                AppConstant.ACTIVITY_FROM = "Simpan (01/08)";
                line1.setBackgroundResource( R.color.grey_s );
                line2.setBackgroundResource( R.color.grey_s );
                line3.setBackgroundResource( R.color.grey_s );
                line4.setBackgroundResource( R.color.colorBar );


                fragment = null;
                fragment = new frag_persuratan_dalamproses_dikembalikan();
                //imgPilih.setVisibility(View.VISIBLE);
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
                //txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDraft.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtStatusSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.black));
                iPosition = 1;
                line1.setBackgroundResource( R.color.colorBar );
                line2.setBackgroundResource( R.color.grey_s );
                line3.setBackgroundResource( R.color.grey_s );
                line4.setBackgroundResource( R.color.grey_s );


                AppConstant.ACTIVITY_FROM = "FOLDER (01/08)";
                //imgPilih.setVisibility(View.VISIBLE);
                fragment = null;
                fragment = new frag_persuratan_pribadi_umum();

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
        Intent mIntent = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }else if (id == android.R.id.home) {
            getActivity().finish();
            return true;
        }else if (id == R.id.action_menu) {

            return true;
        }else if (id == R.id.item1) {
            switch (iPosition){
                case 1:
                    mIntent= new Intent(getActivity(), persuratan_folder_pribadi_umum.class);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    mIntent= new Intent(getActivity(), persuratan_status_surat.class);
                    break;
            }

            if (mIntent != null){
                mIntent.putExtra("ID",AppConstant.PILIH_PESAN);
                startActivity(mIntent);
            }

        }else if (id == R.id.item2) {
            switch (iPosition){
                case 1:
                    mIntent= new Intent(getActivity(), persuratan_folder_pribadi_umum.class);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    mIntent= new Intent(getActivity(), persuratan_status_surat.class);
                    break;
            }
            if (mIntent != null){
                mIntent.putExtra("ID",AppConstant.SEMUA_PESAN);
                startActivity(mIntent);
            }
        }



        return super.onOptionsItemSelected(item);
    }

}
