package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Question_Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Question_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Question_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Question_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Question_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // 데이터 초기화
        prepareListData();

        // 그룹 클릭 이벤트 처리
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false; // 그룹 클릭 이벤트를 처리하지 않음
            }
        });

        // 자식 클릭 이벤트 처리
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return true;
            }
        });
    }

    // 데이터를 초기화하는 메서드
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // 그룹 데이터 추가
        listDataHeader.add("질문 모시깽이1");
        listDataHeader.add("질문 모시깽이2");
        listDataHeader.add("질문 모시깽이3");
        listDataHeader.add("질문 모시깽이4");
        listDataHeader.add("질문 모시깽이5");
        listDataHeader.add("질문 모시깽이6");
        listDataHeader.add("질문 모시깽이7");
        listDataHeader.add("질문 모시깽이8");
        listDataHeader.add("질문 모시깽이9");
        listDataHeader.add("질문 모시깽이10");

        // 자식 데이터 추가
        List<String> question1 = new ArrayList<String>();
        question1.add("답변 모시깽이1");

        List<String> question2 = new ArrayList<String>();
        question2.add("답변 모시깽이2");

        List<String> question3 = new ArrayList<String>();
        question3.add("답변 모시깽이3");

        List<String> question4 = new ArrayList<String>();
        question4.add("답변 모시깽이4");

        List<String> question5 = new ArrayList<String>();
        question5.add("답변 모시깽이5");

        List<String> question6 = new ArrayList<String>();
        question6.add("답변 모시깽이6");

        List<String> question7 = new ArrayList<String>();
        question7.add("답변 모시깽이7");

        List<String> question8 = new ArrayList<String>();
        question8.add("답변 모시깽이8");

        List<String> question9 = new ArrayList<String>();
        question9.add("답변 모시깽이9");

        List<String> question10 = new ArrayList<String>();
        question10.add("답변 모시깽이10");

        listDataChild.put(listDataHeader.get(0), question1); // 질문 1의 답변
        listDataChild.put(listDataHeader.get(1), question2); // 질문 2의 답변
        listDataChild.put(listDataHeader.get(2), question3); // 질문 3의 답변
        listDataChild.put(listDataHeader.get(3), question4);
        listDataChild.put(listDataHeader.get(4), question5);
        listDataChild.put(listDataHeader.get(5), question6);
        listDataChild.put(listDataHeader.get(6), question7);
        listDataChild.put(listDataHeader.get(7), question8);
        listDataChild.put(listDataHeader.get(8), question9);
        listDataChild.put(listDataHeader.get(9), question10);

        expandableListView = findViewById(R.id.expandableListView);
        expandableListAdapter = new MyExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

    }
}
