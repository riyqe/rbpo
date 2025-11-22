package com.example.laba4.controller;

import com.example.laba4.model.*;
import com.example.laba4.repository.*;
import com.example.laba4.service.EscalationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final UsersRepository usersRepository;
    private final ExecutorRepository executorRepository;
    private final EscalationService escalationService;

    public TicketController(TicketRepository ticketRepository,
                            UsersRepository usersRepository,
                            ExecutorRepository executorRepository,
                            EscalationService escalationService) {
        this.ticketRepository = ticketRepository;
        this.usersRepository = usersRepository;
        this.executorRepository = executorRepository;
        this.escalationService = escalationService;
    }

    // Получить все тикеты
    @GetMapping
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    // Создать новый тикет
    @PostMapping
    public Ticket create(@RequestBody Ticket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setStatus(TicketStatus.CREATED);
        return ticketRepository.save(ticket);
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
        escalationService.escalateOverdueTickets();
        return ResponseEntity.ok("Просроченные тикеты успешно эскалированы");
    }

    // Бизнес-операция 5: Получить тикеты конкретного исполнителя
    @GetMapping("/executor/{executorId}")
    public List<Ticket> getTicketsByExecutor(@PathVariable Long executorId) {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getExecutor() != null && executorId.equals(t.getExecutor().getId()))
                .toList();
    }
}
