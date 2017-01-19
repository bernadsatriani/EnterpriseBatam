package com.bpbatam.enterprise.disposisi.fragment;

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
import com.bpbatam.enterprise.model.Disposisi_Detail;
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
public class frag_disposisi_detail_dokumen extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    Disposisi_Detail persuratanDetail;

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
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Disposisi_Detail params = new Disposisi_Detail(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                Integer.toString(AppConstant.EMAIL_ID));

        try{
            Call<Disposisi_Detail> call = NetworkManager.getNetworkService(getActivity()).getDisposisiDetail(params);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {
                    int code = response.code();
                    persuratanDetail = response.body();
                    if (code == 200){
                        if (persuratanDetail.code.equals("00")){
                            for(Disposisi_Detail.Datum dat : persuratanDetail.data){
                                listData = new ListData();
                                listData.setAtr1("Isi Disposisi");
                                listData.setAtr2(dat.dispo_category);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Judul");
                                listData.setAtr2(dat.dispositior);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Penulis");
                                listData.setAtr2(dat.create_name);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Tanggal");
                                listData.setAtr2(dat.mail_date);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Retensi");
                                listData.setAtr2(dat.retensi);
                                AryListData.add(listData);
                            }

                            mAdapter = new AdapterDisposisiDistribusi(getContext(), AryListData);
                            mRecyclerView.setAdapter(mAdapter);

                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

}


