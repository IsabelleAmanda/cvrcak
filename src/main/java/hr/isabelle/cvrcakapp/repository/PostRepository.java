package hr.isabelle.cvrcakapp.repository;

import hr.isabelle.cvrcakapp.mapper.UserMapper;
import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.model.request.PostRequest;
import hr.isabelle.cvrcakapp.utils.JdbcParameters;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Post> getAllPosts(){
        try{
        JdbcParameters jdbcParameters = sqlAllPosts();

           List<Post> posts = namedParameterJdbcTemplate.query(jdbcParameters.sqlQuery,
                    jdbcParameters.sqlParameters,
                    new hr.isabelle.cvrcakapp.mapper.PostListMapper());
           return posts;
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private JdbcParameters sqlAllPosts(){
        String sqlQuery = """
                    SELECT * FROM POST
                """.stripIndent();

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        return new JdbcParameters(sqlQuery, sqlParameterSource);
    }


    public Integer getCommentsCount(Integer postId){
        try{
            JdbcParameters jdbcParameters = sqlCommentsCount(postId);

            Integer commentsCount = namedParameterJdbcTemplate.queryForObject(jdbcParameters.sqlQuery,
                    jdbcParameters.sqlParameters, Integer.class);
            return commentsCount;
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private JdbcParameters sqlCommentsCount(Integer postId){
        String sqlQuery = """
                    SELECT COUNT(*) FROM POST_COMMENT WHERE POST_ID = :postId AND DELETE_DATETIME IS NULL
                """.stripIndent();

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("postId", postId);
        return new JdbcParameters(sqlQuery, sqlParameterSource);
    }


    public List<Comment> getComments(Integer postId){
        try{
            JdbcParameters jdbcParameters = sqlComments(postId);

            List<Comment> comments = namedParameterJdbcTemplate.query(jdbcParameters.sqlQuery,
                    jdbcParameters.sqlParameters,
                    new hr.isabelle.cvrcakapp.mapper.CommentListMapper());
            return comments;
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private JdbcParameters sqlComments(Integer postId){
        String sqlQuery = """
                    SELECT USER_ID, COMMENT_CONTENT, COMMENTING_DATETIME, UPDATE_DATETIME, DELETE_DATETIME FROM POST_COMMENT
                    WHERE POST_ID = :postId AND DELETE_DATETIME IS NULL
                """.stripIndent();

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("postId", postId);
        return new JdbcParameters(sqlQuery, sqlParameterSource);
    }


    /*private JdbcParameters findUserByUsername(String username) {

        String sqlQuery = """
                    SELECT USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, IMAGE FROM KORISNIK WHERE USERNAME = :username
                """.stripIndent();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", username);
        return new JdbcParameters(sqlQuery, parameterSource);
    }*/

    public Integer newPost(PostRequest request) {
        String sqlInsert = """
                INSERT INTO POST (USER_ID, POST_TITLE, POST_CONTENT, POSTING_DATETIME, IMAGE, IS_PUBLIC, IS_PERMANENT, DISAPEAR_DATETIME)
                VALUES (:userId, :title, :content, getdate(), :image, :isPublic, :isPermanent, :disappearTime)
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("userId", request.userId)
                .addValue("title", request.title)
                .addValue("content", request.content)
                .addValue("image", request.image)
                .addValue("isPublic", request.isPublic)
                .addValue("isPermanent", request.isPermanent)
                .addValue("disappearTime", request.disappearTime);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return request.postId;
    }

    public ServiceResultData updatePost(PostRequest request) {
        String sqlUpdate = """
                UPDATE POST
                SET POST_TITLE = :title, POST_CONTENT = :content, IMAGE = :image, UPDATE_DATETIME=getdate()
                WHERE ID_POST = :postId
                """.stripIndent();

                SqlParameterSource sqlParameters = new MapSqlParameterSource()
                        .addValue("postId", request.postId)
                        .addValue("title", request.title)
                        .addValue("content", request.content)
                        .addValue("image", request.image);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.postId);
    }

    public ServiceResultData deletePost(PostRequest request) {
        String sqlUpdate = """
                UPDATE POST SET IS_DELETED = 1, DELETE_DATETIME = GETDATE() WHERE ID_POST = :postId
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("postId", request.postId);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.postId);
    }
}
