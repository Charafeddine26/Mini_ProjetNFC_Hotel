package com.mbds.hotelnfc;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class hotelnfc extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
