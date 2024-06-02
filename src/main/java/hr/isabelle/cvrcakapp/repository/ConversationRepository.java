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

    public ServiceResultData addNewMessage(NewMessageRequest request) {
        String sqlInsert = """
                INSERT INTO MESSAGE(SENDER_ID, RECEIVER_ID, MESSAGE_CONTENT, MESSAGE_TIMESTAMP)
                VALUES(:senderId, :receiverId, :messageContent, GETDATE())
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("senderId", request.senderId)
                .addValue("receiverId", request.receiverId)
                .addValue("messageContent", request.messageContent);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return new ServiceResultData(true, request.messageId);
    }

    public ServiceResultData addNewConversation(NewConversationRequest request, Integer senderId, Integer receiverId) {

        List<Integer> userId = namedParameterJdbcTemplate.queryForList( """
                SELECT M.SENDER_ID FROM MESSAGE AS M WHERE M.ID_MESSAGE =
                (SELECT MIN(M.ID_MESSAGE) FROM MESSAGE AS M WHERE (M.SENDER_ID = :senderId AND M.RECEIVER_ID = :receiverId)
                OR (M.SENDER_ID = :receiverId AND M.RECEIVER_ID = :senderId)) 
                """,
                new MapSqlParameterSource()
                        .addValue("senderId", senderId)
                        .addValue("receiverId", receiverId),
                Integer.class);

        String sqlInsert = """
                INSERT INTO CONVERSATION(USER_ID, MESSAGE_ID)
                VALUES(:userId, :messageId)
                """.stripIndent();

        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("messageId", request.messageId);

        namedParameterJdbcTemplate.update(sqlInsert, sqlParameters);
        return new ServiceResultData(true, request.conversationId);
    }
}
