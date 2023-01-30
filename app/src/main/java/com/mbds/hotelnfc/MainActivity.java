package com.mbds.hotelnfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.mbds.hotelnfc.databinding.ActivityMainBinding;
import com.mbds.hotelnfc.read.NFCReaderActivity;
import com.mbds.hotelnfc.write.NFCWriterActivity;
import com.mbds.hotelnfc.read.NfcReaderViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);


        handleActions();
    }

    private void handleActions() {
        binding.btnReadTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReadNfcTagActivity.class));
            }
        });

        binding.btnWriteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RoomsListActivity.class));
            }
        });
    }

}