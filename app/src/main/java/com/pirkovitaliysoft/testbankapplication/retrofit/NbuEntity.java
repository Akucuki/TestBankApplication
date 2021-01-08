package com.pirkovitaliysoft.testbankapplication.retrofit;

import com.google.gson.annotations.SerializedName;

public class NbuEntity {
    @SerializedName("StartDate")
    private String startDate;
    @SerializedName("TimeSign")
    private String timeSign;
    @SerializedName("CurrencyCode")
    private String currencyCode;
    @SerializedName("CurrencyCodeL")
    private String currencyCodeL;
    @SerializedName("Units")
    private int units;
    @SerializedName("Amount")
    private float amount;

    public NbuEntity(String startDate, String timeSign, String currencyCode, String currencyCodeL, int units, float amount) {
        this.startDate = startDate;
        this.timeSign = timeSign;
        this.currencyCode = currencyCode;
        this.currencyCodeL = currencyCodeL;
        this.units = units;
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTimeSign() {
        return timeSign;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyCodeL() {
        return currencyCodeL;
    }

    public int getUnits() {
        return units;
    }

    public float getAmount() {
        return amount;
    }

    public void setStartDate( String StartDate ) {
        this.startDate = StartDate;
    }

    public void setTimeSign( String TimeSign ) {
        this.timeSign = TimeSign;
    }

    public void setCurrencyCode( String CurrencyCode ) {
        this.currencyCode = CurrencyCode;
    }

    public void setCurrencyCodeL( String CurrencyCodeL ) {
        this.currencyCodeL = CurrencyCodeL;
    }

    public void setUnits( int units ) {
        this.units = units;
    }

    public void setAmount( float Amount ) {
        this.amount = Amount;
    }
}
