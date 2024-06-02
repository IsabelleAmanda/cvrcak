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
        String sqlInsert = """
                INSERT INTO POST_LIKE (POST_ID, USER_ID, LIKE_TIMESTAMP) VALUES (:postId, :userId, GETDATE())
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("postId", request.postId)
                .addValue("userId", request.userId);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return new ServiceResultData(true, request.likeId);
    }

    //TODO: ZAS OVO NE RADI? I KAK NAPRAVIT DA RADI?
    @Transactional
    public ServiceResultData unlike(LikeRequest request) {
        String sqlDelete = """
                DELETE FROM POST_LIKE WHERE ID_LIKE = :likeId
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("likeId", request.likeId);

        namedParameterJdbcTemplate.update(sqlDelete, sqlParameters);
        return new ServiceResultData(true, request.likeId);
    }
}
