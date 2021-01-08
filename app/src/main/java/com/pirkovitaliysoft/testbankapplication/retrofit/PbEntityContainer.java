package com.pirkovitaliysoft.testbankapplication.retrofit;

import java.util.List;

public class PbEntityContainer {
    private String date;
    private String bank;
    private int baseCurrency;
    private String baseCurrencyLit;

    private List<PbEntity> exchangeRate;

    public String getDate() {
        return date;
    }

    public String getBank() {
        return bank;
    }

    public int getBaseCurrency() {
        return baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public List<PbEntity> getExchangeRate() {
        return exchangeRate;
    }
}
