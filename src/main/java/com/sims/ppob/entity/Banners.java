package com.sims.ppob.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "banners")
public class Banners {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "banner_name", nullable = false)
    private String bannerName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "banner_image", columnDefinition = "TEXT", nullable = false)
    private String bannerImage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
