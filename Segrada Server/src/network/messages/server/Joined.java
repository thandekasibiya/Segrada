package network.messages.server;

import network.messages.Message;

public class Joined extends Message {
    private static final long serialVersionUID = 102L;

    public String groupName;
    public String handle;

    public Joined(String groupName, String handle) {
        this.groupName = groupName;
        this.handle = handle;
    }

    @Override
    public String toString() {
        return String.format("Joined(%s, %s)", groupName, handle);
    }

    @Override
    public void apply(Object context) {

    }
}