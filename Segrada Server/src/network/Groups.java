package network;

import network.messages.Message;
import network.messages.server.Joined;
import network.messages.server.Left;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * This class is responsible for managing game groups and sending network.messages to
 * clients in specific groups.
 */
public class Groups {
    // Lock to prevent multiple threads manipulating the groups data
    // structure while busy with an operation.
    private static final ReentrantLock lock = new ReentrantLock();
    // [Group Name] -> {clients}
    public static final Map<String, Set<SegradaClient>> groups = new HashMap<>();

    /**
     * Join a new group. Before joining a new group, the client will leave
     * any groups it is currently in. All other clients in the group are
     * notified of the client joining.
     * @param groupName The new group's name.
     * @param client The client joining.
     */
    public static void join(String groupName, SegradaClient client) {
        // If already in a group, leave it.
        leave(client);

        // If no such group, create it.
        if(!groups.containsKey(groupName))
            addGroup(groupName);

        // Now join the new group.
        lock.lock();
        // Add client to group.
        groups.get(groupName).add(client);
        client.groupName = groupName;

        // Tell all clients that client joined group.
        groups.get(groupName)
                .forEach(chatClient -> chatClient.send(new Joined(groupName, client.userName)));
        lock.unlock();
    }

    /**
     * The client leaves all groups currently a member of. All other clients
     * are sent a notification of this fact.
     * @param client The client leaving.
     */
    public static void leave(SegradaClient client) {
        lock.lock();
        // Get groups to which client belongs.
        List<String> groupsIn = groups.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(client))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        // Remove client from these groups and notify other clients of this.
        groupsIn.forEach(groupName -> {
            // Get group.
            Set<SegradaClient> group = groups.get(groupName);

            // Remove client from group.
            group.remove(client);
            client.groupName = "";

            // Send message to other clients in group.
            Left msg = new Left(groupName, client.userName);
            group.forEach(chatClient -> chatClient.send(msg));
        });
        lock.unlock();
    }

    /**
     * A new (empty) group is added.
     * @param groupName The new group's name.
     */
    public static void addGroup(String groupName) {
        lock.lock();
        groups.put(groupName, new HashSet<>());
        lock.unlock();
    }

    /**
     * A message is sent to all clients in the named group.
     * @param groupName The group's name.
     * @param message The message to be sent.
     */
    public static void send(String groupName, Message message) {
        lock.lock();
        // Is there a group with the given name? If not, exit.
        if(!groups.containsKey(groupName)) return;

        // Get clients in group.
        Set<SegradaClient> clients = groups.get(groupName);

        // Send message to each client.
        for(SegradaClient client : clients)
            client.send(message);
        lock.unlock();
    }

    /**
     * Send a message to ALL clients, regardless of the group
     * they're in.
     * @param message The message being sent.
     */
    public static void sendAll(Message message) {
        lock.lock();
        // groups.values().stream() returns a collection of SETS of
        // chat clients, i.e. a stream of (sets of chat clients).
        // The flatmap method flattens this into a stream of chat clients.
        groups.values()
                .stream()
                .flatMap(Collection::stream)
                .distinct()
                .forEach(chatClient -> {
                    chatClient.send(message);
                    System.out.println("sendAll: " + chatClient.userName + ", " + message);
                });
        lock.unlock();
    }

}
