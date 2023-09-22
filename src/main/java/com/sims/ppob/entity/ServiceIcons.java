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
@Table(name = "service_icons")
public class ServiceIcons {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "bucket_name", columnDefinition = "TEXT", nullable = false)
    private String bucketName;

    @Column(name = "object_name", columnDefinition = "TEXT", nullable = false)
    private String objectName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services services;
}
