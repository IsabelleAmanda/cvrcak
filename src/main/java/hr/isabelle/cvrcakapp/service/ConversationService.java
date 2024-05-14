package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.Message;
import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.repository.ConversationRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    ConversationRepository conversationRepository;

    ConversationService(ConversationRepository conversationRepository){
        this.conversationRepository = conversationRepository;
    }

    public ServiceResultData addNewMessage(NewMessageRequest request) {
        return this.conversationRepository.addNewMessage(request);
    }

    public ServiceResultData addNewConversation(NewConversationRequest request, Integer senderId, Integer receiverId) {
        return this.conversationRepository.addNewConversation(request, senderId, receiverId);
    }
}
