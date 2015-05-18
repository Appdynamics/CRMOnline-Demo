package com.appdynamics.rest;

/**
 * Created by abey.tom on 10/15/14.
 */
public class Request {
    private String region;
    private String operation;
    private String customer;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Request{" +
                "region='" + region + '\'' +
                ", operation='" + operation + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
