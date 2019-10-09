package com.example.CryptoChat.common.data.exceptions;

public class DuplicatedException extends Exception {

    public DuplicatedException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
