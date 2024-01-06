package pk.messager.client;

import pk.messager.domain.Message;
import pk.messager.server.Server;

import java.util.List;

public class Client {

    private Server server;
    private CmdParser parser;
    private String currentUser;
    private Message lastMessage;

    public Client(Server server) {
        this.server = server;
        parser = new CmdParser();
    }

    public void call(String input) {
        Cmd cmd = null;
        try {
            cmd = parser.parse(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }
        List<String> params = cmd.getParams();
        if (cmd.getCmdCode() != CmdCode.LOGIN && currentUser == null) {
            System.out.println("Please login first");
            return;
        }
        switch (cmd.getCmdCode()) {
            case LOGIN:
                if (params.size() != 1) {
                    System.out.println("Only one param expected for login");
                    return;
                }
                login(params.get(0));
                break;
            case SEND:
                if (params.size() != 1 || cmd.getMessage() == null) {
                    System.out.println("please specify recipient and message content");
                    return;
                }
                try {
                    server.send(new Message(cmd.getMessage(), currentUser, params.get(0)));
                    System.out.println("message sent.");
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case REPLY:
                if (lastMessage == null) {
                    System.out.println("Please read first before reply");
                    return;
                }
                try {
                    server.send(new Message(cmd.getMessage(), currentUser, lastMessage.getSender()));
                    System.out.println("message replied");
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case FORWARD:
                if (lastMessage == null) {
                    System.out.println("Please read first before forward");
                    return;
                }
                if (params.size() != 1) {
                    System.out.println("please specify recipient");
                    return;
                }
                try {
                    server.send(new Message(lastMessage.getContent(), currentUser, params.get(0)));
                    System.out.println("message forwarded to " + params.get(0));
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case READ:
                if (params.size() > 1) {
                    System.out.println("Please use following format: Read <msg number>. Specify 0 if you want to read all");
                    return;
                }
                if (params.size() == 0) {
                    int msgCount = server.readCount(currentUser);
                    StringBuilder countMsg = new StringBuilder();
                    if (msgCount > 0) {
                        countMsg.append(msgCount)
                                .append(" new messages. Choose a number from 1 to ")
                                .append(msgCount)
                                .append(" to pick the message to read. Pick 0 to read all.");
                    } else {
                        countMsg.append("No new messages");
                    }
                    System.out.println(countMsg);
                    return;
                }
                try {
                    int msgNo = Integer.valueOf(params.get(0));
                    if (msgNo == 0) {
                        server.readAll(currentUser).forEach(m -> {
                            System.out.println("From " + m.getSender() + ": " + m.getContent());
                            lastMessage = m;
                        });
                        return;
                    }
                    lastMessage = server.read(currentUser, msgNo);
                    System.out.println("From " + lastMessage.getSender() + ": " + lastMessage.getContent());
                } catch (NumberFormatException e) {
                    System.out.println("Please use following format: read <msg number>. Specify 0 if you want to read all");
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please use following format: read <msg number>. Specify 0 if you want to read all");
                }
                break;
            case BROADCAST:
                if (!params.isEmpty() || cmd.getMessage() == null) {
                    System.out.println("please specify only message content");
                    return;
                }
                try {
                    server.broadcast(new Message(cmd.getMessage(), currentUser, null));
                    System.out.println("message broadcast");
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
                break;
        }
    }

    void login(String username) {
        int msgCount = server.login(username);
        currentUser = username;
        StringBuilder loginMsg = new StringBuilder(currentUser);
        loginMsg.append(" logged in.");
        if (msgCount > 0) {
            loginMsg.append(" ")
                    .append(msgCount)
                    .append(" new messages. Choose a number from 1 to ")
                    .append(msgCount)
                    .append(" to pick the message to read. Pick 0 to read all.");
        }
        System.out.println(loginMsg);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
