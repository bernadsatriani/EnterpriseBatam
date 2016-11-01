package com.bpbatam.enterprise.bbs.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ayz4sci.androidfactory.DownloadProgressView;
import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.PDFViewActivity_Edit;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.adapter.AdapterBBSDaftarPesanan;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_Insert;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.net.NetworkManager;

import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/22/2016.
 */
public class Frag_bbs_daftar_pesanan extends Fragment {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    ArrayList<ListData> AryListData;
    ListData listData;

    RelativeLayout rLayoutDownload;
    DownloadProgressView downloadProgressView;
    private long downloadID;
    private DownloadManager downloadManager;

    TextView txtTulisPesan;
    RelativeLayout layoutHeader, layout_spinner_category;

    ImageView imgCancel, imgSave;

    Spinner spnBuletin, spnStatus, spnCategory;

    SimpleAdapter adpGridView;

    BBS_CATEGORY bbs_category;
    BBS_List_ByCategory bbs_list_byCategory, bbs_list_byCategoryFull;
    BBS_Insert bbs_insert;
    String[] lstCategory;
    String sCategoryID = "";

    EditText txtJudul, txtIsi;
    int iMin, iMax;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bbs_daftarpesanan, container, false);
        inflater = inflater;
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        iMin = 1;
        iMax = 10;
//        FillSpinner();
        FillSpinnerCategory();
    }

    void InitControl(View v){
        spnCategory = (Spinner)v.findViewById(R.id.spinner_caetogory);
        /*
        txtJudul = (EditText)v.findViewById(R.id.text_judul);
        txtIsi = (EditText)v.findViewById(R.id.text_isi);
        spnBuletin = (Spinner)v.findViewById(R.id.spinner_buletinboard);
        spnStatus = (Spinner)v.findViewById(R.id.spinner_status);

        imgCancel = (ImageView)v.findViewById(R.id.imageView12);
        imgSave = (ImageView)v.findViewById(R.id.imageView11);
        txtTulisPesan = (TextView)v.findViewById(R.id.text_tulis_pesan);
        layoutHeader = (RelativeLayout)v.findViewById(R.id.layout_header);
        layout_spinner_category = (RelativeLayout)v.findViewById(R.id.layout_spinner_category);
        txtTulisPesan.setVisibility(View.VISIBLE);
        layoutHeader.setVisibility(View.GONE);

        txtTulisPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTulisPesan.setVisibility(View.GONE);
                layout_spinner_category.setVisibility(View.GONE);
                layoutHeader.setVisibility(View.VISIBLE);
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTulisPesan.setVisibility(View.VISIBLE);
                layout_spinner_category.setVisibility(View.VISIBLE);
                layoutHeader.setVisibility(View.GONE);
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidInputan();
                txtTulisPesan.setVisibility(View.VISIBLE);
                layout_spinner_category.setVisibility(View.VISIBLE);
                layoutHeader.setVisibility(View.GONE);
            }
        });
*/

        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        rLayoutDownload = (RelativeLayout)v.findViewById(R.id.layout_download);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        downloadProgressView = (DownloadProgressView) v.findViewById(R.id.downloadProgressView);
        downloadManager = (DownloadManager) v.getContext().getSystemService(v.getContext().DOWNLOAD_SERVICE);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            iMin = iMax;
                            iMax += 10;
                            FillGridMore(sCategoryID);
                        }
                        //loading = false;
                        Log.v("...", "Last Item Wow !");
                        //Do pagination.. i.e. fetch new data
                    }
                }
            }
        });
    }
    
    boolean ValidInputan(){
        boolean bDone = false;

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String sTitle, sName, sStart_Periode, sEnd_Periode,sContent, sBbs_Date, sPriority_id, sRead, sCategory_Id,
                sCreate_By, sCreate_Time, sReply_Id;

        sTitle = txtJudul.getText().toString().trim();
        sName = AppConstant.USER_NAME;
        sStart_Periode = AppController.getInstance().getDate();
        sEnd_Periode = AppController.getInstance().getDate();
        sContent = txtIsi.getText().toString().trim();
        sBbs_Date = AppController.getInstance().getDate();
        sPriority_id = Integer.toString(spnStatus.getSelectedItemPosition());
        sRead = "0";
        sCreate_By = AppConstant.USER;
        sCreate_Time = AppController.getInstance().getDateNTime();
        sReply_Id = "0";
        switch (spnBuletin.getSelectedItemPosition()){
            case 0:
                sCategory_Id = "QNQ";
                break;
            case 1:
                sCategory_Id = "PDK";
                break;
            case 2:
                sCategory_Id = "FRU";
                break;
            case 3:
                sCategory_Id = "RUL";
                break;
            case 4:
                sCategory_Id = "KDS";
                break;
            case 5:
                sCategory_Id = "KSU";
                break;
            case 6:
                sCategory_Id = "INB";
                break;
            case 7:
                sCategory_Id = "PGU";
                break;
            case 8:
                sCategory_Id = "PDB";
                break;
            default:
                sCategory_Id = "QNQ";
                break;
        }


        try{
            BBS_Insert insertParam = new BBS_Insert(
                    AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    sTitle,
                    sName,
                    sStart_Periode,
                    sEnd_Periode,
                    sContent,
                    sBbs_Date,
                    sPriority_id,
                    sRead,
                    sCategory_Id,
                    sCreate_By,
                    sReply_Id
            )
                    ;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hashid", AppConstant.HASHID);
            jsonObject.put("reqid", AppConstant.REQID);
            jsonObject.put("userid", AppConstant.USER);
            jsonObject.put("title", sTitle);
            jsonObject.put("name", sName);
            jsonObject.put("start_period", sStart_Periode);
            jsonObject.put("end_period", sEnd_Periode);
            jsonObject.put("content", sContent);
            jsonObject.put("bbs_date", sBbs_Date);
            jsonObject.put("priority_id", "1");
            jsonObject.put("read", sRead);
            jsonObject.put("category_id", sCategory_Id);
            jsonObject.put("create_by", sCreate_By);
            jsonObject.put("reply_id", "0");


            Call<BBS_Insert> call = NetworkManager.getNetworkService(getActivity()).postBBSInsert(insertParam);
            call.enqueue(new Callback<BBS_Insert>() {
                @Override
                public void onResponse(Call<BBS_Insert> call, Response<BBS_Insert> response) {
                    int code = response.code();
                    bbs_insert = response.body();
                    if (code == 200){
                        if (bbs_insert.code.equals("00")){
                            Toast.makeText(getActivity(),bbs_insert.data, Toast.LENGTH_LONG).show();
                            txtIsi.setText("");
                            txtJudul.setText("");
                        }else{
                            Toast.makeText(getActivity(),bbs_insert.info, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_Insert> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        return bDone;
    }

    void FillSpinner(){

        String[] stsStatus = {"tinggi", "sedang", "rendah"};

        ArrayList<HashMap<String, Object>> lstGrid;
        HashMap<String, Object> mapGrid;

        lstGrid = new ArrayList<HashMap<String,Object>>();
        mapGrid = new HashMap<String, Object>();
        mapGrid.put("img", R.drawable.ball_red);
        mapGrid.put("description", "tinggi");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("img", R.drawable.ball_yellow);
        mapGrid.put("description", "sedang");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("img", R.drawable.ball_green);
        mapGrid.put("description", "rendah");
        lstGrid.add(mapGrid);

        adpGridView = new SimpleAdapter(getActivity(), lstGrid, R.layout.spinner_row,
                new String[] {"img","description"},
                new int[] {R.id.img_status, R.id.text_isi});

        spnStatus.setAdapter(adpGridView);
    }

    void FillSpinnerCategory(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_CATEGORY bbs_categoryParams = new BBS_CATEGORY(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID);
            Call<BBS_CATEGORY> call = NetworkManager.getNetworkService(getActivity()).getBBS_Category(bbs_categoryParams);
            call.enqueue(new Callback<BBS_CATEGORY>() {
                @Override
                public void onResponse(Call<BBS_CATEGORY> call, Response<BBS_CATEGORY> response) {
                    int code = response.code();
                    if (code == 200){
                        bbs_category = response.body();
                        lstCategory = new String[9];

                        ArrayList<HashMap<String, Object>> lstGrid;
                        HashMap<String, Object> mapGrid;

                        lstGrid = new ArrayList<HashMap<String,Object>>();
                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.QNQ);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.PDK);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.FRU);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.RUL);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.KDS);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.KSU);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.INB);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.PGU);
                        lstGrid.add(mapGrid);

                        mapGrid = new HashMap<String, Object>();
                        mapGrid.put("description", bbs_category.data.PDB);
                        lstGrid.add(mapGrid);
/*
                        lstCategory[0] = bbs_category.data.QNQ;
                        lstCategory[1] = bbs_category.data.PDK;
                        lstCategory[2] = bbs_category.data.FRU;
                        lstCategory[3] = bbs_category.data.RUL;
                        lstCategory[4] = bbs_category.data.KDS;
                        lstCategory[5] = bbs_category.data.KSU;
                        lstCategory[6] = bbs_category.data.INB;
                        lstCategory[7] = bbs_category.data.PGU;
                        lstCategory[8] = bbs_category.data.PDB;
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, lstCategory);
*/

                        adpGridView = new SimpleAdapter(getActivity(), lstGrid, R.layout.spinner_row_single,
                                new String[] {"description"},
                                new int[] {R.id.text_isi});
                        //spnBuletin.setAdapter(adpGridView);
                        spnCategory.setAdapter(adpGridView);
                        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                switch (i){
                                    case 0:
                                        sCategoryID = "QNQ";
                                        FillGrid("QNQ");
                                        break;
                                    case 1:
                                        sCategoryID = "PDK";
                                        FillGrid("PDK");
                                        break;
                                    case 2:
                                        sCategoryID = "FRU";
                                        FillGrid("FRU");
                                        break;
                                    case 3:
                                        sCategoryID = "RUL";
                                        FillGrid("RUL");
                                        break;
                                    case 4:
                                        sCategoryID = "KDS";
                                        FillGrid("KDS");
                                        break;
                                    case 5:
                                        sCategoryID = "KSU";
                                        FillGrid("KSU");
                                        break;
                                    case 6:
                                        sCategoryID = "INB";
                                        FillGrid("INB");
                                        break;
                                    case 7:
                                        sCategoryID = "PGU";
                                        FillGrid("PGU");
                                        break;
                                    case 8:
                                        sCategoryID = "PDB";
                                        FillGrid("PDB");
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<BBS_CATEGORY> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    void FillGridMore(String sCategory_id){
/*        AryListData = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            AryListData.add(listData);

        }*/

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BBS_List_ByCategory paramBBBList = new BBS_List_ByCategory(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                sCategory_id,
                Integer.toString(iMin),
                Integer.toString(iMax));

        try{
            Call<BBS_List_ByCategory> call = NetworkManager.getNetworkService(getActivity()).getBBS_List_ByCat(paramBBBList);
            call.enqueue(new Callback<BBS_List_ByCategory>() {
                @Override
                public void onResponse(Call<BBS_List_ByCategory> call, Response<BBS_List_ByCategory> response) {
                    int code = response.code();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (code == 200){
                        bbs_list_byCategory = response.body();

                        if (bbs_list_byCategory !=null){
                            if (bbs_list_byCategory.code.equals("00")){
                                for (BBS_List_ByCategory.Datum dat : bbs_list_byCategory.data){
                                    bbs_list_byCategoryFull.data.add(dat);
                                }



                                mAdapter.notifyItemInserted(bbs_list_byCategoryFull.data.size() - 1);
                                mAdapter.notifyDataSetChanged();
                                //FillAdapter();
                                /*mRecyclerView.setAdapter(mAdapter);
                                mRecyclerView.invalidate();*/
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_List_ByCategory> call, Throwable t) {
                    String a = t.getMessage();
                    a = a;
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            mRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    void FillGrid(String sCategory_id){
/*        AryListData = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            listData = new ListData();
            listData.setAtr1("Attachment " + i);
            listData.setAtr2("(5,88 mb)");
            listData.setAtr3("http://cottonsoft.co.nz/assets/img/our-company-history/history-2011-Paseo.jpg");
            AryListData.add(listData);

        }*/

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BBS_List_ByCategory paramBBBList = new BBS_List_ByCategory(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                sCategory_id,
                Integer.toString(iMin),
                Integer.toString(iMax));

        try{
            Call<BBS_List_ByCategory> call = NetworkManager.getNetworkService(getActivity()).getBBS_List_ByCat(paramBBBList);
            call.enqueue(new Callback<BBS_List_ByCategory>() {
                @Override
                public void onResponse(Call<BBS_List_ByCategory> call, Response<BBS_List_ByCategory> response) {
                    int code = response.code();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (code == 200){
                        bbs_list_byCategory = response.body();

                        if (bbs_list_byCategory !=null){
                            if (bbs_list_byCategory.code.equals("00")){
                                bbs_list_byCategoryFull = bbs_list_byCategory;
                                FillAdapter();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BBS_List_ByCategory> call, Throwable t) {
                    String a = t.getMessage();
                    a = a;
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            mRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    void FillAdapter(){
        mAdapter = new AdapterBBSDaftarPesanan(getActivity(), bbs_list_byCategoryFull, new AdapterBBSDaftarPesanan.OnDownloadClicked() {
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
                        Intent intent = new Intent(getActivity(), PDFViewActivity_Edit.class);
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
