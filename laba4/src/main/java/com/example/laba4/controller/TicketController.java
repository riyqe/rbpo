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
    private final ExecutorRepository executorRepository;
    private final EscalationService escalationService;
    private final SLARepository slaRepository;

    public TicketController(TicketRepository ticketRepository,
                            ExecutorRepository executorRepository,
                            EscalationService escalationService,
                            SLARepository slaRepository) {
        this.ticketRepository = ticketRepository;
        this.executorRepository = executorRepository;
        this.escalationService = escalationService;
        this.slaRepository = slaRepository;
    }

    // Получить все тикеты
    @GetMapping
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    // Создать новый тикет
    @PostMapping
    public Ticket create(@RequestBody Ticket ticket) {
        if (ticket.getCreatedAt() == null) {
            ticket.setCreatedAt(LocalDateTime.now());
        }
        if (ticket.getStatus() == null) {
            ticket.setStatus(TicketStatus.CREATED);
        }
        return ticketRepository.save(ticket);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    if (ticketDetails.getTitle() != null) ticket.setTitle(ticketDetails.getTitle());
                    if (ticketDetails.getDescription() != null) ticket.setDescription(ticketDetails.getDescription());

                    if (ticketDetails.getStatus() != null) ticket.setStatus(ticketDetails.getStatus());

                    if (ticketDetails.getSla() != null && ticketDetails.getSla().getId() != null) {
                        slaRepository.findById(ticketDetails.getSla().getId())
                                .ifPresent(ticket::setSla);
                    }

                    ticket.setUpdatedAt(LocalDateTime.now());

                    return ResponseEntity.ok(ticketRepository.save(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
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
                    ticket.setResolution(resolution); // Если resolution придет как строка в body
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

    // Получить один тикет по ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
