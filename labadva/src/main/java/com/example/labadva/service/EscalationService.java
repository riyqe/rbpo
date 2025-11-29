package com.example.labadva.service;

import com.example.labadva.model.Ticket;
import com.example.labadva.model.TicketStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscalationService {

    private final TicketService ticketService;

    public EscalationService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void escalateOverdueTickets() {
        List<Ticket> overdueTickets = ticketService.getOverdueTickets();
        overdueTickets.forEach(ticket -> ticket.setStatus(TicketStatus.ESCALATED));
    }
}
