package com.bpbatam.enterprise.bbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.enterprise.R;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by setia.n on 11/18/2016.
 */

public class BBS_Prioritas extends AppCompatActivity {

    LinearLayout layoutTinggi, layoutSedang, layoutRendah;
    SmoothCheckBox scb, scb1, scb2;
    String sID = "";
    Toolbar toolbar;
    TextView txtLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perioritas);

        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sID = "0";
        scb.setChecked(true);
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("Prioritas");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        layoutTinggi = (LinearLayout)findViewById(R.id.layout_tinggi);
        layoutSedang = (LinearLayout)findViewById(R.id.layout_sedang);
        layoutRendah = (LinearLayout)findViewById(R.id.layout_rendah);
        scb = (SmoothCheckBox)findViewById(R.id.scb);
        scb1 = (SmoothCheckBox)findViewById(R.id.scb1);
        scb2 = (SmoothCheckBox)findViewById(R.id.scb2);

        scb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scb.setChecked(true);
                scb1.setChecked(false);
                scb2.setChecked(false);
                sID = "0";
            }
        });

        scb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scb.setChecked(false);
                scb1.setChecked(true);
                scb2.setChecked(false);
                sID = "1";
            }
        });

        scb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scb.setChecked(false);
                scb1.setChecked(false);
                scb2.setChecked(true);
                sID = "2";
            }
        });
        layoutTinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scb.setChecked(true);
                scb1.setChecked(false);
                scb2.setChecked(false);
                sID = "0";
            }
        });

        layoutSedang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scb.setChecked(false);
                scb1.setChecked(true);
                scb2.setChecked(false);
                sID = "1";
            }
        });

        layoutRendah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scb.setChecked(false);
                scb1.setChecked(false);
                scb2.setChecked(true);
                sID = "2";
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Bundle conData = new Bundle();
                conData.putString("KODE", sID);
                Intent intent = new Intent();
                intent.putExtras(conData);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
