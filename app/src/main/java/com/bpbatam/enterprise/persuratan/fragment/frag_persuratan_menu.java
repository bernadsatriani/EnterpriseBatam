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
import com.bpbatam.enterprise.model.Persuratan_Folder;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.persuratan_folder_draft;
import com.bpbatam.enterprise.persuratan.persuratan_folder_pribadi_umum;
import com.bpbatam.enterprise.persuratan.persuratan_status_surat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    TextView notif1, notif3, notif4;
    Persuratan_Folder persuratanFolder;

    ImageView imgFolder, imgDraft, imgPermohonan, imgSurat;
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
        FillNotif();
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgFolder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.folder_icon));
        imgDraft.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.draft_inactive));
        imgPermohonan.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.permohonan_icon_inactive));
        imgSurat.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.status_surat_inactive));

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
        imgFolder = (ImageView)v.findViewById(R.id.img_folder);
        imgDraft = (ImageView)v.findViewById(R.id.img_draft);
        imgPermohonan = (ImageView)v.findViewById(R.id.img_permohonan);
        imgSurat = (ImageView)v.findViewById(R.id.img_surat);

        toolbar = (Toolbar)v.findViewById(R.id.tool_bar);
        notif1 = (TextView)v.findViewById(R.id.notif1);
        notif3 = (TextView)v.findViewById(R.id.notif3);
        notif4 = (TextView)v.findViewById(R.id.notif4);
        notif1.bringToFront();
        notif3.bringToFront();
        notif4.bringToFront();

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

                imgFolder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.folder_icon_inactive));
                imgDraft.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.draft_inactive));
                imgPermohonan.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.permohonan_icon));
                imgSurat.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.status_surat_inactive));

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
                imgFolder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.folder_icon_inactive));
                imgDraft.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.draft));
                imgPermohonan.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.permohonan_icon_inactive));
                imgSurat.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.status_surat_inactive));
                iPosition = 2;
                notif3.setVisibility(View.GONE);
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

                imgFolder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.folder_icon_inactive));
                imgDraft.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.draft_inactive));
                imgPermohonan.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.permohonan_icon_inactive));
                imgSurat.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.status_surat));
                iPosition = 4;
                notif4.setVisibility(View.GONE);
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
                imgFolder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.folder_icon));
                imgDraft.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.draft_inactive));
                imgPermohonan.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.permohonan_icon_inactive));
                imgSurat.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.status_surat_inactive));
                iPosition = 1;
                notif1.setVisibility(View.GONE);
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
/*        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);*/
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
                    mIntent= new Intent(getActivity(), persuratan_folder_draft.class);
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
                    mIntent= new Intent(getActivity(), persuratan_folder_draft.class);
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

    void FillNotif(){
        try{
            Persuratan_Folder params = new Persuratan_Folder(AppConstant.HASHID , AppConstant.USER, AppConstant.REQID);
            Call<Persuratan_Folder> call = NetworkManager.getNetworkService(getActivity()).getMailFolder(params);
            call.enqueue(new Callback<Persuratan_Folder>() {
                @Override
                public void onResponse(Call<Persuratan_Folder> call, Response<Persuratan_Folder> response) {
                    int code = response.code();
                    persuratanFolder = response.body();
                    if (persuratanFolder.code.equals("00")){
                        int iPRM  = 0, iFUM = 0;
                        int iDPR  = 0, iDKM = 0;
                        for (Persuratan_Folder.Datum dat : persuratanFolder.data) {
                            if (dat.folder_code.equals("FPR")) {
                                iPRM = dat.unread_count;

                            }

                            if (dat.folder_code.equals("FUM")) {
                                iFUM = dat.unread_count;
                            }

                            if (dat.folder_code.equals("DPR")) {
                                iDPR = dat.unread_count;
                            }

                            if (dat.folder_code.equals("DKM")) {
                                iDKM = dat.unread_count;
                            }

                            if (dat.folder_code.equals("PRM")) {
                                if (dat.unread_count > 0){
                                    notif3.setVisibility(View.VISIBLE);
                                    notif3.setText(String.valueOf(dat.unread_count));
                                }else{
                                    notif3.setVisibility(View.GONE);
                                }
                            }
                        }

                        if ((iPRM+iFUM) > 0){
                            notif1.setVisibility(View.VISIBLE);
                            notif1.setText(String.valueOf(iPRM+iFUM));
                        }else{
                            notif1.setVisibility(View.GONE);
                        }

                        if ((iDPR+iDKM) > 0){
                            notif4.setVisibility(View.VISIBLE);
                            notif4.setText(String.valueOf(iDPR+iDKM));
                        }else{
                            notif4.setVisibility(View.GONE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Folder> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }
}
