package messenger.service;

import messenger.model.Post;

import java.util.List;

public interface PostService {

     List<Post> getAllPosts();

     List<Post> getAllPostsForYear(int year);

     List<Post> getAllPostsPaginated(int start, int size);

     Post getPost(long id);

     Post addPost(Post post);

     Post updatePost(Post post);

     Post removePost(long id);
}
