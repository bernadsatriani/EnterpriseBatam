package com.bpbatam.enterprise.disposisi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiRiwayatDetail;
import com.bpbatam.enterprise.model.Disposisi_Riwayat;
import com.bpbatam.enterprise.model.Disposisi_Riwayat_Detail;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 2/11/2017.
 */

public class disposisi_riwayat_detail extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    Disposisi_Riwayat_Detail riwayatDetail;
    Toolbar toolbar;
    TextView txtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disposisi_riwayat_detail);

        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);

        txtLabel.setText("RIWAYAT     ");

        FillGrid();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    void FillGrid(){

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Disposisi_Riwayat_Detail params = new Disposisi_Riwayat_Detail(AppConstant.HASHID, AppConstant.USER,
                AppConstant.REQID,
                AppConstant.DISPO_ID);
        try{
            Call<Disposisi_Riwayat_Detail> call = NetworkManager.getNetworkService(this).getDisposisiRiwayatDetail(params);
            call.enqueue(new Callback<Disposisi_Riwayat_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Riwayat_Detail> call, Response<Disposisi_Riwayat_Detail> response) {
                    int code = response.code();
                    //swipeRefreshLayout.setRefreshing(false);
                    riwayatDetail = response.body();
                    if (code == 200){
                        if (riwayatDetail.code.equals("00")){
                           /* int iUnread = 0;
                            for (Disposisi_Riwayat.Datum dat : riwayat.data){
                                if (dat.read_date !=null && dat.read_date.equals("-")){
                                    iUnread += 1;
                                }
                            }

                            String sUnread = String.valueOf(iUnread);
                            if (sUnread.length() < 2) sUnread = "0" + sUnread;

                            String sTotal = String.valueOf(riwayat.data.size());
                            if (sTotal.length() < 2) sTotal = "0" + sTotal;
                            txtLabel.setText("RIWAYAT (" + sUnread + "/" + sTotal + ")");*/
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Riwayat_Detail> call, Throwable t) {
                    //swipeRefreshLayout.setRefreshing(false);
                }
            });
        }catch (Exception e){
            //swipeRefreshLayout.setRefreshing(false);
        }

    }

    void FillAdapter(){
        mAdapter = new AdapterDisposisiRiwayatDetail(this, riwayatDetail, new AdapterDisposisiRiwayatDetail.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String sUrl, boolean bStatus) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }else if (id == android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.action_menu) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
