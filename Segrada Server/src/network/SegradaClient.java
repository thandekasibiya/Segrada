package network;

import network.messages.Message;
import network.messages.client.Quit;
import network.messages.server.GroupsListed;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SegradaClient {


    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private BlockingQueue<Message> outgoingMessages = new LinkedBlockingDeque<>();

    private ReadThread readThread; // Reads network.messages from this specific network.messages.client.
    private WriteThread writeThread; // Writes network.messages to this specific network.messages.client.

    // Details about the current connection's network.messages.client.
    public int nClients;
    public String userName = "";
    public String groupName = "";

    public SegradaClient(Socket clientSocket, int nClients) {
        this.clientSocket = clientSocket;
        this.nClients = nClients;

        readThread = new ReadThread();
        readThread.start();

        // Add network.messages.client to lobby.
        //network.Groups.join("Lobby", network.SegradaClient.this);

        // Queue group list to be sent to network.messages.client.
        send(new GroupsListed());
    }

    public void send(Message message) {
        try {
            outgoingMessages.put(message);
        } catch (InterruptedException e) {
            System.out.println(nClients + ": Read.Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(nClients + ": Read thread started.");

                // Obtain I/O streams. Gotcha for object streams is to make sure
                // that both sides do not set up the input stream first. One must
                // set up the output stream and flush it, else the other side will
                // wait for its input stream to be initialised (which it never will).
                // Remember this side's output is other side's input.
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println(nClients + ": Obtained I/O streams.");

                // Start write loop thread. Start write thread here to ensure
                // that the I/O streams have been initialised correctly BEFORE
                // starting to read and write network.messages.
                writeThread = new WriteThread();
                writeThread.start();

                // Read network.messages from network.messages.client.
                System.out.println(nClients + ": Started Read Loop...");
                Message msg;
                do {
                    // Read next message (blocking operation).
                    msg = (Message) inputStream.readObject();
                    System.out.println(nClients + " --> " + msg);

                    // Process the message.
                    msg.apply(SegradaClient.this);

                } while (msg.getClass() != Quit.class);

                // Close the connection.
                clientSocket.close();

            } catch (Exception e) {
                System.out.println(nClients + ": Read.Exception: " + e.getMessage());
                e.printStackTrace();

            } finally {
                System.out.println(nClients + ": Leaving groups...");
                Groups.leave(SegradaClient.this);

                System.out.println(nClients + ": Stopping Write thread...");
                writeThread.interrupt();

                System.out.println(nClients + ": Read thread finished.");
            }
        }
    }

    private class WriteThread extends Thread {
        @Override
        public void run() {
            System.out.println(nClients + ": Started Write Loop thread...");

            // Remember this thread.
            writeThread = this;

            try {
                // Check outgoing network.messages and send.
                while (!isInterrupted()) {
                    // Dequeue message to send. Take blocks until something to send.
                    Message msg = outgoingMessages.take();

                    outputStream.writeObject(msg);
                    outputStream.flush();

                    System.out.println(msg + " --> " + nClients);
                }

            } catch (Exception e) {
                System.out.println(nClients + ": Write.Exception = " + e.getMessage());
                e.printStackTrace();

            } finally {
                writeThread = null;
                System.out.println(nClients + ": Write thread finished.");
            }
        }
    }




}
