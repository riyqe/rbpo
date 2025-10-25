package com.example.labatri.controller;

import com.example.labatri.model.Ticket;
import com.example.labatri.model.TicketStatus;
import com.example.labatri.service.EscalationService;
import com.example.labatri.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final EscalationService escalationService;

    public TicketController(TicketService ticketService, EscalationService escalationService) {
        this.ticketService = ticketService;
        this.escalationService = escalationService;
    }

    // ➕ Добавить новый тикет
    @PostMapping
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }

    // 📋 Получить все тикеты
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getTickets();
    }

    // ⚠️ Получить только просроченные тикеты
    @GetMapping("/overdue")
    public List<Ticket> getOverdueTickets() {
        return ticketService.getOverdueTickets();
    }

    // 🚨 Эскалировать все просроченные тикеты
    @GetMapping("/escalate")
    public String escalateOverdueTickets() {
        escalationService.escalateOverdueTickets();
        return "Просроченные тикеты успешно эскалированы!";
    }

    // 🧹 Очистить все тикеты (для удобства тестов)
    @DeleteMapping("/clear")
    public String clearAllTickets() {
        ticketService.getTickets().clear();
        return "Все тикеты удалены.";
    }
}