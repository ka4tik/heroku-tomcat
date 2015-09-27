package messenger.service;

import messenger.db.MockDatabaseClass;
import messenger.model.Comment;
import messenger.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CommentMockService implements CommentService{

    private Map<Long, Post> messages = MockDatabaseClass.getMessages();

    public List<Comment> getAllComments(long postId) {
        Map<Long, Comment> comments = messages.get(postId).getComments();
        return new ArrayList<Comment>(comments.values());
    }

    public Comment getComment(long postId, long commentId) {
        Map<Long, Comment> comments = messages.get(postId).getComments();
        return comments.get(commentId);
    }

    public Comment addComment(long postId, Comment comment) {
        Map<Long, Comment> comments = messages.get(postId).getComments();
        comment.setId(comments.size() + 1);
        comments.put(comment.getId(), comment);
        return comment;
    }

    public Comment updateComment(long postId, Comment comment) {
        Map<Long, Comment> comments = messages.get(postId).getComments();
        if (comment.getId() <= 0) {
            return null;
        }
        comments.put(comment.getId(), comment);
        return comment;
    }

    public Comment removeComment(long postId, long commentId) {
        Map<Long, Comment> comments = messages.get(postId).getComments();
        return comments.remove(commentId);
    }

}
