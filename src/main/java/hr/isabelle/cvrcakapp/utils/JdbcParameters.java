package hr.isabelle.cvrcakapp.utils;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@AllArgsConstructor
public class JdbcParameters {
    public String sqlQuery;
    public MapSqlParameterSource sqlParameters;
}
