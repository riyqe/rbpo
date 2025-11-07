package com.example.labatri.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Transient
    private Executor executor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String resolution;
    private LocalDateTime dueDate;
}
