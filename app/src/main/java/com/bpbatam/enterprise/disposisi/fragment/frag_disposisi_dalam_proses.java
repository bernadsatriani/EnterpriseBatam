package com.bpbatam.enterprise.disposisi.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.disposisi.adapter.ViewPagerAdapterDisposisiPermohonan;
import com.bpbatam.enterprise.persuratan.adapter.ViewPagerAdapterPersuratanPermohonan;

import ui.QuickAction.ActionItem;
import ui.QuickAction.QuickAction;

/**
 * Created by User on 9/19/2016.
 */
public class frag_disposisi_dalam_proses extends Fragment {

    //action id
    private static final int ID_RIWAYAT     = 1;
    private static final int ID_EXSTERNAL   = 2;

    ImageView imgMenu;
    CharSequence Titles[]={"Pribadi","Umum"};
    int Numboftabs =2;

    ViewPager pager;
    ViewPagerAdapterDisposisiPermohonan adapter;
    TabLayout tabs;

    TextView txtLabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disposisi_permohonan, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    void InitControl(View v){
        ActionItem riwayatItem 	= new ActionItem(ID_RIWAYAT, "Riwayat", getResources().getDrawable(R.drawable.ball_red));
        ActionItem externalItem 	= new ActionItem(ID_EXSTERNAL, "External", getResources().getDrawable(R.drawable.ball_red));

        imgMenu = (ImageView)v.findViewById(R.id.imageView);
        txtLabel = (TextView)v.findViewById(R.id.view2);
        txtLabel.setText("Dalam Proses");
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (TabLayout)v.findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterDisposisiPermohonan(getFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

        riwayatItem.setSticky(true);
        externalItem.setSticky(true);

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(getActivity(), QuickAction.VERTICAL);

        //add action items into QuickAction
        quickAction.addActionItem(riwayatItem);
        quickAction.addActionItem(externalItem);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                ActionItem actionItem = quickAction.getActionItem(pos);

                //here we can filter which action item was clicked with pos or actionId parameter
                if (actionId == ID_RIWAYAT) {

                } else if (actionId == ID_EXSTERNAL) {

                }
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAction.show(v);
            }
        });
    }
}
