package com.nsumba.feed_api.security;

// The user taken from a valid token
public record AuthenticatedUser(Integer userId, String username) {
}
