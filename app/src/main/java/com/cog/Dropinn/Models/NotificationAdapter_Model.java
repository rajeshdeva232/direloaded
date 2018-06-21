package com.cog.Dropinn.Models;

/**
 * Created by test on 2/3/18.
 */

public class NotificationAdapter_Model {


    public int imageId;
    public String txt;
    private String description;

    public NotificationAdapter_Model(int imageId, String text) {
        this.imageId = imageId;
        this.txt=text;
    }
    public String getPlaceDesc() {
        return description;
    }
}
