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
import com.bpbatam.AppController;
import com.bpbatam.enterprise.adapter.AdapterNotification;
import com.bpbatam.enterprise.disposisi.disposisi_lihat_surat;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.Disposisi_Notifikasi;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.persuratan_detail;
import com.bpbatam.enterprise.persuratan.persuratan_folder_pribadi_umum;
import com.bpbatam.enterprise.persuratan.persuratan_lihat_surat;
import com.bpbatam.enterprise.persuratan.persuratan_status_surat;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Disposisi_Notifikasi disposisiNotifikasi;
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
        /*AryListData = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Vincent" );
            listData.setAtr2("Contoh judul pesan");
            listData.setAtr3("Anda telah mendapatkan tembusan (CC)");
            AryListData.add(listData);
        }*/

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Disposisi_Notifikasi param = new Disposisi_Notifikasi(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID);

            Call<Disposisi_Notifikasi>call = NetworkManager.getNetworkService(getActivity()).getNotifikasi(param);
            call.enqueue(new Callback<Disposisi_Notifikasi>() {
                @Override
                public void onResponse(Call<Disposisi_Notifikasi> call, Response<Disposisi_Notifikasi> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiNotifikasi = response.body();
                        if (disposisiNotifikasi.code.equals("00")){
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Notifikasi> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }

    void FillAdapter(){
        mAdapter = new AdapterNotification(getActivity(), disposisiNotifikasi, new AdapterNotification.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(Disposisi_Notifikasi.Datum dat) {
                if (dat.notif_type.toUpperCase().equals("DISPOS")){
                    AppConstant.EMAIL_ID = dat.notif_id;
                    AppConstant.DISPO_ID = Integer.toString(dat.notif_id);
                    Intent intent = new Intent(getActivity(), disposisi_lihat_surat.class);
                    startActivity(intent);
                    UpdateDetail();
                }else{
                    AppConstant.EMAIL_ID = dat.notif_id;
                    Intent intent = new Intent(getActivity(), persuratan_lihat_surat.class);
                    startActivity(intent);
                    UpdateDetailMail();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    void UpdateDetail(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Disposisi_Detail params = new Disposisi_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Disposisi_Detail>call = NetworkManager.getNetworkService(getActivity()).getDisposisiDetail(params);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {

                }

                @Override
                public void onFailure(Call<Disposisi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void UpdateDetailMail(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Persuratan_Detail params = new Persuratan_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Persuratan_Detail> call = NetworkManager.getNetworkService(getActivity()).getMailDetail(params);
            call.enqueue(new Callback<Persuratan_Detail>() {
                @Override
                public void onResponse(Call<Persuratan_Detail> call, Response<Persuratan_Detail> response) {

                }

                @Override
                public void onFailure(Call<Persuratan_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
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
