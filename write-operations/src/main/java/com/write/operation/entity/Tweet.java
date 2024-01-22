package com.write.operation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tweets")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate = null;

    @Column(name = "username")
    private String username;

    @Column(name = "tweet")
    private String tweet;

    @PrePersist
    private void prePersist() {
        createdDate = LocalDateTime.now();
    }
    @PreUpdate
    private void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
