package messenger.resources;

import messenger.model.Comment;
import messenger.service.CommentService;
import messenger.service.DatabaseCommentService;

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


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CommentResource {

    private static CommentService commentService = new DatabaseCommentService();

    @GET
    public List<Comment> getAllComments(@PathParam("postId") long postId) {
        return commentService.getAllComments(postId);
    }

    @POST
    public Comment addComment(@PathParam("postId") long postId, Comment comment) {
        return commentService.addComment(postId, comment);
    }

    @PUT
    @Path("/{commentId}")
    public Comment updateComment(@PathParam("postId") long postId, @PathParam("commentId") long id, Comment comment) {
        comment.setId(id);
        return commentService.updateComment(postId, comment);
    }

    @DELETE
    @Path("/{commentId}")
    public void deleteComment(@PathParam("postId") long postId, @PathParam("commentId") long commentId) {
        commentService.removeComment(postId, commentId);
    }


    @GET
    @Path("/{commentId}")
    public Comment getMessage(@PathParam("postId") long postId, @PathParam("commentId") long commentId) {
        return commentService.getComment(postId, commentId);
    }

}
