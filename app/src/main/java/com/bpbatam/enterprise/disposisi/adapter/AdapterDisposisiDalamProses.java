package com.bpbatam.enterprise.disposisi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Attachment;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterDisposisiDalamProses extends  RecyclerView.Adapter<AdapterDisposisiDalamProses.ViewHolder>{
    Diposisi_List_Folder persuratanListFolder;
    Disposisi_Attachment disposisiAttachment;
    private Context context;

    public AdapterDisposisiDalamProses(Context context, Diposisi_List_Folder persuratanListFolder, OnDownloadClicked listener) {
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
                R.layout.row_disposisi_dalamproses, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Diposisi_List_Folder.Datum listData = persuratanListFolder.data.get(position);
        //Set text
        holder.txtDate.setText(listData.dispo_date);
        holder.txtTime.setText(listData.read_date);
        holder.txtJudul.setText(listData.title);
        holder.lbl_Attach.setText(listData.title);
        holder.lbl_Size.setText("");


        holder.layoutAttc.setVisibility(View.GONE);
        holder.imgRead.setVisibility(View.GONE);
        if (listData.read_date != null && listData.read_date.equals("-")) holder.imgRead.setVisibility(View.VISIBLE);

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
                                //double dFileSize = Double.parseDouble(listData.file_size) / 1024;
                                holder.lbl_Attach.setText(fileName);
                                //holder.lbl_Size.setText("(" + precision.format(dFileSize) + " kb)" );
                                holder.lbl_Size.setText(listData.file_size);

                                holder.layoutAttc.setVisibility(View.VISIBLE);
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
        if ((position%3)==0){
            holder.txtStatus.setText("ditolak");
            holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_red));
        }else{
            holder.txtStatus.setText("disetujui");
            holder.imgStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ball_green));
        }
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                AppConstant.EMAIL_ID = listData.dispo_id;
                AppConstant.DISPO_ID = Integer.toString(listData.dispo_id);
                /*if (listData.file_size != null && !listData.file_size.equals("")){
                    listener.OnDownloadClicked(listData.attach_link, true);
                }*/
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
                txtJudul,
                txtStatus
        ;

        RelativeLayout btnDownload, layoutAttc;
        ImageView imgStatus, imgRead;

        Diposisi_List_Folder.Datum listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterDisposisiDalamProses mCourseAdapter) {
            super(itemView);
            txtJudul = (TextView)itemView.findViewById(R.id.lbl_Judul);
            layoutAttc = (RelativeLayout) itemView.findViewById(R.id.layout_attachment1);

            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            imgRead = (ImageView) itemView.findViewById(R.id.imgRead);
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
