package com.akka.rpc.model.command;

public interface Command {

    Header header();

    Payload payload();
}
