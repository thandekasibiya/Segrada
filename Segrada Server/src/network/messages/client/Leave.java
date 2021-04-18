package network.messages.client;

import network.Groups;
import network.SegradaClient;
import network.messages.Message;

/**
 * The message received when a client wishes to leave the
 * current chat group that they are in.
 */
public class Leave extends Message<SegradaClient> {
    private static final long serialVersionUID = 2L;

    @Override
    public String toString() {
        return "Leave()";
    }

    @Override
    public void apply(SegradaClient chatClient) {
        // Client leaves the group they are currently in.
        Groups.leave(chatClient);
    }
}
