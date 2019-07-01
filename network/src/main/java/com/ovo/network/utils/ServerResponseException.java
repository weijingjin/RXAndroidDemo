package com.ovo.network.utils;

public class ServerResponseException extends RuntimeException {
    public ServerResponseException() {
        super();
    }

    public ServerResponseException(String message){
        super(message);
    }
}
