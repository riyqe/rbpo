package com.example.labadva.service;

import com.example.labadva.model.Ticket;
import com.example.labadva.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getOverdueTickets() {
        return ticketRepository.findAll()
                .stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(java.time.LocalDateTime.now()))
                .toList();
    }
}
