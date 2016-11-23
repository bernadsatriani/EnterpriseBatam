package com.bpbatam.enterprise.disposisi;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.bpbatam.enterprise.DistribusiActivity;
import com.bpbatam.enterprise.PDFViewActivity_Distribusi;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiPribadi;
import com.bpbatam.enterprise.disposisi.disposisi_category_activity;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class disposisi_folder_pribadi_umum extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    RelativeLayout rLayoutDownload, layoutFolder, btnDistribusi;
    DownloadProgressView downloadProgressView;
    private long downloadID;
    private DownloadManager downloadManager;

    ImageView imgMenu;
    TextView txtLabel, txtFolder;
    Diposisi_List_Folder persuratanListFolder;
    SwipeRefreshLayout swipeRefreshLayout;
    String statusPesan = "";
    String sFolder = "";
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_disposisi_pilih_surat);
        InitControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sFolder = AppConstant.FOLDER_DISPOS;

        statusPesan = getIntent().getExtras().getString("ID");
        if (statusPesan.equals(AppConstant.SEMUA_PESAN)) btnDistribusi.setVisibility(View.VISIBLE);
        FillGrid();
    }



    void InitControl(){
        btnDistribusi = (RelativeLayout)findViewById(R.id.layout_btnDistribusi);
        btnDistribusi.setVisibility(View.GONE);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("Pilih Surat           ");

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtFolder = (TextView) findViewById(R.id.text_folder);
        layoutFolder = (RelativeLayout)findViewById(R.id.contact_us_header_container);
        layoutFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), disposisi_category_activity.class);
                startActivityForResult(intent, 10);
            }
        });

        btnDistribusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDistribusi.setVisibility(View.GONE);
                AppConstant.B_DISPOS = true;
                Intent intent;
                intent = new Intent(getBaseContext(), DistribusiActivity.class);
                startActivity(intent);
            }
        });

        //txtLabel = (TextView)v.findViewById(R.id.view2);
        //if (AppConstant.ACTIVITY_FROM != null) txtLabel.setText(AppConstant.ACTIVITY_FROM);
        imgMenu = (ImageView)findViewById(R.id.imageView);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rLayoutDownload = (RelativeLayout)findViewById(R.id.layout_download);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        downloadProgressView = (DownloadProgressView) findViewById(R.id.downloadProgressView);
        downloadManager = (DownloadManager) this.getSystemService(this.DOWNLOAD_SERVICE);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorSearch),
                getResources().getColor(R.color.Green),
                getResources().getColor(R.color.b7_orange),
                getResources().getColor(R.color.red));

    }

    void FillGrid(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (sFolder.equals("DFUM")){
            txtFolder.setText("Folder Umum (00/00)");
        }else{
            txtFolder.setText("Folder Pribadi (00/00)");
        }

        Diposisi_List_Folder params = new Diposisi_List_Folder(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, sFolder,"1","10");
        try{
            Call<Diposisi_List_Folder> call = NetworkManager.getNetworkService(this).getDisposisiFolder(params);
            call.enqueue(new Callback<Diposisi_List_Folder>() {
                @Override
                public void onResponse(Call<Diposisi_List_Folder> call, Response<Diposisi_List_Folder> response) {
                    int code = response.code();
                    swipeRefreshLayout.setRefreshing(false);
                    persuratanListFolder = response.body();
                    if (code == 200){
                        if (persuratanListFolder.code.equals("00")){
                            AppConstant.diposisiListFolder = persuratanListFolder;
                            int iUnread = 0;

                            int iIndex = 0;
                            for (Diposisi_List_Folder.Datum dat : persuratanListFolder.data){
                                if (dat.read_date !=null && dat.read_date.equals("-")){
                                    iUnread += 1;
                                }
                            }




                            if (statusPesan.equals(AppConstant.PILIH_PESAN) || statusPesan.equals(AppConstant.SEMUA_PESAN)){
                                for (Diposisi_List_Folder.Datum dat : persuratanListFolder.data){
                                    persuratanListFolder.data.get(iIndex).flag = statusPesan;
                                    iIndex += 1;
                                }
                            }


                            String sUnread = String.valueOf(iUnread);
                            if (sUnread.length() < 2) sUnread = "0" + sUnread;

                            String sTotal = String.valueOf(persuratanListFolder.data.size());
                            if (sTotal.length() < 2) sTotal = "0" + sTotal;
                            if (sFolder.equals("DFUM")){
                                txtFolder.setText("Folder Umum (" + sUnread + "/" + sTotal + ")");
                            }else{
                                txtFolder.setText("Folder Pribadi (" + sUnread + "/" + sTotal + ")");
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

        /*for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            listData.setJekel(statusPesan);
            AryListData.add(listData);

        }*/

        mAdapter = new AdapterDisposisiPribadi(this, persuratanListFolder, new AdapterDisposisiPribadi.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(final String sUrl, boolean bStatus) {
                if (bStatus){
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sUrl));
                    AppConstant.PDF_FILENAME = AppController.getInstance().getFileName(sUrl);
                    AppConstant.PDF_FILENAME = AppConstant.PDF_FILENAME.replace("%20"," ");

                    File file = new File(AppConstant.STORAGE_CARD + "/Download/" + AppConstant.PDF_FILENAME);
                    if (file.exists()){
                        Intent intent = new Intent(getBaseContext(), PDFViewActivity_Distribusi.class);
                        startActivity(intent);
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
                            Intent intent = new Intent (getBaseContext(), PDFViewActivity_Distribusi.class);
                            startActivity(intent);
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
                    }else
                        btnDistribusi.setVisibility(View.GONE);
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

    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == 10){
            if (resultCode == Activity.RESULT_OK) {
                Bundle res = data.getExtras();
                sFolder = res.getString("KODE");
                FillGrid();
            }
        }
    }


    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }else if (id == android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.action_menu) {

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

}
