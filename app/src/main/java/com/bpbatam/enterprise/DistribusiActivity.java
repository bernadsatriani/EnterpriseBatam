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
import com.bpbatam.enterprise.model.Diposisi_List_Folder;

import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 10/3/2016.
 */
public class DistribusiActivity extends AppCompatActivity {
    EditText txtDistribusi, txtCC, txtPesan;
    TextView txtLabel;
    RelativeLayout btnKirim;

    Toolbar toolbar;

    ImageView imgDistri, imgCC;

    Diposisi_List_Folder diposisiListFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi);
        InitControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_24);
    }

    void InitControl(){
        imgDistri = (ImageView)findViewById(R.id.imgDistribusi);
        imgCC = (ImageView)findViewById(R.id.imgCC);
        txtLabel = (TextView)findViewById(R.id.textLabel);
        txtLabel.setText(AppConstant.PDF_FILENAME);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        txtDistribusi = (EditText)findViewById(R.id.text_distribusi);
        txtCC = (EditText)findViewById(R.id.text_cc);
        txtPesan = (EditText)findViewById(R.id.text_pesan);
        btnKirim = (RelativeLayout)findViewById(R.id.btnKirim);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateInnput()){
                    SendDiposisi();
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

        imgCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.B_USER_DISITRI = false;
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
        String sDistri = txtDistribusi.getText().toString().trim();
        String sCC = txtCC.getText().toString().trim();

        if (sDistri.equals("")){
            AppController.getInstance().CustomeDialog(getBaseContext(),"Data distribusi belum diisi");
            sReturn = false;
        }else if (sCC.equals("")){
            AppController.getInstance().CustomeDialog(getBaseContext(),"Data distribusi belum diisi");
            sReturn = false;
        }

        return sReturn;
    }

    void SendDiposisi(){
        String sPassword = "";
        try {
            sPassword = AppController.getInstance().md5("admin");
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER, sPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {


            String dead_line,  dispo_num,  priority,
                    retensi,  related_mail,  related_dispo,
                    sender,  dispo_date,  mail_no,  mail_date,
                    receive_date,  about,  receiver,
                    dispositior,  dispo_category,  create_by,
                    dispo_parent,  dispo_origin,  content;

            content = txtPesan.getText().toString().trim();

            for (Diposisi_List_Folder.Datum dat : AppConstant.diposisiListFolder.data){
                dead_line = dat.dead_line;
                dispo_num = dat.dispo_num;
                priority = dat.priority;
                //retensi = dat.re
                //related_mail = dat.re
                //related_dispo = dat.re
                //mail_no = dat.m
                //mail_date = dat.m
                //receive_date = dat.r
                //about = dat.a
                //receiver = dat.receiver
                //dispositior = dat.dispositior
                //dispo_category = dat.dispo_category
                //create_by = dat.create_by
                //dispo_parent = dat.dispo_parent
                //dispo_origin = dat.dispo_origin

            }


            /*Diposisi_List_Folder param = new Diposisi_List_Folder(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    sPassword,*/
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
        txtCC.setText(AppConstant.USER_CC);
    }
}
