package com.cog.Dropinn.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmLogin extends RealmObject {

    @PrimaryKey
    private String userID;

    private String userName;

    private String userEmail;

    private String userProfile;

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}