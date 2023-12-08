package com.example.madassignment.quiz;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fragment_Course_Description extends Fragment {
    View view;
    FirebaseFirestore db;
    String courseLevel;
    String courseLanguage;
    String courseTitle;
    String courseID;
    Boolean saveState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__course__description, container, false);
        db = FirebaseFirestore.getInstance();

        Button saveDesc = view.findViewById(R.id.saveDescButton);
        EditText desc = view.findViewById(R.id.description_input);
        Spinner levelSpinner = view.findViewById(R.id.level);
        Spinner languageSpinner = view.findViewById(R.id.language);

        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(requireContext(), R.array.level_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(requireContext(), R.array.language_array, android.R.layout.simple_spinner_item);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapterLevel);
        languageSpinner.setAdapter(adapterLanguage);

        Bundle bundle = getArguments();
        if (bundle != null) {
            courseTitle = bundle.getString("title");
        }

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseLevel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseLanguage = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection
            }
        });

        saveDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseDesc = desc.getText().toString();
                if (!courseDesc.isEmpty() && !courseTitle.isEmpty())
                    createCourse(courseTitle, courseDesc, courseLevel, courseLanguage);
                else {
                    Log.d("TAG", courseDesc);
                    Log.d("TAG", courseTitle);
                    Toast.makeText(getActivity(), "Please complete all info before saving", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void addDetails(String courseTitle, String courseDesc, String courseLevel, String courseLanguage) {
        Log.d("TAG", "createCourse");
        Create_Course activity = (Create_Course) getActivity();
        if (activity != null) {
            Course course = activity.course;
            if (course != null) {
                courseID = course.getCourseID();
            }
            System.out.println(course.getCourseID());
            DocumentReference docRef = db.collection("COURSE").document(courseID);
            docRef.update("description", courseDesc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Description Saved", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Document updated successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to Save Description", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Error updating document", e);
                        }
                    });

            docRef = db.collection("COURSE").document("C0645449");
            docRef.update("level", courseLevel)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Level updated successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating level", e);
                        }
                    });
            docRef = db.collection("COURSE").document("C0645449");
            docRef.update("language", courseLanguage)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Language updated successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating language", e);
                        }
                    });
        }
    }

    private void createCourse(String  title, String description, String level, String language) {
        Log.d("TAG", "createCourse");
        CollectionReference collectionReference = db.collection("COURSE");
        collectionReference.orderBy("dateCreated", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Course> courseList = new ArrayList<>();
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    Course course = convertDocumentToCourse(dc);
                    courseList.add(course);
                }
                courseID = generateCourseID(courseList);
                String advisorID = "A0000001"; // Need to change
                Course newCourse = new Course(courseID, advisorID, title, description, level, language, "Online", 0);
                insertTopicIntoDatabase(newCourse);
            }
        });
    }

    public Course convertDocumentToCourse(QueryDocumentSnapshot dc){
        Course course = new Course();
        course.setCourseID(dc.getId());
        course.setAdvisorID(dc.get("advisorID").toString());
        course.setDateCreated(dc.get("dateCreated").toString());
        course.setCourseTitle(dc.get("title").toString());
        course.setCourseDesc(dc.get("description").toString());
        course.setCourseLevel(dc.get("level").toString());
        course.setCourseLanguage(dc.get("language").toString());
        course.setCourseMode(dc.get("mode").toString());
        course.setCourseNumOfStudents(Integer.parseInt(dc.get("num of students").toString()));
        return course;
    }

    private void insertTopicIntoDatabase(Course course) {
        Map<String, Object> map = new HashMap<>();
        map.put("advisorID", course.getAdvisorID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDateTime = course.getDateCreated().format(formatter);
//        map.put("dateCreated", formattedDateTime);
        map.put("title", course.getCourseTitle());
        map.put("description", course.getCourseDesc());
        map.put("level", course.getCourseLevel());
        map.put("language", course.getCourseLanguage());
        map.put("mode", course.getCourseMode());
        map.put("num of students", course.getCourseNumOfStudents());
        db.collection("COURSE").document(course.getCourseID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    saveState = true;
                    Log.d("TAG", "uploaded");
                    Toast.makeText(getActivity(), "Course Created!", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("TAG", "Failed");
                    Toast.makeText(getActivity(), "Failed to Create Course", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String generateCourseID(List<Course> courses){
        String newID = null;
        Random rand = new Random();
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = "C" + String.format("%07d", randomNum); //C0001000
            if(checkDuplicatedTopicID(newID, courses))
                break;
        }
        Log.d("TAG", "This is new courseID " + newID);
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID, List<Course> courses){
        for(Course topic: courses){
            if(newID.equals(topic.getCourseID()))
                return false;
        }
        Log.d("TAG", "This is checked topic ID " + newID);
        return true;
    }

}