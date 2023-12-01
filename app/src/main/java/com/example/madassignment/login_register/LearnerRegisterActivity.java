package com.example.madassignment.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LearnerRegisterActivity extends AppCompatActivity{

    EditText editTextname,editTextage,editTextemail,editTextpassword,editTextcpassword;
    AutoCompleteTextView spinner_gender;
    Button btn_reg;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    ImageButton btn_back;
    String uid;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_register);
        mAuth=FirebaseAuth.getInstance();
        editTextname=findViewById(R.id.editTextName);
        editTextage=findViewById(R.id.editTextAge);
        editTextemail=findViewById(R.id.editTextEmail);
        editTextpassword=findViewById(R.id.editTextPassword);
        editTextcpassword=findViewById(R.id.editTextCPassword);
        btn_reg=findViewById(R.id.btn_signup);
        btn_back=findViewById(R.id.btn_back);
        progressBar=findViewById(R.id.progressBar);
        fstore=FirebaseFirestore.getInstance();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Select_Role_Activity.class));
                finish();
            }
        });

        spinner_gender=findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> g_adapter=ArrayAdapter.createFromResource(
                this,
                R.array.genders,
                R.layout.dropdown_item
        );
        spinner_gender.setAdapter(g_adapter);
        spinner_gender.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LearnerRegisterActivity.this,spinner_gender.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name,gender,age,email,password,cpassword;
                name=editTextname.getText().toString();
                gender=spinner_gender.getText().toString();
                age=editTextage.getText().toString();
                email=editTextemail.getText().toString();
                password=editTextpassword.getText().toString();
                cpassword=editTextcpassword.getText().toString();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(LearnerRegisterActivity.this,"Enter name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(gender)){
                    Toast.makeText(LearnerRegisterActivity.this,"Select gender",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(age)){
                    Toast.makeText(LearnerRegisterActivity.this,"Enter age",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LearnerRegisterActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LearnerRegisterActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cpassword)){
                    Toast.makeText(LearnerRegisterActivity.this,"Please confirm your password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(cpassword)){
                    Toast.makeText(LearnerRegisterActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LearnerRegisterActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    uid=mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fstore.collection("USER_DETAILS").document(uid);
                                    Map<String,Object> userdetails=new HashMap<>();
                                    userdetails.put("name",name);
                                    userdetails.put("gender",gender);
                                    userdetails.put("role","Learner");
                                    userdetails.put("age",age);
                                    documentReference.set(userdetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("TAG", "User info saved");
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LearnerRegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}