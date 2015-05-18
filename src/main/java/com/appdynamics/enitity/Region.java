package com.appdynamics.enitity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by abey.tom on 10/22/14.
 */
@Entity
@Table
public class Region {
    @Id
    private String id;
    private int zipCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
