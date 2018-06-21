package com.cog.Dropinn.Models;

/**
 * Created by test on 2/20/18.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("check_status")
    @Expose
    private String checkStatus;
    @SerializedName("step_status")
    @Expose
    private String stepStatus;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cancellation_policy")
    @Expose
    private String cancellationPolicy;
    @SerializedName("max_stay")
    @Expose
    private String maxStay;
    @SerializedName("min_stay")
    @Expose
    private String minStay;
    @SerializedName("room_type")
    @Expose
    private String roomType;
    @SerializedName("bedrooms")
    @Expose
    private String bedrooms;
    @SerializedName("listpay")
    @Expose
    private String listpay;
    @SerializedName("beds")
    @Expose
    private String beds;
    @SerializedName("bathrooms")
    @Expose
    private String bathrooms;
    @SerializedName("bed_type")
    @Expose
    private String bedType;
    @SerializedName("amenities")
    @Expose
    private String amenities;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Fname")
    @Expose
    private String fname;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("country_symbol")
    @Expose
    private CountrySymbol countrySymbol;
    @SerializedName("common_currency_code")
    @Expose
    private String commonCurrencyCode;
    @SerializedName("common_currency_value")
    @Expose
    private Integer commonCurrencyValue;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("resize")
    @Expose
    private String resize;
    @SerializedName("resize1")
    @Expose
    private String resize1;
    @SerializedName("map")
    @Expose
    private String map;
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("property_type")
    @Expose
    private String propertyType;
    private final static long serialVersionUID = 3380471597990931153L;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getMaxStay() {
        return maxStay;
    }

    public void setMaxStay(String maxStay) {
        this.maxStay = maxStay;
    }

    public String getMinStay() {
        return minStay;
    }

    public void setMinStay(String minStay) {
        this.minStay = minStay;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getListpay() {
        return listpay;
    }

    public void setListpay(String listpay) {
        this.listpay = listpay;
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

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CountrySymbol getCountrySymbol() {
        return countrySymbol;
    }

    public void setCountrySymbol(CountrySymbol countrySymbol) {
        this.countrySymbol = countrySymbol;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

}
