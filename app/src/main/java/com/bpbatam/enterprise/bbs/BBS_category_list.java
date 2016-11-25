package com.bpbatam.enterprise.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.adapter.AdapterKategory;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/18/2016.
 */

public class BBS_category_list extends AppCompatActivity {
    BBS_CATEGORY bbs_category;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    String[] lstCategory;

    RelativeLayout layout_ok;
    String sID, sDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        sID = "";
        sDescription = "";
        InitControl();
        FillList();
    }

    void InitControl(){
        layout_ok = (RelativeLayout)findViewById(R.id.layout_ok);
        layout_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle conData = new Bundle();
                conData.putString("KODE", sID);
                conData.putString("DES", sDescription);
                Intent intent = new Intent();
                intent.putExtras(conData);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    void FillList(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_CATEGORY bbs_categoryParams = new BBS_CATEGORY(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID);
            Call<BBS_CATEGORY> call = NetworkManager.getNetworkService(this).getBBS_Category(bbs_categoryParams);
            call.enqueue(new Callback<BBS_CATEGORY>() {
                @Override
                public void onResponse(Call<BBS_CATEGORY> call, Response<BBS_CATEGORY> response) {
                    int code = response.code();
                    if (code == 200){
                        bbs_category = response.body();
                        lstCategory = new String[9];

                        ArrayList<ListData> lisData = new ArrayList<ListData>();
                        ListData data = new ListData();

                        for(BBS_CATEGORY.Datum dat : bbs_category.data){
                            data = new ListData();
                            data.setAtr1(dat.code);
                            data.setAtr2(dat.description);
                            lisData.add(data);
                        }
                        /*data.setAtr1("QNA");
                        data.setAtr2(bbs_category.data.QNA);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("PDK");
                        data.setAtr2(bbs_category.data.PDK);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("DPU");
                        data.setAtr2(bbs_category.data.DPU);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("RUL");
                        data.setAtr2(bbs_category.data.RUL);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("KDS");
                        data.setAtr2(bbs_category.data.KDS);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("KSU");
                        data.setAtr2(bbs_category.data.KSU);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("INB");
                        data.setAtr2(bbs_category.data.INB);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("PGU");
                        data.setAtr2(bbs_category.data.PGU);
                        lisData.add(data);

                        data = new ListData();
                        data.setAtr1("PDB");
                        data.setAtr2(bbs_category.data.PDB);
                        lisData.add(data);
*/
                        FillAdapter(lisData);

                    }
                }

                @Override
                public void onFailure(Call<BBS_CATEGORY> call, Throwable t) {
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    void FillAdapter(ArrayList<ListData> lisData){
        mAdapter = new AdapterKategory(getBaseContext(), lisData, new AdapterKategory.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String sKode, String sDes) {
                AppConstant.sCategoryID = sKode;
                AppConstant.sCategoryName = sDes;

                sID = sKode;
                sDescription = sDes;
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
