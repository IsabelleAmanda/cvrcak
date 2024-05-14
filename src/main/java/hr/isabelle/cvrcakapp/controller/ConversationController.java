package hr.isabelle.cvrcakapp.controller;

import hr.isabelle.cvrcakapp.model.Message;
import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.service.ConversationService;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvrcak")
public class ConversationController{

    ConversationService conversationService;

    @Autowired
    ConversationController(ConversationService conversationService){
        this.conversationService = conversationService;
    }

    //TODO: dohvatiti sve poruke u nekom conversationu

    //TODO: dohvatiti samo 1 poruku prema messageId-> jel nam to potrebno, jel bi nam trebalo di?

    @RequestMapping(value = "message/new", method = RequestMethod.POST)
    public ServiceResultData addNewMessage(@RequestBody @Validated NewMessageRequest request){
        return conversationService.addNewMessage(request);
    }

    //TODO: razmislit da se zapravo makne path variable i stavlja se u body putem requesta
    @RequestMapping(value = "conversation/new/{senderId}/{receiverId}", method = RequestMethod.POST)
    public ServiceResultData addNewConversation(@RequestBody @Validated NewConversationRequest request, @PathVariable Integer senderId, @PathVariable Integer receiverId){
        return conversationService.addNewConversation(request, senderId, receiverId);
    }

    //TODO: hocemo li omoguciti editiranje poruka?
}
