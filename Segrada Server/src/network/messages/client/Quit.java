package network.messages.client;

import network.Groups;
import network.SegradaClient;
import network.messages.Message;

/**
 * The message received from a client when they no longer wish
 * to communicate with the server, i.e. log out.
 */
public class Quit extends Message<SegradaClient> {
    private static final long serialVersionUID = 4L;

    @Override
    public String toString() {
        return "Quit()";
    }

    @Override
    public void apply(SegradaClient segradaClient) {
        Groups.leave(segradaClient);
    }
}
