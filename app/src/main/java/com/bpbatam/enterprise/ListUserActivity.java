package com.bpbatam.enterprise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.adapter.AdapterListUser;
import com.bpbatam.enterprise.adapter.AdapterNotification;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.ListUser;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/26/2016.
 */
public class ListUserActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    TextView txtLabel;
    private Toolbar mToolbar;
    ImageView imgBack;

    ListUser  listUser;
    String sUserDistri, sUserCC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        InitControl();
        AppConstant.USER_DISTRI = "";
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FillGrid("");
    }

    void InitControl() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtLabel= (TextView)findViewById(R.id.textLabel);
        imgBack = (ImageView)findViewById(R.id.img_back);
        txtLabel.setText("USER");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void FillGrid(String sKeyword){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            ListUser param = new ListUser(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, sKeyword);
            Call<ListUser> call = NetworkManager.getNetworkService(this).getListUser(param);
            call.enqueue(new Callback<ListUser>() {
                @Override
                public void onResponse(Call<ListUser> call, Response<ListUser> response) {
                    int code = response.code();
                    if (code == 200){
                        listUser = response.body();
                        if (listUser.code.equals("00")){
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListUser> call, Throwable t) {

                }
            });

        }catch (Exception e){

        }

    }

    void FillAdapter(){
        mAdapter = new AdapterListUser(this, listUser, new AdapterListUser.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(boolean bFromDistri, String UserId, String UserName) {
                if (bFromDistri){
//                    AppConstant.USER_DISTRI = UserId + "#" + UserName;
                    AppConstant.USER_DISTRI += UserId + "#";
                }else{
                    AppConstant.USER_CC = UserId;
                }

                finish();
            }

            @Override
            public void OnDownloadClicked() {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
