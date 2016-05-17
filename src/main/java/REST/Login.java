package REST;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import EJB.LoginService;

/**
 * Created by nabil on 17/05/16.
 */

@Path("login/")
public class Login {

    @EJB
    private LoginService loginService;

    @POST
    @Produces("application/json")
    public Response crearLogin(@QueryParam("usuario") String usuario,
                               @QueryParam("password") String password){
        return Response.status(200).entity(loginService.crearLogin(usuario, password)).build();
    }

}
