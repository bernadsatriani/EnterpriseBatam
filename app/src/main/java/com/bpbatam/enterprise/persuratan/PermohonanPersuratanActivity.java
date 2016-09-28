package com.bpbatam.enterprise.persuratan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerAdapterPersuratanPermohonan;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
/**
 * Created by User on 9/19/2016.
 */
public class PermohonanPersuratanActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener{
    ImageView imgMenu;
    CharSequence Titles[]={"PRIBADI","UMUM"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterPersuratanPermohonan adapter;
    TabLayout tabs;

    Integer pageNumber = 0;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persuratan_permohonan_activity);

        imgMenu = (ImageView)findViewById(R.id.imageView);
        //pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        /*adapter =  new ViewPagerAdapterPersuratanPermohonan(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);*/
        pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("sample.pdf")
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}
