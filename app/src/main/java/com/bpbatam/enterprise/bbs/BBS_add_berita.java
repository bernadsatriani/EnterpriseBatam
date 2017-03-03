package com.bpbatam.enterprise.bbs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.BBS_CATEGORY;
import com.bpbatam.enterprise.model.BBS_Insert;
import com.bpbatam.enterprise.model.net.NetworkManager;

import org.json.JSONObject;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/3/2016.
 */

public class BBS_add_berita extends AppCompatActivity {
    int CODE_FILE = 225;
    ImageView imgAvatar, imgDelete, imgBack, imgPrioriti;
    TextView txtName, txtJudul, txtAttach, txtSize, txtPublikasi, txtKategori, txtPrioriti;
    EditText txtHeader;

    String sName, sJudul, sIsi, sSize,
            sFile_Path, sFile_Size, sFile_Type, sBbs_Date, sBbs_read;
    SimpleAdapter adpGridView;

    BBS_CATEGORY bbs_category;
    BBS_Insert bbsInsert;

    String[] lstCategory;

    Uri uri;
    String sCategory_Id,  sPriority_id;
    RelativeLayout layoutLampiran, layoutAttachment, layoutKategori, layout_btn_status;
    boolean bDelete, bUpload;
    String sBBsId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_berita);

        bDelete = false;
        bUpload = false;
        sCategory_Id = "";
        sPriority_id = "0";
        InitControl();
        sName = AppConstant.USER_NAME;

        try{
            sJudul = getIntent().getExtras().getString("BBS_JUDUL").trim();
        }catch (Exception e){
            sJudul = "";
        }

        try{
            sIsi = getIntent().getExtras().getString("BBS_ISI").trim();
        }catch (Exception e){
            sIsi = "";
        }

        try{
            sSize = getIntent().getExtras().getString("BBS_SIZE").trim();
        }catch (Exception e){
            sSize = "";
        }

        try{
            sBbs_Date = getIntent().getExtras().getString("BBS_DATE").trim();
        }catch (Exception e){
            sBbs_Date = "";
        }

        sBbs_Date = AppController.getInstance().getDate();
        try{
            sBbs_read = getIntent().getExtras().getString("BBS_READ").trim();
        }catch (Exception e){
            sBbs_read = "";
        }

        String sFileName = AppController.getInstance().getFileName(AppConstant.BBS_LINK);
        txtName.setText(sName);
        txtJudul.setText(sJudul);
        txtAttach.setText(sFileName);
        txtSize.setText(sSize);

        if (sSize.equals("")) layoutAttachment.setVisibility(View.GONE);


        //FillSpinnerCategory();
    }

    void InitControl(){
        imgPrioriti = (ImageView)findViewById(R.id.img_prioriti);
        txtPrioriti = (TextView)findViewById(R.id.text_prioriti);
        layout_btn_status = (RelativeLayout)findViewById(R.id.layout_btn_status);
        layout_btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), BBS_Prioritas.class);
                startActivityForResult(mIntent, 20);
            }
        });
        txtKategori = (TextView)findViewById(R.id.text_kategori);
        layoutKategori = (RelativeLayout)findViewById(R.id.layout_kategori);
        layoutKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getBaseContext(), BBS_category_list.class);
                startActivityForResult(mIntent, 10);
            }
        });
        txtPublikasi = (TextView)findViewById(R.id.textLabel2);
        imgBack = (ImageView)findViewById(R.id.img_back);
        imgAvatar = (ImageView)findViewById(R.id.img_avatar);
        imgDelete = (ImageView)findViewById(R.id.img_delete);
        txtName = (TextView)findViewById(R.id.text_Name);
        txtJudul = (TextView)findViewById(R.id.text_judul);
        txtAttach = (TextView)findViewById(R.id.lbl_attach);
        txtSize = (TextView)findViewById(R.id.lbl_size);
        //spnStatus = (Spinner)findViewById(R.id.spinner_status);
        //spnBuletin = (Spinner)findViewById(R.id.spinner_caetogory);
        txtHeader = (EditText)findViewById(R.id.text_header);
        layoutLampiran = (RelativeLayout)findViewById(R.id.layout_lampiran);
        layoutAttachment = (RelativeLayout)findViewById(R.id.layout_attachment1);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutLampiran.setVisibility(View.VISIBLE);
            }
        });

        txtPublikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sCategory_Id.equals("")){
                    CustomeDialog();
                }else{
                    UpdateBSB();
                }

            }
        });

        layoutLampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, CODE_FILE);
            }
        });

        layoutLampiran.setVisibility(View.VISIBLE);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteBBSAttachment();
            }
        });
    }

    void CustomeDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //dialog.setTitle("Title...");

        TextView txtDismis = (TextView)dialog.findViewById(R.id.text_dismiss);
        TextView txtDialog = (TextView)dialog.findViewById(R.id.text_dialog);
        txtDialog.setText("Kategori belum dipilih!");
        txtDismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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

        adpGridView = new SimpleAdapter(this, lstGrid, R.layout.spinner_row,
                new String[] {"img","description"},
                new int[] {R.id.img_status, R.id.text_isi});

        //spnStatus.setAdapter(adpGridView);
    }

    public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (requestCode == CODE_FILE){
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                File f = new File(uri.getPath());
                sFile_Path = f.getPath();
                sFile_Size = Long.toString(f.length());
                sFile_Type = f.getPath().substring(f.getPath().lastIndexOf(".") + 1); // Without dot jpg, png

                if (sFile_Type.length() > 5){
                    //Image
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    f = new File(picturePath);
                    sFile_Path = f.getPath();
                    sFile_Size = Long.toString(f.length());
                    sFile_Type = f.getPath().substring(f.getPath().lastIndexOf(".") + 1); // Without dot jpg, png

                }else{
                    //File
                }
                bUpload = true;
                layoutAttachment.setVisibility(View.VISIBLE);
                DecimalFormat precision = new DecimalFormat("0.00");
                double dFileSize = (double) f.length() / 1024;
                txtAttach.setText(AppController.getInstance().getFileName(sFile_Path));
                txtSize.setText("(" + precision.format(dFileSize) + " kb)" );

            }
        }else if (requestCode == 10){
            if (resultCode == Activity.RESULT_OK) {
                sCategory_Id = "";
                Bundle res = data.getExtras();
                sCategory_Id = res.getString("KODE");
                txtKategori.setText(res.getString("DES"));
            }
        }else if (requestCode == 20){
            if (resultCode == Activity.RESULT_OK) {
                sPriority_id= "0";
                Bundle res = data.getExtras();
                sPriority_id = res.getString("KODE");

                FillPrioriti();
            }
        }
    }

    void FillPrioriti(){
        switch (sPriority_id){
            case "1":
                imgPrioriti.setColorFilter(getResources().getColor(R.color.colorRedCircle));
                txtPrioriti.setText("Tinggi");
                break;
            case "0":
                imgPrioriti.setColorFilter(getResources().getColor(R.color.colorGreen));
                txtPrioriti.setText("Sedang");
                break;
            case "2":
                imgPrioriti.setColorFilter(getResources().getColor(R.color.colorRendah));
                txtPrioriti.setText("Rendah");
                break;
        }
    }

    void DeleteBBS(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_Insert param = new BBS_Insert(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));

            Call<BBS_Insert> call = NetworkManager.getNetworkService(this).postBBSDelete(param);
            call.enqueue(new Callback<BBS_Insert>() {
                @Override
                public void onResponse(Call<BBS_Insert> call, Response<BBS_Insert> response) {
                    int code = response.code();
                    if (code == 200){
                        layoutAttachment.setVisibility(View.GONE);
                        DeleteBBSAttachment();
                    }
                }

                @Override
                public void onFailure(Call<BBS_Insert> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void DeleteBBSAttachment(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            BBS_Insert param = new BBS_Insert(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID), AppConstant.BBS_LINK);

            Call<BBS_Insert> call = NetworkManager.getNetworkService(this).postBBSDeleteAttachment(param);
            call.enqueue(new Callback<BBS_Insert>() {
                @Override
                public void onResponse(Call<BBS_Insert> call, Response<BBS_Insert> response) {
                    int code = response.code();
                    if (code == 200){
                        bDelete = true;
                        layoutAttachment.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<BBS_Insert> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    void UpdateBSB(){

        /*switch (spnBuletin.getSelectedItemPosition()){
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
        }*/

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }



        try{
            BBS_Insert updateParam = new BBS_Insert(
                    AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    txtJudul.getText().toString().trim(),
                    AppConstant.USER_NAME,
                    sBbs_Date,
                    sBbs_Date,
                    txtHeader.getText().toString().trim(),
                    sBbs_Date,
                    sPriority_id,
                    "0",
                    sCategory_Id,
                    AppConstant.USER,
                    "0"
            );

            sJudul = txtJudul.getText().toString().trim();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hashid", AppConstant.HASHID);
            jsonObject.put("reqid", AppConstant.REQID);
            jsonObject.put("userid", AppConstant.USER);
            jsonObject.put("title", sJudul);
            jsonObject.put("name", sName);
            jsonObject.put("start_period", sBbs_read);
            jsonObject.put("end_period", sBbs_Date);
            jsonObject.put("content", txtHeader.getText().toString().trim());
            jsonObject.put("bbs_date", sBbs_Date);
            jsonObject.put("priority_id", sPriority_id);
            jsonObject.put("read", "0");
            jsonObject.put("category_id", sCategory_Id);
            jsonObject.put("create_by", AppConstant.USER);
            jsonObject.put("reply_id", "0");

            Call<BBS_Insert> call = NetworkManager.getNetworkService(this).postBBSInsert(updateParam);
            call.enqueue(new Callback<BBS_Insert>() {
                @Override
                public void onResponse(Call<BBS_Insert> call, Response<BBS_Insert> response) {
                    int code = response.code();
                    if (code == 200){
                        sBBsId = "";
                        bbsInsert = response.body();
                        if (bbsInsert.code.equals("00")){
                            sBBsId = bbsInsert.bbs_id;
                            sendBBSAttachment();
                        }

                        finish();
                    }
                }

                @Override
                public void onFailure(Call<BBS_Insert> call, Throwable t) {

                }
            });

        }catch (Exception e){

        }
    }

    void sendBBSAttachment(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            File file = new File(sFile_Path);
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
                            MediaType.parse("multipart/form-data"), sBBsId);

            Call<BBS_Insert> call = NetworkManager.getNetworkServiceUpload(this).postBBSInsertAttachmentOnly(
                    user_id,
                    id,
                    fileKey, body);
            call.enqueue(new Callback<BBS_Insert>() {
                @Override
                public void onResponse(Call<BBS_Insert> call, Response<BBS_Insert> response) {
                    int code = response.code();
                    if(code == 200){

                    }
                }

                @Override
                public void onFailure(Call<BBS_Insert> call, Throwable t) {

                }
            });
        }catch (Exception e){
            //Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
