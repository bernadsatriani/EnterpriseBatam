package com.bpbatam.enterprise.disposisi.fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.PDFViewActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiRiwayat;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiRiwayatNew;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Riwayat;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class frag_disposisi_riwayat_new extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
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
    TextView txtLabel, txtTitle;
    Disposisi_Riwayat riwayat, riwayatSearch;
    SwipeRefreshLayout swipeRefreshLayout;

    int iMin, iMax;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disposisi_riwayat_dalamproses, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillGrid();
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

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorSearch),
                getActivity().getResources().getColor(R.color.Green),
                getActivity().getResources().getColor(R.color.b7_orange),
                getActivity().getResources().getColor(R.color.red));

        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                        if (iMax <= totalItemCount){
                            iMin = iMax +1;
                            iMax += 10;
                            FillGridMore();
                        }
                        //loading = false;
                        Log.v("...", "Last Item Wow !");
                        //Do pagination.. i.e. fetch new data
                    }
                }
            }
        });*/
    }

    void FillGrid(){
        iMin = 1;
        iMax = 10;
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        txtLabel.setText("RIWAYAT (00/00)");
        Disposisi_Riwayat params = new Disposisi_Riwayat(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID);
        try{
            Call<Disposisi_Riwayat> call = NetworkManager.getNetworkService(getActivity()).getDisposisiRiwayat(params);
            call.enqueue(new Callback<Disposisi_Riwayat>() {
                @Override
                public void onResponse(Call<Disposisi_Riwayat> call, Response<Disposisi_Riwayat> response) {
                    int code = response.code();
                    swipeRefreshLayout.setRefreshing(false);
                    riwayat = response.body();
                    if (code == 200){
                        if (riwayat.code.equals("00")){
                            int iUnread = 0;
                            for (Disposisi_Riwayat.Datum dat : riwayat.data){
                                if (dat.read_date !=null && dat.read_date.equals("-")){
                                    iUnread += 1;
                                }
                            }

                            String sUnread = String.valueOf(iUnread);
                            if (sUnread.length() < 2) sUnread = "0" + sUnread;

                            String sTotal = String.valueOf(riwayat.data.size());
                            if (sTotal.length() < 2) sTotal = "0" + sTotal;
                            txtLabel.setText("RIWAYAT (" + sUnread + "/" + sTotal + ")");
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Riwayat> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }catch (Exception e){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    void FillAdapter(){
        AryListData = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            AryListData.add(listData);

        }

        mAdapter = new AdapterDisposisiRiwayatNew(getActivity(), riwayat, new AdapterDisposisiRiwayatNew.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(final String sUrl, boolean bStatus) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sUrl));
                AppConstant.PDF_FILENAME = AppController.getInstance().getFileName(sUrl);
                AppConstant.PDF_FILENAME = AppConstant.PDF_FILENAME.replace("%20"," ");

                File file = new File(AppConstant.STORAGE_CARD + "/Download/" + AppConstant.PDF_FILENAME);
                if (file.exists()){
                    Intent intent = new Intent(getActivity(), PDFViewActivity.class);
                    getActivity().startActivity(intent);
                }else{
                    mRecyclerView.setVisibility(View.GONE);
                    rLayoutDownload.setVisibility(View.VISIBLE);

                    request.setTitle(AppConstant.PDF_FILENAME);

                    request.setDescription("DESCRIPTION");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    // request.setDestinationInExternalPublicDir(AppConstant.FOLDER_DOWNLOAD, "DOWNLOAD_FILE_NAME.pdf");

                    File root = new File(AppConstant.STORAGE_CARD + "/Download/");
                    Uri path = Uri.withAppendedPath(Uri.fromFile(root), AppConstant.PDF_FILENAME);
                    request.setDestinationUri(path);

                    downloadID = downloadManager.enqueue(request);
                }

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
                        Intent intent = new Intent(getActivity(), PDFViewActivity.class);
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

    public  void InitRecycle(String sKeyword){
        sKeyword = sKeyword.toLowerCase(Locale.getDefault());
        riwayatSearch = new Disposisi_Riwayat();
        riwayatSearch.data = new ArrayList<>();
        for (Disposisi_Riwayat.Datum dat : riwayat.data){

            if ( dat.title != null){
                if (dat.title.toLowerCase(Locale.getDefault()).contains(sKeyword) ||
                        dat.name.toLowerCase(Locale.getDefault()).contains(sKeyword))
                    riwayatSearch.data.add(dat);
            }

        }

        mAdapter = new AdapterDisposisiRiwayatNew(getActivity(), riwayatSearch, new AdapterDisposisiRiwayatNew.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(final String sUrl, boolean bStatus) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sUrl));
                AppConstant.PDF_FILENAME = AppController.getInstance().getFileName(sUrl);
                AppConstant.PDF_FILENAME = AppConstant.PDF_FILENAME.replace("%20"," ");

                File file = new File(AppConstant.STORAGE_CARD + "/Download/" + AppConstant.PDF_FILENAME);
                if (file.exists()){
                    Intent intent = new Intent(getActivity(), PDFViewActivity.class);
                    getActivity().startActivity(intent);
                }else{
                    mRecyclerView.setVisibility(View.GONE);
                    rLayoutDownload.setVisibility(View.VISIBLE);

                    request.setTitle(AppConstant.PDF_FILENAME);

                    request.setDescription("DESCRIPTION");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    // request.setDestinationInExternalPublicDir(AppConstant.FOLDER_DOWNLOAD, "DOWNLOAD_FILE_NAME.pdf");

                    File root = new File(AppConstant.STORAGE_CARD + "/Download/");
                    Uri path = Uri.withAppendedPath(Uri.fromFile(root), AppConstant.PDF_FILENAME);
                    request.setDestinationUri(path);

                    downloadID = downloadManager.enqueue(request);
                }

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
                        Intent intent = new Intent(getActivity(), PDFViewActivity.class);
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
    @Override
    public void onRefresh() {
        FillGrid();
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_search2, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv =(SearchView) menu.findItem(R.id.action_search).getActionView();
        sv.setQueryHint("Search Surat...");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                InitRecycle(newText);
                //FillGrid(newText);
                return false;
            }
        });

    }
}
