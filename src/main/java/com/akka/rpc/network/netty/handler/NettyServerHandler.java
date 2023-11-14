package com.akka.rpc.network.netty.handler;/*
    create qiangzhiwei time 2023/11/14
 */

import com.akka.rpc.model.command.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<Command> {

    private final CommandHandler handler;

    public NettyServerHandler(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) throws Exception {
        handler.handler(ctx, command);
    }
}
