package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.service.ConversationService;
import hr.isabelle.cvrcakapp.service.UserService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvrcak")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
    public ServiceResultData getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    //TODO: sloziti GET za ime, prezime -> queryForList! najmanje 3 slova -> npr. like ':firstName%'

    //TODO: GET za count followers za nekog usera

    //TODO: lista usera followers za nekog usera -> za sad komplicirano u mom mozgu, u kasnijoj fazi projekta

    //TODO: GET za count following za nekog usera

    //TODO: lista usera following za nekog usera -> za sad komplicirano u mom mozgu, u kasnijoj fazi projekta


    @RequestMapping(value = "user/register", method = RequestMethod.POST)
    public ServiceResultData registerUser(@RequestBody @Validated NewUserRequest request){
        return userService.registerUser(request);
    }

    @PutMapping(value = "user/update")
    public ServiceResultData updateUser(@RequestBody @Validated NewUserRequest request){
        return userService.updateUser(request);
    }

    @PutMapping(value = "user/delete")
    public ServiceResultData deleteUser(@RequestBody @Validated NewUserRequest request){
        return userService.deleteUser(request);
    }

}
