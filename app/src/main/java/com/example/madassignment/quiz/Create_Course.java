package com.example.madassignment.quiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.madassignment.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class Create_Course extends AppCompatActivity {
    FirebaseFirestore db;
    Random rand = new Random();
    EditText titleInput;
    Course course;
    Fragment_Course_Description descriptionFragment;
    Fragment_Course_Lessons lessonsFragment;
    private static final int PICK_IMAGE_REQUEST_CODE = 123;
    private StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri mediaUri;
    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        titleInput = findViewById(R.id.course_title_input);
        Button descButton = findViewById(R.id.descButton);
        Button lessonButton = findViewById(R.id.lessonButton);
        ImageButton addPhoto = findViewById(R.id.addPhotoButton);

        descriptionFragment = new Fragment_Course_Description();
        lessonsFragment = new Fragment_Course_Lessons();

        String fragmentTag = getIntent().getStringExtra("DescFrag");
        if (fragmentTag != null && fragmentTag.equals("DescriptionFragment")) {
            showDescriptionFragment();
        }

        descButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = titleInput.getText().toString();
                if (!courseTitle.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", courseTitle);
                    descriptionFragment.setArguments(bundle);
                    showDescriptionFragment();
                } else {
                    Toast.makeText(Create_Course.this, "Please enter title of course!", Toast.LENGTH_SHORT).show();
                }
                descButton.setTextColor(getResources().getColor(R.color.dark_blue));
                lessonButton.setTextColor(getResources().getColor(R.color.blue_grey));
            }
        });

        lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descButton.setTextColor(getResources().getColor(R.color.blue_grey));
                lessonButton.setTextColor(getResources().getColor(R.color.dark_blue));
                showLessonFragment();
            }
        });
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
            }
        });
    }
    private void showDescriptionFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!descriptionFragment.isAdded()) {
            fragmentTransaction.add(R.id.frameLayout, descriptionFragment, "DescriptionFragment");
        }
        if (lessonsFragment.isAdded()) {
            fragmentTransaction.hide(lessonsFragment);
        }
        fragmentTransaction.show(descriptionFragment).commit();
    }

    private void hideDescriptionFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(descriptionFragment).commit();
    }

    private void showLessonFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!lessonsFragment.isAdded()) {
            fragmentTransaction.add(R.id.frameLayout, lessonsFragment, "LessonFragment");
        }
        if (descriptionFragment.isAdded()) {
            fragmentTransaction.hide(descriptionFragment);
        }
        fragmentTransaction.show(lessonsFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // Now, you can proceed to upload the selected image using the Uri (selectedImageUri)
            // You can pass this Uri to your image upload method
            uploadImageToStorage(selectedImageUri);
        }
    }

    private void uploadImageToStorage(Uri imageUri) {
        // Get a reference to the Firebase Storage location where the image will be stored
        String courseID = "C0687878"; // Replace this with your course ID
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("course_images/" + courseID + ".jpg");

        // Upload the image to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully
                    // Now, get the download URL of the uploaded image
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString(); // URL of the uploaded image
                        // Save this imageUrl to your Firestore or Realtime Database along with other course details
                        // For example, you can create a 'courses' collection in Firestore and store the imageUrl along with other course details
                    });
                })
                .addOnFailureListener(exception -> {
                    // Handle unsuccessful uploads
                    // Show an error toast or log the error message
                });
    }


}
