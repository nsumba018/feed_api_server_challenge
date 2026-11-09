package com.nsumba.feed_api.post.controller;

import com.nsumba.feed_api.dto.FeedResponseDto;
import com.nsumba.feed_api.dto.PostResponseDto;
import com.nsumba.feed_api.post.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    @GetMapping("/feed")
    public ResponseEntity<FeedResponseDto> getFeed() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new FeedResponseDto(postService.getFeed()));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PostResponseDto(postService.getPostById(id)));
    }
}
