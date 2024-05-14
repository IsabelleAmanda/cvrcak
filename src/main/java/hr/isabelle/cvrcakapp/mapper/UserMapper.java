package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.User;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUsername(rs.getString("USERNAME"));
        user.setFirstName(rs.getString("FIRST_NAME"));
        user.setLastName(rs.getString("LAST_NAME"));
        user.setEmail(rs.getString("EMAIL"));
        user.setBirthday(rs.getDate("BIRTHDAY"));
        user.setImage(rs.getString("IMAGE"));

        return user;
    }
}
