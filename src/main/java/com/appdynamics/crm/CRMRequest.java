package com.appdynamics.crm;

/**
 * Created by abey.tom on 10/15/14.
 */
public class CRMRequest {
    private String customerType;
    private String customerName;
    private String userName;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
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
                "customerType='" + customerType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
