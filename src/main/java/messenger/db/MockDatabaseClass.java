package messenger.db;

import messenger.model.Post;
import messenger.model.Profile;

import java.util.HashMap;
import java.util.Map;

public class MockDatabaseClass {

    private static Map<Long, Post> messages = new HashMap<>();
    private static Map<String, Profile> profiles = new HashMap<>();

    public static Map<Long, Post> getMessages() {
        return messages;
    }

    public static Map<String, Profile> getProfiles() {
        return profiles;
    }
}
