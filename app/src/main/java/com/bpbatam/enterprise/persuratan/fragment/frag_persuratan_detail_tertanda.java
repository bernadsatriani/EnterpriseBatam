package com.bpbatam.enterprise.persuratan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiDistribusi;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiTertanda;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 10/1/2016.
 */
public class frag_persuratan_detail_tertanda extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;
    Persuratan_Detail persuratanDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_tertanda, container, false);
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
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Persuratan_Detail params = new Persuratan_Detail(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                Integer.toString(AppConstant.EMAIL_ID));
        AryListData = new ArrayList<>();
        try{
            Call<Persuratan_Detail> call = NetworkManager.getNetworkService(getActivity()).getMailDetail(params);
            call.enqueue(new Callback<Persuratan_Detail>() {
                @Override
                public void onResponse(Call<Persuratan_Detail> call, Response<Persuratan_Detail> response) {
                    int code = response.code();
                    persuratanDetail = response.body();
                    if (code == 200){
                        if (persuratanDetail.code.equals("00")){
                            for(Persuratan_Detail.Datum datum : persuratanDetail.data){
                                for (Persuratan_Detail.ApprovalState dat: datum.approval_state ){
                                    listData = new ListData();
                                    listData.setNama(dat.deptartement);
                                    listData.setAlamat(dat.user_name);
                                    listData.setAtr1("Persetujuan");
                                    listData.setAtr2("Disetujui");
                                    listData.setAtr3(dat.approve_date);
                                    AryListData.add(listData);
                                    /* listData = new ListData();
                                    listData.setAtr1("Departemen");
                                    listData.setAtr2(dat.deptartement);
                                    listData.setAtr3("External");
                                    AryListData.add(listData);

                                    listData = new ListData();
                                    listData.setAtr1("Nama");
                                    listData.setAtr2(dat.user_name);
                                    listData.setAtr3("Unit IP");
                                    AryListData.add(listData);

                                    listData = new ListData();
                                    listData.setAtr1("Tipe Persetujuan");
                                    listData.setAtr2("Persetujuan");
                                    listData.setAtr3("Persetujuan");
                                    AryListData.add(listData);

                                    listData = new ListData();
                                    listData.setAtr1("Status Persetujuan");
                                    listData.setAtr2("Disetujui");
                                    listData.setAtr3("Ditolak");
                                    AryListData.add(listData);

                                    listData = new ListData();
                                    listData.setAtr1("Tanggal Persetujuan");
                                    listData.setAtr2(dat.approve_date);
                                    listData.setAtr3(dat.approve_date);
                                    AryListData.add(listData);*/
                                }

                            }

                            mAdapter = new AdapterDisposisiTertanda(getContext(), AryListData);
                            mRecyclerView.setAdapter(mAdapter);

                        }
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void FillGrid() {
        AryListData = new ArrayList<>();

        listData = new ListData();
        listData.setAtr1("Departemen");
        listData.setAtr2("Seksi Media dan Aplikasi (Staff)");
        listData.setAtr3("External");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Nama");
        listData.setAtr2("Nurul Yuniarti");
        listData.setAtr3("Unit IP");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Tipe Persetujuan");
        listData.setAtr2("Persetujuan");
        listData.setAtr3("Persetujuan");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Status Persetujuan");
        listData.setAtr2("Disetujui");
        listData.setAtr3("Ditolak");
        AryListData.add(listData);

        listData = new ListData();
        listData.setAtr1("Tanggal Persetujuan");
        listData.setAtr2("21 Sep 2016 17:53");
        listData.setAtr3("21 Sep 2016 17:53");
        AryListData.add(listData);

        mAdapter = new AdapterDisposisiTertanda(getContext(), AryListData);
        mRecyclerView.setAdapter(mAdapter);
    }

}
