package hr.isabelle.cvrcakapp.repository;

import hr.isabelle.cvrcakapp.mapper.CommentListMapper;
import hr.isabelle.cvrcakapp.mapper.LikeMapper;
import hr.isabelle.cvrcakapp.mapper.PostListMapper;
import hr.isabelle.cvrcakapp.mapper.UserMapper;
import hr.isabelle.cvrcakapp.model.Comment;
import hr.isabelle.cvrcakapp.model.Like;
import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.User;
import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.model.request.NewUserRequest;
import hr.isabelle.cvrcakapp.utils.JdbcParameters;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import net.sf.jsqlparser.expression.JdbcParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public User getUserByUsername(String username){
        try {
            JdbcParameters jdbcParameters = findUserByUsername(username);

            List<User> users = namedParameterJdbcTemplate.query(
                    jdbcParameters.sqlQuery,
                    jdbcParameters.sqlParameters,
                    new UserMapper());

            if(users.isEmpty()) {
                return null;
            } else if (users.size() == 1) {
                return users.getFirst();
            } else { // List has more than 1 user with the same username
                return null;
            }
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private JdbcParameters findUserByUsername(String username) {

        String sqlQuery = """
                    SELECT ID_USER, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, IMAGE, REGISTER_TIMESTAMP FROM KORISNIK WHERE USERNAME = :username
                """.stripIndent();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", username);
        return new JdbcParameters(sqlQuery, parameterSource);
    }

    public ServiceResultData registerUser(NewUserRequest request) {
        // Check if the username or email already exists
        String checkUserSql = "SELECT COUNT(*) FROM KORISNIK WHERE USERNAME = :username OR EMAIL = :email";
        SqlParameterSource checkUserParams = new MapSqlParameterSource()
                .addValue("username", request.username)
                .addValue("email", request.email);
        Integer count = namedParameterJdbcTemplate.queryForObject(checkUserSql, checkUserParams, Integer.class);
        if (count != null && count > 0) {
            return new ServiceResultData(false, "Username or email already exists");
        }

        String sqlInsert = """
                INSERT INTO KORISNIK(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL, COUNTRY_ID, GENDER, BIRTHDAY, IMAGE, REGISTER_TIMESTAMP, IS_DELETED)
                VALUES(:username, :firstName, :lastName, :password, :email, 1, :gender, :birthday, :image, GETDATE(), 0)
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("username", request.username)
                .addValue("firstName", request.firstName)
                .addValue("lastName", request.lastName)
                .addValue("password", request.password)
                .addValue("email", request.email)
                .addValue("gender", request.gender)
                .addValue("birthday", request.birthday)
                .addValue("image", request.image);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return new ServiceResultData(true, request.userId);
    }

    public ServiceResultData updateUser(NewUserRequest request) {
        String sqlUpdate = """
                UPDATE KORISNIK
                SET USERNAME = :username, FIRST_NAME = :firstName, LAST_NAME = :lastName,
                PASSWORD = :password, EMAIL = :email, GENDER = :gender, BIRTHDAY = :birthday, IMAGE = :image
                WHERE ID_USER = :userId
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("userId", request.userId)
                .addValue("username", request.username)
                .addValue("firstName", request.firstName)
                .addValue("lastName", request.lastName)
                .addValue("password", request.password)
                .addValue("email", request.email)
                .addValue("gender", request.gender)
                .addValue("birthday", request.birthday)
                .addValue("image", request.image);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.userId);
    }

    public ServiceResultData deleteUser(NewUserRequest request) {
        String sqlUpdate = """
                UPDATE KORISNIK
                SET IS_DELETED = 1,
                    USERNAME = 'user_' + CAST(
                        (SELECT ISNULL(MAX(CAST(SUBSTRING(USERNAME, 6,1) AS INT)), 0) + 1
                         FROM KORISNIK WHERE ISNUMERIC(SUBSTRING(USERNAME, 6, 1)) = 1) AS NVARCHAR(50)),
                    FIRST_NAME = 'user',
                    LAST_NAME = 'user',
                    PASSWORD = 'password',
                    EMAIL = 'user',
                    GENDER = 'M',
                    BIRTHDAY = '2000-01-01',
                    IMAGE = NULL
                WHERE ID_USER = :userId
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("userId", request.userId);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.userId);
    }
    public List<User> getUsersByName(String name) {
        String sqlQuery = """
            SELECT ID_USER, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, IMAGE
            FROM KORISNIK
            WHERE FIRST_NAME LIKE :name OR LAST_NAME LIKE :name
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("name", name + "%");
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new UserMapper());
    }

    public int getFollowersCount(int userId) {
        String sqlQuery = "SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWED_USER_ID = :userId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, parameterSource, Integer.class);
    }

    public int getFollowingCount(int userId) {
        String sqlQuery = "SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWING_USER_ID = :userId";
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, parameterSource, Integer.class);
    }

    public List<User> getFollowers(int userId, int page) {
        int limit = 100;
        int offset = (page - 1) * limit;
        String sqlQuery = """
            SELECT ID_USER, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, IMAGE
            FROM KORISNIK
            WHERE ID_USER IN (SELECT FOLLOWING_USER_ID FROM FOLLOW WHERE FOLLOWED_USER_ID = :userId)
            ORDER BY USERNAME
            OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new UserMapper());
    }

    public List<User> getFollowing(int userId, int page) {
        int limit = 100;
        int offset = (page - 1) * limit;
        String sqlQuery = """
            SELECT ID_USER, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, IMAGE
            FROM KORISNIK
            WHERE ID_USER IN (SELECT FOLLOWED_USER_ID FROM FOLLOW WHERE FOLLOWING_USER_ID = :userId)
            ORDER BY USERNAME
            OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new UserMapper());
    }


    // Fetches posts made by a specific user from the database
    public List<Post> getPostsByUserId(int userId) {
        String sqlQuery = """
            SELECT * FROM POST
            WHERE USER_ID = :userId
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new PostListMapper());
    }

    // Fetches comments on a specific post from the database
    /*public List<Comment> getCommentsByPostId(int postId) {
        String sqlQuery = """
            SELECT * FROM POST_COMMENT
            WHERE POST_ID = :postId
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("postId", postId);
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new CommentListMapper());
    }*/

    // Fetches likes on a specific post from the database
    public List<Like> getLikesByPostId(int postId) {
        String sqlQuery = """
            SELECT * FROM POST_LIKE
            WHERE POST_ID = :postId
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("postId", postId);
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new LikeMapper());
    }

    // Fetches posts that a specific user has liked from the database
    public List<Post> getPostsLikedByUserId(int userId) {
        String sqlQuery = """
            SELECT * FROM POST
            WHERE ID_POST IN (SELECT POST_ID FROM POST_LIKE WHERE USER_ID = :userId)
        """.stripIndent();
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.query(sqlQuery, parameterSource, new PostListMapper());
    }

    // Follows a user in the database
    public void followUser(int userId, int followerId) {
        String sqlInsert = """
            INSERT INTO FOLLOW (FOLLOWING_USER_ID, FOLLOWED_USER_ID, FOLLOWING_TIMESTAMP)
            VALUES (:followerId, :userId, GETADET())
        """.stripIndent();
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("followerId", followerId)
                .addValue("userId", userId);
        //TODO: dodati insert u activity

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
    }

    // Unfollows a user in the database
    public void unfollowUser(int userId, int followerId) {
        String sqlDelete = """
            DELETE FROM FOLLOW
            WHERE FOLLOWING_USER_ID = :followerId AND FOLLOWED_USER_ID = :userId
        """.stripIndent();
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("followerId", followerId)
                .addValue("userId", userId);
        namedParameterJdbcTemplate.update(sqlDelete, sqlParameters);
    }
}
