package com.sjm.web.dto;

public class Result {

    private String phoneNumber;
    private String result;

    public Result() {
        super();
    }

    public Result(final String phoneNumber, final String result) {
        super();

        this.phoneNumber = phoneNumber;
        this.result = result;
    }

    public String getphoneNumber() {
        return this.phoneNumber;
    }

    public void phoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(final String result) {
    	this.result = result;
    }

}
