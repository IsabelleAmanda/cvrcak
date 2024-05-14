package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.model.request.PostRequest;
import hr.isabelle.cvrcakapp.repository.PostRepository;
import hr.isabelle.cvrcakapp.repository.UserRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    PostRepository postRepository;

    PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public ServiceResultData getAllPosts() {
        return new ServiceResultData(true, this.postRepository.getAllPosts());
    }
    public ServiceResultData getCommentsCount(Integer postId) {
        return new ServiceResultData(true, this.postRepository.getCommentsCount(postId));
    }

    public ServiceResultData getComments(Integer postId) {
        return new ServiceResultData(true, this.postRepository.getComments(postId));
    }

    public ServiceResultData newPost(PostRequest request) {
        return new ServiceResultData(true, this.postRepository.newPost(request));
    }

    public ServiceResultData updatePost(PostRequest request) {
        return this.postRepository.updatePost(request);
    }

    public ServiceResultData deletePost(PostRequest request) {
        return this.postRepository.deletePost(request);
    }
}
