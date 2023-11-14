package com.akka.rpc.network.api;/*
    create qiangzhiwei time 2023/11/14
 */

import com.akka.rpc.model.command.Command;
import io.netty.channel.ChannelHandlerContext;

public interface RequestProcess {
    void processRequest(ChannelHandlerContext ctx, Command command);
}
