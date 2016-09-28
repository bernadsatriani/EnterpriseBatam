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
public class AdapterKomentar extends  RecyclerView.Adapter<AdapterKomentar.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;

    public AdapterKomentar(Context context, ArrayList<ListData> mCourseArrayList) {
        this.context = context;
        this.mCourseArrayList = mCourseArrayList;
        if (mCourseArrayList == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_bbs_komentar, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtName.setText(listData.getAtr1());
        holder.txtIsi.setText(listData.getAtr2());
        holder.txtTime.setText(listData.getAtr3());

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

        TextView txtName,
                txtIsi,
                txtTime
        ;

        ImageView imgCover;
        ListData listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterKomentar mCourseAdapter) {
            super(itemView);
            imgCover = (ImageView)itemView.findViewById(R.id.imageView7);
            txtName = (TextView)itemView.findViewById(R.id.text_Name);
            txtIsi = (TextView)itemView.findViewById(R.id.text_isi);
            txtTime = (TextView)itemView.findViewById(R.id.text_time);

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
