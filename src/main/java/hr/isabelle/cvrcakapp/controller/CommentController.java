package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.request.CommentRequest;
import hr.isabelle.cvrcakapp.model.request.LikeRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.service.CommentService;
import hr.isabelle.cvrcakapp.service.LikeService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvrcak")
public class CommentController {

    CommentService commentService;

    @Autowired
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    //TODO: dohvatiti count komentara na nekom postu -> zapravo ne treba tu nego u POSTCONTROLLERU!!!

    //TODO: dohvatiti sve usernameove usera koji su komentirali i njihove komentare na nekom postu -> zapravo ne treba tu nego u POSTCONTROLLERU!!!

    @PostMapping(value = "comment")
    public ServiceResultData commentPost(@RequestBody @Validated CommentRequest request){
        return commentService.commentPost(request);
    }

    @PutMapping(value = "comment/update")
    public ServiceResultData updateComment(@RequestBody @Validated CommentRequest request){
        return commentService.updateComment(request);
    }

    @PutMapping(value = "comment/delete")
    public ServiceResultData deleteComment(@RequestBody @Validated CommentRequest request){
        return commentService.deleteComment(request);
    }

}
