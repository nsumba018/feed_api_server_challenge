package com.nsumba.feed_api.exception;

// Thrown when the post body is missing, empty or too long
public class InvalidPostBodyException extends RuntimeException {

    public InvalidPostBodyException(String message) {
        super(message);
    }
}
