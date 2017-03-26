/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iac_rest.service;


import iac_rest.logic.NormaalVerdCalculator;
import iac_rest.model.Counted;
import iac_rest.model.Data;
import iac_rest.model.MyFault;
import iac_rest.model.NormVerd;
import iac_rest.persistence.SQLite;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Matthias
 */

@Path("/normverd")
public class NormVerdService {
    @Context
    UriInfo uriInfo;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        SQLite sql = new SQLite();

        GenericEntity<List<NormVerd>> result = new GenericEntity<List<NormVerd>>(sql.getAll()) {};
        Link url = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("GET").build();
        
        return Response.ok(result).links(url).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public Response createNormVerd(List<Data> data) throws MyFault {
        
        if (data.size() < 3) {
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
        }
        
        List<Double> datalist = new ArrayList<>();
        for (Data item : data) {
            datalist.add(item.getData());
        }
        
        NormaalVerdCalculator calculator = new NormaalVerdCalculator(datalist);
        
        SQLite sql = new SQLite();
        int id = sql.insert(calculator.calculate());
        
        GenericEntity<NormVerd> result = new GenericEntity<NormVerd>(sql.get(id)) {};
        
        return Response.status(Response.Status.CREATED).entity(result).build();
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Counted getCount() {
        SQLite sql = new SQLite();
        Counted result = new Counted(sql.getCount());
        return result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNormVerd(@PathParam("id") int id) {
        
        SQLite sql = new SQLite();
        sql.connectionInit();

        NormVerd fetch = sql.get(id);
        if(fetch == null || fetch.getId() == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        GenericEntity<NormVerd> result = new GenericEntity<NormVerd>(fetch) {};
        Link url = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("GET").build();
        
        return Response.ok(result).links(url).build();
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NormVerd updateNormVerd(@PathParam("id") int id, NormVerd normVerd) {
        if (id != normVerd.getId()) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        
        SQLite sql = new SQLite();
        sql.update(normVerd);
        
        return sql.get(id);
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NormVerd deleteNormVerd(@PathParam("id") int id) {
   
        SQLite sql = new SQLite();
        NormVerd normVerd = sql.get(id);

        if(normVerd == null || normVerd.getId() == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        boolean result = sql.delete(id);
        if (!result) {
            throw new WebApplicationException(Response.Status.EXPECTATION_FAILED);
        }
        
        return normVerd;
    }
}
