package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.AdapterDisposisiRiwayat;
import com.bpbatam.enterprise.model.DISPOSISI_Category;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterDisposisiRiwayat_Beranda extends  RecyclerView.Adapter<AdapterDisposisiRiwayat_Beranda.ViewHolder>{
    DISPOSISI_Category disposisiCategory;
    private Context context;

    public AdapterDisposisiRiwayat_Beranda(Context context, DISPOSISI_Category disposisiCategory, OnDownloadClicked listener) {
        this.context = context;
        this.disposisiCategory = disposisiCategory;
        this.listener = listener;
        if (disposisiCategory == null) {
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
                R.layout.row_disposisi_riwayat, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DISPOSISI_Category.Datum listData = disposisiCategory.data.get(position);
        //Set text
        holder.txtDate.setText(listData.createTime);
        holder.txtTime.setText(listData.createTime);
        holder.lbl_Attach.setText(listData.description);
        holder.lbl_Size.setText("");

        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return disposisiCategory.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtTime,
                lbl_Attach,
                lbl_Size,
                txtStatus
        ;

        RelativeLayout btnDownload;
        ImageView imgStatus;

        DISPOSISI_Category.Datum listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterDisposisiRiwayat_Beranda mCourseAdapter) {
            super(itemView);

            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);

            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                    listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
                }
            });
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
