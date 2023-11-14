package com.akka.rpc.network.netty;

import com.akka.rpc.model.Pair;
import com.akka.rpc.model.command.Command;
import com.akka.rpc.model.command.CommandCode;
import com.akka.rpc.model.config.NettyServerConfig;
import com.akka.rpc.network.api.RequestProcess;
import com.akka.rpc.network.coder.DecoderCoder;
import com.akka.rpc.network.coder.EncoderCoder;
import com.akka.rpc.network.netty.handler.CommandHandler;
import com.akka.rpc.network.netty.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyServer {

    private final NettyServerConfig nettyServerConfig;

    private final ServerBootstrap serverBootstrap;

    private final NioEventLoopGroup bossEventGroup;

    private final NioEventLoopGroup workerEventGroup;

    private final CommandHandler commandHandler;

    private NettyServerHandler nettyServerHandler;

    private InetSocketAddress inetSocketAddress;

    private final ExecutorService defaultExecutorService;


    public NettyServer(String host, int port) {
        this.defaultExecutorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(256));
        this.serverBootstrap = new ServerBootstrap();
        this.nettyServerConfig = new NettyServerConfig();
        this.bossEventGroup = new NioEventLoopGroup();
        this.workerEventGroup = new NioEventLoopGroup();
        this.commandHandler = new CommandHandler(defaultExecutorService);
        this.inetSocketAddress = new InetSocketAddress(host, port);
    }

    public void start() throws InterruptedException {
        this.nettyServerHandler = new NettyServerHandler(commandHandler);
        this.serverBootstrap
                .group(bossEventGroup, workerEventGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ServerSocketHandler())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new DecoderCoder());
                        socketChannel.pipeline().addLast(new EncoderCoder());
                        socketChannel.pipeline().addLast(nettyServerHandler);
                    }
                })
                .bind(inetSocketAddress)
                .sync();
    }

    public void shutdown() {
        this.bossEventGroup.shutdownGracefully();
        this.workerEventGroup.shutdownGracefully();
    }

    public void registerProcess(CommandCode commandCode, RequestProcess requestProcess, ExecutorService executorService) {
        this.commandHandler.registerProcess(commandCode, requestProcess, executorService);
    }
}
