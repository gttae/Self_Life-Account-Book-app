package com.example.self_life;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class CreatePost_Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private static final int REQUEST_IMAGE_PICK = 2;
    private EditText editTextTitle;
    private EditText editTextContent;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ImageView galleryImageView;
    private Uri selectedImageUri;
    private LinearLayout post;
    private List<Uri> selectedImageUris = new ArrayList<>();
    private int selectedImageCount = 0;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef2;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private int uploadedImageCount;
    private String uid, postId, commentId;
    private long postTime;
    private static final int MAX_IMAGE_COUNT = 5;

    ImageView[] imageViews = new ImageView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        editTextTitle = findViewById(R.id.TitleEt);
        editTextContent = findViewById(R.id.WriteEt);
        radioGroup = findViewById(R.id.categoryRadioGroup);
        galleryImageView = findViewById(R.id.GallaryIv);
        post = findViewById(R.id.WriteLl);
        // Firebase Storage 초기화
        mFirebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        imageViews[0] = findViewById(R.id.UserGallaryIv1);
        imageViews[1] = findViewById(R.id.UserGallaryIv2);
        imageViews[2] = findViewById(R.id.UserGallaryIv3);
        imageViews[3] = findViewById(R.id.UserGallaryIv4);
        imageViews[4] = findViewById(R.id.UserGallaryIv5);

        for (int i = 0; i < imageViews.length; i++) {
            final int index = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedImageUris.size() > index) {
                        // 해당 이미지뷰에 선택한 이미지를 표시
                        imageViews[index].setImageURI(selectedImageUris.get(index));
                        removeSelectedImage(index);
                    }
                }
            });
        }

        galleryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageCount < MAX_IMAGE_COUNT) {
                    try {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        imagePickerLauncher.launch(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CreatePost_Activity.this, "최대 5개의 이미지를 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        // 선택한 이미지를 이미지뷰에 표시
                        if (selectedImageCount < MAX_IMAGE_COUNT) {
                            imageViews[selectedImageCount].setImageURI(selectedImageUri);
                            imageViews[selectedImageCount].setVisibility(View.VISIBLE);
                            // 이미지 Uri를 리스트에 추가
                            selectedImageUris.add(selectedImageUri);
                            selectedImageCount++;
                        } else {
                            Toast.makeText(CreatePost_Activity.this, "최대 5개의 이미지를 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(CreatePost_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(CreatePost_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(CreatePost_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(CreatePost_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatePost_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void createPost() {
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        uploadedImageCount = 0;
        List<String> imageUrls = new ArrayList<>();
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedRadioButtonId);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        if (title.isEmpty() || content.isEmpty() || selectedRadioButtonId == -1) {
            Toast.makeText(this, "제목, 내용, 카테고리를 모두 선택하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!selectedImageUris.isEmpty()) {
            for (Uri imageUri : selectedImageUris) {
                String imageName = "image_" + System.currentTimeMillis() + ".jpg";
                final StorageReference imageRef = storageRef.child("images/" + imageName);

                imageRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // 이미지 업로드 성공 시 이미지 다운로드 URL을 가져옴
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        imageUrls.add(imageUrl);
                                        uploadedImageCount++;
                                        // 모든 이미지 업로드가 완료되면 게시글 데이터를 Firebase Realtime Database에 저장
                                        if (uploadedImageCount == selectedImageUris.size()) {
                                            savePostToDatabase(title, content, radioButton.getText().toString(), uid, imageUrls);
                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // 이미지 업로드 실패 처리
                                Toast.makeText(CreatePost_Activity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            // 이미지를 선택하지 않은 경우에도 게시글 데이터를 Firebase Realtime Database에 저장
            savePostToDatabase(title, content, radioButton.getText().toString(), uid, imageUrls);
        }
    }

    private void savePostToDatabase(String title, String content, String category, String userName, List<String> imageUrls) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData");
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userName);
        mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userNametemp = dataSnapshot.child("UserInfo").child("userNickName").getValue(String.class);
                mDatabaseRef.child(postId).child("writer").setValue(uid);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 오류 처리
            }
        });
        postId = mDatabaseRef.push().getKey();
        postTime = System.currentTimeMillis();
        mDatabaseRef.child(postId).child("postId").setValue(postId);
        mDatabaseRef.child(postId).child("title").setValue(title);
        mDatabaseRef.child(postId).child("content").setValue(content);
        mDatabaseRef.child(postId).child("category").setValue(category);
        mDatabaseRef.child(postId).child("time").setValue(postTime);
        mDatabaseRef.child(postId).child("image").setValue(imageUrls);
        mDatabaseRef.child(postId).child("comment").setValue("");
        Toast.makeText(CreatePost_Activity.this, "게시글 작성이 성공되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CreatePost_Activity.this, Board_Activity.class);
        startActivity(intent);
        finish();
    }
    private void removeSelectedImage(int index) {
        if (selectedImageUris.size() > index) {
            selectedImageUris.remove(index);
            selectedImageCount--;
            // 이미지를 지운 후에 해당 이미지뷰를 숨김
            imageViews[index].setVisibility(View.GONE);
        }
    }
}
