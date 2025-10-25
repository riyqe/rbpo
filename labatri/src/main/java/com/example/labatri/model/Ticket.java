package com.example.labatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private Long id;

    private String title;               // заголовок тикета
    private String description;         // описание проблемы

    private TicketStatus status;        // статус (из enum ниже)

    private Users user;                  // кто создал

    private Executor agent;                // кто выполняет

    private Category category;          // к какой категории относится

    private SLA sla;                    // регламент обслуживания

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    private String resolution;          // описание решения (при закрытии)
}