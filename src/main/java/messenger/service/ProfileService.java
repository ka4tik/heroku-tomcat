package messenger.service;

import messenger.model.Profile;

import java.util.ArrayList;
import java.util.List;

public interface ProfileService {

     List<Profile> getAllProfiles() ;


     Profile getProfile(String profileName) ;

     Profile addProfile(Profile profile) ;

     Profile updateProfile(Profile profile) ;

     Object removeProfile(String profileName) ;
}
