package com.bpbatam.enterprise.disposisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    CharSequence Titles[]={"Dokumen", "Distribusi"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterDisposisiDetail adapter;
    TabLayout tabs;

    Toolbar toolbar;
    TextView txtLabel;

    static TextView notifCount;
    static int mNotifCount = 28;
    Button btnNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposisi_detail);

        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
    }

    void InitControl(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("DISPOSISI     ");
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorSelectButton));
        adapter =  new ViewPagerAdapterDisposisiDetail(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        /*getMenuInflater().inflate(R.menu.menu_notification_blue, menu);
        MenuItem item1 = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(item1, R.layout.notification_update);

        View count = menu.findItem(R.id.action_notification).getActionView();
        notifCount = (TextView) count.findViewById(R.id.badge_notification_1);
        notifCount.setText(String.valueOf(mNotifCount));

        btnNotif= (Button) count.findViewById(R.id.button1);
        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });*/

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