package com.akka.rpc.network.netty;/* 
    create qiangzhiwei time 2023/11/14
 */

import com.akka.rpc.model.command.CommandCode;
import com.akka.rpc.network.api.RequestProcess;
import com.akka.rpc.network.coder.DecoderCoder;
import com.akka.rpc.network.coder.EncoderCoder;
import com.akka.rpc.network.netty.handler.CommandHandler;
import com.akka.rpc.network.netty.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ExecutorService;

public class NettyClient {

    private final Bootstrap bootstrap;

    private final NioEventLoopGroup workerEventGroup;


    private final CommandHandler commandHandler;
    public NettyClient() {
        this.bootstrap = new Bootstrap();
        this.workerEventGroup = new NioEventLoopGroup();
        this.commandHandler = new CommandHandler();
    }


    public void start() {
        bootstrap
                .group(workerEventGroup)
                .channel(SocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new DecoderCoder());
                        ch.pipeline().addLast(new EncoderCoder());
                        ch.pipeline().addLast(new NettyClientHandler(commandHandler));
                    }
                });
    }



    public void registerProcess(CommandCode commandCode, RequestProcess requestProcess, ExecutorService executorService) {
        this.commandHandler.registerProcess(commandCode,requestProcess, executorService);
    }
}
