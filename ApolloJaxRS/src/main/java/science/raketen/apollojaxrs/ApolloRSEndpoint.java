/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package science.raketen.apollojaxrs;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
@Path("/hallo")
@SessionScoped
public class ApolloRSEndpoint implements Serializable {
    
    private int i;
    
    @GET
    public Response get(@QueryParam("name") String name) {
        return Response.ok().entity(String.format("Hallo %s %d!", name, i++)).build();
    }
}
