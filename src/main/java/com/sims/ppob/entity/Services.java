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
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "service_code", nullable = false, unique = true)
    private String serviceCode;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "service_tariff", nullable = false)
    private Long serviceTariff;

    @Column(name = "service_icon", nullable = false)
    private String serviceIcon;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
