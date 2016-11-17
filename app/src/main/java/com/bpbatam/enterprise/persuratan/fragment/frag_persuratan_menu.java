package com.bpbatam.enterprise.persuratan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_dalamproses;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_permohonan;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_pribadi_umum;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_riwayat;

/**
 * Created by setia.n on 11/14/2016.
 */

public class frag_persuratan_menu extends Fragment {

    TextView txtLabel, txtPermohonan, txtDalamSurat, txtDikembalikan,txtSimpan, txtFolder;

    FrameLayout frameLayout;
    RelativeLayout lyPermohonan, lyDalamSurat, lyDikembalikan, lySimpan, lyFolder, lyMenu;
    ImageView imgPilih, imgBack;
    Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_persuratan, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        InitControl(view);
        imgPilih.setVisibility(View.GONE);
        fragment = null;
        fragment = new frag_persuratan_permohonan();

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content1, fragment);
            fragmentTransaction.commit();
        }
    }

    void InitControl(View v){
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
        imgPilih = (ImageView)v.findViewById(R.id.imgPilih);
        txtLabel = (TextView)v.findViewById(R.id.textLabel);
        txtPermohonan = (TextView)v.findViewById(R.id.text_permohonan);
        txtDalamSurat = (TextView)v.findViewById(R.id.text_dalamsurat);
        txtDikembalikan = (TextView)v.findViewById(R.id.text_dikembalikan);
        txtSimpan = (TextView)v.findViewById(R.id.text_simpan);
        txtFolder = (TextView)v.findViewById(R.id.text_folder);
        frameLayout = (FrameLayout)v.findViewById(R.id.frame_content);
        lyPermohonan = (RelativeLayout)v.findViewById(R.id.layout_permohonan);
        lyDalamSurat = (RelativeLayout)v.findViewById(R.id.layout_dalamsurat);
        lyDikembalikan = (RelativeLayout)v.findViewById(R.id.layout_dikembalikan);
        lySimpan = (RelativeLayout)v.findViewById(R.id.layout_simpan);
        lyFolder = (RelativeLayout)v.findViewById(R.id.layout_folder);


        txtLabel.setText("Persuratan");

        txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.black));
        txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtDikembalikan.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));

        lyPermohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDikembalikan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "PERMOHONAN (01/08)";
                fragment = null;
                fragment = new frag_persuratan_permohonan();
                imgPilih.setVisibility(View.GONE);
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
                txtDikembalikan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "DALAM PROSES (01/08)";
                imgPilih.setVisibility(View.GONE);
                fragment = null;
                fragment = new frag_persuratan_dalam_proses();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_content1, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        lyDikembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPermohonan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDikembalikan.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "DIKEMBALIKAN (01/08)";
                fragment = null;
                fragment = new frag_persuratan_dikembalikan();

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
                txtDalamSurat.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtDikembalikan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.black));
                txtFolder.setTextColor(getActivity().getResources().getColor(R.color.grey));
                AppConstant.ACTIVITY_FROM = "Simpan (01/08)";
                fragment = null;
                fragment = new frag_persuratan_disimpan();
                imgPilih.setVisibility(View.VISIBLE);
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
                txtDikembalikan.setTextColor(getActivity().getResources().getColor(R.color.grey));
                txtSimpan.setTextColor(getActivity().getResources().getColor(R.color.grey));
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
