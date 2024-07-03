package hr.isabelle.cvrcakapp.service;

import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Like;
import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.request.PostRequest;
import hr.isabelle.cvrcakapp.repository.PostRepository;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.getPostsByTitle(title);
    }

    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }
    public Integer getCommentsCount(Integer postId) {
        return this.postRepository.getCommentsCount(postId);
    }

    public List<Comment> getComments(Integer postId) {
        return this.postRepository.getComments(postId);
    }

    // Fetches likes on a specific post
    public List<Like> getLikesByPostId(int postId) {
        return postRepository.getLikesByPostId(postId);
    }

    public Integer newPost(PostRequest request) {
        return postRepository.newPost(request);
    }

    public ServiceResultData updatePost(PostRequest request) {
        return this.postRepository.updatePost(request);
    }

    public ServiceResultData deletePost(Integer postId) {
        return this.postRepository.deletePost(postId);
    }

    public int getLikesCount(Integer postId) {
        return postRepository.getLikesCountByPostId(postId);

    }
}
