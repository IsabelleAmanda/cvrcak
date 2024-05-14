package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;

@Validated
public class LikeRequest {
    public Integer likeId;
    public Integer userId;
    public Integer postId;
}
