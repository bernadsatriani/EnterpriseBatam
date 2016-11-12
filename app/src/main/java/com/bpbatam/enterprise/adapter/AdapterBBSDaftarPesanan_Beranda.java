package com.bpbatam.enterprise.adapter;

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
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.ListData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterBBSDaftarPesanan_Beranda extends  RecyclerView.Adapter<AdapterBBSDaftarPesanan_Beranda.ViewHolder>{
    private BBS_LIST bbs_list;
    private Context context;

    public AdapterBBSDaftarPesanan_Beranda(Context context, BBS_LIST bbs_list, OnDownloadClicked listener) {
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
                R.layout.row_bbs_semua_pesanan, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BBS_LIST.Datum listData = bbs_list.data.get(position);
        //Set text
        holder.txtDate.setText(listData.bbs_date);
        holder.txtTime.setText(listData.bbs_time);
        holder.lbl_Attach.setText((listData.title == null) ? "" : listData.title + " " + ((listData.content == null) ? "" : listData.content));
        holder.lbl_Judul.setText((listData.title == null) ? "" : listData.title + " " + ((listData.content == null) ? "" : listData.content));
        //holder.lbl_Isi.setText(listData.content);
        holder.imgPDF.setVisibility(View.GONE);
        holder.lbl_Attach1.setVisibility(View.GONE);
        holder.lbl_Attach.setVisibility(View.GONE);

        holder.lbl_Size.setText("");
        holder.btnDokumen.setVisibility(View.GONE);
        DecimalFormat precision = new DecimalFormat("0.00");
        try{
            if (listData.attc_data.size() > 0){
                for(BBS_LIST.AttcData dat : listData.attc_data){
                    String fileName = dat.attc_link.substring(dat.attc_link.lastIndexOf('/') + 1);
                    double dFileSize = Double.parseDouble(dat.file_size) / 1024;
                    holder.lbl_Attach.setText(fileName);
                    holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );

                    holder.btnDokumen.setVisibility(View.VISIBLE);
                    holder.imgPDF.setVisibility(View.VISIBLE);
                    holder.imgPDF.setVisibility(View.VISIBLE);
                    holder.lbl_Attach1.setVisibility(View.VISIBLE);
                    holder.lbl_Attach.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){

        }
       /* if (listData.get(position).attc_size != null){
            holder.lbl_Size.setText("(" + listData.get(position).attc_size + " mb)");
        }else*/


        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);
        holder.btnDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.EMAIL_ID = listData.bbs_id;
                try{
                    AppConstant.BBS_LINK = listData.attc_data.get(0).attc_link;
                    listener.OnDownloadClicked(listData.attc_data.get(0).attc_link, true);
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
                    AppConstant.BBS_LINK = listData.attc_data.get(0).attc_link;
                }catch (Exception e){
                    AppConstant.BBS_LINK = "";
                }


                Intent mIntent = new Intent(context, BBS_edit_berita.class);
                mIntent.putExtra("BBS_NAME", listData.name);
                mIntent.putExtra("BBS_JUDUL", listData.title);
                mIntent.putExtra("BBS_SIZE", holder.lbl_Size.getText().toString());
                mIntent.putExtra("BBS_ISI", listData.content);
                mIntent.putExtra("BBS_DATE", listData.bbs_date);
                mIntent.putExtra("BBS_READ", listData.read_sts);
                context.startActivity(mIntent);
            }
        });
        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return bbs_list.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtTime,
                txtStatus,
                lbl_Attach,
                lbl_Attach1,
                lbl_Size,
                lbl_Judul
                        ;
        ImageView imgStatus, imgPDF;
        RelativeLayout btnDownload, btnDokumen;
        BBS_LIST.Datum listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterBBSDaftarPesanan_Beranda mCourseAdapter) {
            super(itemView);

            imgPDF = (ImageView)itemView.findViewById(R.id.imageView6);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Attach1 = (TextView)itemView.findViewById(R.id.lbl_attach1);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            lbl_Judul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            //lbl_Isi = (TextView)itemView.findViewById(R.id.lbl_Isi);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            btnDokumen = (RelativeLayout) itemView.findViewById(R.id.layout_dokumen);
            /*btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                    listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
                }
            });
*/

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
