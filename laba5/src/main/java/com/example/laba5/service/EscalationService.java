package com.example.laba5.service;

import com.example.laba5.model.Ticket;
import com.example.laba5.model.TicketStatus;
import com.example.laba5.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EscalationService {

    private final TicketRepository ticketRepository;

    public EscalationService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Метод для эскалации просроченных тикетов
    public void escalateOverdueTickets() {
        List<Ticket> overdueTickets = getOverdueTickets();
        overdueTickets.forEach(ticket -> {
            ticket.setStatus(TicketStatus.ESCALATED);
            ticketRepository.save(ticket);
        });
    }

    // Метод для получения всех просроченных тикетов
    public List<Ticket> getOverdueTickets() {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(LocalDateTime.now()))
                .filter(t -> t.getStatus() != TicketStatus.RESOLVED && t.getStatus() != TicketStatus.ESCALATED)
                .toList();
    }
}
