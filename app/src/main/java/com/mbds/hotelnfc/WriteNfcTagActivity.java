package com.mbds.hotelnfc;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class WriteNfcTagActivity extends AppCompatActivity {

    private EditText mEtRoomCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_write_nfc_tag);

//        mEtRoomCode = findViewById(R.id.et_room_code);
    }

    public void onWriteClick(View view) {
        String roomCode = mEtRoomCode.getText().toString();
        if (roomCode.isEmpty()) {
            Toast.makeText(this, "Please enter a room code", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO: Write the room code to the NFC tag using the Android NFC API
    }
}
