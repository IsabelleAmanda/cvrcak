package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentListMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();

        comment.setCommentId(rs.getInt("ID_COMMENT"));
        comment.setPostId(rs.getInt("POST_ID"));
        comment.setUserId(rs.getInt("USER_ID"));
        comment.setContent(rs.getString("COMMENT_CONTENT"));
        comment.setUpdateDate(rs.getTimestamp("UPDATE_DATETIME"));
        comment.setCommentingDate(rs.getTimestamp("COMMENTING_DATETIME"));
        comment.setDeleteDate(rs.getTimestamp("DELETE_DATETIME"));

        return comment;
    }
}