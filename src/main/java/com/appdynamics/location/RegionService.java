package com.appdynamics.location;

import com.appdynamics.enitity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by abey.tom on 10/22/14.
 */
@Component
public class RegionService {
    @Autowired
    private RegionDao regionDao;

    private Random random = new Random();

    public Region getRegionByZip(int zipCode) {
        checkInvalidZip(zipCode);
        return regionDao.getRegionByZipCode(zipCode);
    }

    private void checkInvalidZip(int zipCode) {
        int i = random.nextInt(20);
        if (i < 2) {
            throw new InvalidZipException(zipCode);
        }
        if (i < 3) {
            throw new DBConnectionPoolTimeoutException();
        }
    }

    public static class InvalidZipException extends RuntimeException {
        public InvalidZipException(int zip) {
            super("Invalid zipcode " + zip);
        }
    }

    public static class DBConnectionPoolTimeoutException extends RuntimeException {

    }
}
