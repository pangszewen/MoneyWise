//package com.example.madassignment.quiz;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.example.madassignment.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class Fragment_Course_Lessons extends Fragment {
//    String courseID, courseTitle, courseDesc, courseLevel, courseMode, courseLanguage;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.fragment__course__lessons, container, false);
//            Button saveButton = view.findViewById(R.id.saveButton);
//            Bundle bundle = getArguments();
//            if (bundle != null){
//                courseTitle = bundle.getString("title");
//                courseDesc = bundle.getString("desc");
//                courseLevel = bundle.getString("level");
//                courseMode = bundle.getString("mode");
//                courseLanguage = bundle.getString("language");
//            }
//            saveButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    createCourse(courseTitle, courseDesc, courseLevel, courseMode, courseLanguage);
//                }
//            });
//            return view;
//        }
//
//        private void createCourse(String title, String description, String level, String language, String mode) {
//            Log.d("TAG", "CreateCourse");
//            CollectionReference collectionReference = db.collection("COURSE");
//            collectionReference.orderBy("dateCreated", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    ArrayList<Course> courseList = new ArrayList<>();
//                    for (QueryDocumentSnapshot dc : task.getResult()) {
//                        Course course = convertDocumentToCourse(dc);
//                        courseList.add(course);
//                    }
//                    courseID = generateCourseID(courseList);
//                    String advisorID = "A0000001"; // Need to change
//                    Course newCourse = new Course(courseID, advisorID, title, description, level, language, "Online");
//                    insertTopicIntoDatabase(newCourse);
//                }
//            });
//        }
//
//        private void insertTopicIntoDatabase(Course course) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("advisorID", course.getAdvisorID());
////            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
////            String formattedDateTime = course.getDateCreated().format(formatter);
////            map.put("dateCreated", formattedDateTime);
//            map.put("title", course.getCourseTitle());
//            map.put("description", course.getCourseDesc());
//            map.put("level", course.getCourseLevel());
//            map.put("language", course.getCourseLanguage());
//            map.put("mode", course.getCourseMode());
//            db.collection("COURSE").document(course.getCourseID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()) {
//                        Log.d("TAG", "uploaded");
//                        Toast.makeText(getActivity(), "Course Created!", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Log.d("TAG", "Failed");
//                        Toast.makeText(getActivity(), "Failed to Create Course", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//
//        public Course convertDocumentToCourse(QueryDocumentSnapshot dc){
//            Course course = new Course();
//            course.setCourseID(dc.getId());
//            course.setAdvisorID(dc.get("advisorID").toString());
//            course.setDateCreated(dc.get("dateCreated").toString());
//            course.setCourseTitle(dc.get("title").toString());
//            course.setCourseDesc(dc.get("description").toString());
//            course.setCourseLevel(dc.get("level").toString());
//            course.setCourseLanguage(dc.get("language").toString());
//            course.setCourseMode(dc.get("mode").toString());
//            return course;
//        }
//
//        private String generateCourseID(ArrayList<Course> courses){
//            String newID;
//            Random rand = new Random();
//            while(true) {
//                int randomNum = rand.nextInt(1000000);
//                newID = "C" + String.format("%07d", randomNum); //C0001000
//                if(checkDuplicatedTopicID(newID, courses))
//                    break;
//            }
//            Log.d("TAG", "This is new courseID " + newID);
//            return newID;
//        }
//
//        private boolean checkDuplicatedTopicID(String newID, ArrayList<Course> courses){
//            for(Course topic: courses){
//                if(newID.equals(topic.getCourseID()))
//                    return false;
//            }
//            Log.d("TAG", "This is checked topic ID " + newID);
//            return true;
//        }
//
//}