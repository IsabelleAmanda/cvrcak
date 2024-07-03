package hr.isabelle.cvrcakapp.repository;

import hr.isabelle.cvrcakapp.model.request.CommentRequest;
import hr.isabelle.cvrcakapp.model.request.LikeRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CommentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ServiceResultData commentPost(CommentRequest request) {
        String sqlInsert = """
                INSERT INTO POST_COMMENT (POST_ID, USER_ID, COMMENT_CONTENT, UPDATE_DATETIME, DELETE_DATETIME, COMMENTING_DATETIME)
                VALUES (:postId, :userId, :content, NULL, NULL, GETDATE())
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("postId", request.postId)
                .addValue("userId", request.userId)
                .addValue("content", request.content);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return new ServiceResultData(true, request.commentId);
    }

    public ServiceResultData updateComment(CommentRequest request) {
        String sqlUpdate = """
                UPDATE POST_COMMENT SET COMMENT_CONTENT = :content, UPDATE_DATETIME = GETDATE()
                WHERE ID_COMMENT = :commentId
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("commentId", request.commentId)
                .addValue("content", request.content);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.commentId);
    }

    public ServiceResultData deleteComment(Integer commentId) {
        String sqlUpdate = """
            UPDATE POST_COMMENT SET DELETE_DATETIME = GETDATE()
            WHERE ID_COMMENT = :commentId
            """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("commentId", commentId);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, commentId);
    }
}
