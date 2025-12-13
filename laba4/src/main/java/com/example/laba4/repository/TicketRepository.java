package com.example.laba4.repository;

import com.example.laba4.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByExecutorId(Long executorId);
}

