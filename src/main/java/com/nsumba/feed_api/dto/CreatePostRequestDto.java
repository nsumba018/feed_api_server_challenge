package com.nsumba.feed_api.dto;

// body is Object so {"body":123} is not turned into "123", I check the type myself
public record CreatePostRequestDto(Object body) {
}
