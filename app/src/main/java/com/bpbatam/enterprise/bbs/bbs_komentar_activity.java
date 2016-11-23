package com.bpbatam.enterprise.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.adapter.AdapterKomentar;
import com.bpbatam.enterprise.disposisi.disposisi_folder_pribadi_umum;
import com.bpbatam.enterprise.model.BBS_Opini;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.github.barteksc.pdfviewer.PDFView;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/27/2016.
 */
public class bbs_komentar_activity extends AppCompatActivity {
    TextView txtLabel;
    EditText txtOpini;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;
    Toolbar toolbar;
    ImageView imgSend;
    BBS_Opini bbsOpini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);

        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);

        txtLabel.setText("Opini");
        FillGrid();

    }

    void InitControl(){
        imgSend = (ImageView)findViewById(R.id.img_send);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtOpini = (EditText)findViewById(R.id.text_opini);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sOpini = txtOpini.getText().toString().trim();
                if (!sOpini.equals("")){
                    sendOpini();
                    finish();
                }
            }
        });

        txtOpini.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    imgSend.setColorFilter(getResources().getColor(R.color.colorSearch));
                }else{
                    imgSend.setColorFilter(getResources().getColor(R.color.grey));
                }

            }
        });
    }


    void FillGrid(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_Opini param = new BBS_Opini(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    String.valueOf(AppConstant.EMAIL_ID));

            Call<BBS_Opini> call = NetworkManager.getNetworkService(this).getBBS_Opini(param);
            call.enqueue(new Callback<BBS_Opini>() {
                @Override
                public void onResponse(Call<BBS_Opini> call, Response<BBS_Opini> response) {
                    int code = response.code();
                    if (code == 200){
                        bbsOpini = response.body();
                        if (bbsOpini.code.equals("00")){
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_Opini> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
    void FillAdapter() {
        AryListData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            listData = new ListData();
            listData.setAtr1("Bpk Budi ");
            listData.setAtr2("Komentar komentar ke " + i);
            listData.setAtr3("11:46 AM");
            AryListData.add(listData);

        }

        mAdapter = new AdapterKomentar(this, bbsOpini);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        //getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
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
        }else if (id == R.id.action_share) {
            String sOpini = txtOpini.getText().toString().trim();
            if (!sOpini.equals("")){
                sendOpini();
                finish();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    void sendOpini(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_Opini param = new BBS_Opini(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    String.valueOf(AppConstant.EMAIL_ID),
                    txtOpini.getText().toString().trim());

            Call<BBS_Opini> call = NetworkManager.getNetworkService(this).postBBS_Opini(param);
            call.enqueue(new Callback<BBS_Opini>() {
                @Override
                public void onResponse(Call<BBS_Opini> call, Response<BBS_Opini> response) {
                    int code = response.code();
                    if (code == 200){
                        bbsOpini = response.body();
                        if (bbsOpini.code.equals("00")){

                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_Opini> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}
