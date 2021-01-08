package com.pirkovitaliysoft.testbankapplication.retrofit;

public class PbEntity {
    private String baseCurrency;
    private String currency;
    private float saleRateNB;
    private float purchaseRateNB;
    private float saleRate;
    private float purchaseRate;

    public PbEntity(String baseCurrency, String currency, float saleRateNB, float purchaseRateNB, float saleRate, float purchaseRate) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.saleRateNB = saleRateNB;
        this.purchaseRateNB = purchaseRateNB;
        this.saleRate = saleRate;
        this.purchaseRate = purchaseRate;
    }

    // Getter Methods

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public float getSaleRateNB() {
        return saleRateNB;
    }

    public float getPurchaseRateNB() {
        return purchaseRateNB;
    }

    public float getSaleRate() {
        return saleRate;
    }

    public float getPurchaseRate() {
        return purchaseRate;
    }

    // Setter Methods

    public void setBaseCurrency( String baseCurrency ) {
        this.baseCurrency = baseCurrency;
    }

    public void setCurrency( String currency ) {
        this.currency = currency;
    }

    public void setSaleRateNB( float saleRateNB ) {
        this.saleRateNB = saleRateNB;
    }

    public void setPurchaseRateNB( float purchaseRateNB ) {
        this.purchaseRateNB = purchaseRateNB;
    }

    public void setSaleRate( float saleRate ) {
        this.saleRate = saleRate;
    }

    public void setPurchaseRate( float purchaseRate ) {
        this.purchaseRate = purchaseRate;
    }
}
