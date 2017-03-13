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
import com.bpbatam.enterprise.model.Persuratan_Detail_CC;
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
    Persuratan_Detail_CC persuratanDetailCc;

    String sReadDate;

    String user_id = "";
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

        try{
            user_id = getIntent().getExtras().getString("USER_ID");
        }catch (Exception e){
            user_id = "";
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        FillGrid();
    }

    void InitContol(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("Carbon Copy ( CC )");
        if (AppConstant.ACTIVITY_FROM != null) txtLabel.setText(AppConstant.ACTIVITY_FROM + "       ");
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
            Persuratan_Detail_CC param = new Persuratan_Detail_CC(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Persuratan_Detail_CC> call = NetworkManager.getNetworkService(this).getMailCC(param);
            call.enqueue(new Callback<Persuratan_Detail_CC>() {
                @Override
                public void onResponse(Call<Persuratan_Detail_CC> call, Response<Persuratan_Detail_CC> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanDetailCc = response.body();
                        int iIndex = 0;
                        AryListData = new ArrayList<>();
                        if (persuratanDetailCc.code.equals("00")){
                            for(Persuratan_Detail_CC.Datum dat : persuratanDetailCc.data){
                                listData = new ListData();
                                listData.setAtr1(dat.user_dept);
                                listData.setAtr2(dat.user_name);
                                listData.setAtr3(dat.receive_date);
                                listData.setNama(dat.read_date);
                                AryListData.add(listData);

                                iIndex += 1;
                            }
                            mAdapter = new AdapterCC(getBaseContext(),AryListData);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Detail_CC> call, Throwable t) {

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
