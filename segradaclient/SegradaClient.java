package com.example.segradaclient;

import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



import com.example.segradaclient.messages.Message;
import com.example.segradaclient.messages.MessageReceiver;
import com.example.segradaclient.messages.client.Join;
import com.example.segradaclient.messages.client.Leave;
import com.example.segradaclient.messages.client.Quit;
import com.example.segradaclient.messages.client.SetHandle;

public class SegradaClient {
    private String serverAddress;

    // I/O streams for communication.
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    private BlockingQueue<Message> outgoingMessages;
    private MessageReceiver messageReceiver;

    private Thread readThread; // Thread that reads messages from the server.
    private Thread writeThread; // Thread that writes messages to the server.

    public SegradaClient(MessageReceiver messageReceiver) {
        super();
        outgoingMessages = new LinkedBlockingQueue<>();
        this.messageReceiver = messageReceiver;
    }

    public void connect(String serverAddress, String handle) {
        Log.i("SegradaClient", "Connecting to " + serverAddress + "...");

        // Cache info.
        this.serverAddress = serverAddress;
        // Details about the client.

        // Start the read thread (which establishes a connection.
        Log.i("SegradaClient", "Starting Read Loop thread...");
        readThread = new ReadThread();
        readThread.start();

        // Send the setHandle message.
        Log.i("SegradaClient", "Queuing SetHandle(" + handle + ")");
        send(new SetHandle(handle));
    }

    public void disconnect() {
        leaveGroup();
        send(new Quit());
    }

    public void joinGroup(String group)
    {
        send(new Join(group));
    }

    public void leaveGroup()
    {
        send( new Leave());
    }

    private void send(Message message) {
        try {
            outgoingMessages.put(message);
        } catch (InterruptedException e) {
            Log.e("SegradaClient", e.getMessage());
        }
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            Log.i("SegradaClient", "Started Read Loop thread...");

            readThread = this;

            try {
                // Connect to server (if can).
                Socket connection = new Socket(serverAddress, 5056);
                Log.i("SegradaClient", "Connected to " + serverAddress + "...");

                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                Log.i("SegradaClient", "Obtained I/O stream...");

                // Create and start the write thread.
                Log.i("SegradaClient", "Starting Write Loop thread...");
                writeThread = new WriteThread();
                writeThread.start();

                // Go into read message loop.
                Log.i("SegradaClient", "Starting Read Loop...");
                Message msg;
                do {
                    // Read message from server.
                    msg = (Message) in.readObject();
                    Log.i("SegradaClient", ">> " + msg);

                    // If Message Receiver given, pass it the message.
                    if (messageReceiver != null) messageReceiver.messageReceived(msg);
                } while (msg.getClass() != Quit.class);

                // Done, so close connection.
                connection.close();
                Log.i("SegradaClient", "Closed connection...");


            } catch (Exception e) {
                Log.e("SegradaClient", "Exception: " + e.getMessage());

            } finally {
                readThread = null;
                if (writeThread != null) writeThread.interrupt();
            }
        }
    }

    private class WriteThread extends Thread {
        @Override
        public void run() {
            Log.i("SegradaClient", "Started Write Loop thread...");

            try {
                while (true) {
                    Message msg = outgoingMessages.take();

                    out.writeObject(msg);
                    out.flush();

                    Log.i("SegradaClient", "<< " + msg);
                }
            } catch (Exception e) {
                writeThread = null;
            }
        }
    }
}
