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

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.bbs.bbs_komentar_activity;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterBBSDaftarPesanan extends  RecyclerView.Adapter<AdapterBBSDaftarPesanan.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;

    public AdapterBBSDaftarPesanan(Context context, ArrayList<ListData> mCourseArrayList, OnDownloadClicked listener) {
        this.context = context;
        this.mCourseArrayList = mCourseArrayList;
        this.listener = listener;
        if (mCourseArrayList == null) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtDate.setText("Senin, 29 Agustus 2016, pukul 02:02");
        holder.txtView.setText("202");
        holder.lbl_Attach.setText(listData.getAtr1());
        holder.lbl_Size.setText(listData.getAtr2());

        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);

        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtView,
                lbl_Attach,
                lbl_Size,
                txtStatus,
                btnDownload
        ;

        ImageView imgIndicator, imgCover;
        RelativeLayout rLayout_print,rLayout_komentar;
        ListData listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterBBSDaftarPesanan mCourseAdapter) {
            super(itemView);
            imgCover = (ImageView)itemView.findViewById(R.id.imageView7);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtView = (TextView)itemView.findViewById(R.id.textView);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgIndicator = (ImageView) itemView.findViewById(R.id.img_indicator);
            btnDownload = (TextView)itemView.findViewById(R.id.btnDownload);
            rLayout_print = (RelativeLayout)itemView.findViewById(R.id.layout_print);
            rLayout_komentar = (RelativeLayout)itemView.findViewById(R.id.layout_komentar);

            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                    listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
                }
            });

            rLayout_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            rLayout_komentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), bbs_komentar_activity.class);
                    v.getContext().startActivity(intent);
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
