package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Board_Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private RecyclerView recyclerView;
    private BoardAdapter adapter;
    private DatabaseReference mDatabaseRef;
    private List<Board_List> boardList;
    private FrameLayout derivationPage;
    private String category, title, postId;
    private Button page1, page2, page3, page4, page5;
    private int currentPage = 1;
    private int itemsPerPage = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        derivationPage = findViewById(R.id.DerivationPage);
        recyclerView = findViewById(R.id.board_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardList = new ArrayList<>();
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);
        page4 = findViewById(R.id.page4);
        page5 = findViewById(R.id.page5);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData");

        // 초기 페이지 로드
        loadPage(currentPage);

        page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(1);
            }
        });

        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(2);
            }
        });

        page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(3);
            }
        });

        page4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(4);
            }
        });

        page5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(5);
            }
        });

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
                    Intent intent = new Intent(Board_Activity.this, Board_Activity.class);
                    startActivity(intent);
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
                Intent intent = new Intent(Board_Activity.this, CreatePost_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void loadPage(final int page) {
        Query query = mDatabaseRef.orderByChild("time");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                boardList.clear();
                int startIndex = (page - 1) * itemsPerPage;
                int endIndex = page * itemsPerPage;
                int currentItem = 0;

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if (currentItem >= startIndex && currentItem < endIndex) {
                        category = snapshot.child("category").getValue(String.class);
                        title = snapshot.child("title").getValue(String.class);
                        postId = snapshot.child("postId").getValue(String.class);
                        boardList.add(new Board_List(category, title, postId));
                    }
                    if (currentItem >= endIndex) {
                        break;
                    }
                    currentItem++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
