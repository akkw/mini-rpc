package com.akka.rpc.network.coder;


import com.akka.rpc.model.config.NettyServerConfig;
import com.akka.rpc.network.utils.CommandUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class DecoderCoder extends LengthFieldBasedFrameDecoder {
    public DecoderCoder() {
        super(NettyServerConfig.MAX_FRAME_LENGTH, 0, 4, 0, 4);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
       try {
           frame = (ByteBuf) super.decode(ctx, in);
           assert frame != null;
           return CommandUtils.decoder(ctx.alloc(), frame);
       } catch (Exception e) {
           // TODO
       } finally {
           assert frame != null;
           frame.release();
       }
       return null;
    }
}
