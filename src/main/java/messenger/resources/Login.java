package messenger.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import messenger.service.AuthenticationService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/login")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class Login {

    AuthenticationService authenticationService = new AuthenticationService();

    @POST
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {
        String token = authenticationService.getToken(username, password);
        if (token == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        JsonObject json = new JsonObject();
        json.addProperty("token", token);

        return Response.ok(new Gson().toJson(json), MediaType.APPLICATION_JSON_TYPE).build();
    }

}