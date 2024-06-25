package hr.isabelle.cvrcakapp.mapper;

import hr.isabelle.cvrcakapp.model.Message;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();

        message.setId(rs.getInt("ID_MESSAGE"));
        message.setSenderId(rs.getInt("SENDER_ID"));
        message.setReceiverId(rs.getInt("RECEIVER_ID"));
        message.setContent(rs.getString("MESSAGE_CONTENT"));
        message.setSendDate(rs.getTimestamp("MESSAGE_TIMESTAMP"));

        return message;
    }
}
