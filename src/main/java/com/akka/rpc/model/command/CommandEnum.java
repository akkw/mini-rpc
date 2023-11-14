package com.akka.rpc.model.command;

public enum CommandEnum {
    CallRequest {
        @Override
        public Payload newObject() {
            return new CallCommandPayload();
        }
    };


    public abstract Payload newObject();
}
