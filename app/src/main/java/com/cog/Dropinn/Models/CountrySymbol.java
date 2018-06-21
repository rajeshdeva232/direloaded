package com.cog.Dropinn.Models;

/**
 * Created by test on 2/20/18.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountrySymbol implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currency_name")
    @Expose
    private String currencyName;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("default")
    @Expose
    private String _default;
    private final static long serialVersionUID = -4901679065658935941L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

}