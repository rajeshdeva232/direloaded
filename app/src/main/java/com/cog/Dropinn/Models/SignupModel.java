package com.cog.Dropinn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private String success;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
