package com.akka.rpc.model.command;/* 
    create qiangzhiwei time 2023/11/14
 */

public enum CommandCode {
    REGISTER(10086),


    ;

    private final int code;

    CommandCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
