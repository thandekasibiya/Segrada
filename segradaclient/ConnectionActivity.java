package com.example.segradaclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.segradaclient.messages.Message;
import com.example.segradaclient.messages.MessageReceiver;
import com.example.segradaclient.messages.server.HandleSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.Random;

public class ConnectionActivity extends AppCompatActivity {

    private SegradaClient segradaClient;
    String serverAddress = "192.168.8.103";
    String handle;
 Button Connection;
 TextView IPAddress;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Connection=findViewById(R.id.btnConnect);

        boolean hasPermission = checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;

        String groupName = "lobby";

        segradaClient.joinGroup(groupName);

        //---onConnectClicked---
        Connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handle = handleGenerator();

                addLogMessage("Connecting to " + serverAddress + "...");

                Log.i("ChatClient", "Connecting to " + serverAddress + " as " + handle);
                segradaClient = new SegradaClient(
                        new MessageReceiver() {
                            @Override
                            public void messageReceived(final Message message) {
                                ConnectionActivity.this.runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                String handle = IPAddress.getText().toString();

                                                ConnectionActivity.this.onMessageReceived(message);
                                            }
                                        }
                                );
                            }
                        });

                segradaClient.connect(serverAddress, handle);

            }
        });




    }

    public void onMessageReceived(Message msg) {
        // Display the received message.
        //if(msg instanceof ChatMessageReceived)
        //addChatMessage((ChatMessageReceived) msg);
        //else
        //addLogMessage(msg.toString());

        // The handle that the server set. MIGHT be different from the one
        // requested by the user.
        if(msg instanceof HandleSet) {
            HandleSet hs = (HandleSet) msg;

            setTitle("Client: " + hs.handle);
        }
    }

    private String handleGenerator() {
        byte[] array = new byte[4]; // length is bounded by 5
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return "player" + generatedString;
    }

    private void addLogMessage(String s) {
        Log.println(Log.INFO,"###", s);
    }
}



