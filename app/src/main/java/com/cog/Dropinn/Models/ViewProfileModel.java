package com.cog.Dropinn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewProfileModel {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("school")
    @Expose
    private Object school;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("work")
    @Expose
    private String work;
    @SerializedName("phnum")
    @Expose
    private String phnum;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("join_date")
    @Expose
    private String joinDate;
    @SerializedName("language")
    @Expose
    private Object language;
    @SerializedName("live")
    @Expose
    private String live;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Object getSchool() {
        return school;
    }

    public void setSchool(Object school) {
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhnum() {
        return phnum;
    }

    public void setPhnum(String phnum) {
        this.phnum = phnum;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
