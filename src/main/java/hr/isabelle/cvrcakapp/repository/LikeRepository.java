package hr.isabelle.cvrcakapp.repository;

import hr.isabelle.cvrcakapp.model.request.LikeRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LikeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ServiceResultData like(LikeRequest request) {
        String sqlCheck = """
            SELECT COUNT(*) FROM POST_LIKE WHERE POST_ID = :postId AND USER_ID = :userId
            """.stripIndent();

        SqlParameterSource sqlParametersCheck = new MapSqlParameterSource()
                .addValue("postId", request.postId)
                .addValue("userId", request.userId);

        int count = namedParameterJdbcTemplate.queryForObject(sqlCheck, sqlParametersCheck, Integer.class);

        if (count > 0) {
            return new ServiceResultData(false, "The post has already been liked by the user.");
        }

        String sqlInsert = """
            INSERT INTO POST_LIKE (POST_ID, USER_ID, LIKE_TIMESTAMP) VALUES (:postId, :userId, GETDATE())
            """.stripIndent();

        SqlParameterSource sqlParametersInsert = new MapSqlParameterSource()
                .addValue("postId", request.postId)
                .addValue("userId", request.userId);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParametersInsert);
        return new ServiceResultData(true, request.likeId);
    }

    //TODO: ZAS OVO NE RADI? I KAK NAPRAVIT DA RADI?
    // Radi - Mika
    // Update: ja sam promjenio da trazi po postId i userId zbog jednostavnosti
    @Transactional
    public ServiceResultData unlike(LikeRequest request) {
        String sqlDelete = """
                DELETE FROM POST_LIKE WHERE POST_ID = :post_id AND USER_ID = :user_id
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("post_id", request.postId)
                .addValue("user_id", request.userId);

        namedParameterJdbcTemplate.update(sqlDelete, sqlParameters);
        return new ServiceResultData(true, request.likeId);
    }
}
