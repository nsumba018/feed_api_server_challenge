package com.nsumba.feed_api.repository;

import com.nsumba.feed_api.entity.LikeId;
import com.nsumba.feed_api.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, LikeId> {

    long countByIdPostId(Integer postId);
}
