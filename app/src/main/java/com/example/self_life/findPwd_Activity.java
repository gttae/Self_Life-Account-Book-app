package com.example.self_life;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class findPwd_Activity extends AppCompatActivity
{
    private EditText userName, userEmail, userDOB, userPhoneNumber;
    private Button findPwd;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_findpass);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life");
        userName = findViewById(R.id.eT2Et);
        userEmail = findViewById(R.id.eT3Et);
        userDOB = findViewById(R.id.eT4Et);
        userPhoneNumber = findViewById(R.id.eT5Et);
        findPwd = findViewById(R.id.btn2Btn);

        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Query query = mDatabaseRef.orderByChild("phoneNumber").equalTo(userPhoneNumber.getText().toString().trim());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String name = snapshot.child("Name").getValue(String.class);
                            String DOB = snapshot.child("DOB").getValue(String.class);
                            if ((userName.getText().toString().trim().equals(name)) && (userDOB.getText().toString().trim().equals(DOB))) {
                                String email = snapshot.child("userId").child("emailId").getValue(String.class);
                                if(userEmail.getText().toString().trim().equals(email)){
                                    mFirebaseAuth.sendPasswordResetEmail(userEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(findPwd_Activity.this,"메일보내기 성공", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
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