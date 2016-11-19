package com.bpbatam.enterprise.bbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterKategory extends  RecyclerView.Adapter<AdapterKategory.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;

    public AdapterKategory(Context context, ArrayList<ListData> mCourseArrayList, OnDownloadClicked listener) {
        this.context = context;
        this.mCourseArrayList = mCourseArrayList;
        this.listener = listener;

        if (mCourseArrayList == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String sKode, String sDes);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_category, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtIsi.setText(listData.getAtr2());

        holder.txtIsi.setTextColor(context.getResources().getColor(R.color.black));

        if (listData.getAtr1().equals(AppConstant.sCategoryID)){
            holder.txtIsi.setTextColor(context.getResources().getColor(R.color.colorSearch));
        }
        holder.txtIsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtIsi.setTextColor(context.getResources().getColor(R.color.colorSearch));
                listener.OnDownloadClicked(listData.getAtr1(), listData.getAtr2());
            }
        });
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

        TextView
                txtIsi

        ;

        ImageView imgCover;
        ListData listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterKategory mCourseAdapter) {
            super(itemView);
            txtIsi = (TextView)itemView.findViewById(R.id.text_isi);

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
