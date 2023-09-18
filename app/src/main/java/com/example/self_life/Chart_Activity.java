package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Chart_Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private FrameLayout dataInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //PieChart pieChart = findViewById(R.id.chart1);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        dataInput = findViewById(R.id.chartPlus);

        /*
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
*/

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Chart_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Chart_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Chart_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Chart_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chart_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });
        dataInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chart_Activity.this, Input_Fund_Data.class);
                startActivity(intent);
            }
        });
    }
}