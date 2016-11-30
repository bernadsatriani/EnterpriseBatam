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
import com.bpbatam.enterprise.adapter.AdapterCC;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiDistribusi;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiTertanda;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 10/1/2016.
 */
public class frag_disposisi_detail_tertanda extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    Disposisi_Detail disposisiDetail;
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
        //FillGrid(view);
        FillGridDisposisi();
    }

    void InitControl(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    void FillGridDisposisi() {

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Disposisi_Detail param = new Disposisi_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Disposisi_Detail> call = NetworkManager.getNetworkService(getActivity()).getDisposisiDetail(param);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiDetail = response.body();
                        int iIndex = 0;
                        AryListData = new ArrayList<>();
                        for(Disposisi_Detail.Datum dat : disposisiDetail.data){
                            listData = new ListData();
                            listData.setNama("");
                            listData.setJekel("");
                            listData.setAtr1("");
                            listData.setAtr2("");
                            listData.setAtr3("");
                            AryListData.add(listData);

                            iIndex += 1;
                        }
                        mAdapter = new AdapterDisposisiTertanda(getActivity(),AryListData);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }

    void FillGrid(View v) {
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
