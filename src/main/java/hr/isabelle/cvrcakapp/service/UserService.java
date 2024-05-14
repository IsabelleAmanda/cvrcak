package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.repository.ConversationRepository;
import hr.isabelle.cvrcakapp.repository.UserRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ServiceResultData getUserByUsername(String username) {
        return new ServiceResultData(true, this.userRepository.getUserByUsername(username));
    }

    public ServiceResultData registerUser(NewUserRequest request) {
        return new ServiceResultData(true, this.userRepository.registerUser(request));
    }

    public ServiceResultData updateUser(NewUserRequest request) {
        return this.userRepository.updateUser(request);
    }

    public ServiceResultData deleteUser(NewUserRequest request) {
        return this.userRepository.deleteUser(request);
    }
}
