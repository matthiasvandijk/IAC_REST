/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iac_rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Matthias
 */

@Path("/echo")
public class EchoService {

    @GET
    @Path("{echo}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEcho(@PathParam("echo") String echo) {
        
        if (echo.endsWith("ping")) {
            return "pong";
        }
        
        return echo;
    }
    
}
