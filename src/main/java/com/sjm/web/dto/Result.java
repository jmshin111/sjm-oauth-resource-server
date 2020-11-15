package com.sjm.web.dto;

public class Result {
    private String phoneNumber;

    public Result() {
        super();
    }

    public Result(final String phoneNumber) {
        super();

        this.phoneNumber = phoneNumber;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void phoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}