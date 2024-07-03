package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.Post;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@NoArgsConstructor
public class PostListMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getInt("ID_POST"));
        post.setUserId(rs.getInt("USER_ID"));
        post.setTitle(rs.getString("POST_TITLE"));
        post.setContent(rs.getString("POST_CONTENT"));
        post.setImage(rs.getString("IMAGE"));
        post.setPostingDate(rs.getTimestamp("POSTING_DATETIME"));
        post.setIsDeleted(rs.getBoolean("IS_DELETED"));
        post.setUpdateDate(rs.getDate("UPDATE_DATETIME"));
        post.setDeleteDate(rs.getDate("DELETE_DATETIME"));
        post.setIsPublic(rs.getBoolean("IS_PUBLIC"));
        post.setIsPermanent(rs.getBoolean("IS_PERMANENT"));
        Timestamp timestamp = rs.getTimestamp("DISAPEAR_DATETIME");
        if (timestamp != null) {
            post.setDisappearTime(timestamp.toLocalDateTime());
        }
        //TODO: istražiti kako se settira kolekcija
        post.setComments(null);
        post.setLikes(null);

        return post;
    }
}
