package com.bpbatam.enterprise.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.adapter.ViewPagerAdapterBBS;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_Insert;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerAdapterPersuratanPermohonan;

import org.json.JSONObject;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class frag_bbs extends Fragment {

    int CODE_FILE = 45;
    ImageView imgMenu, imgDelete;
    CharSequence Titles[]={"Daftar Pesan","Semua Pesan"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterBBS adapter;
    TabLayout tabs;

    TextView txtTulisPesan, text_publikasi, txtFileName, txtSize;
    RelativeLayout layoutHeader, layoutBtnLampiran, layoutAttachment;

    Spinner spnBuletin, spnStatus, spnCategory;

    SimpleAdapter adpGridView;

    BBS_CATEGORY bbs_category;
    BBS_Insert bbs_insert;
    String[] lstCategory;
    String sCategoryID = "";

    EditText txtJudul, txtIsi;
    LinearLayout layout_button_kembali;
    String sFile_Size, sFile_Type, sBBS_id, sFile_Path;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bbs, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        layoutAttachment.setVisibility(View.GONE);
        FillSpinner();
        FillSpinnerCategory();
    }

    void InitControl(View v){
        txtFileName = (TextView)v.findViewById(R.id.text_attachment);
        txtSize = (TextView)v.findViewById(R.id.text_size);
        imgDelete = (ImageView)v.findViewById(R.id.img_delete);
        layoutAttachment = (RelativeLayout)v.findViewById(R.id.layout_attachment);
        txtJudul = (EditText)v.findViewById(R.id.text_judul);
        txtIsi = (EditText)v.findViewById(R.id.text_isi);
        spnBuletin = (Spinner)v.findViewById(R.id.spinner_buletinboard);
        spnStatus = (Spinner)v.findViewById(R.id.spinner_status);
        spnCategory = (Spinner)v.findViewById(R.id.spinner_caetogory);
        layout_button_kembali = (LinearLayout) v.findViewById(R.id.layout_button_kembali);
        layoutBtnLampiran = (RelativeLayout) v.findViewById(R.id.layout_btn_lampiran);
        text_publikasi = (TextView) v.findViewById(R.id.text_publikasi);
        txtTulisPesan = (TextView)v.findViewById(R.id.text_tulis_pesan);
        layoutHeader = (RelativeLayout)v.findViewById(R.id.layout_header);
        txtTulisPesan.setVisibility(View.VISIBLE);
        layoutHeader.setVisibility(View.GONE);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sFile_Path = "";
                sFile_Size = "";
                sFile_Type = "";
                layoutAttachment.setVisibility(View.GONE);
            }
        });

        txtTulisPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTulisPesan.setVisibility(View.GONE);
                layoutHeader.setVisibility(View.VISIBLE);
            }
        });

        layout_button_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTulisPesan.setVisibility(View.VISIBLE);
                layoutHeader.setVisibility(View.GONE);
            }
        });

        text_publikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidInputan();
                txtTulisPesan.setVisibility(View.VISIBLE);
                layoutHeader.setVisibility(View.GONE);
            }
        });


        layoutBtnLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sFile_Size = "";
                sFile_Type = "";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, CODE_FILE);
            }
        });

        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (TabLayout)v.findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterBBS(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setSelectedTabIndicatorColor(getActivity().getResources().getColor(R.color.colorSelectButton));
        tabs.setupWithViewPager(pager);
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
                            sBBS_id = bbs_insert.bbs_id;
                            sendBBSAttachment();
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
                        spnBuletin.setAdapter(adpGridView);
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

    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == CODE_FILE){
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                File f = new File(uri.getPath());
                sFile_Path = f.getPath();
                sFile_Size = Long.toString(f.length());
                sFile_Type = f.getPath().substring(f.getPath().lastIndexOf(".") + 1); // Without dot jpg, png

                DecimalFormat precision = new DecimalFormat("0.00");
                double dFileSize = Double.parseDouble(sFile_Size) / 1024;

                String sFileName = AppController.getInstance().getFileName(f.getPath());
                layoutAttachment.setVisibility(View.VISIBLE);
                txtFileName.setText(sFileName);
                txtSize.setText("(" + precision.format(dFileSize) + " kb)");
            }
        }

    }

    void sendBBSAttachment(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_Insert param = new BBS_Insert(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID,
                    sBBS_id,
                    sFile_Path,
                    sFile_Type,
                    sFile_Size
                    );

            File file = new File(uri.getPath());
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("files", file.getName(), requestFile);

            RequestBody user_id =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), AppConstant.USER);

            RequestBody id =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "bbs");


            RequestBody fileKey =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), sBBS_id);

            Call<BBS_Insert> call = NetworkManager.getNetworkServiceUpload(getActivity()).postBBSInsertAttachmentOnly(
                    user_id,
                    id,
                    fileKey, body);
            call.enqueue(new Callback<BBS_Insert>() {
                @Override
                public void onResponse(Call<BBS_Insert> call, Response<BBS_Insert> response) {
                    int code = response.code();
                    bbs_insert = null;
                    if(code == 200){
                        bbs_insert = response.body();
                    }
                }

                @Override
                public void onFailure(Call<BBS_Insert> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
