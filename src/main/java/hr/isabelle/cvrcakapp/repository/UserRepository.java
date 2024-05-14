package hr.isabelle.cvrcakapp.repository;

import hr.isabelle.cvrcakapp.mapper.UserMapper;
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
        try{
        JdbcParameters jdbcParameters = findUserByUsername(username);

           User user = namedParameterJdbcTemplate.queryForObject(
                    jdbcParameters.sqlQuery,
                    jdbcParameters.sqlParameters,
                    new UserMapper());

           //TODO: varti ga, ali ga ne ispise na postmanu, isto ko ni success za sve ostalo, ZASTO?!
           return user;
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private JdbcParameters findUserByUsername(String username) {

        String sqlQuery = """
                    SELECT USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, IMAGE FROM KORISNIK WHERE USERNAME = :username
                """.stripIndent();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", username);
        return new JdbcParameters(sqlQuery, parameterSource);
    }

    public ServiceResultData registerUser(NewUserRequest request) {
    //TODO: dodati dodatne provjere ako vec postoji email/username -> select queryForList, ako size==0 onda moze taj username
        String sqlInsert = """
                INSERT INTO KORISNIK(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL, COUNTRY_ID, GENDER, BIRTHDAY, IMAGE, IS_DELETED)
                VALUES(:username, :firstName, :lastName, :password, :email, 1, :gender, :birthday, :image, 0)
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("username", request.username)
                .addValue("firstName", request.firstName)
                .addValue("lastName", request.lastName)
                .addValue("password", request.password)
                .addValue("email", request.email)
                //.addValue("country", request.country)
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
                        //.addValue("country", request.country)
                        .addValue("gender", request.gender)
                        .addValue("birthday", request.birthday)
                        .addValue("image", request.image);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.userId);
    }

    public ServiceResultData deleteUser(NewUserRequest request) {
        String sqlUpdate = """
                UPDATE KORISNIK SET IS_DELETED = 1 WHERE ID_USER = :userId
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("userId", request.userId);

        namedParameterJdbcTemplate.update(sqlUpdate, sqlParameters);
        return new ServiceResultData(true, request.userId);
    }
}
