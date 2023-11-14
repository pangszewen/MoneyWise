package com.example.madassignment.forum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Forum_CreateTopic_Fragment extends Fragment {
    /*
    FirebaseFirestore db;
    CollectionReference collectionReference;
    Random rand = new Random(1000000);
    EditText ETTopicSubject, ETTopicDescription;
    Button btn_createTopic;

    CoordinatorLayout CLForumNavBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forum__create_topic_, container, false);
        db = FirebaseFirestore.getInstance();
        db.collection("FORUM_TOPIC");

        ETTopicSubject = rootView.findViewById(R.id.ETTopicSubject);
        ETTopicDescription = rootView.findViewById(R.id.ETTopicDescription);
        btn_createTopic = rootView.findViewById(R.id.btn_createTopic);
        CLForumNavBar = getActivity().findViewById(R.id.CLForumNavBar);

        btn_createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TopicSubject = ETTopicSubject.getText().toString();
                String TopicDescription = ETTopicDescription.getText().toString();
                if(createTopic(TopicSubject, TopicDescription))
                    Toast.makeText(getActivity(), "Topic Successfully Posted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Topic Failed to Post", Toast.LENGTH_SHORT).show();

            }
        });


        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            CLForumNavBar.setVisibility(View.INVISIBLE);
                        }else{
                            CLForumNavBar.setVisibility(View.VISIBLE);
                        }
                    }
                });


        // Inflate the layout for this fragment
        return rootView;
    }

    private boolean createTopic(String TopicSubject, String TopicDescription){
        String topicID = generateTopicID();
        String userID = "L0000000";
        ForumTopic newTopic = new ForumTopic(topicID, userID, TopicSubject, TopicDescription);

        return insertTopicIntoDatabase(newTopic);
    }

    private boolean insertTopicIntoDatabase(ForumTopic topic){
        final AtomicBoolean result = new AtomicBoolean(false);
        Map<String, Object> map = new HashMap<>();
        map.put("userID", topic.getUserID());
        map.put("datePosted", topic.getDatePosted());
        map.put("subject", topic.getSubject());
        map.put("description", topic.getDescription());
        map.put("likes", topic.getLikes());
        map.put("commentID", topic.getCommentID());
        db.collection("FORUM_TOPIC").document(topic.getTopicID()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                result.set(true);
            }
        });
        return result.get();
    }

    private String generateTopicID(){
        String newID = null;
        while(true) {
            int randomNum = rand.nextInt();
            newID = "T" + String.format("%07d", randomNum);
            if(checkDuplicatedTopicID(newID))
                break;
        }
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID){
        ArrayList<DocumentSnapshot> documentData = getDocumentData();
        for(DocumentSnapshot document : documentData){
            if(newID.equals(document.getId()))
                return false;
        }
        return true;
    }

    private ArrayList<DocumentSnapshot> getDocumentData(){
        ArrayList<DocumentSnapshot> documentData = new ArrayList<>();
        // Specify the collection reference
        collectionReference = db.collection("FORUM_TOPIC");
        // Retrieve all documents in the collection
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                           documentData.add(document);
                    }
                }
            }
        });
        return documentData;
    }

     */
}