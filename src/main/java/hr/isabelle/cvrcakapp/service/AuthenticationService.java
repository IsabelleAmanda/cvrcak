package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.repository.UserRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public ServiceResultData register(NewUserRequest request) {
        // TODO: not sure if this is the best way to encode the password
        request.password = passwordEncoder.encode(request.password);

        return userRepository.registerUser(request);
    }

    public User authenticate(NewUserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.username, request.password
        ));

        return userRepository.getUserByUsername(request.username);
    }
}
