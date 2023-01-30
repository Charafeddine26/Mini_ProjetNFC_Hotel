package com.mbds.hotelnfc;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mbds.hotelnfc.write.NFCWriterActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mbds.hotelnfc.model.TagType;
import com.mbds.hotelnfc.write.NFCWriterActivity;
import com.mbds.hotelnfc.write.NfcTagViewModel;

public class RoomAddActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private NfcTagViewModel viewModel;
    private TagType tagType;
    private EditText et99;
    private String tagText;
    private String roomCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_add);
        et99 = findViewById(R.id.et99);


        // init ViewModel
        viewModel = new ViewModelProvider(this).get(NfcTagViewModel.class);

        viewModel.getTagWritten().observe(this, writeSuccess -> {
                    Toast.makeText(RoomAddActivity.this, "Tag written successfully", Toast.LENGTH_SHORT).show();
                }
        );

        viewModel.getWrittenFailed().observe(this, writeFailed -> {
            Toast.makeText(RoomAddActivity.this, "Tag writing failed!", Toast.LENGTH_SHORT).show();
        });


        //Get default NfcAdapter and PendingIntent instances
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // check NFC feature:
        if (nfcAdapter == null) {
            // process error device not NFC-capable…

        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // single top flag avoids activity multiple instances launching
        Intent intent = getIntent();
        String roomCode = intent.getStringExtra("ROOM_CODE");
        if (roomCode != null) {
            et99.setText(roomCode);
        }


    }

    private void getRoomCode() {
        Intent intent = getIntent();
        roomCode = intent.getStringExtra("ROOM_CODE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Enable NFC foreground detection
        //

        if (nfcAdapter != null) {

            if (!nfcAdapter.isEnabled()) {
                Toast.makeText(this, "Please enable NFC in your device settings.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(intent);

            } else {
                nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            }
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erreur");
            builder.setMessage("Votre téléphone ne prend pas en charge le NFC");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Action à effectuer lorsque l'utilisateur clique sur le bouton Fermer
                    finish(); // Ferme l'activité en cours
                }
            });
            builder.setCancelable(false); // Empêche l'utilisateur de fermer la boîte de dialogue en appuyant sur le bouton retour
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Disable NFC foreground detection
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    public void parseForm()
    {
        tagText = "";
        tagType = TagType.TEXT;

        EditText txt = findViewById(R.id.et99);

        String text_str = txt.getText().toString();
        // Utilisez une expression régulière pour vérifier si le texte saisi est une URL, un numéro de téléphone ou du texte simple
        if(!text_str.isEmpty()) {
            tagText = text_str;
            tagType = TagType.TEXT;
        }

    }



    @Override
    public void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        String action = intent.getAction();
        // check the event was triggered by the tag discovery
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            // get the tag object from the received intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            parseForm();

            if(!tagText.isEmpty())
                viewModel.writeTag(tagText, tag, tagType);
        } else {
            Toast.makeText(RoomAddActivity.this, "Tag type not supported", Toast.LENGTH_SHORT).show();

        }
    }
}