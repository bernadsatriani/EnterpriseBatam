package com.bpbatam.enterprise.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.adapter.adapter_menu.CardPagerAdapter;
import com.bpbatam.enterprise.adapter.adapter_menu.ShadowTransformer;
import com.bpbatam.enterprise.bbs.bbs_menu;
import com.bpbatam.enterprise.disposisi.disposisi_menu;
import com.bpbatam.enterprise.model.AuthUser;
import com.bpbatam.enterprise.model.ListData;
import com.bpbatam.enterprise.persuratan.persuratan_menu;

import java.util.ArrayList;

/**
 * Created by setia.n on 10/31/2016.
 */

public class fragment_menu extends Fragment {

    ArrayList<ListData> AryListData;
    ListData listData;

    private ViewPager pager;
    private ShadowTransformer mCardShadowTransformer;

    private ShadowTransformer mFragmentCardShadowTransformer;
    private CardPagerAdapter mCardAdapter;

    ImageView img1, img2, img3, img4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);

        mCardAdapter = new CardPagerAdapter(getActivity(), new CardPagerAdapter.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(int position) {
                Intent mIntent;
                switch (position){
                    case 0:
                        mIntent = new Intent(getActivity(), bbs_menu.class);
                        startActivity(mIntent);
                        break;
                    case 1:
                        mIntent = new Intent(getActivity(), persuratan_menu.class);
                        startActivity(mIntent);
                        break;
                    case 2:
                        mIntent = new Intent(getActivity(), disposisi_menu.class);
                        startActivity(mIntent);
                        break;
                }
            }
        });

        mCardShadowTransformer = new ShadowTransformer(pager, mCardAdapter);

        pager.setAdapter(mCardAdapter);
        pager.setPageTransformer(false, mCardShadowTransformer);
        pager.setOffscreenPageLimit(3);

        img1.setColorFilter(getActivity().getResources().getColor(R.color.white));
        img2.setColorFilter(getActivity().getResources().getColor(R.color.grey));
        img3.setColorFilter(getActivity().getResources().getColor(R.color.grey));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {  }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        img1.setColorFilter(getActivity().getResources().getColor(R.color.white));
                        img2.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                        img3.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                        break;
                    case 1:
                        img1.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                        img2.setColorFilter(getActivity().getResources().getColor(R.color.white));
                        img3.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                        break;
                    case 2:
                        img1.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                        img2.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                        img3.setColorFilter(getActivity().getResources().getColor(R.color.white));
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        AuthUser authUser = AppController.getInstance().getSessionManager().getUserProfile();

        if (authUser.data.size() > 0){
            for (AuthUser.Datum dat : authUser.data){
                AppConstant.USER_NAME = dat.user_name;
            }
        }
    }

    void InitControl(View v){
        pager = (ViewPager)v.findViewById(R.id.pager);
        img1 = (ImageView)v.findViewById(R.id.img1);
        img2 = (ImageView)v.findViewById(R.id.img2);
        img3 = (ImageView)v.findViewById(R.id.img3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
