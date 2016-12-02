package com.bpbatam.enterprise.disposisi.fragment;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiDistribusi;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Distribusi_Detail;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 10/1/2016.
 */
public class frag_disposisi_detail_distribusi extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    Persuratan_Distribusi_Detail persuratanDistribusiDetail;
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

        try{
            Persuratan_Distribusi_Detail param = new Persuratan_Distribusi_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    "dispo",
                    Integer.toString(AppConstant.EMAIL_ID));

            Call<Persuratan_Distribusi_Detail> call = NetworkManager.getNetworkService(getActivity()).getDistribusiDetail(param);
            call.enqueue(new Callback<Persuratan_Distribusi_Detail>() {
                @Override
                public void onResponse(Call<Persuratan_Distribusi_Detail> call, Response<Persuratan_Distribusi_Detail> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanDistribusiDetail = response.body();
                        if (persuratanDistribusiDetail.code.equals("00")){
                            for(Persuratan_Distribusi_Detail.Datum dat : persuratanDistribusiDetail.data){
                                listData = new ListData();
                                listData.setAtr1("Departemen (Posisi)");
                                listData.setAtr2(dat.department);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Nama");
                                listData.setAtr2(dat.user_name);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Terkirim");
                                listData.setAtr2(dat.receive_time);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Dibaca");
                                listData.setAtr2(dat.read_time);
                                AryListData.add(listData);

                                listData = new ListData();
                                listData.setAtr1("Distributor");
                                listData.setAtr2(dat.dist_name);
                                AryListData.add(listData);
                            }


                            mAdapter = new AdapterDisposisiDistribusi(getContext(), AryListData);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Distribusi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

}
