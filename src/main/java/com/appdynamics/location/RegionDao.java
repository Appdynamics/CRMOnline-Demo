package com.appdynamics.location;

import com.appdynamics.enitity.Region;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by abey.tom on 10/22/14.
 */
@Component
public class RegionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Region getRegionByZipCode(int zipCode) {
        Region o = (Region) sessionFactory.getCurrentSession().get(Region.class, "");
        return o;
    }
}
