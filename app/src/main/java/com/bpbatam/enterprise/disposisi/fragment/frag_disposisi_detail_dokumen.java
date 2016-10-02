package com.bpbatam.enterprise.disposisi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpbatam.enterprise.R;

/**
 * Created by User on 10/1/2016.
 */
public class frag_disposisi_detail_dokumen extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        setHasOptionsMenu(true);
        return view;
    }
}


