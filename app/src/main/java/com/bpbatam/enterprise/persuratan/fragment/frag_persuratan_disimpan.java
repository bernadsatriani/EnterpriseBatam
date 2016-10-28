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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.PDFViewActivityDisposisiDistribusi;
import com.bpbatam.enterprise.PDFViewActivitySimpanKirim;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.adapter.AdapterPersuratanDisimpan;
import com.bpbatam.enterprise.persuratan.adapter.AdapterPersuratanPribadi;

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
public class frag_persuratan_disimpan extends Fragment {
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

    ImageView imgMenu;
    TextView txtLabel;
    LinearLayout layout_button, btnDelete;

    String statusPesan;

    Persuratan_List_Folder persuratanListFolder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_persuratan_simpan, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        statusPesan = AppConstant.TIDAK_PESAN;
        InitControl(view);
        FillGrid(view);

        ActionItem pilihItem 	= new ActionItem(ID_PILIH_PESAN, "Pilih Pesan", null);
        ActionItem semuaItem 	= new ActionItem(ID_SEMUA_PESAN, "Semua Pesan", null);
        ActionItem kembaliItem 	= new ActionItem(ID_TIDAK_PESAN, "Kembali", null);

        pilihItem.setSticky(true);
        semuaItem.setSticky(true);

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(getActivity(), QuickAction.VERTICAL);

        //add action items into QuickAction
        quickAction.addActionItem(kembaliItem);
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
                    FillGrid(view);
                    layout_button.setVisibility(View.GONE);
                } else if (actionId == ID_SEMUA_PESAN) {
                    statusPesan = AppConstant.SEMUA_PESAN;
                    FillGrid(view);
                    layout_button.setVisibility(View.VISIBLE);
                }else if (actionId == ID_TIDAK_PESAN) {
                    statusPesan = AppConstant.TIDAK_PESAN;
                    FillGrid(view);
                    layout_button.setVisibility(View.GONE);
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
        layout_button = (LinearLayout)v.findViewById(R.id.layout_button);
        btnDelete = (LinearLayout)v.findViewById(R.id.btnDelete);
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_button.setVisibility(View.GONE);
            }
        });

    }

    void FillGrid(View v){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Persuratan_List_Folder params = new Persuratan_List_Folder(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, "SIM","1","10");
        try{
            Call<Persuratan_List_Folder> call = NetworkManager.getNetworkService(getActivity()).getMailFolder(params);
            call.enqueue(new Callback<Persuratan_List_Folder>() {
                @Override
                public void onResponse(Call<Persuratan_List_Folder> call, Response<Persuratan_List_Folder> response) {
                    int code = response.code();
                    persuratanListFolder = response.body();
                    if (code == 200){
                        if (persuratanListFolder.code.equals("00")){
                            FillAdapter();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Persuratan_List_Folder> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void FillAdapter(){
        AryListData = new ArrayList<>();
/*

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            listData.setJekel(statusPesan);
            AryListData.add(listData);

        }
*/

        mAdapter = new AdapterPersuratanDisimpan(getActivity(), persuratanListFolder, new AdapterPersuratanDisimpan.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(final String sUrl, boolean bStatus) {
                if (bStatus){
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
                            layout_button.setVisibility(View.GONE);
                            AppConstant.PDF_FILENAME = "DOWNLOAD_FILE_NAME.pdf";
                            Intent intent = new Intent(getActivity(), PDFViewActivitySimpanKirim.class);
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
                    for (ListData dat : AryListData){
                        if (dat.getJekel().equals("2")){
                            bDone = true;
                            break;
                        }
                    }
                    if (bDone){
                        layout_button.setVisibility(View.VISIBLE);
                    }else layout_button.setVisibility(View.GONE);
                }

            }
        });
        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

}
