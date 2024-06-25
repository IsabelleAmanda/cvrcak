package hr.isabelle.cvrcakapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    private Integer id;
    private List<Integer> participants = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addParticipants(int p1, int p2) {
        participants.add(p1);
        participants.add(p2);
    }
}
