package com.akka.rpc.model.command;

public  class CallCommandPayload implements Payload {

        @Override
        public byte[] encoder() {
            return new byte[0];
        }

        @Override
        public Payload decoder(byte[] in) {
            return null;
        }
    }