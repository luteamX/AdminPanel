package com.example.ideahack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TopVoters extends AppCompatActivity {

    HashMap<String, Integer> map = new HashMap<String, Integer>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("userinfo");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_voters);


        map.put("anik", 4);    //DEMO
        map.put("chomok", 6);
        map.put("tonmoy", 2);

        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            //note.setDocumentId(documentSnapshot.getId());
                            String username = note.getUsername();
                            String uid = note.getUserid();
                            int givevote = note.getGiveVote();

                            Toast.makeText(TopVoters.this, username+" "+ uid+"  "+givevote, Toast.LENGTH_SHORT).show();
                            map.put(username, givevote);
                        }

                    }
                });

        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        for (Object e : a) {
            String person = ((Map.Entry<String, Integer>) e).getKey()  + " ";
            String result =  ((Map.Entry<String, Integer>) e).getValue() + "";



        }
    }
}
