package com.mbds.hotelnfc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RoomsListActivity extends AppCompatActivity {

    private ListView listView;
    private RoomAdapter adapter;
    private TextView tv3;
    private Button btnAdd;

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    String t = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("hotel");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("data", document.getData().toString());
                        tv3 = findViewById(R.id.tv3);
                        t = t + document.getData().toString();


                    }
                    tv3.setText(t);
                } else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });


        RoomsList roomsList = new RoomsList(collectionReference, adapter);
        listView = findViewById(R.id.list_rooms);
        btnAdd = findViewById(R.id.btn_add);
        adapter = new RoomAdapter(this, roomsList.getRooms());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) parent.getItemAtPosition(position);
                Intent intent = new Intent(RoomsListActivity.this, RoomAddActivity.class);
                intent.putExtra("ROOM_CODE", room.getCode());
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomsListActivity.this, WriteNfcTagActivity.class);
                startActivity(intent);
            }
        });

    }
}
