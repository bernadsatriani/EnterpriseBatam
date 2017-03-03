package com.bpbatam.enterprise.disposisi.adapter;

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
import com.bpbatam.enterprise.disposisi.disposisi_lihat_surat;
import com.bpbatam.enterprise.model.Disposisi_Attachment;
import com.bpbatam.enterprise.model.Disposisi_Riwayat;
import com.bpbatam.enterprise.model.Disposisi_Riwayat_Detail;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterDisposisiRiwayatDetail extends  RecyclerView.Adapter<AdapterDisposisiRiwayatDetail.ViewHolder>{
    Disposisi_Riwayat_Detail persuratanListFolder;
    Disposisi_Attachment disposisiAttachment;
    private Context context;

    public AdapterDisposisiRiwayatDetail(Context context, Disposisi_Riwayat_Detail persuratanListFolder, OnDownloadClicked listener) {
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
                R.layout.row_disposisi_riwayat_detail, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Disposisi_Riwayat_Detail.Datum listData = persuratanListFolder.data.get(position);
        //Set text
        holder.txtDate.setText(listData.dispo_date);
        holder.txtFrom.setText(listData.sender);
        holder.txtNomor.setText(listData.dispo_num);
        holder.txtPengirim.setText("Ke : " + listData.create_name);
        holder.txtStatus.setText("Isi Disposisi : " +listData.isi_dispo);
        /*holder.layoutAttc.setVisibility(View.GONE);
        holder.imgRead.setVisibility(View.GONE);*/
        //if (listData.read_date != null && listData.read_date.equals("-")) holder.imgRead.setVisibility(View.VISIBLE);

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
                txtNomor,
                txtPengirim,
                txtStatus

                        ;
        ImageView imgStatus, imgRead, imgChecklist;

        public ViewHolder(View itemView,
                          Context context,
                          final AdapterDisposisiRiwayatDetail mCourseAdapter) {
            super(itemView);
            txtNomor = (TextView)itemView.findViewById(R.id.lbl_nomor);
            txtFrom = (TextView)itemView.findViewById(R.id.lbl_from);
            txtPengirim = (TextView)itemView.findViewById(R.id.lbl_pengirim);
                        txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.lbl_status);
            //lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            //lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            imgRead = (ImageView) itemView.findViewById(R.id.imgRead);
            imgChecklist = (ImageView)itemView.findViewById(R.id.imageView15);

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
