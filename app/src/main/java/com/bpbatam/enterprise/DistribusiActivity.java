package com.bpbatam.enterprise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;

/**
 * Created by User on 10/3/2016.
 */
public class DistribusiActivity extends AppCompatActivity {
    EditText txtDistribusi, txtCC, txtPesan;
    TextView txtLabel;
    RelativeLayout btnKirim;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi);
        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_24);
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText(AppConstant.PDF_FILENAME);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtDistribusi = (EditText)findViewById(R.id.text_distribusi);
        txtCC = (EditText)findViewById(R.id.text_cc);
        txtPesan = (EditText)findViewById(R.id.text_pesan);
        btnKirim = (RelativeLayout)findViewById(R.id.btnKirim);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
