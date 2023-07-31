package com.example.self_life;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Chart_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_main);

        PieChart pieChart = findViewById(R.id.chart1);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(80f, "maths"));
        entries.add(new PieEntry(90f, "Science"));
        entries.add(new PieEntry(75f, "en"));
        entries.add(new PieEntry(60f, "it"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Subjects");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();

        PieChart pieChart2 = findViewById(R.id.chart2); // Changed variable name to pieChart2

        ArrayList<PieEntry> entries1 = new ArrayList<>();
        entries1.add(new PieEntry(80f, "maths"));
        entries1.add(new PieEntry(90f, "Science"));
        entries1.add(new PieEntry(75f, "en"));
        entries1.add(new PieEntry(60f, "it"));

        PieDataSet pieDataSet2 = new PieDataSet(entries1, "Subjects"); // Changed variable name to pieDataSet2
        pieDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData2 = new PieData(pieDataSet2); // Changed variable name to pieData2
        pieChart2.setData(pieData2);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.animateY(1000);
        pieChart2.invalidate();
    }
}