package com.nsumba.feed_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeResponseDto(
        boolean liked,
        @JsonProperty("already_liked") boolean alreadyLiked,
        @JsonProperty("post_id") Integer postId
) {
}
