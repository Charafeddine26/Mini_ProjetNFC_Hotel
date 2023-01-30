package com.mbds.hotelnfc;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class RoomsList {
    private List<Room> rooms = new ArrayList<>();
    private CollectionReference collectionReference;
    private RoomAdapter adapter;

    public RoomsList(CollectionReference collectionReference, RoomAdapter adapter) {
        this.collectionReference = collectionReference;
        this.adapter = adapter;
        populateRoomsList();
    }

    private void populateRoomsList() {
        collectionReference.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Log.w("Error: ", "listen:error", e);
                return;
            }
            rooms.clear();
            for (QueryDocumentSnapshot document : querySnapshot) {
                Room room = document.toObject(Room.class);
                rooms.add(room);
            }
        });
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
