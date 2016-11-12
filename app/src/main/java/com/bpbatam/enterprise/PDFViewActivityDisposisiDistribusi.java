package com.bpbatam.enterprise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.adapter.AdapterCC;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/24/2016.
 */
public class PDFViewActivityDisposisiDistribusi extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    TextView txtLabel;
    Integer pageNumber = 0;
    Toolbar toolbar;
    String pdfFileName;
    PDFView pdfView;

    Diposisi_List_Folder diposisiListFolder;
    Disposisi_Detail disposisiDetail;

    Persuratan_Detail persuratanDetail;
    LinearLayout btnDisposisi, btnDistribusi;

    String dead_line,  dispo_num,  priority,
            retensi,  related_mail,  related_dispo,
            sender,  dispo_date,  mail_no,  mail_date,
            receive_date,  about,  receiver,
            dispositior,  dispo_category,  create_by,
            dispo_parent,  dispo_origin,  content, dispo_dist, dispo_cc;

    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview_disposisi_distribusi);
        imgView = (ImageView)findViewById(R.id.imgView);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        btnDisposisi = (LinearLayout)findViewById(R.id.btnDisposisi);
        btnDistribusi = (LinearLayout)findViewById(R.id.btnDistribusi);

        txtLabel.setText(AppConstant.PDF_FILENAME);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_24);
        if (AppConstant.PDFVIEW_FROM != null) txtLabel.setText(AppConstant.PDFVIEW_FROM);

        File file = new File(AppConstant.STORAGE_CARD + "/Download/" + AppConstant.PDF_FILENAME);
        if (file.exists()){
            pdfView.fromFile(file)
                    .defaultPage(pageNumber)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }

        String sFileExtension = AppController.getInstance().getFileExtension(AppConstant.PDF_FILENAME);
        if (!sFileExtension.toUpperCase().equals("PDF") && !sFileExtension.toUpperCase().equals("TXT")){
            pdfView.setVisibility(View.GONE);
            imgView.setVisibility(View.VISIBLE);
            Uri uri = Uri.fromFile(new File(AppConstant.STORAGE_CARD + "/Download/" + AppConstant.PDF_FILENAME));

            AppController.getInstance().displayImageSDCard(getBaseContext(), uri, imgView);
        }


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

    void SendDiposisi(){
        String sPassword = "";
        try {
            sPassword = AppController.getInstance().md5("admin");
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            content = "";

            for (Diposisi_List_Folder.Datum dat : AppConstant.diposisiListFolder.data){
                dead_line = dat.dead_line;
                dispo_num = dat.dispo_num;
                priority = dat.priority;
                dispo_category = dat.category;
            }

            Diposisi_List_Folder param = new Diposisi_List_Folder(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    sPassword,
                    dead_line,
                    dispo_num,
                    priority,
                    retensi,
                    related_mail,
                    related_dispo,
                    sender,
                    dispo_date,
                    mail_no,
                    mail_date,
                    receive_date,
                    about,
                    receiver,
                    dispositior,
                    dispo_category,
                    create_by,
                    dispo_parent,
                    dispo_origin,
                    content,
                    dispo_dist,
                    dispo_cc);

            Call<Diposisi_List_Folder> call = NetworkManager.getNetworkService(this).postSendDisposisi(param);
            call.enqueue(new Callback<Diposisi_List_Folder>() {
                @Override
                public void onResponse(Call<Diposisi_List_Folder> call, Response<Diposisi_List_Folder> response) {
                    int code = response.code();
                    if (code == 200){
                        diposisiListFolder = response.body();
                    }

                }

                @Override
                public void onFailure(Call<Diposisi_List_Folder> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void getDispoDetail(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Disposisi_Detail params = new Disposisi_Detail(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                Integer.toString(AppConstant.EMAIL_ID));

        try{
            Call<Disposisi_Detail> call = NetworkManager.getNetworkService(this).getDisposisiDetail(params);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {
                    int code = response.code();
                    disposisiDetail = response.body();
                    if (code == 200){
                        if (disposisiDetail.code.equals("00")){
                            for(Disposisi_Detail.Datum dat : disposisiDetail.data){
                                retensi = dat.retensi;
                                related_mail = dat.related_mail;
                                related_dispo = dat.related_dispo;
                                mail_no = dat.mail_no;
                                mail_date = dat.mail_date;
                                receive_date = dat.receive_date;
                                about = dat.about;
                                receiver = dat.receiver;
                                dispositior = dat.dispositior;
                                create_by = dat.create_by;
                                dispo_parent = Integer.toString(dat.dispo_parent);
                                dispo_origin = Integer.toString(dat.dispo_origin);
                            }

                            SendDiposisi();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void getMailDetail(){
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
                                retensi = dat.retency;
                                related_mail = Integer.toString(AppConstant.EMAIL_ID);
                                related_dispo = "";
                                mail_no = "";
                                mail_date = dat.mail_date;
                                receive_date = dat.receive_date;
                                about = "";
                                receiver = dat.receiver;
                                dispositior = "";
                                create_by = dat.create_by;
                                dispo_parent = "";
                                dispo_origin = "";
                            }

                            SendDiposisi();
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

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}
