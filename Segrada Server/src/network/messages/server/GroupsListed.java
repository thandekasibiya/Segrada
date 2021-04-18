package network.messages.server;

import network.Groups;
import network.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Message sent to a client listing all the groups that the server
 * has defined.
 */
public class GroupsListed extends Message {
    private static final long serialVersionUID = 101L;

    public List<String> groupNames;

    public GroupsListed() {
        groupNames = new ArrayList<>(Groups.groups.keySet());
    }

    @Override
    public String toString() {
        return String.format("GroupsListed(%s)", groupNames.toString());
    }

    @Override
    public void apply(Object context) {

    }
}
