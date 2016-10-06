package com.bpbatam.enterprise;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_dalamproses;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_permohonan;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_pribadi;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_riwayat;
import com.bpbatam.enterprise.disposisi.fragment.frag_disposisi_umum;
import com.bpbatam.enterprise.fragment.Frag_Beranda;
import com.bpbatam.enterprise.fragment.frag_bbs;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_pribadi;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_umum;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_dalam_proses;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_dikembalikan;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_disimpan;
import com.bpbatam.enterprise.persuratan.fragment.frag_persuratan_permohonan;

public class MainActivity extends AppCompatActivity implements NavMenuFragment.FragmentDrawerListener {
    private Toolbar mToolbar;
    private NavMenuFragment drawerFragment;
    TextView txtLabel;

    static TextView notifCount;
    static int mNotifCount = 28;
    Button btnNotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtLabel= (TextView)findViewById(R.id.textLabel);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavMenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout_new), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        Intent intent;
        switch (position) {
            case 0:
                fragment = new Frag_Beranda();
                txtLabel.setText("BERANDA");
                break;
            //BBS----------------------------------------------------------
            case 10:
                fragment = new frag_bbs();
                txtLabel.setText("BBS");
                break;
            //PERSURATAN ----------------------------------------------
            case 20:
                fragment = new frag_persuratan_pribadi();
                txtLabel.setText("PERSURATAN");
                AppConstant.ACTIVITY_FROM = "Pribadi";
                break;
            case 21:
                fragment = new frag_persuratan_umum();
                txtLabel.setText("PERSURATAN");
                AppConstant.ACTIVITY_FROM = "Umum";
                break;
            case 22:
                fragment = new frag_persuratan_permohonan();
                txtLabel.setText("PERSURATAN");
                AppConstant.ACTIVITY_FROM = "Permohonan";
                break;
            case 23:
                fragment = new frag_persuratan_dalam_proses();
                txtLabel.setText("PERSURATAN");
                AppConstant.ACTIVITY_FROM = "Dalam Proses";
                break;
            case 24:
                fragment = new frag_persuratan_disimpan();
                txtLabel.setText("PERSURATAN");
                AppConstant.ACTIVITY_FROM = "Simpan";
                break;
            case 25:
                fragment = new frag_persuratan_dikembalikan();
                txtLabel.setText("PERSURATAN");
                AppConstant.ACTIVITY_FROM = "Dikembalikan";
                break;
            //DISPOSISI------------------------------------------------
            case 30:
                fragment = new frag_disposisi_pribadi();
                txtLabel.setText("DISPOSISI");
                AppConstant.ACTIVITY_FROM = "Pribadi";
                break;
            case 31:
                fragment = new frag_disposisi_umum();
                txtLabel.setText("DISPOSISI");
                AppConstant.ACTIVITY_FROM = "Umum";
                break;
            case 32:
                fragment = new frag_disposisi_permohonan();
                txtLabel.setText("DISPOSISI");
                AppConstant.ACTIVITY_FROM = "Permohonan";
                break;
            case 33:
                fragment = new frag_disposisi_dalamproses();
                txtLabel.setText("DISPOSISI");
                AppConstant.ACTIVITY_FROM = "Dalam Proses";
                break;
            case 34:
                fragment = new frag_disposisi_riwayat();
                txtLabel.setText("DISPOSISI");
                AppConstant.ACTIVITY_FROM = "Riwayat";
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_tabcontent, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_notification_blue, menu);
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
        });
        return true;
    }


    private void setNotifCount(int count){
        mNotifCount = count;
        invalidateOptionsMenu();
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
        }
        return super.onOptionsItemSelected(item);
    }
}
