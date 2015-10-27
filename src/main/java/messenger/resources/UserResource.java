package messenger.resources;

import messenger.model.User;
import messenger.service.DatabaseUserService;
import messenger.service.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static UserService userService = new DatabaseUserService();

    @GET
    public List<User> getProfiles() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{username}")
    public User getUser(@PathParam("username") String username) {
        return userService.getUser(username);
    }

    @PUT
    @Path("/{username}")
    public User updateProfile(@PathParam("username") String username, User user) {
        user.setUsername(username);
        return userService.updateUser(user);
    }

    @DELETE
    @Path("/{username}")
    public void deleteProfile(@PathParam("username") String username) {
        userService.removeUser(username);
    }

}
