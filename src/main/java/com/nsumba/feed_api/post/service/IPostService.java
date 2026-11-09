package com.nsumba.feed_api.post.service;

import com.nsumba.feed_api.dto.CreatePostRequestDto;
import com.nsumba.feed_api.dto.LikeResponseDto;
import com.nsumba.feed_api.dto.PostDto;
import com.nsumba.feed_api.security.AuthenticatedUser;

import java.util.List;

public interface IPostService {

    List<PostDto> getFeed();

    PostDto getPostById(String id);

    PostDto createPost(AuthenticatedUser user, CreatePostRequestDto request);

    LikeResponseDto likePost(AuthenticatedUser user, String id);
}
