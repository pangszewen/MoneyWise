//package com.example.madassignment.quiz;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.madassignment.R;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class Fragment_Course_Description extends Fragment {
//    View view;
//    FirebaseFirestore db;
//    String courseDesc;
//    String courseLevel;
//    String courseLanguage;
//    String courseTitle;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment__course__description, container, false);
//        db = FirebaseFirestore.getInstance();
//
//        EditText title = view.findViewById(R.id.titleInput);
//        EditText desc = view.findViewById(R.id.descInput);
//        AutoCompleteTextView levelSpinner = view.findViewById(R.id.levelInput);
//        AutoCompleteTextView languageSpinner = view.findViewById(R.id.languageInput);
//        Button nextButton = view.findViewById(R.id.nextButton);
//
//        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(requireContext(), R.array.level_array, android.R.layout.simple_spinner_item);
//        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(requireContext(), R.array.language_array, android.R.layout.simple_spinner_item);
//        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        levelSpinner.setAdapter(adapterLevel);
//        languageSpinner.setAdapter(adapterLanguage);
//        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                courseLevel = parent.getItemAtPosition(position).toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//
//        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                courseLanguage = parent.getItemAtPosition(position).toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//        courseTitle = title.getText().toString();
//        courseDesc = desc.getText().toString();
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment_course_lessons = new Fragment_Course_Lessons();
//                Bundle bundle = new Bundle();
//                bundle.putString("title", courseTitle);
//                bundle.putString("desc", courseDesc);
//                bundle.putString("language", courseLanguage);
//                bundle.putString("level", courseLevel);
//                bundle.putString("mode","Online");
//                fragment_course_lessons.setArguments(bundle);
//
//                if (getActivity() != null) {
//                    ((Create_Course)getActivity()).replaceFragment(new Fragment_Course_Lessons());
//                }
//            }
//        });
//        return view;
//    }
//}