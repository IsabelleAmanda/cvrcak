package hr.isabelle.cvrcakapp.model.request;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;

@Validated
public class NewConversationRequest {
    public Integer conversationId;
    public Integer userId;
    public Integer messageId;
}
