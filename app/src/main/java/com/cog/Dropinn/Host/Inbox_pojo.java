package com.cog.Dropinn.Host;

/**
 * Created by test on 2/2/18.
 */

public class Inbox_pojo {

    public int imageId;
    public String txt;
    public String time;
    public String address="";
    public String tittle,status;
    public String state="";
    private String noofBeds="";
    public String title;

    private String price;

    public Inbox_pojo(int imageId, String text,String ago,String tittle,String Status) {
        this.imageId = imageId;
        this.txt=text;
        this.time=ago;
        this.status=Status;
        this.title=tittle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setNoofBeds(String noofBeds) {
        this.noofBeds = noofBeds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNoofBeds() {
        return noofBeds;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }
}
