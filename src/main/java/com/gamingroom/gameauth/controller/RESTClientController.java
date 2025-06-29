package com.gamingroom.gameauth.controller;
 
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gamingroom.gameauth.representations.GameUserInfo;
 

/*
* RESTClientController is rest client api controller
*/
@Produces(MediaType.TEXT_PLAIN)
@Path("/client/")
public class RESTClientController 
{
    private Client client;
 
    public RESTClientController(Client client) {
        this.client = client;
    }
     
    // Done: Add annotation for GET and Path for gameusers 

    @GET
    @Path("/gameusers")
    public String getGameUsers()
    {
    	WebTarget target = client.target("http://localhost:8080/gameusers");
        Response resp = target.request(MediaType.APPLICATION_JSON).get();
        ArrayList<?> list = resp.readEntity(ArrayList.class);
        return list.toString();
    	
    }
     
    @GET
    @Path("/gameusers/{id}")
    public String getGameUserById(@PathParam("id") int id)
    {
    	WebTarget target = client.target("http://localhost:8080/gameusers/" + id);
        Response resp = target.request(MediaType.APPLICATION_JSON).get();
        GameUserInfo info = resp.readEntity(GameUserInfo.class);
        return info.toString();
    }
}