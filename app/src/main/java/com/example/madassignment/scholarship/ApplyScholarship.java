package com.example.madassignment.scholarship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madassignment.R;

public class ApplyScholarship extends AppCompatActivity {

    TextView txtTitle, txtAbout, txtValue, txtCriteria, txtWebsite;

    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_scholarship);

        txtTitle = findViewById(R.id.txtTitle);
        txtAbout = findViewById(R.id.txtAbout);
        txtValue = findViewById(R.id.txtValue);
        txtCriteria = findViewById(R.id.txtCriteria);
        txtWebsite = findViewById(R.id.txtWebsite);

        txtTitle.setText(getIntent().getExtras().getString("title"));
        txtAbout.setText(getIntent().getExtras().getString("description"));
        txtValue.setText(getIntent().getExtras().getString("award"));
        txtCriteria.setText(getIntent().getExtras().getString("criteria"));
        txtWebsite.setText(getIntent().getExtras().getString("website"));


        apply = findViewById(R.id.btnApply);

// Set an OnClickListener for the TextView
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getIntent().getExtras().getString("website")));
                startActivity(intent);
            }
        });


        // Header, back to Scholarship Main Page
        ImageView back = findViewById(R.id.imageArrowleft);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event, navigate to another activity
                Intent intent = new Intent(ApplyScholarship.this, FindScholarshipActivity.class);
                startActivity(intent);
            }
        });

        // press bookmark to add scholarship to saved
        // press Apply Scholarship, direct to website

    }

//    private void gotoURL(String website) {
//        Uri uri = Uri.parse(website);
//        startActivity(new Intent(Intent.ACTION_VIEW,uri));
//    }
}