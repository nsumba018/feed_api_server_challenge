package com.nsumba.feed_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostDto(
        Integer id,
        String body,
        @JsonProperty("created_at") String createdAt,
        String author,
        @JsonProperty("like_count") long likeCount
) {
}
