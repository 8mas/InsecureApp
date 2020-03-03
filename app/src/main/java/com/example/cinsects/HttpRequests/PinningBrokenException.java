package com.example.cinsects.HttpRequests;

public class PinningBrokenException extends Exception {
    public PinningBrokenException() {}

    public PinningBrokenException(String message) {
        super(message);
    }

    public PinningBrokenException(Throwable cause) {
        super(cause);
    }
}
