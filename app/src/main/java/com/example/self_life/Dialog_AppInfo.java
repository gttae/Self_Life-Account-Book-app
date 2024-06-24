package com.example.self_life;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dialog_AppInfo extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_appinfo);

        prepareListData();

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
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // 그룹 데이터 추가
        listDataHeader.add("1.내역을 잘못입력했는데 어떻게 수정하나요?");
        listDataHeader.add("2.게시글 신고는 어떻게 하나요?");
        listDataHeader.add("3.앱을 계속 사용안하면 휴면계정이 되나요? ");
        listDataHeader.add("4.신용카드나 은행 계좌는 어떻게 연동 하나요?");
        listDataHeader.add("5.추천 모델 변경은 어떻게 하나요?");
        listDataHeader.add("6.생활용품 1회 사용량은 변경 가능하며 기본으로 제공된 사용량은 정확한가요?");
        listDataHeader.add("7.친구나 가족과 데이터를 공유할 수 있는 기능이 있나요?");
        listDataHeader.add("8.수입 지출을 더 빠르게 입력하는 방법은 없나요?");
        listDataHeader.add("9.계정 삭제는 어떻게 할 수 있나요?");
        listDataHeader.add("10.닉네임, 비밀번호 변경이 가능한가요?");
        listDataHeader.add("11.앱 업데이트를 위한 일정은 어떻게 되나요??");
        listDataHeader.add("12.기타 문의는 어떻게 하나요?");

        // 자식 데이터 추가
        List<String> question1 = new ArrayList<String>();
        question1.add("지출 수입 내역은 메뉴에서 차트를 클릭하신 후 화면 하단에 “지출 수입 내역 변경하기”에서 수정하실 수 있습니다.");

        List<String> question2 = new ArrayList<String>();
        question2.add("게시글 우측 하단의 신고 버튼으로 신고할 수 있습니다.");

        List<String> question3 = new ArrayList<String>();
        question3.add("아니요. 휴면 계정이 되지 않습니다.");

        List<String> question4 = new ArrayList<String>();
        question4.add("저희 어플에서는 제공하지 않는 기능입니다.");

        List<String> question5 = new ArrayList<String>();
        question5.add("차트페이지에서 우측 상단 \"추천모델\"을 선택 후, 설문을 통해 추천 모델을 변경할 수 있습니다.");

        List<String> question6 = new ArrayList<String>();
        question6.add("조사를 통해 평균값을 나타낸 것이며 변경은 1회 사용량에 입력 하시면 변경 가능합니다.");

        List<String> question7 = new ArrayList<String>();
        question7.add("저희 어플에서는 아직 제공하지 않는 기능입니다.");

        List<String> question8 = new ArrayList<String>();
        question8.add("바탕화면에 기본 위젯을 추가 하여 입력하는 방법이 있습니다.");

        List<String> question9 = new ArrayList<String>();
        question9.add("계정 삭제는 아주 간단합니다. 마이페이지로 이동한 후 '회원 탈퇴'를 클릭하고 그에 따른 단계를 따르시면 됩니다.");

        List<String> question10 = new ArrayList<String>();
        question10.add("네 가능합니다. 마이페이지로 이동한 후 \"회원정보 수정하기\"들어가시면 쉽게 변경 가능합니다");

        List<String> question11 = new ArrayList<String>();
        question11.add("앱 오류나 업데이트 내용이 있을 경우에 업데이트를 진행하고, 월마다 주기적으로 업데이트를 진행합니다.");

        List<String> question12 = new ArrayList<String>();
        question12.add("현재페이지의 하단에 있는 \"1:1 문의하기\"에서 문의하실 수 있습니다.");

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
        listDataChild.put(listDataHeader.get(10), question11);
        listDataChild.put(listDataHeader.get(11), question12);

        expandableListView = findViewById(R.id.AppInfoexpandableListView);
        expandableListAdapter = new AppInfoExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

    }
}
