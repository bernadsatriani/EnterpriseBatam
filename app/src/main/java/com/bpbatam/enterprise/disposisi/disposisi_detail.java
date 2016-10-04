package com.bpbatam.enterprise.disposisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bpbatam.enterprise.NotificationActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.ViewPagerAdapterDisposisiDetail;
import com.bpbatam.enterprise.disposisi.adapter.ViewPagerAdapterDisposisiPermohonan;

import org.w3c.dom.Text;

/**
 * Created by User on 10/1/2016.
 */
public class disposisi_detail extends AppCompatActivity {

    CharSequence Titles[]={"Dokumen","Tertanda", "Distribusi"};
    int Numboftabs =3;

    ViewPager pager;
    ViewPagerAdapterDisposisiDetail adapter;
    TabLayout tabs;

    Toolbar toolbar;
    TextView txtLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposisi_detail);

        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_24);
    }

    void InitControl(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("DISPOSISI");
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue2));
        adapter =  new ViewPagerAdapterDisposisiDetail(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_notification_blue, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_notification:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(getBaseContext(),NotificationActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}