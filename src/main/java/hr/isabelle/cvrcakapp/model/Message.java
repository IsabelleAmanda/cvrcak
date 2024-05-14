package hr.isabelle.cvrcakapp.model;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

public class Message {
    Integer id;
    Integer senderId; //ili cijeli user?
    Integer receiverId;
    String messageContent;
    DateTimeLiteralExpression.DateTime sendDate;

}
