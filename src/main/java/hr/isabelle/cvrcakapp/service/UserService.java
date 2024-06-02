package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Like;
import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.repository.UserRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
        //return new ServiceResultData(true, this.userRepository.getUserByUsername(username));
    }

    public ServiceResultData registerUser(NewUserRequest request) {
        return new ServiceResultData(true, this.userRepository.registerUser(request));
    }

    public ServiceResultData updateUser(NewUserRequest request) {
        return this.userRepository.updateUser(request);
    }

    public ServiceResultData deleteUser(NewUserRequest request) {
        return this.userRepository.deleteUser(request);
    }

    public List<User> getUsersByName(String name) {
        return this.userRepository.getUsersByName(name);
    }

    public int getFollowersCount(int userId) {
        return this.userRepository.getFollowersCount(userId);
    }

    public int getFollowingCount(int userId) {
        return this.userRepository.getFollowingCount(userId);
    }
    public List<User> getFollowers(int userId, int page) {
        return userRepository.getFollowers(userId, page);
    }

    public List<User> getFollowing(int userId, int page) {
        return userRepository.getFollowing(userId, page);
    }

    // Fetches posts made by a specific user
    public List<Post> getPostsByUserId(int userId) {
        return userRepository.getPostsByUserId(userId);
    }

    // Fetches comments on a specific post
    /*public List<Comment> getCommentsByPostId(int postId) {
        return userRepository.getCommentsByPostId(postId);
    }*/

    // Fetches likes on a specific post
    public List<Like> getLikesByPostId(int postId) {
        return userRepository.getLikesByPostId(postId);
    }

    // Fetches posts that a specific user has liked
    public List<Post> getPostsLikedByUserId(int userId) {
        return userRepository.getPostsLikedByUserId(userId);
    }

    // Follows a user
    public void followUser(int userId, int followerId) {
        userRepository.followUser(userId, followerId);
    }

    // Unfollows a user
    public void unfollowUser(int userId, int followerId) {
        userRepository.unfollowUser(userId, followerId);
    }
}
