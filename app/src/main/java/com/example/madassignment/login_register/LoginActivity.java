package com.example.madassignment.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editTextEmail,editTextPassword;
    Button btn_login;
    TextView forget_pw,signup;
    ProgressBar progressBar;

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
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.editTextAge);
        editTextPassword=findViewById(R.id.editTextPassword);
        btn_login=findViewById(R.id.btn_login);
        forget_pw=findViewById(R.id.forget_password);
        signup=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progressBar);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Select_Role_Activity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email=editTextEmail.getText().toString();
                password=editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    Toast.makeText(LoginActivity.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });
    }
}