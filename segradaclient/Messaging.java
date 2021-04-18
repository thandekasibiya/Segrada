package com.example.segradaclient;
// Reference Lecture Examples
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Messaging extends AppCompatActivity {
    private Button btnConnect;
    private Button btnDisconnect;
    private Button btnUserList;

    private EditText txtMessage;
    private EditText txtUser;

    private ListView lstMessages;

    private ArrayList<String> messages;
    private ArrayAdapter<String> adapter;

    private ServerThread connection;

    private LinearLayout layNotConnected;
    private LinearLayout layConnected;

    // string constants that are used to indicate which command is being
    // received by the server from the client or for returning results to the
    // client
    private static final String MESSAGE_COMMAND = "#MESSAGE";
    private static final String USERS_COMMAND = "#USERS";
    private static final String QUIT_COMMAND = "#QUIT";

    // address of server machine
    private static final String ip = "10.0.0.109";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        // get references to views
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtUser = (EditText) findViewById(R.id.txtUser);
        lstMessages = (ListView) findViewById(R.id.lstMessages);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        btnUserList = (Button) findViewById(R.id.btnUserList);

        layNotConnected = (LinearLayout) findViewById(R.id.layNotConnected);
        layConnected = (LinearLayout) findViewById(R.id.layConnected);

        // set up adapter
        messages = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, messages);
        lstMessages.setAdapter(adapter);

        // attach event listener
        txtMessage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                    onTxtMessageEnterPressed();
                    return true;
                }

                return false;
            }
        });
    }

    protected void onTxtMessageEnterPressed() {
        if (connection != null) {
            String message = txtMessage.getText().toString();
            txtMessage.getText().clear();

            connection.sendMessage(message);
        }
    }

    public void btnConnectClicked(View view) {
        String handle = txtUser.getText().toString();
        connection = new ServerThread(handle);
        connection.start();

        adapter.clear();

        layConnected.setVisibility(View.VISIBLE);
        layNotConnected.setVisibility(View.GONE);
    }

    public void btnDisconnectClicked(View view) {
        connection.sendQuitRequest();

        layConnected.setVisibility(View.GONE);
        layNotConnected.setVisibility(View.VISIBLE);
    }

    public void btnUserListClicked(View view) {
        connection.sendUserListRequest();
    }

    public void addMessage(String message) {
        adapter.add(message);
        lstMessages.setSelectionFromTop(adapter.getCount(), 0);
    }

    /**********************************New Thread*****************************************************************************/

    public class ServerThread extends Thread {
        private Socket socket = null;
        private DataInputStream in = null;
        private DataOutputStream out = null;
        private String user;

        public ServerThread(String user) {
            super();
            this.user = user;
        }

        @Override
        public void run() {
            // attempt to connect
            if (connect()) {
                // connected and have streams,

                try {
                    // send user name
                    out.writeUTF(user);

                    // continuously poll input stream for strings from server

                    String command = "";

                    // poll input stream for input from server
                    while (isConnected()) {
                        // obtain command from server
                        command = in.readUTF();

                        // do something with command
                        if (command.equals(MESSAGE_COMMAND)) {
                            // next string will be the user name followed by
                            // another
                            // string, which will be the message
                            String user = in.readUTF();
                            String message = in.readUTF();

                            // display message
                            addMessage(user + ": " + message);

                        } else if (command.equals(USERS_COMMAND)) {
                            // result from a user request issued by the user.
                            // First a
                            // number will be sent indicating the number of
                            // users,
                            // followed by that many strings of user names
                            int numUsers = in.readInt();

                            addMessage("Users (" + numUsers + ")");

                            for (int i = 0; i < numUsers; i++) {
                                String user = in.readUTF();
                                addMessage((i + 1) + ") " + user);
                            }

                        } else if (command.equals(QUIT_COMMAND)) {
                            // server has indicated that the connection must be
                            // severed
                            addMessage("Quitting...");
                            connected = false;
                        }
                    }

                } catch (IOException e) {
                    // input stream no longer working, e.g. connection lost
                    Log.e("THREAD", "ERROR: " + e.getMessage());
                }
            }
        }

        /*******************************************************************************************/

        private boolean connected;

        public boolean isConnected() {
            return connected;
        }

        public boolean connect() {
            connected = false;

            try {
                // connect to server (if can)
                socket = new Socket(ip, 5050);

                // obtain streams
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                connected = true;

            } catch (Exception e) {
                // something went wrong with the connect
                Log.e("THREAD", "ERROR: " + e.getMessage());
            }

            return connected;
        }

        /*******************************************************************************************/

        // display on UI thread
        public void addMessage(final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // add message to lstMessages
                    Messaging.this.addMessage(message);
                }
            });
        }

        // sent from UI thread
        public void sendMessage(String message) {
            if (isConnected()) {
                try {
                    out.writeUTF(MESSAGE_COMMAND);
                    out.writeUTF(message);
                    out.flush();
                } catch (IOException e) {
                    Log.e("THREAD", "ERROR: " + e.getMessage());
                }
            }
        }

        // sent from UI thread
        public void sendUserListRequest() {
            if (isConnected()) {
                try {
                    out.writeUTF(USERS_COMMAND);
                    out.flush();
                } catch (IOException e) {
                    Log.e("THREAD", "ERROR: " + e.getMessage());
                }
            }
        }

        // sent from UI thread
        public void sendQuitRequest() {
            if (isConnected()) {
                try {
                    out.writeUTF(QUIT_COMMAND);
                    out.flush();
                    connected = false;

                    // cause thread to stop running
                    interrupt();
                } catch (IOException e) {
                    Log.e("THREAD", "ERROR: " + e.getMessage());
                }
            }
        }
    }

}
