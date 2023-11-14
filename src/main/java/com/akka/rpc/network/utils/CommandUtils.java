package com.akka.rpc.network.utils;


import com.akka.rpc.model.command.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class CommandUtils {
    public static Command decoder(ByteBufAllocator allocator, ByteBuf frame) {
        ByteBuf headerByteBuf = null;
        ByteBuf payloadBuf = null;
        try {
            int headerLength = frame.readInt();
            if (headerLength <= 0) {
                return null;
            }
            headerByteBuf = allocator.buffer(headerLength);
            if (headerByteBuf.writableBytes() >= headerLength) {
                frame.readBytes(headerByteBuf, headerLength);
            }
            assert headerByteBuf.readableBytes() == headerLength;

            Header header = new Header();
            header.decoder(headerByteBuf.array());

            final Payload payload = header.getCommand().newObject();
            assert  payload != null;

            final int payloadLength = header.getPayloadLength();
            payloadBuf = allocator.buffer();
            if (payloadBuf.writableBytes() >= payloadLength) {
                frame.readBytes(payloadBuf, payloadLength);
            }
            payload.decoder(payloadBuf.array());
            return new RequestCommand(header, payload);
        } catch (Exception e) {
            // TODO
        } finally {
            assert headerByteBuf != null;
            headerByteBuf.release();
            assert payloadBuf != null;
            payloadBuf.release();
        }
        return null;
    }

    private static Payload newPayload(Header header) {
        return header.getCommand().newObject();
    }
}
