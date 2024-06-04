package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Like;
import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.service.UserService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @RequestMapping(value = "user/register", method = RequestMethod.POST)
    public ServiceResultData registerUser(@RequestBody @Validated NewUserRequest request){
        return userService.registerUser(request);
    }

    @PutMapping(value = "user/update")
    public ServiceResultData updateUser(@RequestBody @Validated NewUserRequest request){
        return userService.updateUser(request);
    }

    @DeleteMapping(value = "user/delete")
    public ServiceResultData deleteUser(@RequestBody @Validated NewUserRequest request){
        return userService.deleteUser(request);
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
    @GetMapping("user/{userId}/followers/{page}")
    public List<User> getFollowers(@PathVariable int userId, @PathVariable int page){
        return userService.getFollowers(userId, page);
    }

    @GetMapping("user/{userId}/following/{page}")
    public List<User> getFollowing(@PathVariable int userId, @PathVariable int page){
        return userService.getFollowing(userId, page);
    }


    // Endpoint to fetch posts made by a specific user
    @GetMapping("user/{userId}/posts")
    public List<Post> getUserPosts(@PathVariable int userId){
        return userService.getPostsByUserId(userId);
    }

    // Endpoint to fetch comments on a specific post
    /*@GetMapping("post/{postId}/comments")
    public List<Comment> getPostComments(@PathVariable int postId){
        return userService.getCommentsByPostId(postId);
    }*/

    // Endpoint to fetch likes on a specific post
    @GetMapping("post/{postId}/likes")
    public List<Like> getPostLikes(@PathVariable int postId){
        return userService.getLikesByPostId(postId);
    }

    // Endpoint to fetch posts that a specific user has liked
    @GetMapping("user/{userId}/likedPosts")
    public List<Post> getUserLikedPosts(@PathVariable int userId){
        return userService.getPostsLikedByUserId(userId);
    }

    // Endpoint to follow a user
    @PostMapping("user/{userId}/follow")
    public void followUser(@PathVariable int userId, @RequestBody int followerId){
        userService.followUser(userId, followerId);
    }

    // Endpoint to unfollow a user
    @PostMapping("user/{userId}/unfollow")
    public void unfollowUser(@PathVariable int userId, @RequestBody int followerId){
        userService.unfollowUser(userId, followerId);
    }

}
