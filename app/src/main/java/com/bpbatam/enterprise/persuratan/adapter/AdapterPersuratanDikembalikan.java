package com.bpbatam.enterprise.persuratan.adapter;

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
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterPersuratanDikembalikan extends  RecyclerView.Adapter<AdapterPersuratanDikembalikan.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;

    public AdapterPersuratanDikembalikan(Context context, ArrayList<ListData> mCourseArrayList, OnDownloadClicked listener) {
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
                R.layout.row_persuratan_dikembalikan, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtDate.setText("Rabu, 21 Sept 2016");
        holder.txtTime.setText("12:37 PM");
        holder.lbl_Attach.setText(listData.getAtr1());
        holder.lbl_Size.setText(listData.getAtr2());

        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);
        if (listData.getNama() != null){
            if (listData.getNama().equals(AppConstant.TIDAK_PESAN)){
                holder.imgChecklist.setVisibility(View.GONE);
            }else if (listData.getNama().equals(AppConstant.PILIH_PESAN)){
                holder.imgChecklist.setVisibility(View.VISIBLE);
            }else if (listData.getNama().equals(AppConstant.SEMUA_PESAN)){
                holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.check32));
                holder.imgChecklist.setVisibility(View.VISIBLE);
            }
        }

        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtTime,
                lbl_Attach,
                lbl_Size,
                txtStatus;
        ;

        RelativeLayout btnDownload;
        ImageView imgStatus, imgChecklist;

        ListData listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterPersuratanDikembalikan mCourseAdapter) {
            super(itemView);

            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtStatus = (TextView)itemView.findViewById(R.id.text_status);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            imgStatus = (ImageView) itemView.findViewById(R.id.imageView5);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);
            imgChecklist = (ImageView)itemView.findViewById(R.id.imageView15);
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
