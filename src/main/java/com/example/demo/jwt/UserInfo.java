package com.example.demo.jwt;


public class UserInfo {
    private String redisId;
    private String firstName;
    private String msisdn;
    private String lastName;
    private String tckn;

    public UserInfo(String redisId, String firstName, String msisdn, String lastName, String tckn) {
        this.redisId = redisId;
        this.firstName = firstName;
        this.msisdn = msisdn;
        this.lastName = lastName;
        this.tckn = tckn;
    }

    public String getRedisId() {
        return redisId;
    }

    public void setRedisId(String redisId) {
        this.redisId = redisId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }
}
