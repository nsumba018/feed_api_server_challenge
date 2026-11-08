package com.nsumba.feed_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// I named it PostLike instead of Like because LIKE is a reserved word in JPQL
@Getter
@Setter
@Entity
@Table(name = "likes")
public class PostLike {

    @EmbeddedId
    private LikeId id;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private String createdAt;
}
