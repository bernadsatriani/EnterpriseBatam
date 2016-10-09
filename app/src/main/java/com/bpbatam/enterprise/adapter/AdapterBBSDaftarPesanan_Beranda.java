package com.bpbatam.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.BBS_LIST;
import com.bpbatam.enterprise.model.BBS_LIST_ATTACHMENT;
import com.bpbatam.enterprise.model.BBS_LIST_Data;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 9/19/2016.
 */
public class AdapterBBSDaftarPesanan_Beranda extends  RecyclerView.Adapter<AdapterBBSDaftarPesanan_Beranda.ViewHolder>{

    private ArrayList<BBS_LIST_Data> mCourseArrayList;
    private Context context;
    BBS_LIST_ATTACHMENT bbs_list_attachment ;
    public AdapterBBSDaftarPesanan_Beranda(Context context, ArrayList<BBS_LIST_Data> mCourseArrayList, OnDownloadClicked listener) {
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

    void ViewFileSize(final ViewHolder holder, String bbs_id){
        BBS_LIST_ATTACHMENT paramBBBList = new BBS_LIST_ATTACHMENT(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, bbs_id);


        try{
            Call<BBS_LIST_ATTACHMENT> call = NetworkManager.getNetworkService(context).getBBS_List_Attachment(paramBBBList);
            call.enqueue(new Callback<BBS_LIST_ATTACHMENT>() {
                @Override
                public void onResponse(Call<BBS_LIST_ATTACHMENT> call, Response<BBS_LIST_ATTACHMENT> response) {
                    int code = response.code();
                    bbs_list_attachment = response.body();

                    int totalrow =  bbs_list_attachment.getData().length;


                    Object[][]data = bbs_list_attachment.getData();
                    String[] structur = bbs_list_attachment.getStructure();

                    if (totalrow > 0){
                        holder.lbl_Size.setText(String.valueOf(data[0][3]));
                    }
                }
                @Override
                public void onFailure(Call<BBS_LIST_ATTACHMENT> call, Throwable t) {
                    String a = t.getMessage();
                    a = a;
                }
            });
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BBS_LIST_Data listData = mCourseArrayList.get(position);
        //Set text
        holder.txtName.setText(listData.getName());
        holder.txtDate.setText(listData.getBbs_date());
        holder.lbl_Attach.setText(listData.getDescription());
        holder.lbl_Size.setText(listData.getCategory_id());

        //ViewFileSize(holder,Integer.toString(listData.getBbs_id()));
        //holder.txtStatus.setText(listData.getAtr2());

        //AppController.getInstance().displayImage(context,listData.getAtr3(), holder.imgCover);
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.dropbox.com/s/jadu92w71vnku3o/Wireframe.pdf?dl=0
                listener.OnDownloadClicked("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf", true);
            }
        });
        holder.listData = listData;
    }

    @Override
    public int getItemCount() {
        return mCourseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDate,
                txtName,
                lbl_Attach,
                lbl_Size
        ;
        RelativeLayout btnDownload;
        ImageView imgCover;
        BBS_LIST_Data listData;
        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterBBSDaftarPesanan_Beranda mCourseAdapter) {
            super(itemView);
            imgCover = (ImageView)itemView.findViewById(R.id.imageView7);
            txtDate = (TextView)itemView.findViewById(R.id.text_Date);
            txtName = (TextView)itemView.findViewById(R.id.text_Name);
            lbl_Attach = (TextView)itemView.findViewById(R.id.lbl_attach);
            lbl_Size = (TextView)itemView.findViewById(R.id.lbl_size);
            btnDownload = (RelativeLayout) itemView.findViewById(R.id.btnDownload);



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
