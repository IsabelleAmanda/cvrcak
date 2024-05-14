package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.request.LikeRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.repository.LikeRepository;
import hr.isabelle.cvrcakapp.repository.UserRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    LikeRepository likeRepository;

    LikeService(LikeRepository likeRepository){
        this.likeRepository = likeRepository;
    }

    public ServiceResultData like(LikeRequest request) {
        return this.likeRepository.like(request);
    }

    public ServiceResultData unlike(LikeRequest request) {
        return this.likeRepository.unlike(request);
    }
}
