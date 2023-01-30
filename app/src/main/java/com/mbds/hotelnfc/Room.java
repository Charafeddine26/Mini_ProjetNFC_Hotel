package com.mbds.hotelnfc;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Room {
    private String code;
    private String title;
    private boolean available;

    public Room() {
    }
    public Room(String roomCode, String title) {
        this.code = code;
        this.title = title;
    }

    public Room(String code, int number, boolean available) {
        this.code = code;
        this.title = title;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return title;
    }

    public void setNumber(int number) {
        this.title = title;
    }


}
