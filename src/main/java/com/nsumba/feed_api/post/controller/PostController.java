package com.nsumba.feed_api.post.controller;

import com.nsumba.feed_api.dto.CreatePostRequestDto;
import com.nsumba.feed_api.dto.FeedResponseDto;
import com.nsumba.feed_api.dto.LikeResponseDto;
import com.nsumba.feed_api.dto.PostResponseDto;
import com.nsumba.feed_api.post.service.IPostService;
import com.nsumba.feed_api.security.AuthenticatedUser;
import com.nsumba.feed_api.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestAttribute(JwtFilter.AUTH_USER) AuthenticatedUser user,
                                                      @RequestBody(required = false) CreatePostRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PostResponseDto(postService.createPost(user, request)));
    }

    // 201 the first time, 200 if the user already liked the post
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<LikeResponseDto> likePost(@RequestAttribute(JwtFilter.AUTH_USER) AuthenticatedUser user,
                                                    @PathVariable String id) {
        LikeResponseDto response = postService.likePost(user, id);
        HttpStatus status = response.alreadyLiked() ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(response);
    }
}
