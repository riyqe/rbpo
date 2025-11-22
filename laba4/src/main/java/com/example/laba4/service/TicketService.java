package com.example.laba4.service;

import com.example.laba4.model.*;
import com.example.laba4.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getOverdueTickets() {
        return ticketRepository.findAll()
                .stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(LocalDateTime.now()))
                .toList();
    }
}
