package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public AdapterNotification(Context context, Disposisi_Notifikasi disposisiNotifikasi) {
        this.context = context;
        this.disposisiNotifikasi = disposisiNotifikasi;
        if (disposisiNotifikasi == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_notification, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Disposisi_Notifikasi.Datum listData = disposisiNotifikasi.data.get(position);
        //Set text
        holder.txtDate.setText(listData.receive_date);
        holder.txtFrom.setText(listData.sender_name);
        holder.txtJudul.setText(listData.title);
        holder.txtStatus.setText(listData.location);

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

        public ViewHolder(View itemView,
                          Context context,
                          final AdapterNotification mCourseAdapter) {
            super(itemView);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.lbl_status);
            txtFrom = (TextView)itemView.findViewById(R.id.lbl_from);
            txtJudul = (TextView)itemView.findViewById(R.id.lbl_Judul);
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
