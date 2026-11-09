package com.nsumba.feed_api.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("post not found");
    }
}
