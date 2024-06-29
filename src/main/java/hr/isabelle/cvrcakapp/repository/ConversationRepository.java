package hr.isabelle.cvrcakapp.repository;

import hr.isabelle.cvrcakapp.model.Message;
import hr.isabelle.cvrcakapp.model.request.NewConversationRequest;
import hr.isabelle.cvrcakapp.model.request.NewMessageRequest;
import hr.isabelle.cvrcakapp.utils.ServiceResultData;
import net.sf.jsqlparser.expression.JdbcParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ConversationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public ServiceResultData sendMessage(NewMessageRequest request) {
        String sqlInsert = """
                INSERT INTO MESSAGE(SENDER_ID, RECEIVER_ID, MESSAGE_CONTENT, MESSAGE_TIMESTAMP)
                VALUES(:senderId, :receiverId, :messageContent, getdate())
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("senderId", request.senderId)
                .addValue("receiverId", request.receiverId)
                .addValue("messageContent", request.messageContent);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return new ServiceResultData(true, request.messageId);
    }

    public Integer getMessageCount(int senderId, int receiverId) {
        String sqlQuery = """
            SELECT COUNT(*) FROM MESSAGE WHERE (SENDER_ID = :senderId AND RECEIVER_ID = :receiverId)
                OR (SENDER_ID = :receiverId AND RECEIVER_ID = :senderId)
                        """;

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("senderId", senderId)
                .addValue("receiverId", receiverId);

        return namedParameterJdbcTemplate.queryForObject(sqlQuery, parameterSource, Integer.class);
    }
}
