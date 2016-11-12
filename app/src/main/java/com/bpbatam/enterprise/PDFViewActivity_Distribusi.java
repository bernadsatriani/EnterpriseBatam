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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

/**
 * Created by User on 9/24/2016.
 */
public class PDFViewActivity_Distribusi extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    TextView txtLabel;
    Integer pageNumber = 0;
    Toolbar toolbar;
    String pdfFileName;
    PDFView pdfView;
    ImageView imgView;

    RelativeLayout btnDistribusi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        imgView = (ImageView)findViewById(R.id.imgView);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        btnDistribusi = (RelativeLayout)findViewById(R.id.btnDistribusi);

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
                AppConstant.DISPO_ID = Integer.toString(AppConstant.EMAIL_ID);
                Intent intent = new Intent(getBaseContext(), DistribusiActivity.class);
                startActivity(intent);
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

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}
