package com.nsumba.feed_api.repository;

import com.nsumba.feed_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findTop20ByOrderByCreatedAtDescIdDesc();
}
