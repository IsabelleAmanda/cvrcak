package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.service.UserService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvrcak")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "user/id/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }
    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PutMapping(value = "user/update")
    public ServiceResultData updateUser(@RequestBody @Validated NewUserRequest request){
        return userService.updateUser(request);
    }

    @DeleteMapping(value = "user/delete/id/{id}")
    public ServiceResultData deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
    @GetMapping("user/search/{name}")
    public List<User> getUsersByName(@PathVariable String name){
        return userService.getUsersByName(name);
    }

    @GetMapping("user/{userId}/followers/count")
    public int getFollowersCount(@PathVariable int userId){
        return userService.getFollowersCount(userId);
    }

    @GetMapping("user/{userId}/following/count")
    public int getFollowingCount(@PathVariable int userId){
        return userService.getFollowingCount(userId);
    }

    @GetMapping("user/{userId}/following/{page}")
    public List<User> getFollowers(@PathVariable int userId, @PathVariable int page) {
        return userService.getFollowers(userId, page);
    }

    @GetMapping("user/{userId}/followers/{page}")
    public List<User> getFollowing(@PathVariable int userId, @PathVariable int page) {
        return userService.getFollowing(userId, page);
    }
    // Endpoint to fetch posts made by a specific user
    @GetMapping("user/{userId}/posts")
    public List<Post> getUserPosts(@PathVariable int userId){
        return userService.getPostsByUserId(userId);
    }


    // Endpoint to fetch posts that a specific user has liked
    @GetMapping("user/{userId}/likedPosts")
    public List<Post> getUserLikedPosts(@PathVariable int userId){
        return userService.getPostsLikedByUserId(userId);
    }

    //Follow
    @PostMapping("user/{userId}/follow/{followerId}")
    public ResponseEntity<String> followUser(@PathVariable int userId, @PathVariable int followerId) {
        try {
            userService.followUser(userId, followerId);
            return new ResponseEntity<>("Followed successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to unfollow a user
    @DeleteMapping("user/{userId}/unfollow/{followerId}")
    public ResponseEntity<String> unfollowUser(@PathVariable int userId, @PathVariable int followerId) {
        try {
            userService.unfollowUser(userId, followerId);
            return new ResponseEntity<>("Unfollowed successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
