package com.preesoft.mortgagevip.ModelClass;

public class ViewRates {

    private String lender, apr, moPayment,rate, upfrontCost;

    public ViewRates(String lender, String apr, String moPayment, String rate, String upfrontCost) {
        this.lender = lender;
        this.apr = apr;
        this.moPayment = moPayment;
        this.rate = rate;
        this.upfrontCost = upfrontCost;
    }

    public ViewRates(){};

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getMoPayment() {
        return moPayment;
    }

    public void setMoPayment(String moPayment) {
        this.moPayment = moPayment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUpfrontCost() {
        return upfrontCost;
    }

    public void setUpfrontCost(String upfrontCost) {
        this.upfrontCost = upfrontCost;
    }
}
