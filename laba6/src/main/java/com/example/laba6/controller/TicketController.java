package com.example.laba6.controller;

import com.example.laba6.model.*;
import com.example.laba6.repository.*;
import com.example.laba6.service.EscalationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final ExecutorRepository executorRepository;
    private final EscalationService escalationService;
    private final SLARepository slaRepository;
    private final CategoryRepository categoryRepository;

    public TicketController(TicketRepository ticketRepository,
                            ExecutorRepository executorRepository,
                            EscalationService escalationService,
                            SLARepository slaRepository,
                            CategoryRepository categoryRepository) {
        this.ticketRepository = ticketRepository;
        this.executorRepository = executorRepository;
        this.escalationService = escalationService;
        this.slaRepository = slaRepository;
        this.categoryRepository = categoryRepository;
    }

    // Получить все тикеты
    @GetMapping
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    // Получить один тикет
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ticket create(@RequestBody Ticket ticket) {
        if (ticket.getCreatedAt() == null) {
            ticket.setCreatedAt(LocalDateTime.now());
        }

        if (ticket.getStatus() == null) {
            ticket.setStatus(TicketStatus.CREATED);
        }

        // Привязываем SLA и Категорию, если передали их ID
        if (ticket.getSla() != null && ticket.getSla().getId() != null) {
            slaRepository.findById(ticket.getSla().getId()).ifPresent(ticket::setSla);
        }
        if (ticket.getCategory() != null && ticket.getCategory().getId() != null) {
            categoryRepository.findById(ticket.getCategory().getId()).ifPresent(ticket::setCategory);
        }

        return ticketRepository.save(ticket);
    }

    // обновление
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket details) {
        return ticketRepository.findById(id).map(ticket -> {
            if (details.getTitle() != null) ticket.setTitle(details.getTitle());
            if (details.getDescription() != null) ticket.setDescription(details.getDescription());
            if (details.getStatus() != null) ticket.setStatus(details.getStatus());

            // Если меняем SLA или категорию
            if (details.getSla() != null && details.getSla().getId() != null) {
                slaRepository.findById(details.getSla().getId()).ifPresent(ticket::setSla);
            }

            ticket.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(ticketRepository.save(ticket));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Бизнес-операция 1: Назначить тикет исполнителю
    @PostMapping("/{ticketId}/assign/{executorId}")
    public ResponseEntity<Ticket> assignExecutor(@PathVariable Long ticketId, @PathVariable Long executorId) {
        return ticketRepository.findById(ticketId)
                .flatMap(ticket -> executorRepository.findById(executorId)
                        .map(exec -> {
                            ticket.setExecutor(exec);
                            ticket.setStatus(TicketStatus.IN_PROGRESS);
                            ticket.setUpdatedAt(LocalDateTime.now());
                            return ResponseEntity.ok(ticketRepository.save(ticket));
                        }))
                .orElse(ResponseEntity.notFound().build());
    }

    // Бизнес-операция 2: Закрыть тикет
    @PostMapping("/{id}/resolve")
    public ResponseEntity<Ticket> resolve(@PathVariable Long id, @RequestBody String resolution) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setStatus(TicketStatus.RESOLVED);
                    ticket.setResolution(resolution);
                    ticket.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(ticketRepository.save(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Бизнес-операция 3: Просроченные тикеты
    @GetMapping("/overdue")
    public List<Ticket> getOverdue() {
        return escalationService.getOverdueTickets();
    }

    // Бизнес-операция 4: Эскалировать тикеты
    @PostMapping("/escalate")
    public ResponseEntity<String> escalateAll() {
        // Вызываем логику проверки сроков
        int count = escalationService.escalateOverdueTickets();
        return ResponseEntity.ok("Проверка завершена. Эскалировано тикетов: " + count);
    }

    // Бизнес-операция 5: Получить тикеты конкретного исполнителя
    @GetMapping("/executor/{executorId}")
    public List<Ticket> getTicketsByExecutor(@PathVariable Long executorId) {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getExecutor() != null && executorId.equals(t.getExecutor().getId()))
                .toList();
    }
}