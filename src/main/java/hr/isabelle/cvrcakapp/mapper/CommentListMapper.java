package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Post;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class CommentListMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setUserId(rs.getInt("USER_ID"));
        comment.setContent(rs.getString("COMMENT_CONTENT"));
        comment.setUpdateDate(rs.getDate("UPDATE_DATETIME"));
        comment.setUpdateDate(rs.getDate("COMMENTING_DATETIME"));
        //comment.setDeleteDate(rs.getDate("DELETE_DATETIME"));

        return comment;
    }
}
