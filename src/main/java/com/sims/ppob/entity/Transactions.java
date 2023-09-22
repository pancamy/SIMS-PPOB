package com.sims.ppob.entity;

import com.sims.ppob.enumeration.TransactionTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "transaction_type", nullable = false)
    private TransactionTypes transactionType;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services services;
}
