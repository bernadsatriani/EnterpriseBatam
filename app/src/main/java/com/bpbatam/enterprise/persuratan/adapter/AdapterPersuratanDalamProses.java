package com.bpbatam.enterprise.persuratan.adapter;

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
import com.bpbatam.enterprise.disposisi.disposisi_detail;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_Detail;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.persuratan.persuratan_detail;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterPersuratanDalamProses extends  RecyclerView.Adapter<AdapterPersuratanDalamProses.ViewHolder>{

    Persuratan_List_Folder persuratanListFolder;
    private Context context;

    public AdapterPersuratanDalamProses(Context context, Persuratan_List_Folder persuratanListFolder, OnDownloadClicked listener) {
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
                R.layout.row_persuratan_dalamproses, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Persuratan_List_Folder.Datum listData = persuratanListFolder.data.get(position);
        //Set text
        holder.txtDate.setText(listData.mail_date);
        holder.txtTime.setText(listData.read_date);
        holder.lbl_Attach.setText(listData.title);
        holder.lbl_Size.setText("");

        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);
        if (listData.flag != null){
            if (listData.flag.equals(AppConstant.TIDAK_PESAN)){
                holder.imgChecklist.setVisibility(View.GONE);
            }else if (listData.flag.equals(AppConstant.PILIH_PESAN)){
                holder.imgChecklist.setVisibility(View.VISIBLE);
            }else if (listData.flag.equals(AppConstant.SEMUA_PESAN)){
                holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.check32));
                holder.imgChecklist.setVisibility(View.VISIBLE);
            }
        }

        holder.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.EMAIL_ID = listData.mail_id;
                Intent intent = new Intent(context, persuratan_detail.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                AppConstant.EMAIL_ID = listData.mail_id;
                listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
            }
        });

        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return persuratanListFolder.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtTime,
                lbl_Attach,
                lbl_Size,
                txtStatus;
        ;

        RelativeLayout btnDownload,
                btnPrint;
        ImageView imgStatus,  imgChecklist;

        Persuratan_List_Folder.Datum listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterPersuratanDalamProses mCourseAdapter) {
            super(itemView);

            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            btnPrint = (RelativeLayout) itemView.findViewById(R.id.btnPrint);
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
