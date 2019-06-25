package com.yaopb.nethttpmanager.model;

public class ResponseClass {
    private int resultCode;
    private String reson;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
