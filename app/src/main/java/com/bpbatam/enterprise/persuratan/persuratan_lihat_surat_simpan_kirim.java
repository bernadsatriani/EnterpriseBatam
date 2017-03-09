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

public class persuratan_lihat_surat_simpan_kirim extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtLabel, txtIsi, txtDistribusi;
    Persuratan_Detail persuratanDetail;
    ImageView img_back;
    String sIsi;
    WebView myWeb1;

    LinearLayout btnSimpan, btnKirim;
    Persuratan_proses persuratanProses;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persuratan_pilih_surat_simpan_kirim);

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
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtIsi = (TextView)findViewById(R.id.text_isi);
        btnSimpan = (LinearLayout)findViewById(R.id.btnSimpan);
        btnKirim = (LinearLayout)findViewById(R.id.btnKirim);
        myWeb1 = (WebView) findViewById(R.id.webview);

        myWeb1.getSettings().setLoadsImagesAutomatically(true);
        myWeb1.getSettings().setJavaScriptEnabled(true);
        myWeb1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWeb1.getSettings().setLoadWithOverviewMode(true);
        myWeb1.getSettings().setUseWideViewPort(true);
        myWeb1.getSettings().setBuiltInZoomControls(true);
        myWeb1.getSettings().setDisplayZoomControls(false);
        String sUrl = AppConstant.DOMAIN_URL_VIEW + "/pdf/preview.php?user="+AppConstant.USER+"&id=" + AppConstant.EMAIL_ID;
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

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vSimpan();
                finish();
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void vSimpan(){
        String sPassword = "";
        try {
            sPassword = AppController.getInstance().md5("admin");
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Persuratan_proses param = new Persuratan_proses(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    sPassword,
                    Integer.toString(AppConstant.EMAIL_ID));

            Call<Persuratan_proses> call = NetworkManager.getNetworkService(this).postDisetejui(param);
            call.enqueue(new Callback<Persuratan_proses>() {
                @Override
                public void onResponse(Call<Persuratan_proses> call, Response<Persuratan_proses> response) {
                    if (response.code() == 200){
                        persuratanProses = response.body();
                        //AppController.getInstance().CustomeDialog(PDFViewActivityDitolakDisetujui.this, persuratanProses.info);
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_proses> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void FillGrid() {
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Persuratan_Detail param = new Persuratan_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Persuratan_Detail> call = NetworkManager.getNetworkService(this).getMailDetail(param);
            call.enqueue(new Callback<Persuratan_Detail>() {
                @Override
                public void onResponse(Call<Persuratan_Detail> call, Response<Persuratan_Detail> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanDetail = response.body();

                        if (persuratanDetail.code.equals("00")){
                            for(Persuratan_Detail.Datum dat : persuratanDetail.data){
                                if(dat.header != null){
                                    txtIsi.setText(Html.fromHtml(dat.header));
                                    txtLabel.setText(Html.fromHtml(dat.content + "      "));
                                }

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }

    void vDisetuji(){
        String sPassword = "";
        try {
            sPassword = AppController.getInstance().md5("admin");
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Persuratan_proses param = new Persuratan_proses(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    sPassword,
                    Integer.toString(AppConstant.EMAIL_ID));

            Call<Persuratan_proses> call = NetworkManager.getNetworkService(this).postDisetejui(param);
            call.enqueue(new Callback<Persuratan_proses>() {
                @Override
                public void onResponse(Call<Persuratan_proses> call, Response<Persuratan_proses> response) {
                    if (response.code() == 200){
                        persuratanProses = response.body();
                        //AppController.getInstance().CustomeDialog(PDFViewActivityDitolakDisetujui.this, persuratanProses.info);
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_proses> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void vDitolak(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Persuratan_proses param = new Persuratan_proses(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));

            Call<Persuratan_proses> call = NetworkManager.getNetworkService(this).postDitolak(param);
            call.enqueue(new Callback<Persuratan_proses>() {
                @Override
                public void onResponse(Call<Persuratan_proses> call, Response<Persuratan_proses> response) {
                    int code = response.code();

                    if (code == 200){
                        persuratanProses = response.body();
                        //AppController.getInstance().CustomeDialog(PDFViewActivityDitolakDisetujui.this, persuratanProses.info);
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_proses> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

}
