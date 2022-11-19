package com.example.loginusinggoogle.Models;

public class Users {
    String userId, userName, profilePic;

    public Users(String userId, String userName, String profilePic) {
        this.userId = userId;
        this.userName = userName;
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Users(){}


}
