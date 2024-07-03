package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Like;
import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.model.request.PostRequest;
import hr.isabelle.cvrcakapp.service.PostService;
import hr.isabelle.cvrcakapp.service.UserService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cvrcak")
public class PostController {

    PostService postService;

    @Autowired
    PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value = "/post/all", method = RequestMethod.GET)
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @RequestMapping(value = "/post/title/{title}", method = RequestMethod.GET)
    public List<Post> getPostsByTitle(@PathVariable String title){
        return postService.getPostsByTitle(title);
    }

    @RequestMapping(value = "/post/commentsCount/{postId}", method = RequestMethod.GET)
    public Integer getCommentsCount(@PathVariable Integer postId){
        return postService.getCommentsCount(postId);
    }

    @RequestMapping(value = "/post/likesCount/{postId}", method = RequestMethod.GET)
    public Integer getLikesCount(@PathVariable Integer postId) {
        return postService.getLikesCount(postId);
    }

    // Endpoint to fetch likes on a specific post
    @GetMapping("post/{postId}/likes")
    public List<Like> getPostLikes(@PathVariable int postId) {
        return postService.getLikesByPostId(postId);
    }

    @RequestMapping(value = "/post/comments/{postId}", method = RequestMethod.GET)
    public List<Comment> getComments(@PathVariable Integer postId){
        return postService.getComments(postId);
    }

    @RequestMapping(value = "post/newPost", method = RequestMethod.POST)
    public Integer postNewPost(@RequestBody @Validated PostRequest request) {
        return postService.newPost(request);
    }

    @PutMapping(value = "post/update")
    public ServiceResultData updatePost(@RequestBody @Validated PostRequest request) {
        return postService.updatePost(request);
    }

    @DeleteMapping(value = "post/delete/{postId}")
    public ServiceResultData deletePost(@PathVariable Integer postId) {
        return postService.deletePost(postId);
    }
}
