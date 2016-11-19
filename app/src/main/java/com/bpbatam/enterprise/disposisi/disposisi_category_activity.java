package com.bpbatam.enterprise.disposisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/18/2016.
 */

public class disposisi_category_activity extends AppCompatActivity {
    BBS_CATEGORY bbs_category;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    String[] lstCategory;

    RelativeLayout layout_ok;
    String sID, sIDPersuratan;
    TextView txtPribadi, txtUmum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        sID = "";
        InitControl();
    }

    void InitControl(){
        txtPribadi = (TextView)findViewById(R.id.text_pribadi);
        txtPribadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPribadi.setTextColor(getResources().getColor(R.color.colorSearch));
                txtUmum.setTextColor(getResources().getColor(R.color.black));
                sID = "DFPR";
                sIDPersuratan = "FPR";
            }
        });
        txtUmum = (TextView)findViewById(R.id.text_umum);
        txtUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPribadi.setTextColor(getResources().getColor(R.color.black));
                txtUmum.setTextColor(getResources().getColor(R.color.colorSearch));
                sID = "DFUM";
                sIDPersuratan = "FUM";
            }
        });

        layout_ok = (RelativeLayout)findViewById(R.id.layout_ok);
        layout_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle conData = new Bundle();
                conData.putString("KODE", sID);
                conData.putString("KODE_PERSURATAN", sIDPersuratan);
                Intent intent = new Intent();
                intent.putExtras(conData);
                setResult(RESULT_OK, intent);

                finish();
            }
        });

    }

}
