package com.nsumba.feed_api.post.service;

import com.nsumba.feed_api.dto.PostDto;

import java.util.List;

public interface IPostService {

    List<PostDto> getFeed();

    PostDto getPostById(String id);
}
