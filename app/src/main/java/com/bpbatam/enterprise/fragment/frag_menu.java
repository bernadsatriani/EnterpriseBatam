package com.bpbatam.enterprise.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.ExpandableListAdapter;
import com.bpbatam.enterprise.bbs.bbs_menu;
import com.bpbatam.enterprise.disposisi.disposisi_menu;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.persuratan.persuratan_menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 9/16/2016.
 */
public class frag_menu extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, ArrayList<ListData>> listDataChild;

    ImageView menuBBS, menuPersuratan, menuDisposisi;
    TextView txtLabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    void InitControl(View v){
        txtLabel = (TextView)v.findViewById(R.id.textLabel);
        txtLabel.setText("Menu");
        menuBBS = (ImageView)v.findViewById(R.id.menu_bbs);
        menuPersuratan = (ImageView)v.findViewById(R.id.menu_persuratan);
        menuDisposisi = (ImageView)v.findViewById(R.id.menu_disposisi);

        menuBBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), bbs_menu.class);
                startActivity(mIntent);
            }
        });

        menuPersuratan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), persuratan_menu.class);
                startActivity(mIntent);
            }
        });

        menuDisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), disposisi_menu.class);
                startActivity(mIntent);
            }
        });

    }
}
