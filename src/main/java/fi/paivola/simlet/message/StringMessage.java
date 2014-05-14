package fi.paivola.simlet.message;

/**
 * Created by juhani on 5/14/14.
 */
public class StringMessage implements Message {

    private final MessageBus sender;
    private final String string;

    public StringMessage(String string, MessageBus sender) {
        this.string = string;
        this.sender = sender;
    }

    @Override
    public MessageBus getSender() {
        return sender;
    }

    @Override
    public Object getObject() {
        return string;
    }

    public String getString() {
        return string;
    }

}
