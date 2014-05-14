package fi.paivola.simlet.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhani on 5/14/14.
 */
public class MessageBus {
    private final List<Message> messages;
    private final List<Message> buffer;

    public MessageBus() {
        this.messages = new ArrayList<Message>();
        this.buffer = new ArrayList<Message>();
    }

    public synchronized void addMessage(Message message) {
        buffer.add(message);
    }

    public synchronized List<Message> getAllMessages() {
        return new ArrayList<Message>(messages);
    }

    public synchronized void updateMessages() {
        this.messages.clear();
        this.messages.addAll(buffer);
        this.buffer.clear();
    }
}
