package messenger.service;

import messenger.model.User;

import java.util.List;

public interface UserService {

     List<User> getAllUsers() ;

     User getUser(String username) ;

     User updateUser(User user) ;

     User removeUser(String username) ;
}
