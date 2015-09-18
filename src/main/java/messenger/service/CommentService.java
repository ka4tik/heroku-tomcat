package messenger.service;

import messenger.model.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> getAllComments(long messageId);

    Comment getComment(long messageId, long commentId);

    Comment addComment(long messageId, Comment comment);

    Comment updateComment(long messageId, Comment comment);

    Comment removeComment(long messageId, long commentId);

}
