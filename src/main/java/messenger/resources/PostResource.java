package messenger.resources;

import messenger.model.Post;
import messenger.resources.beans.MessageFilterBean;
import messenger.service.DatabasePostService;
import messenger.service.PostService;

import javax.ws.rs.BeanParam;
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


@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    PostService postService = new DatabasePostService();

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
    public Post addPost(Post post) {
        return postService.addPost(post);
    }

    @PUT
    @Path("/{postId}")
    public Post updatePost(@PathParam("postId") long id, Post post) {
        post.setId(id);
        return postService.updatePost(post);
    }

    @DELETE
    @Path("/{postId}")
    public void deletePost(@PathParam("postId") long id) {
        postService.removePost(id);
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
