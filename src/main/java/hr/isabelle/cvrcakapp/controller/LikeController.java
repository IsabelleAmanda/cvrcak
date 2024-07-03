package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.request.LikeRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.service.LikeService;
import hr.isabelle.cvrcakapp.service.UserService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvrcak")
public class LikeController {

    LikeService likeService;

    @Autowired
    LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    //TODO: gettati count likeove na nekom postu -> zapravo ne treba tu nego u POSTCONTROLLERU!!!

    //TODO: gettati username usera koji su likeali post -> zapravo ne treba tu nego u POSTCONTROLLERU!!!

    @RequestMapping(value = "like", method = RequestMethod.POST)
    public ServiceResultData likePost(@RequestBody @Validated LikeRequest request){
        return likeService.like(request);
    }


    @DeleteMapping(value = "unlike")
    public ServiceResultData unlikePost(@RequestBody @Validated LikeRequest request){
        return likeService.unlike(request);
    }

}
