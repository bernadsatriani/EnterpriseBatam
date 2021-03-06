package com.bpbatam.enterprise.disposisi.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterDisposisiTertanda extends  RecyclerView.Adapter<AdapterDisposisiTertanda.ViewHolder>{

    private ArrayList<ListData> mCourseArrayList;
    private Context context;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public AdapterDisposisiTertanda(Context context, ArrayList<ListData> mCourseArrayList) {
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
                R.layout.row_disposisi_detail_tertanda, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        holder.txtID.setText(Integer.toString(position + 1));
        holder.txt1.setText(listData.getNama());
        holder.txt2.setText(listData.getAlamat());
        holder.txt3.setText(listData.getAtr1());
        holder.txt4.setText(listData.getAtr2());
        holder.txt5.setText(listData.getAtr3());
        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtID,
        txt1,
        txt2,
        txt3,
        txt4,
        txt5
        ;

        ListData listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterDisposisiTertanda mCourseAdapter) {
            super(itemView);
            txtID = (TextView)itemView.findViewById(R.id.text_index);
            txt1 = (TextView)itemView.findViewById(R.id.text_1);
            txt2 = (TextView)itemView.findViewById(R.id.text_2);
            txt3 = (TextView)itemView.findViewById(R.id.text_3);
            txt4 = (TextView)itemView.findViewById(R.id.text_4);
            txt5 = (TextView)itemView.findViewById(R.id.text_5);
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
