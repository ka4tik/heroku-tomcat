package messenger.service;

import messenger.db.MockDatabaseClass;
import messenger.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileMockService implements  ProfileService{

    private Map<String, Profile> profiles = MockDatabaseClass.getProfiles();


    public ProfileMockService() {
        profiles.put("kartik", new Profile(1, "kartik", "Kartik", "Singal"));
    }


    public List<Profile> getAllProfiles() {
        return new ArrayList<Profile>(profiles.values());
    }

    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }

    public Profile addProfile(Profile profile) {
        profile.setId(profiles.size() + 1);
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile updateProfile(Profile profile) {
        if (profile.getProfileName().isEmpty())
            return null;
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Object removeProfile(String profileName) {
        return profiles.remove(profileName);
    }

}
