package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.Disposisi_Notifikasi;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterNotification extends  RecyclerView.Adapter<AdapterNotification.ViewHolder>{
    Disposisi_Notifikasi disposisiNotifikasi;
    private Context context;

    public AdapterNotification(Context context, Disposisi_Notifikasi disposisiNotifikasi, OnDownloadClicked listener) {
        this.context = context;
        this.disposisiNotifikasi = disposisiNotifikasi;
        this.listener = listener;
        if (disposisiNotifikasi == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(Disposisi_Notifikasi.Datum dat);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_notification, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Disposisi_Notifikasi.Datum listData = disposisiNotifikasi.data.get(position);
        //Set text
        holder.txtDate.setText("");
        holder.txtStatus.setText(listData.receive_date);
        //holder.txtFrom.setText(listData.title);
        holder.txtFrom.setText(listData.title);

        if (listData.notif_type.toUpperCase().equals("DISPO")){
            String sFrom = "Anda Mendapatkan Disposisi dari <b>#</b>";
            sFrom = sFrom.replace("#", listData.sender_name);
            holder.txtJudul.setText(Html.fromHtml(sFrom));
        }else if (listData.notif_type.toUpperCase().equals("MAIL")){
            String sFrom = "Anda Mendapatkan Surat dari <b>#</b>";
            sFrom = sFrom.replace("#", listData.sender_name);
            holder.txtJudul.setText(Html.fromHtml(sFrom));
        }else{
            //holder.txtFrom.setText("Anda Mendapatkan (CC) Disposisi");
            String sFrom = "Anda Mendapatkan tembusan (CC) Disposisi dari <b>#</b>";
            sFrom = sFrom.replace("#", listData.sender_name);
            holder.txtJudul.setText(Html.fromHtml(sFrom));
        }


        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDownloadClicked(listData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return disposisiNotifikasi.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtFrom,
                txtJudul,
                txtStatus,
                txtDate
        ;

        LinearLayout layout_row;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterNotification mCourseAdapter) {
            super(itemView);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.lbl_status);
            txtFrom = (TextView)itemView.findViewById(R.id.lbl_from);
            txtJudul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
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
