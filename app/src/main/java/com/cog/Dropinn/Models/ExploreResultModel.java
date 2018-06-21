package com.cog.Dropinn.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExploreResultModel {
//    @SerializedName("page")
//    @Expose
    private Boolean page;
//    @SerializedName("id")
//    @Expose
    private String id;
//    @SerializedName("user_id")
//    @Expose
    private String userId;
//    @SerializedName("address")
//    @Expose
    private String address;
//    @SerializedName("summary")
//    @Expose
    private String summary;
//    @SerializedName("bedrooms")
//    @Expose
    private String bedrooms;
//    @SerializedName("beds")
//    @Expose
    private String beds;
//    @SerializedName("bathrooms")
//    @Expose
    private String bathrooms;
//    @SerializedName("amenities")
//    @Expose
    private String amenities;
//    @SerializedName("overallreview")
//    @Expose
    private String overallreview;
//    @SerializedName("image")
//    @Expose
    private String image;
//    @SerializedName("resize")
//    @Expose
    private String resize;
//    @SerializedName("resize1")
//    @Expose
    private String resize1;
//    @SerializedName("src")
//    @Expose
    private String src;
//    @SerializedName("country_symbol")
//    @Expose
    private String countrySymbol;
//    @SerializedName("currency_code")
//    @Expose
    private String currencyCode;
//    @SerializedName("price")
//    @Expose
    private String price;
//    @SerializedName("title")
//    @Expose
    private String title;
//    @SerializedName("email")
//    @Expose
    private String email;
//    @SerializedName("room_type")
//    @Expose
    private String roomType;
//    @SerializedName("state")
//    @Expose
    private String state;
//    @SerializedName("city")
//    @Expose
    private String city;
//    @SerializedName("country")
//    @Expose
    private String country;
//    @SerializedName("common_currency_code")
//    @Expose
    private String commonCurrencyCode;
//    @SerializedName("common_currency_value")
//    @Expose
    private Integer commonCurrencyValue;
//    @SerializedName("status")
//    @Expose
    private String status;
    //private final static long serialVersionUID = -8839699184691333298L;

    public Boolean getPage() {
        return page;
    }

    public void setPage(Boolean page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getOverallreview() {
        System.out.println("overallreview==>"+overallreview);
        return overallreview;
    }

    public void setOverallreview(String overallreview) {
        System.out.println("setoverallreview===>"+overallreview);
        this.overallreview = overallreview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        System.out.println("setImage==>"+image);
        this.image = image;
    }

    public String getResize() {
        return resize;
    }

    public void setResize(String resize) {
        this.resize = resize;
    }

    public String getResize1() {
        return resize1;
    }

    public void setResize1(String resize1) {
        this.resize1 = resize1;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCountrySymbol() {
        return countrySymbol;
    }

    public void setCountrySymbol(String countrySymbol) {
        System.out.println("countrySymbol==>"+countrySymbol);
        this.countrySymbol = countrySymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCommonCurrencyCode() {
        return commonCurrencyCode;
    }

    public void setCommonCurrencyCode(String commonCurrencyCode) {
        this.commonCurrencyCode = commonCurrencyCode;
    }

    public Integer getCommonCurrencyValue() {
        return commonCurrencyValue;
    }

    public void setCommonCurrencyValue(Integer commonCurrencyValue) {
        this.commonCurrencyValue = commonCurrencyValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return id + " : " + title;
    }
}