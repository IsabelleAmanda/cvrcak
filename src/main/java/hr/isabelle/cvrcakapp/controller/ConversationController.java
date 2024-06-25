package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.Message;
import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.service.ConversationService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/cvrcak")
public class ConversationController{

    ConversationService conversationService;

    @Autowired
    ConversationController(ConversationService conversationService){
        this.conversationService = conversationService;
    }

    @RequestMapping(value = "message/new", method = RequestMethod.POST)
    public ServiceResultData sendMessage(@RequestBody @Validated NewMessageRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        if(Objects.equals(request.senderId, currentUser.getUserId())) {
            return conversationService.sendMessage(request);
        }

        return null;
    }

    //TODO: razmislit da se zapravo makne path variable i stavlja se u body putem requesta
    @RequestMapping(value = "conversation/new/{senderId}/{receiverId}", method = RequestMethod.POST)
    public ServiceResultData addNewConversation(@RequestBody @Validated NewConversationRequest request, @PathVariable Integer senderId, @PathVariable Integer receiverId){
        return conversationService.addNewConversation(request, senderId, receiverId);
    }
}
