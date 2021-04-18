package network;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SegradaServer {
    //1. establish the connection
    //2. obtain the streams
    //3. creating a handler object
    //4. invoking the start() method

    public static void main(String[] args) throws Exception {
        new SegradaServer();
    }

    // The network.messages.server socket that listens to port 5056 for connection requests.
    private ServerSocket server;

    private int nClients = 0;

    public SegradaServer() throws Exception {
        // Start new network.messages.server socket on port 5056.
        server = new ServerSocket(5056);
        System.out.printf("Chat network.messages.server started on: %s:5056\n",
                InetAddress.getLocalHost().getHostAddress());

        // Create the initial group to which all clients belong.
        //network.Groups.addGroup("Lobby");

        while(true) {
            // Accept connection requests.
            Socket client = server.accept();
            System.out.printf("Connection request received: %s\n", client.getInetAddress().getHostAddress());

            // Increment number of clients encountered.
            nClients++;

            // Create new network.messages.client connection object to manage.
            SegradaClient segradaClient = new SegradaClient(client, nClients);
        }
    }

}
