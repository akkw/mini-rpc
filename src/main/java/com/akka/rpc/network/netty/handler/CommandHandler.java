package com.akka.rpc.network.netty.handler;/*
    create qiangzhiwei time 2023/11/14
 */

import com.akka.rpc.model.Pair;
import com.akka.rpc.model.command.Command;
import com.akka.rpc.model.command.CommandCode;
import com.akka.rpc.model.command.Header;
import com.akka.rpc.network.api.RequestProcess;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class CommandHandler {

    private final Map<Integer, Pair<RequestProcess, ExecutorService>> processTable;

    private final ExecutorService defaultExecutorService;

    public CommandHandler() {
        this(null);
    }

    public CommandHandler(ExecutorService defaultExecutorService) {
        this.processTable = new HashMap<>();
        this.defaultExecutorService = defaultExecutorService;
    }

    public void handler(ChannelHandlerContext ctx, Command command) {
        final Header header = command.header();
        switch (header.kind()) {
            case REQUEST_COMMAND:
                processRequest(ctx, command);
                break;
            case RESPONSE_COMMAND:
                processResponse(ctx, command);
            default:
                // TODO
                break;
        }
    }


    public void registerProcess(CommandCode commandCode, RequestProcess process, ExecutorService executorService) {
        ExecutorService thisExecutor = executorService == null ? defaultExecutorService : executorService;
        this.processTable.put(commandCode.getCode(), new Pair<>(process, thisExecutor));
    }

    private void processResponse(ChannelHandlerContext ctx, Command command) {

    }

    private void processRequest(ChannelHandlerContext ctx, Command command) {
        final Header header = command.header();
        assert header != null;

        final Pair<RequestProcess, ExecutorService> registered = processTable.get(header.getCommandCode().getCode());

        if (registered != null) {

        } else {
            // TODO
        }
    }
}
