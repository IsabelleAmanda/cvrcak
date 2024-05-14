package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.request.CommentRequest;
import hr.isabelle.cvrcakapp.model.request.LikeRequest;
import hr.isabelle.cvrcakapp.repository.CommentRepository;
import hr.isabelle.cvrcakapp.repository.LikeRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    CommentRepository commentRepository;

    CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public ServiceResultData commentPost(CommentRequest request) {
        return this.commentRepository.commentPost(request);
    }
    public ServiceResultData updateComment(CommentRequest request) {
            return this.commentRepository.updateComment(request);
    }

    public ServiceResultData deleteComment(CommentRequest request) {
        return this.commentRepository.deleteComment(request);
    }
}
