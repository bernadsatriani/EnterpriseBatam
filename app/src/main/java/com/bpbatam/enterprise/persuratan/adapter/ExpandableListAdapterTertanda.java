package com.bpbatam.enterprise.persuratan.adapter;

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
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.ListData;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterTertanda extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ListData>> _listDataChild;

    public ExpandableListAdapterTertanda(Context context, List<String> listDataHeader,
                                         HashMap<String, List<ListData>> listChildData) {
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
        if (sResult[2].trim().equals("0")){
            txtUnread.setVisibility(View.GONE);
        }

        for (String dat : AppConstant.AryListMenuChek){
            if (dat.equals(sPosition)){
                txtUnread.setVisibility(View.GONE);
                return convertView;
            }
        }
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
            convertView = infalInflater.inflate(R.layout.item_row_header_tertanda, null);
        }

        TextView txtIndex = (TextView) convertView
                .findViewById(R.id.text_index);

        txtIndex.setText(headerTitle);
        if (isExpanded) {
            convertView.setSelected(true);
        } else {
            convertView.setSelected(false);
        }

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
