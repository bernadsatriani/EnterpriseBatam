package com.bpbatam.enterprise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.bpbatam.enterprise.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 9/23/2016.
 */
public class Frag_Home_Detail extends Fragment {

    ArrayList<HashMap<String, Object>> lstGrid;
    HashMap<String, Object> mapGrid;
    SimpleAdapter adpGridView;

    Spinner spnMonth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_detail, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    void InitControl(View v){
        spnMonth = (Spinner)v.findViewById(R.id.spinner_month);
        LineChart chart = (LineChart) v.findViewById(R.id.chart);
        chart = new LineChart(v.getContext());
        //getActivity().setContentView(chart);

        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.layout_chart);
        rl.addView(chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        chart.setData(data);
        chart.animateY(5000);
        chart.invalidate();

        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        chart.getLayoutParams().height = height;
        chart.getLayoutParams().width = width;

        FillSpinner();
    }


    void FillSpinner(){
        lstGrid = new ArrayList<HashMap<String,Object>>();
        mapGrid = new HashMap<String, Object>();
        mapGrid.put("month", "January");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("month", "February");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("month", "March");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("month", "April");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("month", "May");
        lstGrid.add(mapGrid);

        mapGrid = new HashMap<String, Object>();
        mapGrid.put("month", "June");
        lstGrid.add(mapGrid);

        adpGridView = new SimpleAdapter(getActivity(), lstGrid, R.layout.spinner_row_single,
                new String[] {"month"},
                new int[] {R.id.text_isi});

        spnMonth.setAdapter(adpGridView);
    }
}
