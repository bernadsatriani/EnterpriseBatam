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
public class frag_persuratan_detail_dokumen extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    Persuratan_Detail persuratanDetail;

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

        Persuratan_Detail params = new Persuratan_Detail(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                Integer.toString(AppConstant.EMAIL_ID));

        try{
            Call<Persuratan_Detail> call = NetworkManager.getNetworkService(getActivity()).getMailDetail(params);
            call.enqueue(new Callback<Persuratan_Detail>() {
                @Override
                public void onResponse(Call<Persuratan_Detail> call, Response<Persuratan_Detail> response) {
                    int code = response.code();
                    persuratanDetail = response.body();
                    if (code == 200){
                        if (persuratanDetail.code.equals("00")){
                            for(Persuratan_Detail.Datum dat : persuratanDetail.data){
                                listData = new ListData();
                                listData.setAtr1("Ketagori");
                                listData.setAtr2(dat.category_name);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Judul");
                                listData.setAtr2(dat.title);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Penulis");
                                listData.setAtr2(dat.user_name);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Tanggal");
                                listData.setAtr2(dat.mail_date);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Retensi");
                                listData.setAtr2(dat.retency_name);
                                AryListData.add(listData);
                            }

                            mAdapter = new AdapterDisposisiDistribusi(getContext(), AryListData);
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

}


