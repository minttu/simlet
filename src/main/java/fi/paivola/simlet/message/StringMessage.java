package fi.paivola.simlet.message;

/**
 * Created by juhani on 5/14/14.
 */
public class StringMessage implements Message {

    private final MessageBus sender;
    private final String name;
    private final String value;

    public StringMessage(String name, String value, MessageBus sender) {
        this.name = name;
        this.value = value;
        this.sender = sender;
    }

    @Override
    public MessageBus getSender() {
        return sender;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object getObject() {
        return value;
    }

    public String getString() {
        return value;
    }

}
