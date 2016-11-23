package com.bpbatam.enterprise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.USER_Info;
import com.bpbatam.enterprise.model.net.NetworkManager;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by setia.n on 11/19/2016.
 */

public class frag_profile extends Fragment {

    TextView txtName,
    txtPerusahaan,
    txtDepartemen,
    txtGolongan,
    txtPosisi,
    txtTelfInternal,
    txtTelfGenggam,
    txtMail;

    USER_Info userInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);

        GetDataUser();
    }

    void InitControl(View v){
        txtName = (TextView)v.findViewById(R.id.text_Name);
        txtPerusahaan = (TextView)v.findViewById(R.id.text_perusahaan);
        txtDepartemen = (TextView)v.findViewById(R.id.text_departemen);
        txtGolongan = (TextView)v.findViewById(R.id.text_golongan);
        txtPosisi = (TextView)v.findViewById(R.id.text_posisi);
        txtTelfInternal = (TextView)v.findViewById(R.id.text_telf_internal);
        txtTelfGenggam = (TextView)v.findViewById(R.id.text_telf_genggam);
        txtMail = (TextView)v.findViewById(R.id.text_mail);
        txtPerusahaan.setText("-");
        txtDepartemen.setText("-");
        txtGolongan.setText("-");
        txtPosisi.setText("-");
        txtTelfInternal.setText("-");
        txtTelfGenggam.setText("-");
        txtMail.setText("-");
    }

    void GetDataUser(){
        try {
            AppConstant.HASHID = AppController.getInstance().getHashId(AppConstant.USER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try{
            USER_Info param = new USER_Info(AppConstant.HASHID, AppConstant.USER, AppConstant.REQID, AppConstant.USER);
            Call<USER_Info> call = NetworkManager.getNetworkService(getActivity()).getUserInfo(param);
            call.enqueue(new Callback<USER_Info>() {
                @Override
                public void onResponse(Call<USER_Info> call, Response<USER_Info> response) {
                    int code = response.code();
                    if (code == 200){
                        userInfo = response.body();
                        if (userInfo.code.equals("00")){
                            txtName.setText(AppConstant.USER_NAME);

                            for(USER_Info.Datum dat : userInfo.data){
                                txtPerusahaan.setText("-");
                                txtDepartemen.setText(dat.dept_name);
                                txtGolongan.setText(dat.job_level);
                                txtPosisi.setText("-");
                                txtTelfInternal.setText("-");
                                txtTelfGenggam.setText("-");
                                txtMail.setText("-");
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<USER_Info> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}
