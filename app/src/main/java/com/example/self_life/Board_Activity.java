package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board_Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private ImageButton searching;
    private RecyclerView recyclerView;
    private BoardAdapter adapter;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private List<Board_List> boardList;
    private FrameLayout derivationPage;
    private String category, title, postId;
    private EditText queryTitle;
    private String searchQuery, userId;
    private int currentPage = 1;
    private int itemsPerPage = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        derivationPage = findViewById(R.id.DerivationPage);
        queryTitle = findViewById(R.id.sample_EditText);
        searching = findViewById(R.id.button18);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        recyclerView = findViewById(R.id.board_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardList = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData");

        // 초기 페이지 로드
        loadPage(currentPage);


        adapter = new BoardAdapter(this, boardList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String postId) {
                Intent intent = new Intent(Board_Activity.this, Select_Board.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Board_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Board_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Board_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Board_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        derivationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId.equals("dC9EUCkwqGeqQELJIYHLOEwYJzk2")){
                    Intent intent = new Intent(Board_Activity.this, CreateNotice_Activity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Board_Activity.this, CreatePost_Activity.class);
                    startActivity(intent);
                }
            }
        });

        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuery = queryTitle.getText().toString();
                if(searchQuery.equals("")){
                    loadPage(currentPage);
                }
                performSearch(searchQuery);
            }
        });

    }

    private void loadPage(final int page) {
        Query query = mDatabaseRef.orderByChild("time");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                boardList.clear();
                List<Board_List> noticeList = new ArrayList<>();


                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    // 기존 코드와 동일
                    category = snapshot.child("category").getValue(String.class);
                    title = snapshot.child("title").getValue(String.class);
                    postId = snapshot.child("postId").getValue(String.class);

                    // "공지" 카테고리면 따로 리스트에 추가
                    if ("공지".equals(category)) {
                        noticeList.add(new Board_List(category, title, postId));
                    } else {
                        // "공지" 카테고리가 아니면 기존 방식대로 리스트에 추가
                            boardList.add(new Board_List(category, title, postId));

                    }
                }
                Collections.reverse(boardList);
                // "공지" 카테고리의 항목들을 boardList의 맨 앞에 추가
                boardList.addAll(0, noticeList);
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void searchloadPage(final int page) {
        Query query = mDatabaseRef.orderByChild("time");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                boardList.clear();
                List<Board_List> noticeList = new ArrayList<>();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        // 기존 코드와 동일
                        category = snapshot.child("category").getValue(String.class);
                        title = snapshot.child("title").getValue(String.class);
                        postId = snapshot.child("postId").getValue(String.class);
                        if ("공지".equals(category)) {
                            noticeList.add(new Board_List(category, title, postId));
                        } else {
                            // "공지" 카테고리가 아니면 기존 방식대로 리스트에 추가
                            if (searchQuery.isEmpty() || title.toLowerCase().contains(searchQuery.toLowerCase())) {
                                boardList.add(new Board_List(category, title, postId));
                            }
                        }
                        // 검색어가 비어 있거나, 타이틀에 검색어가 포함된 경우에만 리스트에 추가
                }
                Collections.reverse(boardList);
                boardList.addAll(0, noticeList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 처리
            }
        });
    }
    private void performSearch(String query) {
        // 검색 버튼을 클릭할 때 호출되는 메서드로, 현재 페이지 기준으로 검색을 수행
        searchloadPage(currentPage);
    }
}
