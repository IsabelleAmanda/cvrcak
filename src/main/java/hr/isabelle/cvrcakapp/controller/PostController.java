package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.model.request.PostRequest;
import hr.isabelle.cvrcakapp.service.PostService;
import hr.isabelle.cvrcakapp.service.UserService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvrcak")
public class PostController {

    PostService postService;

    @Autowired
    PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value = "/post/all", method = RequestMethod.GET)
    public ServiceResultData getAllPosts(){
        return postService.getAllPosts();
    }

    @RequestMapping(value = "/post/commentsCount/{postId}", method = RequestMethod.GET)
    public ServiceResultData getCommentsCount(@PathVariable Integer postId){
        return postService.getCommentsCount(postId);
    }

    @RequestMapping(value = "/post/comments/{postId}", method = RequestMethod.GET)
    public ServiceResultData getComments(@PathVariable Integer postId){
        return postService.getComments(postId);
    }

    @RequestMapping(value = "post/newPost", method = RequestMethod.POST)
    public ServiceResultData postNewPost(@RequestBody @Validated PostRequest request){
        return postService.newPost(request);
    }

    @PutMapping(value = "post/update")
    public ServiceResultData updatePost(@RequestBody @Validated PostRequest request){
        return postService.updatePost(request);
    }

    @PutMapping(value = "post/delete")
    public ServiceResultData deletePost(@RequestBody @Validated PostRequest request){
        return postService.deletePost(request);
    }

}
