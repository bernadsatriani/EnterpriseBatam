package com.bpbatam.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiDistribusi;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.Disposisi_Distribusi;
import com.bpbatam.enterprise.model.Disposisi_Distribusi_Opini;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Distribusi;
import com.bpbatam.enterprise.model.Persuratan_Distribusi_Opini;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 10/3/2016.
 */
public class DistribusiActivity extends AppCompatActivity {
    EditText txtPesan;
    TextView txtLabel, txtDistribusi;
    RelativeLayout btnKirim;

    Toolbar toolbar;

    ImageView imgDistri;

    Diposisi_List_Folder diposisiListFolder;
    Disposisi_Detail disposisiDetail;

    Disposisi_Distribusi disposisiDistribusi;
    Persuratan_Distribusi persuratanDistribusi;

    Disposisi_Distribusi_Opini disposisiDistribusiOpini;
    Persuratan_Distribusi_Opini persuratanDistribusiOpini;

    String dead_line,  dispo_num,  priority,
            retensi,  related_mail,  related_dispo,
            sender,  dispo_date,  mail_no,  mail_date,
            receive_date,  about,  receiver,
            dispositior,  dispo_category,  create_by,
            dispo_parent,  dispo_origin,  content, dispo_dist, dispo_cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi);
        InitControl();
        AppConstant.USER_DISTRI = "";
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void InitControl(){
        imgDistri = (ImageView)findViewById(R.id.imgDistribusi);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText("Pilih Surat           ");
        txtLabel.setText("DISTRIBUSI            ");

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtDistribusi = (TextView)findViewById(R.id.text_distribusi);
        txtPesan = (EditText)findViewById(R.id.text_pesan);
        btnKirim = (RelativeLayout)findViewById(R.id.btnKirim);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateInnput()){
                    if (AppConstant.B_DISPOS){
                        SendDiposisi();
                    }else{
                        SendPersuratan();
                    }

                    finish();
                }

            }
        });

        imgDistri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.B_USER_DISITRI = true;
                Intent mIntent = new Intent(getBaseContext(), ListUserActivity.class);
                startActivity(mIntent);
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

    boolean ValidateInnput(){
        boolean sReturn = true;
        dispo_dist = txtDistribusi.getText().toString().trim();

        dispo_dist = dispo_dist.replace("#","||");
        if (dispo_dist.equals("")){
            AppController.getInstance().CustomeDialog(getBaseContext(),"Data distribusi belum diisi");
            sReturn = false;
        }

        return sReturn;
    }

    void SendDiposisi(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            content = txtPesan.getText().toString().trim();

            Disposisi_Distribusi param = new Disposisi_Distribusi(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    AppConstant.DISPO_ID,
                    dispo_dist);

            Call<Disposisi_Distribusi> call = NetworkManager.getNetworkService(this).postSendDistribusiDispos(param);
            call.enqueue(new Callback<Disposisi_Distribusi>() {
                @Override
                public void onResponse(Call<Disposisi_Distribusi> call, Response<Disposisi_Distribusi> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiDistribusi = response.body();
                        if (disposisiDistribusi.code.equals("00")){
                            SendDiposisiOpini();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Disposisi_Distribusi> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void SendDiposisiOpini(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            content = txtPesan.getText().toString().trim();

            Disposisi_Distribusi_Opini param = new Disposisi_Distribusi_Opini(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    AppConstant.DISPO_ID,
                    content);

            Call<Disposisi_Distribusi_Opini> call = NetworkManager.getNetworkService(this).postSendDistribusiDisposOpini(param);
            call.enqueue(new Callback<Disposisi_Distribusi_Opini>() {
                @Override
                public void onResponse(Call<Disposisi_Distribusi_Opini> call, Response<Disposisi_Distribusi_Opini> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiDistribusiOpini = response.body();
                    }

                }

                @Override
                public void onFailure(Call<Disposisi_Distribusi_Opini> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void SendPersuratan(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            content = txtPesan.getText().toString().trim();

            Persuratan_Distribusi param = new Persuratan_Distribusi(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    AppConstant.DISPO_ID,
                    dispo_dist);

            Call<Persuratan_Distribusi> call = NetworkManager.getNetworkService(this).postSendDistribusiPersuratan(param);
            call.enqueue(new Callback<Persuratan_Distribusi>() {
                @Override
                public void onResponse(Call<Persuratan_Distribusi> call, Response<Persuratan_Distribusi> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanDistribusi = response.body();
                        if (persuratanDistribusi.code.equals("00")){
                            SendPersuratanOpini();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Persuratan_Distribusi> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void SendPersuratanOpini(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            content = txtPesan.getText().toString().trim();

            Persuratan_Distribusi_Opini param = new Persuratan_Distribusi_Opini(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    AppConstant.DISPO_ID,
                    content);

            Call<Persuratan_Distribusi_Opini> call = NetworkManager.getNetworkService(this).postSendDistribusiPersuratanOpini(param);
            call.enqueue(new Callback<Persuratan_Distribusi_Opini>() {
                @Override
                public void onResponse(Call<Persuratan_Distribusi_Opini> call, Response<Persuratan_Distribusi_Opini> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanDistribusiOpini = response.body();
                    }

                }

                @Override
                public void onFailure(Call<Persuratan_Distribusi_Opini> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void SendDiposisiOld(){
        String sPassword = "";
        try {
            sPassword = AppController.getInstance().md5("admin");
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            content = txtPesan.getText().toString().trim();

            for (Diposisi_List_Folder.Datum dat : AppConstant.diposisiListFolder.data){
                dead_line = dat.dead_line;
                dispo_num = dat.dispo_num;
                priority = dat.priority;
                dispo_category = dat.category;
            }

            Diposisi_List_Folder param = new Diposisi_List_Folder(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    sPassword,
                    dead_line,
                    dispo_num,
                    priority,
                    retensi,
                    related_mail,
                    related_dispo,
                    sender,
                    dispo_date,
                    mail_no,
                    mail_date,
                    receive_date,
                    about,
                    receiver,
                    dispositior,
                    dispo_category,
                    create_by,
                    dispo_parent,
                    dispo_origin,
                    content,
                    dispo_dist,
                    dispo_cc);

            Call<Diposisi_List_Folder> call = NetworkManager.getNetworkService(this).postSendDisposisi(param);
            call.enqueue(new Callback<Diposisi_List_Folder>() {
                @Override
                public void onResponse(Call<Diposisi_List_Folder> call, Response<Diposisi_List_Folder> response) {
                    int code = response.code();
                    if (code == 200){
                        diposisiListFolder = response.body();
                    }

                }

                @Override
                public void onFailure(Call<Diposisi_List_Folder> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }

    void getDispoDetail(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Disposisi_Detail params = new Disposisi_Detail(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                Integer.toString(AppConstant.EMAIL_ID));

        try{
            Call<Disposisi_Detail> call = NetworkManager.getNetworkService(this).getDisposisiDetail(params);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {
                    int code = response.code();
                    disposisiDetail = response.body();
                    if (code == 200){
                        if (disposisiDetail.code.equals("00")){
                            for(Disposisi_Detail.Datum dat : disposisiDetail.data){
                                retensi = dat.retensi;
                                related_mail = dat.related_mail;
                                related_dispo = dat.related_dispo;
                                mail_no = dat.mail_no;
                                mail_date = dat.mail_date;
                                receive_date = dat.receive_date;
                                about = dat.about;
                                receiver = dat.receiver;
                                dispositior = dat.dispositior;
                                create_by = dat.create_by;
                                dispo_parent = Integer.toString(dat.dispo_parent);
                                dispo_origin = Integer.toString(dat.dispo_origin);
                            }

                            SendDiposisi();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
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
    public void onResume(){
        super.onResume();
        // put your code here...
        txtDistribusi.setText(AppConstant.USER_DISTRI);
    }
}
