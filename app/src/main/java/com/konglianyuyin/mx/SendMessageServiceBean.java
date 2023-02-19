package com.konglianyuyin.mx;

import java.io.Serializable;

public class SendMessageServiceBean implements Serializable {
    private String roomID;
    protected String message;

    public SendMessageServiceBean(String roomID, String message) {
        this.roomID = roomID;
        this.message = message;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
