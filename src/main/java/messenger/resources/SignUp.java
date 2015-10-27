package messenger.resources;

import messenger.service.AuthenticationService;
import messenger.service.DatabaseUserService;
import messenger.service.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/signup")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class SignUp {

    private static AuthenticationService authenticationService = new AuthenticationService();
    private static UserService userService = new DatabaseUserService();

    @POST
    public Response signUp(@FormParam("username") String username,
                           @FormParam("password") String password) {
        if (username == null || password == null || userService.getUser(username) != null)
            return Response.status(Status.BAD_REQUEST).build();
        else {
            authenticationService.addUser(username, password);
            return Response.ok().entity(userService.getUser(username)).build();
        }
    }

}