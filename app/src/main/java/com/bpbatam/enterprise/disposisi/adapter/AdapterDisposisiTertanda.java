package com.bpbatam.enterprise.disposisi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
                R.layout.row_disposisi_tertanda, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListData listData = mCourseArrayList.get(position);
        //Set text
        holder.txtSusunan.setText(listData.getAtr1());
        holder.txt1.setText(listData.getAtr2());
        holder.txt2.setText(listData.getAtr3());

        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);

        if ((position%2) == 0) {
            holder.layoutRow.setBackgroundColor(context.getResources().getColor(R.color.navigationDrawerBackgroundDetail));
        }
        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txt1,
                txt2,
                txtSusunan
        ;

        LinearLayout layoutRow;
        ListData listData;
        public ViewHolder(View itemView,
                          Context context,
                          final AdapterDisposisiTertanda mCourseAdapter) {
            super(itemView);
            txtSusunan = (TextView)itemView.findViewById(R.id.text_susunan);
            txt1 = (TextView)itemView.findViewById(R.id.text_1);
            txt2 = (TextView)itemView.findViewById(R.id.text_2);
            layoutRow = (LinearLayout)itemView.findViewById(R.id.layout_row);

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
