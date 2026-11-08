package com.nsumba.feed_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // SQLite stores it as TEXT in the exact format the API returns
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private String createdAt;
}
