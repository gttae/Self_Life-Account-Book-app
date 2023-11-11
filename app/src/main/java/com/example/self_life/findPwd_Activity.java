package com.example.self_life;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView firstText, secondText;
    private Button findPwd;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_findpass);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData");
        userName = findViewById(R.id.eT2Et);
        userEmail = findViewById(R.id.eT3Et);
        userDOB = findViewById(R.id.eT4Et);
        userPhoneNumber = findViewById(R.id.eT5Et);
        firstText = findViewById(R.id.firstText);
        secondText = findViewById(R.id.secondText);
        findPwd = findViewById(R.id.btn2Btn);

        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Query query = mDatabaseRef.orderByChild("UserInfo/userPhoneNumber").equalTo(userPhoneNumber.getText().toString().trim());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String name = snapshot.child("UserInfo").child("userName").getValue(String.class);
                            String DOB = snapshot.child("UserInfo").child("userDOB").getValue(String.class);
                            if ((userName.getText().toString().trim().equals(name)) && (userDOB.getText().toString().trim().equals(DOB))) {
                                String email = snapshot.child("UserInfo").child("userEmail").getValue(String.class);
                                if(userEmail.getText().toString().trim().equals(email)){
                                    mFirebaseAuth.sendPasswordResetEmail(userEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                firstText.setVisibility(View.VISIBLE);
                                                secondText.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(findPwd_Activity.this,"메일보내기 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}