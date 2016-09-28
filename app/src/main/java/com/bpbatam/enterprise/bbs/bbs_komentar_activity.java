package com.bpbatam.enterprise.bbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.adapter.AdapterKomentar;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/27/2016.
 */
public class bbs_komentar_activity extends AppCompatActivity {
    TextView txtLabel;
    ImageView imgBack;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);

        InitControl();
        FillGrid();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("BBS");

        imgBack = (ImageView)findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    void FillGrid() {
        AryListData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            listData = new ListData();
            listData.setAtr1("Bpk Budi ");
            listData.setAtr2("Komentar komentar ke " + i);
            listData.setAtr3("11:46 AM");
            AryListData.add(listData);

        }

        mAdapter = new AdapterKomentar(this, AryListData);
        mRecyclerView.setAdapter(mAdapter);
    }
}
