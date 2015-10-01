package messenger.service;

import messenger.db.MockDatabaseClass;
import messenger.model.Post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class PostMockService implements PostService {

    private Map<Long, Post> messages = MockDatabaseClass.getPosts();


    public PostMockService() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            messages.put(1L, new Post(1, "Hello world!", "kartik"));
            messages.put(2L, new Post(2, "Hello jersy!", "josh"));
            messages.put(3L, new Post(3, "Hello 2014!", dateFormat.parse("20140216"), "kartik"));
            messages.put(4L, new Post(4, "Hello 2013!", dateFormat.parse("20130301"), "john"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Post> getAllPosts() {
        return new ArrayList<Post>(messages.values());
    }

    public List<Post> getAllPostsForYear(int year) {
        ArrayList<Post> messagesForYear = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (Post post : messages.values()) {
            calendar.setTime(post.getCreated());
            if (calendar.get(Calendar.YEAR) == year)
                messagesForYear.add(post);
        }
        return messagesForYear;
    }

    public List<Post> getAllPostsPaginated(int start, int size) {
        List<Post> list = new ArrayList<>(messages.values());
        if (start + size > list.size())
            return new ArrayList<>();
        else
            return list.subList(start, start + size);
    }


    public Post getPost(long id) {
        return messages.get(id);
    }

    public Post addPost(Post post) {
        post.setId(messages.size() + 1);
        post.setCreated(new Date());
        messages.put(post.getId(), post);
        return post;
    }

    public Post updatePost(Post post) {
        if (post.getId() <= 0)
            return null;
        messages.put(post.getId(), post);
        return post;
    }

    public Post removePost(long id) {
        return messages.remove(id);
    }

    @Override
    public String getAuthor(long id) {
        return null;
    }

}
