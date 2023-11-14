package com.akka.rpc.model.command;

public interface Payload {

    byte[] encoder();

    Payload decoder(byte[] in);
}
