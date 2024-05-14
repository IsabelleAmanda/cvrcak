package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.Comment;
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
        post.setTitle(rs.getString("POST_TITLE"));
        post.setContent(rs.getString("POST_CONTENT"));
        post.setImage(rs.getString("IMAGE"));
        post.setPostingDate(rs.getTimestamp("POSTING_DATETIME"));
        post.setUpdateDate(rs.getDate("UPDATE_DATETIME"));
        post.setDeleteDate(rs.getDate("DELETE_DATETIME"));
        post.setIsPublic(rs.getBoolean("IS_PUBLIC"));
        post.setIsPermanent(rs.getBoolean("IS_PERMANENT"));

        //TODO: istražiti kako se settira kolekcija
        post.setComments(null);
        post.setLikes(null);

        return post;
    }
}
