package com.bpbatam.enterprise.bbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.BBS_edit_berita;
import com.bpbatam.enterprise.bbs.bbs_komentar_activity;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.BBS_List_ByCategory;
import com.bpbatam.enterprise.model.ListData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterBBSDaftarPesanan extends  RecyclerView.Adapter<AdapterBBSDaftarPesanan.ViewHolder>{
    private BBS_List_ByCategory bbs_list;
    private Context context;

    public AdapterBBSDaftarPesanan(Context context, BBS_List_ByCategory bbs_list, OnDownloadClicked listener) {
        this.context = context;
        this.bbs_list = bbs_list;
        this.listener = listener;
        if (bbs_list == null) {
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
                R.layout.row_bbs_daftar_pesanan, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BBS_List_ByCategory.Datum listData = bbs_list.data.get(position);
        //Set text
        if (listData.read_sts.equals("N")){
            holder.txtName.setText(listData.name);
            holder.txtDate.setText(listData.bbs_date);
            holder.lbl_Attach.setText(listData.title);
            holder.lbl_Judul.setText(listData.title);
            holder.txtDivisi.setText(listData.title);
            holder.lbl_Size.setText("");


            DecimalFormat precision = new DecimalFormat("0.00");
            holder.btnDokumen.setVisibility(View.GONE);
            try{
                if (listData.attc_size != null ){
                    String fileName = listData.attc_link.substring(listData.attc_link.lastIndexOf('/') + 1);
                    double dFileSize = Double.parseDouble(listData.attc_size) / 1024;
                    holder.lbl_Attach.setText(fileName);
                    holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );
                    holder.btnDokumen.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){

            }

            //holder.txtStatus.setText(listData.getAtr2());

            //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);

            holder.btnDokumen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppConstant.EMAIL_ID = listData.bbs_id;
                    AppConstant.BBS_LINK = listData.attc_link;
                    try{
                        AppConstant.BBS_LINK = listData.attc_link;
                        listener.OnDownloadClicked(listData.attc_link, true);
                    }catch (Exception e){
                        AppConstant.BBS_LINK = "";
                        listener.OnDownloadClicked("", false);
                    }

                }
            });

            holder.btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.EMAIL_ID = listData.bbs_id;
                    try{
                        AppConstant.BBS_LINK = listData.attc_link;
                    }catch (Exception e){
                        AppConstant.BBS_LINK = "";
                    }
                    Intent mIntent = new Intent(context, BBS_edit_berita.class);
                    mIntent.putExtra("BBS_NAME", listData.name);
                    mIntent.putExtra("BBS_JUDUL", listData.title);
                    mIntent.putExtra("BBS_SIZE", holder.lbl_Size.getText().toString());
                    mIntent.putExtra("BBS_ISI", listData.title);
                    mIntent.putExtra("BBS_DATE", listData.bbs_date);
                    mIntent.putExtra("BBS_READ", listData.read_sts);
                    AppConstant.EMAIL_ID = listData.bbs_id;
                    context.startActivity(mIntent);
                }
            });
            holder.listData = listData;
        }

    }

    @Override
    public int getItemCount() {
        return bbs_list.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtName,
                txtDivisi,
                txtStatus,
                lbl_Attach,
                lbl_Size,
                lbl_Judul
        ;
        RelativeLayout btnDownload, btnDokumen;
        ImageView imgCover, imgStatus;
        BBS_List_ByCategory.Datum listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterBBSDaftarPesanan mCourseAdapter) {
            super(itemView);
            imgCover = (ImageView)itemView.findViewById(R.id.imageView7);
            imgStatus = (ImageView)itemView.findViewById(R.id.img_status);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtName = (TextView)itemView.findViewById(R.id.text_Name);
            txtDivisi = (TextView)itemView.findViewById(R.id.text_Division);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            lbl_Judul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            btnDokumen = (RelativeLayout) itemView.findViewById(R.id.layout_dokumen);
            /*btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                    listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
                }
            });*/


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
