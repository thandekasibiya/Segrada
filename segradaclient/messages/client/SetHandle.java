package com.example.segradaclient.messages.client;

import messages.Message;

public class SetHandle extends Message {
    private static final long serialVersionUID = 6L;

    public String handle;

    public SetHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return String.format("SetHandle('%s')", handle);
    }
}
