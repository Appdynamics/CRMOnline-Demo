package com.appdynamics.crm;

/**
 * Created by abey.tom on 10/15/14.
 */
public class CRMRequest {
    private String userRegion;
    private String customerName;
    private String userName;

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "CRMRequest{" +
                "userRegion='" + userRegion + '\'' +
                ", customerName='" + customerName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
