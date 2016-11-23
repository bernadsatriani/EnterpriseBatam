package com.bpbatam.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.adapter.AdapterNotification;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.persuratan.persuratan_folder_pribadi_umum;
import com.bpbatam.enterprise.persuratan.persuratan_status_surat;

import java.util.ArrayList;

/**
 * Created by User on 9/26/2016.
 */
public class NotificationActivity extends Fragment {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    TextView txtLabel;
    private Toolbar mToolbar;
    ImageView imgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_notification, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillGrid();
    }


    void InitControl(View v) {
        mToolbar = (Toolbar) v.findViewById(R.id.tool_bar);
        txtLabel= (TextView)v.findViewById(R.id.textLabel);
        imgBack = (ImageView)v.findViewById(R.id.img_back);
        txtLabel.setText("NOTIFIKASI");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    void FillGrid(){
        AryListData = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Vincent" );
            listData.setAtr2("Contoh judul pesan");
            listData.setAtr3("Anda telah mendapatkan tembusan (CC)");
            AryListData.add(listData);
        }

        mAdapter = new AdapterNotification(getActivity(), AryListData);
        mRecyclerView.setAdapter(mAdapter);
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
        int id = item.getItemId();

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                getActivity().finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
