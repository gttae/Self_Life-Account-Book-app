package com.example.self_life;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class findId_Activity extends AppCompatActivity
{
    private EditText userName, userDOB, userPhoneNumber;
    private DatabaseReference mDatabaseRef;

    private Button findId;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_findid);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life");
        userName = findViewById(R.id.eT1Et);
        userDOB = findViewById(R.id.eT2Et);
        userPhoneNumber = findViewById(R.id.eT3Et);
        findId = findViewById(R.id.btn2Btn);
        findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Query query = mDatabaseRef.orderByChild("Name").equalTo(userName.getText().toString().trim());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String name = snapshot.child("phoneNumber").getValue(String.class);
                            if (userPhoneNumber.getText().toString().trim().equals(name)) {
                                String userEmail = snapshot.child("userId").child("emailId").getValue(String.class);
                                Toast.makeText(findId_Activity.this,userEmail, Toast.LENGTH_SHORT).show();
                                break; // 원하는 데이터를 찾았으므로 더 이상 반복할 필요가 없습니다.
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 에러 처리
                    }
                });
            }
        });
    }

}