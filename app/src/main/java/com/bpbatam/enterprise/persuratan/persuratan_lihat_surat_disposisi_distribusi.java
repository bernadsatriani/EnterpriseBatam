package com.bpbatam.enterprise.persuratan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.DistribusiActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.Persuratan_proses;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/24/2016.
 */

public class persuratan_lihat_surat_disposisi_distribusi extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtLabel, txtIsi, txtDistribusi;
    ImageView img_back;
    String sIsi;
    WebView myWeb1;

    Persuratan_Detail persuratanDetail;
    LinearLayout btnDisposisi, btnDistribusi;
    Persuratan_proses persuratanProses;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persuratan_pilih_surat_disposisi_distribusi);

        InitControl();
             txtLabel.setText("");
        //FillGrid();
        /*
        try{
            sIsi = getIntent().getExtras().getString("ISI");
        }catch (Exception e){
            sIsi = "";
        }

        txtIsi.setText(Html.fromHtml(sIsi));
*/
    }

    void InitControl(){
        img_back = (ImageView)findViewById(R.id.img_back);
        txtDistribusi = (TextView)findViewById(R.id.textLabel2);
        txtDistribusi.setVisibility(View.GONE);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtIsi = (TextView)findViewById(R.id.text_isi);
        myWeb1 = (WebView) findViewById(R.id.webview);
        btnDisposisi = (LinearLayout)findViewById(R.id.btnDisposisi);
        btnDistribusi = (LinearLayout)findViewById(R.id.btnDistribusi);

        myWeb1.getSettings().setLoadsImagesAutomatically(true);
        myWeb1.getSettings().setJavaScriptEnabled(true);
        myWeb1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWeb1.getSettings().setLoadWithOverviewMode(true);
        myWeb1.getSettings().setUseWideViewPort(true);
        myWeb1.getSettings().setBuiltInZoomControls(true);
        myWeb1.getSettings().setDisplayZoomControls(false);
        String sUrl = "http://epdev.bpbatam.go.id/pdf/preview.php?user="+AppConstant.USER+"&id=" + AppConstant.EMAIL_ID;
        myWeb1.loadUrl(sUrl);

        txtDistribusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.USER_DISTRI = "";
                AppConstant.USER_CC = "";
                AppConstant.DISPO_ID = Integer.toString(AppConstant.EMAIL_ID);
                Intent intent = new Intent(getBaseContext(), DistribusiActivity.class);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDistribusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.USER_DISTRI = "";
                AppConstant.USER_CC = "";
                Intent intent = new Intent(getBaseContext(), DistribusiActivity.class);
                startActivity(intent);
            }
        });

        btnDisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDispoDetail();
                finish();
            }
        });
    }

}
