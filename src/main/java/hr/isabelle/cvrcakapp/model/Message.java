package hr.isabelle.cvrcakapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    Integer id;
    Integer senderId;
    Integer receiverId;
    String content;
    Date sendDate;
}
