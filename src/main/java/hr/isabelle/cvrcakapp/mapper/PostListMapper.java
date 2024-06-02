package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Like;
import hr.isabelle.cvrcakapp.model.Post;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class PostListMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getInt("ID_POST"));
        post.setTitle(rs.getString("POST_TITLE"));
        post.setContent(rs.getString("POST_CONTENT"));
        post.setImage(rs.getString("IMAGE"));
        post.setPostingDate(rs.getTimestamp("POSTING_DATETIME"));
        post.setUpdateDate(rs.getDate("UPDATE_DATETIME"));
        post.setDeleteDate(rs.getDate("DELETE_DATETIME"));
        post.setIsPublic(rs.getBoolean("IS_PUBLIC"));
        post.setIsPermanent(rs.getBoolean("IS_PERMANENT"));
        //post.setActivityId(rs.getInt("ACTIVITY_ID"));

        //TODO: istražiti kako se settira kolekcija
        /*Like like = new Like();
        like.setPostId(rs.getInt("ID_POST"));
        like.setLikeId(rs.getInt("ID_LIKE"));
        List<Like> likes = new ArrayList<>();
        likes.add(like);*/
        post.setLikes(null);

        post.setComments(null);

        return post;
    }
}
