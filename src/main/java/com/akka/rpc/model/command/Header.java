package com.akka.rpc.model.command;

import static com.akka.rpc.model.command.CommandKind.REQUEST_COMMAND;

public class Header implements Payload {



    private CommandEnum command;

    private CommandCode commandCode;

    private int payloadLength;


    private int kind;

    public int getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = payloadLength;
    }

    public CommandEnum getCommand() {
        return command;
    }


    @Override
    public byte[] encoder() {
        return new byte[0];
    }

    @Override
    public Payload decoder(byte[] in) {
        return null;
    }

    public CommandKind kind() {
        return REQUEST_COMMAND ;
    }

    public CommandCode getCommandCode() {
        return commandCode;
    }
}
