package com.example.madassignment.login_register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String uid;
    ImageButton btn_back;
    Button btn_logout;
    RelativeLayout user_details;
    TextView detail_name,detail_role;
    ImageView gender_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        btn_back=findViewById(R.id.btn_back);
        detail_name=findViewById(R.id.detail_name);
        detail_role=findViewById(R.id.detail_role);
        gender_icon=(ImageView) findViewById(R.id.gender_icon);
        btn_logout=findViewById(R.id.btn_logout);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        uid=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference=fStore.collection("USER_DETAILS").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                detail_name.setText(value.getString("name"));
                detail_role.setText(value.getString("role"));
                if (value.getString("gender").equals("Male")) {
                    gender_icon.setImageResource(R.drawable.baseline_male_24);
                }
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
}