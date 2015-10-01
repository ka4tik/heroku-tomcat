package messenger.db;

import messenger.model.Post;
import messenger.model.User;

import java.util.HashMap;
import java.util.Map;

public class MockDatabaseClass {

    private static Map<Long, Post> posts = new HashMap<>();
    private static Map<String, User> users = new HashMap<>();

    public static Map<Long, Post> getPosts() {
        return posts;
    }

    public static Map<String, User> getUsers() {
        return users;
    }
}
