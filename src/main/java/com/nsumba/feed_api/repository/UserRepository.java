package com.nsumba.feed_api.repository;

import com.nsumba.feed_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
