package com.akka.rpc.network.coder;


import com.akka.rpc.model.command.Command;
import com.akka.rpc.model.command.Header;
import com.akka.rpc.model.command.Payload;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class EncoderCoder extends MessageToByteEncoder<Command> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Command command, ByteBuf byteBuf) throws Exception {
        try {
            Header header = command.header();
            Payload payload = command.payload();
            byte[] hEncoder = header.encoder();
            if (hEncoder != null) {
                byteBuf.writeBytes(hEncoder);
            }
            byte[] encoder = payload.encoder();
            if (encoder != null) {
                byteBuf.writeBytes(encoder);
            }
        } catch (Exception e) {
            // TODO close channel
        }
    }
}
