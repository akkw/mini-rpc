package com.akka.rpc.model.command;

public class RequestCommand implements Command {

    private final Header header;

    private final Payload payload;


    public RequestCommand(Header header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public Header header() {
        return header;
    }

    @Override
    public Payload payload() {
        return payload;
    }
}
