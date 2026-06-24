package com.example.hostel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class ServiceRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(length = 1000)
    private String description;

    private LocalDate preferredDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private User student;

    @ManyToOne
    private User cleaner; // can be null until assigned
}
