package com.bpbatam.enterprise.persuratan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.CC_Activity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiPribadi;
import com.bpbatam.enterprise.disposisi.disposisi_detail;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Attachment;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.persuratan_detail;
import com.bpbatam.enterprise.persuratan.persuratan_lihat_surat;
import com.bpbatam.enterprise.persuratan.persuratan_lihat_surat_disposisi_distribusi;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterPersuratanPribadi extends  RecyclerView.Adapter<AdapterPersuratanPribadi.ViewHolder>{
    Persuratan_List_Folder persuratanListFolder;

    Persuratan_Attachment persuratanAttachment;
    private ArrayList<ListData> mCourseArrayList;
    private
    Context context;

    Persuratan_Detail persuratanDetail;
    public AdapterPersuratanPribadi(Context context, Persuratan_List_Folder persuratanListFolder, OnDownloadClicked listener) {
        this.context = context;
        this.persuratanListFolder = persuratanListFolder;
        this.listener = listener;
        if (persuratanListFolder == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String sUrl, boolean bStatus);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_persuratan_pribadi, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Persuratan_List_Folder.Datum listData = persuratanListFolder.data.get(position);
        //Set text

        holder.txtDate.setText(listData.mail_date);
        holder.txtFrom.setText(listData.user_name);
        holder.txtJudul.setText(listData.title);
        holder.txtNomor.setText(String.valueOf(listData.mail_id));
        holder.txtPengirim.setText("Disposisi : " + listData.user_name);

        holder.layoutButton.setVisibility(View.VISIBLE);
        holder.btnDownload_lampiran.setVisibility(View.GONE);
        /*holder.txtDate.setText(listData.mail_date);
        holder.txtTime.setText(listData.mail_time);
        holder.txtJudul.setText(listData.title);
        holder.lbl_Attach.setText(listData.title);
        holder.lbl_Size.setText("");
*/


        if (listData.read_date !=null && listData.read_date.equals("-")){
            holder.img_unread.setVisibility(View.VISIBLE);
        }else{
            holder.img_unread.setVisibility(View.GONE);
        }


        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        final DecimalFormat precision = new DecimalFormat("0.00");
        try{
            Persuratan_Attachment param = new Persuratan_Attachment(AppConstant.HASHID, AppConstant.USER,
                                        AppConstant.REQID, Integer.toString(listData.mail_id));

            Call<Persuratan_Attachment> call = NetworkManager.getNetworkService(context).getMailAttachment(param);
            call.enqueue(new Callback<Persuratan_Attachment>() {
                @Override
                public void onResponse(Call<Persuratan_Attachment> call, Response<Persuratan_Attachment> response) {
                    int code = response.code();
                    if (code == 200){
                        persuratanAttachment = response.body();
                        if (persuratanAttachment.code.equals("00")){
                            for(Persuratan_Attachment.Datum dat : persuratanAttachment.data){
                                listData.attach_link = dat.attcLink;
                                listData.file_size = dat.fileSize;
                                listData.file_type = dat.fileType;
                            }

                            if (listData.file_size != null ){
                                String fileName = listData.attach_link.substring(listData.attach_link.lastIndexOf('/') + 1);
                                double dFileSize = Double.parseDouble(listData.file_size) / 1024;
                                /*holder.lbl_Attach.setText(fileName);
                                holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );

                                holder.btn.setVisibility(View.VISIBLE);*/
                                holder.btnDownload_lampiran.setVisibility(View.VISIBLE);
                            }

                        }

                    }
                }

                @Override
                public void onFailure(Call<Persuratan_Attachment> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        holder.btnDownload_lampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstant.DISPO_ID = Integer.toString(listData.mail_id);
                AppConstant.EMAIL_ID = listData.mail_id;
                //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0

                if (listData.file_size != null && !listData.file_size.equals("")){
                    listener.OnDownloadClicked(listData.attach_link, true);
                }

                //listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
            }
        });

        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);

        if (listData.flag != null){
            if (listData.flag.equals(AppConstant.SEMUA_PESAN)){
                //        holder.imgChecklist.setVisibility(View.VISIBLE);
                holder.layoutButton.setVisibility(View.GONE);
                holder.layoutCheckList.setVisibility(View.VISIBLE);
                holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.check32));
                //ButtonSelected(holder);
            }else if (listData.flag.equals(AppConstant.PILIH_PESAN)){
                holder.layoutButton.setVisibility(View.GONE);
                holder.layoutCheckList.setVisibility(View.VISIBLE);
                //ButtonNotSelected(holder);
                holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.circle));
            }else{
                holder.layoutButton.setVisibility(View.VISIBLE);
                holder.imgChecklist.setVisibility(View.GONE);
            }
        }

        holder.imgChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listData.flag.equals("1")){
                    listData.flag = "2";
                    holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.check32));
                    //ButtonSelected(holder);
                    listener.OnDownloadClicked("", false);
                }else{
                    listData.flag = "1";
                    holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.circle));
                    //ButtonNotSelected(holder);
                    listener.OnDownloadClicked("", false);
                }
            }
        });



        holder.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.mail_id;
                Intent intent = new Intent(context, persuratan_detail.class);
                v.getContext().startActivity(intent);
                UpdateDetail();
            }
        });

        holder.btnLihatSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.mail_id;
                if (AppConstant.FOLDER_DISPOS.equals("FUM")){
                    Intent intent = new Intent(context, persuratan_lihat_surat.class);
                    v.getContext().startActivity(intent);
                }else{
                    Intent intent = new Intent(context, persuratan_lihat_surat_disposisi_distribusi.class);
                    v.getContext().startActivity(intent);
                }

                UpdateDetail();
            }
        });

        holder.listData = listData;
    }

    void UpdateDetail(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Persuratan_Detail params = new Persuratan_Detail(AppConstant.HASHID,
                AppConstant.USER,
                AppConstant.REQID,
                Integer.toString(AppConstant.EMAIL_ID));
        Call<Persuratan_Detail> call = NetworkManager.getNetworkService().getMailDetail(params);
        call.enqueue(new Callback<Persuratan_Detail>() {
            @Override
            public void onResponse(Call<Persuratan_Detail> call, Response<Persuratan_Detail> response) {
                listener.OnDownloadClicked("", false);
            }

            @Override
            public void onFailure(Call<Persuratan_Detail> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return persuratanListFolder.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
        //txtTime,
        txtFrom,
                txtJudul,
                txtNomor,
                txtPengirim
                        ;

        RelativeLayout btnLihatSurat, layoutCheckList,
                btnPrint, btnDownload_lampiran;
        ImageView imgStatus, imgCC, imgChecklist, img_unread;

        ImageView imgInfo,imgDownload;
        TextView textInfo, textDownload;
        LinearLayout layoutButton;
        Persuratan_List_Folder.Datum listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterPersuratanPribadi mCourseAdapter) {
            super(itemView);
            img_unread = (ImageView) itemView.findViewById(R.id.img_unread);
            txtNomor = (TextView)itemView.findViewById(R.id.lbl_nomor);
            txtJudul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            txtFrom = (TextView)itemView.findViewById(R.id.lbl_from);
            imgInfo = (ImageView) itemView.findViewById(R.id.imgInfo);
            imgDownload = (ImageView) itemView.findViewById(R.id.imgDownload);
            textInfo = (TextView)itemView.findViewById(R.id.textInfo);
            textDownload = (TextView)itemView.findViewById(R.id.textDownload);

            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtPengirim = (TextView)itemView.findViewById(R.id.lbl_pengirim);
            //txtTime = (TextView)itemView.findViewById(R.id.text_time);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            btnLihatSurat = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            btnPrint = (RelativeLayout) itemView.findViewById(R.id.btnPrint);
            imgCC = (ImageView)itemView.findViewById(R.id.imageView5);
            imgChecklist = (ImageView)itemView.findViewById(R.id.imageView15);
            layoutCheckList = (RelativeLayout) itemView.findViewById(R.id.layout_checklist);

            btnDownload_lampiran = (RelativeLayout) itemView.findViewById(R.id.btnDownload_lampiran);
            layoutButton = (LinearLayout)itemView.findViewById(R.id.layout_button);
            btnLihatSurat = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
        }

        /*TextView txtDate,
                txtTime,
                txtJudul,
                lbl_Attach,
                lbl_Size,
                txtStatus;
        ;

        RelativeLayout btnDownload,
                btnPrint, layoutAttc;
        ImageView imgStatus,  imgChecklist;

        Persuratan_List_Folder.Datum listData;

        ImageView imgInfo,imgDownload;
        TextView textInfo, textDownload;

        public ViewHolder(View itemView,
                          Context context,
                          final AdapterPersuratanPribadi mCourseAdapter) {
            super(itemView);
            txtJudul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            layoutAttc = (RelativeLayout) itemView.findViewById(R.id.layout_attachment1);

            imgInfo = (ImageView) itemView.findViewById(R.id.imgInfo);
            imgDownload = (ImageView) itemView.findViewById(R.id.imgDownload);
            textInfo = (TextView)itemView.findViewById(R.id.textInfo);
            textDownload = (TextView)itemView.findViewById(R.id.textDownload);

            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            btnPrint = (RelativeLayout) itemView.findViewById(R.id.btnPrint);
            imgChecklist = (ImageView)itemView.findViewById(R.id.imageView15);

            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                    listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
                }
            });


        }*/

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
