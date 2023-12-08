package com.example.madassignment.Expenses;

import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase_Expenses {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
    ArrayList<ForumTopic> forumTopics = new ArrayList<>();


    public void getForumData(){
        forumTopics.clear();
        Log.d("TAG", "getForumData");
        ArrayList<ForumTopic> forumTopics = new ArrayList<>();

        CollectionReference collectionReference = db.collection("FORUM_TOPIC");


        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> documents = value.getDocuments();
                for(DocumentSnapshot document : documents){
                    forumTopics.add(document.toObject(ForumTopic.class));
                    Log.d("TAG", Integer.toString(forumTopics.size()));
                }
            }
        });



        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        String topicID = document.getId();
                        String userID = document.get("userID").toString();
                        String dateString = document.get("datePosted").toString(); // Replace this with your string
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        LocalDateTime datePosted = LocalDateTime.parse(dateString, formatter);
                        String subject = document.get("subject").toString();
                        String description = document.get("description").toString();

                        // cast the returned Object to Long, then convert it to an int
                        Long likesLong = (Long) document.get("likes");
                        int likes = likesLong != null ? likesLong.intValue() : 0;
                        // Firestore retrieves List objects as List<Object> and not as ArrayList<String>
                        List<Object> commentIDObjects = (List<Object>) document.get("commentID");
                        ArrayList<String> commentID = new ArrayList<>();
                        if (commentIDObjects != null) {
                            for (Object obj : commentIDObjects) {
                                if (obj instanceof String) {
                                    commentID.add((String) obj);
                                }
                            }
                        }
                        ForumTopic topic = new ForumTopic(topicID, userID, datePosted, subject, description, likes, commentID);
                        Log.d("TAG", topic.getTopicID());
                        setForumTopics(topic);
                    }
                }
            }
        });
        Log.d("TAG", "after reading, " + Integer.toString(forumTopics.size()));
    }

    public void setForumTopics(ForumTopic topic){
        Log.d("TAG", "setForumTopics");
        forumTopics.add(topic);
        Log.d("TAG", Integer.toString(forumTopics.size()));
    }

    public ArrayList<ForumTopic> getForumTopics(){
        Log.d("TAG", "getForumTopics");
        Log.d("TAG", Integer.toString(forumTopics.size()));
        return forumTopics;
    }


    public interface ForumDataCallback {
        void onForumDataReceived(ForumTopic forumTopic);
    }




    public void getForumData(final ForumDataCallback callback){
        Log.d("TAG", "getForumData");
        ArrayList<ForumTopic> forumTopics = new ArrayList<>();

        CollectionReference collectionReference = db.collection("FORUM_TOPIC");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("TAG", "task successful");
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Log.d("TAG", documentSnapshot.getData().toString());
                    forumTopics.add(documentSnapshot.toObject(ForumTopic.class));
                    Log.d("TAG", Integer.toString(forumTopics.size()));
                }
                callback.onForumDataReceived(forumTopics);
            }
        });

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("TAG", "complete");
                if (task.isSuccessful()) {
                    Log.d("TAG", "task successful");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        forumTopics.add(document.toObject(ForumTopic.class));
                        Log.d("TAG", Integer.toString(forumTopics.size()));
                    }
                    callback.onForumDataReceived(forumTopics); // Pass the data through the callback
                }
            }
        });

    }
    */
}