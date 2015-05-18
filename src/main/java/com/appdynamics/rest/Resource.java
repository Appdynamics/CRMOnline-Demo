package com.appdynamics.rest;

import com.appdynamics.Util;
import com.appdynamics.crm.CRMRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by abey.tom on 10/15/14.
 */
@Path("/")
@Component
public class Resource {

    public static final Logger logger = LoggerFactory.getLogger(Resource.class);
  

    @Path("crm/firstRequest")
    @GET
    public CRMRequest crmFirstLoad() {
        return new CRMRequest();
    }

    @Path("crm/{path}")
    @POST
    public void crmOperations(CRMRequest crmRequest, @PathParam("path") String path) {
        Util.applyResponseTimeAdjustment();
        System.out.println("Resource.update" + crmRequest);
    }

}
