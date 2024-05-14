package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Pattern;

@Validated
public class NewMessageRequest {
    public Integer messageId;
    public Integer senderId;
    public Integer receiverId;
    @Pattern(regexp = "^.{0,160}$", message = "Too long message")
    public String messageContent;

    String sendTime;
}
