package com.bpbatam.enterprise.persuratan.adapter;

import android.content.Context;
import android.os.Build;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtDate.setText("Rabu, 21 Sept 2016");
        holder.txtTime.setText("12:37 PM");
        holder.lbl_Attach.setText(listData.getAtr1());
        holder.lbl_Size.setText(listData.getAtr2());

        if (listData.getJekel() != null){
            if (listData.getJekel().equals(AppConstant.SEMUA_PESAN)){
                holder.imgChecklist.setVisibility(View.VISIBLE);
                holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.check32));
                ButtonSelected(holder);
            }else if (listData.getJekel().equals(AppConstant.PILIH_PESAN)){
                holder.imgChecklist.setVisibility(View.VISIBLE);
                ButtonNotSelected(holder);
                holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.circle));
            }else{
                holder.imgChecklist.setVisibility(View.GONE);
            }
        }

        holder.imgChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listData.getJekel().equals("1")){
                    mCourseArrayList.get(position).setJekel("2");
                    ButtonSelected(holder);
                    listener.OnDownloadClicked("", false);
                }else{

                    mCourseArrayList.get(position).setJekel("1");
                    ButtonNotSelected(holder);
                    listener.OnDownloadClicked("", false);
                }
            }
        });
        holder.listData = listData;
    }

    void ButtonSelected(AdapterPersuratanDikembalikan.ViewHolder holder){
        holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.check32));
        holder.imgDownload.setColorFilter(context.getResources().getColor(R.color.white));
        holder.textDownload.setTextColor(context.getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.btnDownload.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_blue));
        }
    }

    void ButtonNotSelected(AdapterPersuratanDikembalikan.ViewHolder holder){
        holder.imgChecklist.setImageDrawable(context.getResources().getDrawable(R.drawable.circle));
        holder.imgDownload.setColorFilter(context.getResources().getColor(R.color.grey));
        holder.textDownload.setTextColor(context.getResources().getColor(R.color.grey));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.btnDownload.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_all_transparant_blue));
        }
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

        ImageView imgDownload;
        TextView  textDownload;

        public ViewHolder(View itemView,
                          Context context,
                          final AdapterPersuratanDikembalikan mCourseAdapter) {
            super(itemView);
            imgDownload = (ImageView) itemView.findViewById(R.id.imgDownload);
            textDownload = (TextView)itemView.findViewById(R.id.textDownload);

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
