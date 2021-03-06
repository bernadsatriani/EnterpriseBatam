package com.bpbatam.enterprise.disposisi.adapter;

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
import com.bpbatam.enterprise.CC_DisposActivity;
import com.bpbatam.enterprise.DistribusiActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.AdapterCC;
import com.bpbatam.enterprise.disposisi.disposisi_detail;
import com.bpbatam.enterprise.disposisi.disposisi_lihat_surat;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Attachment;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.Disposisi_Detail_CC;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.model.net.NetworkManager;
import com.bpbatam.enterprise.persuratan.adapter.AdapterPersuratanUmum;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterDisposisiPribadi extends  RecyclerView.Adapter<AdapterDisposisiPribadi.ViewHolder>{
    Diposisi_List_Folder persuratanListFolder;
    Disposisi_Detail_CC disposisiDetailCc;
    Disposisi_Attachment disposisiAttachment;
    private Context context;

    Disposisi_Detail disposisiDetail;
    public AdapterDisposisiPribadi(Context context, Diposisi_List_Folder persuratanListFolder, OnDownloadClicked listener) {
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
                R.layout.row_disposisi_pribadi, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Diposisi_List_Folder.Datum listData = persuratanListFolder.data.get(position);
        //Set text
        holder.txtDate.setText((listData.dispo_date == null) ? "" : listData.dispo_date);
        holder.txtFrom.setText(listData.name);
        holder.txtJudul.setText(listData.title);
        holder.txtNomor.setText(listData.dispo_num);
        holder.txtPengirim.setText("Pengirim Awal : " + listData.sender);

        holder.layoutButton.setVisibility(View.VISIBLE);
        holder.btnDownload_lampiran.setVisibility(View.GONE);

        if (listData.read_date !=null && listData.read_date.equals("-")){
            holder.img_unread.setVisibility(View.VISIBLE);
        }else{
            holder.img_unread.setVisibility(View.GONE);
        }

        holder.imgCC.setVisibility(View.GONE);
        //holder.layoutAttc.setVisibility(View.GONE);
        final DecimalFormat precision = new DecimalFormat("0.00");
        try{
            Disposisi_Attachment param = new Disposisi_Attachment(AppConstant.HASHID, AppConstant.USER,
                    AppConstant.REQID, Integer.toString(listData.dispo_id));

            Call<Disposisi_Attachment> call = NetworkManager.getNetworkService(context).getDisposisiAttachment(param);
            call.enqueue(new Callback<Disposisi_Attachment>() {
                @Override
                public void onResponse(Call<Disposisi_Attachment> call, Response<Disposisi_Attachment> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiAttachment = response.body();
                        if (disposisiAttachment.code.equals("00")){
                            for(Disposisi_Attachment.Datum dat : disposisiAttachment.data){
                                listData.attach_link = dat.attc_link;
                                listData.file_size = dat.file_size;
                                listData.file_type = dat.file_type;
                            }

                            if (listData.file_size != null ){
                                String fileName = listData.attach_link.substring(listData.attach_link.lastIndexOf('/') + 1);
                                //double dFileSize = Double.parseDouble(listData.file_size); /// 1024;
                                /*holder.lbl_Attach.setText(fileName);
                                holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );

                                holder.layoutAttc.setVisibility(View.VISIBLE);*/
                                holder.btnDownload_lampiran.setVisibility(View.VISIBLE);
                            }

                        }

                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Attachment> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            Disposisi_Detail_CC param = new Disposisi_Detail_CC(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(listData.dispo_id));
            Call<Disposisi_Detail_CC> call = NetworkManager.getNetworkService(context).getDispoCC(param);
            call.enqueue(new Callback<Disposisi_Detail_CC>() {
                @Override
                public void onResponse(Call<Disposisi_Detail_CC> call, Response<Disposisi_Detail_CC> response) {
                    int code = response.code();
                    if (code == 200){
                        disposisiDetailCc = response.body();
                        int iIndex = 0;
                        if (disposisiDetailCc.code.equals("00")){
                            holder.imgCC.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Disposisi_Detail_CC> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


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

        holder.imgCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.EMAIL_ID = listData.dispo_id;
                AppConstant.DISPO_ID = Integer.toString(listData.dispo_id);
                Intent intent = new Intent(context, CC_DisposActivity.class);
                holder.img_unread.setVisibility(View.GONE);
                context.startActivity(intent);
                UpdateDetail();
            }
        });

        holder.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.dispo_id;
                AppConstant.DISPO_ID = Integer.toString(listData.dispo_id);
                Intent intent = new Intent(context, disposisi_detail.class);
                v.getContext().startActivity(intent);
                holder.img_unread.setVisibility(View.GONE);
                UpdateDetail();
            }
        });

        holder.btnDownload_lampiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.dispo_id;
                AppConstant.DISPO_ID = Integer.toString(listData.dispo_id);
                //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                if (listData.file_size != null && !listData.file_size.equals("")){
                    listener.OnDownloadClicked(listData.attach_link, true);
                }
                //listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
            }
        });

        holder.btnLihatSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.dispo_id;
                AppConstant.DISPO_ID = Integer.toString(listData.dispo_id);
                Intent intent = new Intent(context, disposisi_lihat_surat.class);
                v.getContext().startActivity(intent);
                holder.img_unread.setVisibility(View.GONE);
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

        try{
            Disposisi_Detail params = new Disposisi_Detail(AppConstant.HASHID,
                    AppConstant.USER,
                    AppConstant.REQID,
                    Integer.toString(AppConstant.EMAIL_ID));
            Call<Disposisi_Detail>call = NetworkManager.getNetworkService(context).getDisposisiDetail(params);
            call.enqueue(new Callback<Disposisi_Detail>() {
                @Override
                public void onResponse(Call<Disposisi_Detail> call, Response<Disposisi_Detail> response) {
                    listener.OnDownloadClicked("", false);
                }

                @Override
                public void onFailure(Call<Disposisi_Detail> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

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
        Diposisi_List_Folder.Datum listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterDisposisiPribadi mCourseAdapter) {
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

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
