package com.bpbatam.enterprise.adapter;

/**
 * Created by Setia Nugraha on 16/09/2016.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);
        //final String doctor_id = _doctorId.get((this._listDataHeader.get(groupPosition))).get(childPosition);
        final String childText = String.valueOf(_listDataChild.get((this._listDataHeader.get(groupPosition))).get(childPosition).toString());
        /*final String childCustomer = String.valueOf(_listDataChild.get((this._listDataHeader.get(groupPosition))).get(childPosition).getAlamat());
        final String childAmmount = String.valueOf(_listDataChild.get((this._listDataHeader.get(groupPosition))).get(childPosition).getAtr1());
        final int childImg = _listDataChild.get((this._listDataHeader.get(groupPosition))).get(childPosition).getImg();*/

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_row_detail_expandable, null);
        }

        String sPosition = groupPosition + "" + childPosition;

        TextView txtData = (TextView) convertView.findViewById(R.id.textView);
        TextView txtCount = (TextView) convertView.findViewById(R.id.textStatus1);
        TextView txtUnread = (TextView) convertView.findViewById(R.id.textStatus2);
        LinearLayout LayoutRow = (LinearLayout)convertView.findViewById(R.id.layoutRowDetail);

        LayoutRow.setBackgroundColor(convertView.getResources().getColor(R.color.navigationDrawerBackgroundDetail));

        if (sPosition.equals(AppConstant.POSITION_CHILD)){
            LayoutRow.setBackgroundColor(convertView.getResources().getColor(R.color.navigationDrawerSelectedBackgroundDetail));
        }

        String[] sResult = childText.trim().split("#");
        txtData.setText(sResult[0].trim());
        txtCount.setText(sResult[1].trim());
        txtUnread.setText(sResult[2].trim());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_row_header_expandable, null);
        }


        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textView);

        LinearLayout layoutRowHeader = (LinearLayout) convertView.findViewById(R.id.item_nav_menu_container);
        ImageView imgMenu = (ImageView) convertView.findViewById(R.id.item_nav_menu_icon_image_view);
        ImageView arrow = (ImageView) convertView.findViewById(R.id.imageView10);


        String sPosition = String.valueOf(groupPosition);
        switch (groupPosition){
            case 0:
                if (sPosition.equals(AppConstant.POSITION_CHILD)){
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSearch));
                }else{
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSelectButton));
                }

                imgMenu.setBackgroundResource(R.drawable.gns_home_white);
                arrow.setImageDrawable(null);
                break;
            case 1:
                if (sPosition.equals(AppConstant.POSITION_CHILD)){
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSearch));
                }else{
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSelectButton));
                }
                arrow.setImageDrawable(null);
                imgMenu.setBackgroundResource(R.drawable.flag_grey);

                break;
            case 2:
                imgMenu.setBackgroundResource(R.drawable.gns_email);
                if (isExpanded) {
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSearch));
                    arrow.setImageDrawable(_context.getResources().getDrawable(R.drawable.minus_white));
                } else {
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSelectButton));
                    arrow.setImageDrawable(_context.getResources().getDrawable(R.drawable.plus_white));
                }
                break;

            case 3:
                imgMenu.setBackgroundResource(R.drawable.document_white);
                if (isExpanded) {
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSearch));
                    arrow.setImageDrawable(_context.getResources().getDrawable(R.drawable.minus_white));
                } else {
                    layoutRowHeader.setBackgroundColor(_context.getResources().getColor(R.color.colorSelectButton));
                    arrow.setImageDrawable(_context.getResources().getDrawable(R.drawable.plus_white));
                }
                break;
        }


        if (isExpanded) {
            convertView.setSelected(true);
        } else {
            convertView.setSelected(false);
        }


        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
