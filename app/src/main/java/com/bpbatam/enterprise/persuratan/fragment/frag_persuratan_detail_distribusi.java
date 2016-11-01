package com.bpbatam.enterprise.persuratan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiDistribusi;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 10/1/2016.
 */
public class frag_persuratan_detail_distribusi extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillGrid(view);
    }

    void InitControl(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    void FillGrid(View v) {
        AryListData = new ArrayList<>();

        listData = new ListData();
        listData.setAtr1("Departemen (Posisi)");
        listData.setAtr2("Finance");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Nama");
        listData.setAtr2("");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Terkirim");
        listData.setAtr2("20 Sep 2016 | 15:30");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Dibaca");
        listData.setAtr2("");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Distributor");
        listData.setAtr2("Unit IP");
        AryListData.add(listData);

        mAdapter = new AdapterDisposisiDistribusi(getContext(), AryListData);
        mRecyclerView.setAdapter(mAdapter);
    }

}
