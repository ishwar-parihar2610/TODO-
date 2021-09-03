package com.example.todo.Modal;

public class LoginModel {
    String uuid;
    String userName;
    String gmailId;

    public LoginModel() {
    }

    public LoginModel(String uuid, String userName,  String gmailId) {
        this.uuid = uuid;
        this.userName = userName;

        this.gmailId = gmailId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getGmailId() {
        return gmailId;
    }

    public void setG_mailId(String gmailId) {
        this.gmailId = gmailId;
    }
}
