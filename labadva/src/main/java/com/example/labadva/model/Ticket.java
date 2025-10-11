package com.example.labadva.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;               // заголовок тикета
    private String description;         // описание проблемы

    @Enumerated(EnumType.STRING)
    private TicketStatus status;        // статус (из enum ниже)

    @ManyToOne
    private Users user;                  // кто создал

    @ManyToOne
    private Executor agent;                // кто выполняет

    @ManyToOne
    private Category category;          // к какой категории относится

    @ManyToOne
    private SLA sla;                    // регламент обслуживания

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    private String resolution;          // описание решения (при закрытии)
}