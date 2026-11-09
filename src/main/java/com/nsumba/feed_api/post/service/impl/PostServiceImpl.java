package com.nsumba.feed_api.post.service.impl;

import com.nsumba.feed_api.dto.PostDto;
import com.nsumba.feed_api.entity.Post;
import com.nsumba.feed_api.exception.InvalidPostIdException;
import com.nsumba.feed_api.exception.PostNotFoundException;
import com.nsumba.feed_api.post.service.IPostService;
import com.nsumba.feed_api.repository.PostLikeRepository;
import com.nsumba.feed_api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getFeed() {
        return postRepository.findTop20ByOrderByCreatedAtDescIdDesc()
                .stream()
                .map(this::mapToPostDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(String id) {
        Integer postId = parsePostId(id);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        return mapToPostDto(post);
    }

    private Integer parsePostId(String id) {
        if (!id.matches("\\d+")) {
            throw new InvalidPostIdException();
        }
        long value = Long.parseLong(id);
        if (value <= 0) {
            throw new InvalidPostIdException();
        }
        if (value > Integer.MAX_VALUE) {
            throw new PostNotFoundException();
        }
        return (int) value;
    }

    private PostDto mapToPostDto(Post post) {
        long likeCount = postLikeRepository.countByIdPostId(post.getId());
        return new PostDto(
                post.getId(),
                post.getBody(),
                post.getCreatedAt(),
                post.getUser().getUsername(),
                likeCount
        );
    }
}
