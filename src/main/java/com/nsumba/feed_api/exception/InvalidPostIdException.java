package com.nsumba.feed_api.exception;

public class InvalidPostIdException extends RuntimeException {

    public InvalidPostIdException() {
        super("invalid post id");
    }
}
