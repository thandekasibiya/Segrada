package network.messages.client;

import network.SegradaClient;
import network.messages.Message;

public class Join extends Message<SegradaClient> {
    private static final long serialVersionUID = 1L;
    public String groupName;

    public Join (String groupName){
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return String.format("Join('%s')", groupName);
    }

    @Override
    public void apply(SegradaClient context) {

    }
}
