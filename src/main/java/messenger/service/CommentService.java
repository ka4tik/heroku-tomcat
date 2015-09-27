package messenger.service;

import messenger.model.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> getAllComments(long postId);

    Comment getComment(long postId, long commentId);

    Comment addComment(long postId, Comment comment);

    Comment updateComment(long postId, Comment comment);

    Comment removeComment(long postId, long commentId);

}
