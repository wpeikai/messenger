package pk.messager;

import pk.messager.client.Client;
import pk.messager.server.Server;

import java.util.Scanner;

public class MessagerApp {
    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(server);
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.equals("exit")) {
            client.call(input);
            input = in.nextLine();
        }
    }
}
