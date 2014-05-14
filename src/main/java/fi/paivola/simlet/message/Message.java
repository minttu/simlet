package fi.paivola.simlet.message;

/**
 * Created by juhani on 5/14/14.
 */
public interface Message {
    public MessageBus getSender();

    public Object getObject();
}
