package com.bpbatam.enterprise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.adapter.AdapterCC;
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
 * Created by setia.n on 10/4/2016.
 */

public class CC_Activity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;
    TextView txtLabel;

    Toolbar toolbar;
    Persuratan_Detail persuratanDetail;
    Disposisi_Detail disposisiDetail;
    String sReadDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc);

        InitContol();

        try{
            sReadDate = getIntent().getExtras().getString("READ_DATE");
        }catch (Exception e){
            sReadDate = "";
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back_24);
        FillGrid();
    }

    void InitContol(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        if (AppConstant.ACTIVITY_FROM != null) txtLabel.setText(AppConstant.ACTIVITY_FROM);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    void FillGrid() {


        /*for (int i = 0; i < 1; i++) {
            listData = new ListData();
            listData.setAtr1("Seksi Media dan Aplikasi " + i);
            listData.setAtr2("Rizal Safani S.Kom");
            listData.setAtr3("21 Sept 2016 17:36");
            listData.setNama("24 Sept 2016 14:36");
            AryListData.add(listData);

        }

        mAdapter = new AdapterCC(this,AryListData);
        mRecyclerView.setAdapter(mAdapter);
*/
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Persuratan_Detail param = new Persuratan_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Persuratan_Detail> call = NetworkManager.getNetworkService(this).getMailDetail(param);
            call.enqueue(new Callback<Persuratan_Detail>() {
                @Override
                public void onResponse(Call<Persuratan_Detail> call, Response<Persuratan_Detail> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanDetail = response.body();
                        int iIndex = 0;
                        AryListData = new ArrayList<>();
                        for(Persuratan_Detail.Datum dat : persuratanDetail.data){
                            for(Persuratan_Detail.ApprovalState dat1 : persuratanDetail.data.get(iIndex).approval_state){
                                listData = new ListData();
                                listData.setAtr1(dat1.deptartement);
                                listData.setAtr2(dat1.user_name);
                                listData.setAtr3(dat.mail_date);
                                listData.setNama(sReadDate);
                                AryListData.add(listData);
                            }

                            iIndex += 1;
                        }
                        mAdapter = new AdapterCC(getBaseContext(),AryListData);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }

    void FillGridDisposisi() {


        /*for (int i = 0; i < 1; i++) {
            listData = new ListData();
            listData.setAtr1("Seksi Media dan Aplikasi " + i);
            listData.setAtr2("Rizal Safani S.Kom");
            listData.setAtr3("21 Sept 2016 17:36");
            listData.setNama("24 Sept 2016 14:36");
            AryListData.add(listData);

        }

        mAdapter = new AdapterCC(this,AryListData);
        mRecyclerView.setAdapter(mAdapter);
*/
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
            Call<Disposisi_Detail> call = NetworkManager.getNetworkService(this).getDisposisiDetail(param);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiDetail = response.body();
                        int iIndex = 0;
                        AryListData = new ArrayList<>();
                        for(Disposisi_Detail.Datum dat : disposisiDetail.data){
                            /*for(Disposisi_Detail.ApprovalState dat1 : persuratanDetail.data.get(iIndex).approval_state){
                                listData = new ListData();
                                listData.setAtr1(dat.d);
                                listData.setAtr2(dat1.user_name);
                                listData.setAtr3(dat.mail_date);
                                listData.setNama(sReadDate);
                                AryListData.add(listData);
                            }*/

                            iIndex += 1;
                        }
                        mAdapter = new AdapterCC(getBaseContext(),AryListData);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.clear();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
