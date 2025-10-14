package com.example.labadva.controller;

import com.example.labadva.model.Ticket;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final List<Ticket> tickets = new ArrayList<>();

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return tickets;
    }
}
