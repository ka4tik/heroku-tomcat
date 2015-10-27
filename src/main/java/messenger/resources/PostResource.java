package messenger.resources;

import messenger.model.Post;
import messenger.model.User;
import messenger.resources.beans.MessageFilterBean;
import messenger.service.AuthenticationService;
import messenger.service.DatabasePostService;
import messenger.service.PostService;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private static PostService postService = new DatabasePostService();
    private static AuthenticationService authenticationService = new AuthenticationService();

    @GET
    public List<Post> getPosts(@BeanParam MessageFilterBean filterBean) {
        if (filterBean.getYear() > 0) {
            return postService.getAllPostsForYear(filterBean.getYear());
        }
        if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
            return postService.getAllPostsPaginated(filterBean.getStart(), filterBean.getSize());
        }
        return postService.getAllPosts();
    }

    @POST
    public Post addPost(@HeaderParam("Authorization") String authHeader, Post post) {
        final String token = authHeader.replaceFirst("Bearer: ", "");
        User user = authenticationService.getTokenOwner(token);
        System.out.println(user);
        if (user!=null && post.getAuthor().equals(user.getUsername())) {
            return postService.addPost(post);
        } else
            throw new ForbiddenException();
    }

    @PUT
    @Path("/{postId}")
    public Post updatePost(@HeaderParam("Authorization") String authHeader, @PathParam("postId") long id, Post post) {
        post.setId(id);
        final String token = authHeader.replaceFirst("Bearer: ", "");
        User user = authenticationService.getTokenOwner(token);
        if (user!=null
                && post.getAuthor().equals(user.getUsername())
                && authenticationService.authenticatePostRequest(id, authHeader))
                return postService.updatePost(post);
        else
            throw new ForbiddenException();

    }

    @DELETE
    @Path("/{postId}")
    public void deletePost(@HeaderParam("Authorization") String authHeader, @PathParam("postId") long id) {
        if (authenticationService.authenticatePostRequest(id, authHeader))
            postService.removePost(id);
        else
            throw new ForbiddenException();
    }


    @GET
    @Path("/{postId}")
    public Post getPost(@PathParam("postId") long id) {
        return postService.getPost(id);
    }


    @Path("/{postId}/comments")
    public CommentResource getCommentResource() {
        return new CommentResource();
    }

}
