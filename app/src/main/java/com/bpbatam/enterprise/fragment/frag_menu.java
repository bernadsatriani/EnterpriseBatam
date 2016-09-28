package com.bpbatam.enterprise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.ExpandableListAdapter;
import com.bpbatam.enterprise.model.ListData;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_row, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    void InitControl(View v){

    }
}
