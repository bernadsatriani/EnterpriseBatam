package com.bpbatam.enterprise.persuratan.fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.PDFViewActivityDisposisiDistribusi;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.persuratan.adapter.AdapterPersuratanUmum;

import java.util.ArrayList;

import ui.QuickAction.ActionItem;
import ui.QuickAction.QuickAction;

/**
 * Created by User on 9/19/2016.
 */
public class frag_persuratan_umum extends Fragment {
    //action id
    private static final int ID_PILIH_PESAN     = 1;
    private static final int ID_SEMUA_PESAN   = 2;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    RelativeLayout rLayoutDownload;
    DownloadProgressView downloadProgressView;
    private long downloadID;
    private DownloadManager downloadManager;

    ImageView imgMenu;
    TextView txtLabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_persuratan_umum, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillGrid(view);

        ActionItem pilihItem 	= new ActionItem(ID_PILIH_PESAN, "Pilih Pesan", null);
        ActionItem semuaItem 	= new ActionItem(ID_SEMUA_PESAN, "Semua Pesan", null);

        pilihItem.setSticky(true);
        semuaItem.setSticky(true);

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(getActivity(), QuickAction.VERTICAL);

        //add action items into QuickAction
        quickAction.addActionItem(pilihItem);
        quickAction.addActionItem(semuaItem);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                ActionItem actionItem = quickAction.getActionItem(pos);

                //here we can filter which action item was clicked with pos or actionId parameter
                if (actionId == ID_PILIH_PESAN) {

                } else if (actionId == ID_SEMUA_PESAN) {

                }
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAction.show(v);
            }
        });

    }

    void InitControl(View v){
        txtLabel = (TextView)v.findViewById(R.id.view2);
        if (AppConstant.ACTIVITY_FROM != null) txtLabel.setText(AppConstant.ACTIVITY_FROM);
        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        rLayoutDownload = (RelativeLayout)v.findViewById(R.id.layout_download);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        downloadProgressView = (DownloadProgressView) v.findViewById(R.id.downloadProgressView);
        downloadManager = (DownloadManager) v.getContext().getSystemService(v.getContext().DOWNLOAD_SERVICE);

    }

    void FillGrid(View v){
        AryListData = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            AryListData.add(listData);

        }

        mAdapter = new AdapterPersuratanUmum(v.getContext(), AryListData, new AdapterPersuratanUmum.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(final String sUrl, boolean bStatus) {
                mRecyclerView.setVisibility(View.GONE);
                rLayoutDownload.setVisibility(View.VISIBLE);

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sUrl));
                request.setTitle("TITLE");
                request.setDescription("DESCRIPTION");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(AppConstant.FOLDER_DOWNLOAD, "DOWNLOAD_FILE_NAME.pdf");
                request.allowScanningByMediaScanner();
                downloadID = downloadManager.enqueue(request);

                downloadProgressView.show(downloadID, new DownloadProgressView.DownloadStatusListener() {
                    @Override
                    public void downloadFailed(int reason) {
                        //Action to perform when download fails, reason as returned by DownloadManager.COLUMN_REASON
                        mRecyclerView.setVisibility(View.VISIBLE);
                        rLayoutDownload.setVisibility(View.GONE);
                    }

                    @Override
                    public void downloadSuccessful() {
                        //Action to perform on success
                        mRecyclerView.setVisibility(View.VISIBLE);
                        rLayoutDownload.setVisibility(View.GONE);
                        AppConstant.PDF_FILENAME = "DOWNLOAD_FILE_NAME.pdf";
                        Intent intent = new Intent(getActivity(), PDFViewActivityDisposisiDistribusi.class);
                        getActivity().startActivity(intent);
                    }

                    @Override
                    public void downloadCancelled() {
                        //Action to perform when user press the cancel button
                        mRecyclerView.setVisibility(View.VISIBLE);
                        rLayoutDownload.setVisibility(View.GONE);
                    }
                });
            }
        });
        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

}
