package com.example.labadva.service;

import com.example.labadva.model.Ticket;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final List<Ticket> tickets = new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Ticket addTicket(Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> getOverdueTickets() {
        return tickets.stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(LocalDateTime.now()))
                .toList();
    }
}
