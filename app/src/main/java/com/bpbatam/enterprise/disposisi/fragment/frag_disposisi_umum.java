package com.bpbatam.enterprise.disposisi.fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.DistribusiActivity;
import com.bpbatam.enterprise.PDFViewActivity;
import com.bpbatam.enterprise.PDFViewActivity_Distribusi;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiPribadi;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiUmum;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.QuickAction.ActionItem;
import ui.QuickAction.QuickAction;

/**
 * Created by User on 9/19/2016.
 */
public class frag_disposisi_umum extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    //action id
    private static final int ID_PILIH_PESAN     = 1;
    private static final int ID_SEMUA_PESAN   = 2;
    private static final int ID_TIDAK_PESAN     = 3;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    RelativeLayout rLayoutDownload;
    DownloadProgressView downloadProgressView;
    private long downloadID;
    private DownloadManager downloadManager;

    EditText txtSearch;
    ImageView imgMenu;
    TextView txtLabel;

    String statusPesan;
    LinearLayout layout_button, btnDistribusi, layoutKembali;
    Diposisi_List_Folder persuratanListFolder;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disposisi_umum, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        InitControl(view);
        FillGrid();
        statusPesan = AppConstant.TIDAK_PESAN;
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
                    statusPesan = AppConstant.PILIH_PESAN;
                    FillGrid();
                    btnDistribusi.setVisibility(View.GONE);

                    layoutKembali.setVisibility(View.VISIBLE);
                } else if (actionId == ID_SEMUA_PESAN) {
                    statusPesan = AppConstant.SEMUA_PESAN;
                    FillGrid();
                    btnDistribusi.setVisibility(View.VISIBLE);
                    layoutKembali.setVisibility(View.VISIBLE);
                }
                quickAction.dismiss();
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
        layoutKembali = (LinearLayout)v.findViewById(R.id.layout_button_kembali);
        layout_button = (LinearLayout)v.findViewById(R.id.layout_button);
        btnDistribusi = (LinearLayout)v.findViewById(R.id.btnDistribusi);
        txtLabel = (TextView)v.findViewById(R.id.view2);
        if (AppConstant.ACTIVITY_FROM != null) txtLabel.setText(AppConstant.ACTIVITY_FROM);
        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        txtSearch = (EditText)v.findViewById(R.id.text_search);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        rLayoutDownload = (RelativeLayout)v.findViewById(R.id.layout_download);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        downloadProgressView = (DownloadProgressView) v.findViewById(R.id.downloadProgressView);
        downloadManager = (DownloadManager) v.getContext().getSystemService(v.getContext().DOWNLOAD_SERVICE);

        btnDistribusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDistribusi.setVisibility(View.GONE);
                AppConstant.B_DISPOS = true;
                Intent intent;
                intent = new Intent(getActivity(), DistribusiActivity.class);
                getActivity().startActivity(intent);
            }
        });

        layoutKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusPesan = AppConstant.TIDAK_PESAN;
                FillGrid();
                btnDistribusi.setVisibility(View.GONE);
                layoutKembali.setVisibility(View.GONE);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorSearch),
                getActivity().getResources().getColor(R.color.Green),
                getActivity().getResources().getColor(R.color.b7_orange),
                getActivity().getResources().getColor(R.color.red));

    }

    void FillGrid(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Diposisi_List_Folder params = new Diposisi_List_Folder(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, "DFUM","1","10");
        try{
            Call<Diposisi_List_Folder> call = NetworkManager.getNetworkService(getActivity()).getDisposisiFolder(params);
            call.enqueue(new Callback<Diposisi_List_Folder>() {
                @Override
                public void onResponse(Call<Diposisi_List_Folder> call, Response<Diposisi_List_Folder> response) {
                    int code = response.code();
                    swipeRefreshLayout.setRefreshing(false);
                    persuratanListFolder = response.body();
                    if (code == 200){
                        if (persuratanListFolder.code.equals("00")){
                            int iIndex = 0;
                            if (statusPesan.equals(AppConstant.PILIH_PESAN) || statusPesan.equals(AppConstant.SEMUA_PESAN)){
                                for (Diposisi_List_Folder.Datum dat : persuratanListFolder.data){
                                    persuratanListFolder.data.get(iIndex).flag = statusPesan;
                                    iIndex += 1;
                                }
                            }
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Diposisi_List_Folder> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }catch (Exception e){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    void FillAdapter(){
        AryListData = new ArrayList<>();

/*        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            listData.setJekel(statusPesan);
            AryListData.add(listData);

        }*/

        mAdapter = new AdapterDisposisiUmum(getActivity(), persuratanListFolder, new AdapterDisposisiUmum.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(final String sUrl, boolean bStatus) {
                if (bStatus){
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sUrl.replace("https://", "http://")));
                    AppConstant.PDF_FILENAME = AppController.getInstance().getFileName(sUrl);
                    AppConstant.PDF_FILENAME = AppConstant.PDF_FILENAME.replace("%20"," ");

                    File file = new File(AppConstant.STORAGE_CARD + "/Download/" + AppConstant.PDF_FILENAME);
                    if (file.exists()){
                        Intent intent = new Intent(getActivity(), PDFViewActivity_Distribusi.class);
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
                            layout_button.setVisibility(View.GONE);
                            AppConstant.PDF_FILENAME = "DOWNLOAD_FILE_NAME.pdf";
                            Intent intent = new Intent (getActivity(), PDFViewActivity_Distribusi.class);
                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void downloadCancelled() {
                            //Action to perform when user press the cancel button
                            mRecyclerView.setVisibility(View.VISIBLE);
                            rLayoutDownload.setVisibility(View.GONE);
                        }
                    });
                }else{
                    boolean bDone = false;
                    AppConstant.DISPO_ID = "";
                    for (Diposisi_List_Folder.Datum dat : persuratanListFolder.data){
                        if (dat.flag.equals("2")){
                            bDone = true;
                            AppConstant.DISPO_ID += dat.dispo_id + "||";
                        }
                    }
                    if (bDone){
                        btnDistribusi.setVisibility(View.VISIBLE);
                    }else btnDistribusi.setVisibility(View.GONE);
                }

            }
        });
        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        FillGrid();
    }
}
