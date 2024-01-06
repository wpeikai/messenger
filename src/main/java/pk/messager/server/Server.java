package pk.messager.server;

import pk.messager.client.InvalidInputException;
import pk.messager.domain.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    /**
     * Internal store for user and their messages
     */
    private Map<String, List<Message>> msgs = new ConcurrentHashMap<>();

    /**
     * @param username
     * @return number of message for this user.
     */
    public int login(String username) {
        if (!isUserLoggedIn(username)) {
            msgs.put(username, new ArrayList<>());
        }
        return msgs.get(username).size();
    }

    /**
     * Assumption: message can only be sent to a logged in user. You can send to yourself
     * @param msg
     * @param msg
     */
    public void send(Message msg) throws InvalidInputException {
        if (!isUserLoggedIn(msg.getRecipient())) {
            throw new InvalidInputException("you can only send to a logged in user");
        }
        if (msg.getContent() == null) {
            throw new InvalidInputException("Message content cannot be null");
        }
        msgs.get(msg.getRecipient()).add(msg);
    }

    public void broadcast(Message msg) throws InvalidInputException {
        if (!isUserLoggedIn(msg.getSender())) {
            throw new InvalidInputException("Please log in first");
        }
        if (msg.getContent() == null) {
            throw new InvalidInputException("Message content cannot be null");
        }
        msgs.forEach((k, v) -> {
            if (!msg.getSender().equals(k)) {
                v.add(new Message(msg.getContent(), msg.getSender(), k));
            }
        });
    }

    public int readCount(String username) {
        return msgs.get(username).size();
    }

    /**
     * Read all message for user
     * @param username
     * @return
     */
    public List<Message> readAll(String username) {
        return msgs.get(username);
    }

    public Message read(String username, int num) throws InvalidInputException {
        if (num < 1 || num > msgs.get(username).size()) {
            throw new InvalidInputException("Invalid message number");
        }
        return msgs.get(username).get(num-1);
    }

    private boolean isUserLoggedIn(String username) {
        return msgs.containsKey(username);
    }
}
