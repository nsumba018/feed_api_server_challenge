package com.nsumba.feed_api.post.service.impl;

import com.nsumba.feed_api.dto.CreatePostRequestDto;
import com.nsumba.feed_api.dto.LikeResponseDto;
import com.nsumba.feed_api.dto.PostDto;
import com.nsumba.feed_api.entity.LikeId;
import com.nsumba.feed_api.entity.Post;
import com.nsumba.feed_api.entity.PostLike;
import com.nsumba.feed_api.exception.InvalidPostBodyException;
import com.nsumba.feed_api.exception.InvalidPostIdException;
import com.nsumba.feed_api.exception.PostNotFoundException;
import com.nsumba.feed_api.post.service.IPostService;
import com.nsumba.feed_api.repository.PostLikeRepository;
import com.nsumba.feed_api.repository.PostRepository;
import com.nsumba.feed_api.repository.UserRepository;
import com.nsumba.feed_api.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

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

    @Override
    @Transactional
    public PostDto createPost(AuthenticatedUser user, CreatePostRequestDto request) {
        String body = validateBody(request);

        Post post = new Post();
        post.setUser(userRepository.getReferenceById(user.userId()));
        post.setBody(body);
        Post savedPost = postRepository.save(post);

        // The author comes from the token, and a new post has no likes yet
        return new PostDto(savedPost.getId(), savedPost.getBody(), savedPost.getCreatedAt(),
                user.username(), 0);
    }

    @Override
    @Transactional
    public LikeResponseDto likePost(AuthenticatedUser user, String id) {
        Integer postId = parsePostId(id);
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        LikeId likeId = new LikeId(user.userId(), postId);
        if (postLikeRepository.existsById(likeId)) {
            return new LikeResponseDto(true, true, postId);
        }

        PostLike like = new PostLike();
        like.setId(likeId);
        postLikeRepository.save(like);
        return new LikeResponseDto(true, false, postId);
    }

    private String validateBody(CreatePostRequestDto request) {
        if (request == null || !(request.body() instanceof String text) || text.strip().isEmpty()) {
            throw new InvalidPostBodyException("body is required");
        }
        String body = text.strip();
        if (body.length() > 500) {
            throw new InvalidPostBodyException("body must be at most 500 characters");
        }
        return body;
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
