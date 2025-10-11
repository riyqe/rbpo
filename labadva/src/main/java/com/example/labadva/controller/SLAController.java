package com.example.labadva.controller;

import com.example.labadva.model.SLA;
import com.example.labadva.repository.SLARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sla")
@RequiredArgsConstructor
public class SLAController {

    private final SLARepository SLARepository;

    @PostMapping
    public SLA createSLA(@RequestBody SLA sla) {
        return SLARepository.save(sla);
    }

    @GetMapping
    public List<SLA> getAllSLA() {
        return SLARepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SLA> getSLAById(@PathVariable Long id) {
        return SLARepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SLA> updateSLA(@PathVariable Long id, @RequestBody SLA updated) {
        return SLARepository.findById(id)
                .map(sla -> {
                    sla.setLevel(updated.getLevel());
                    sla.setResponseHours(updated.getResponseHours());
                    sla.setResolveHours(updated.getResolveHours());
                    return ResponseEntity.ok(SLARepository.save(sla));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSLA(@PathVariable Long id) {
        if (SLARepository.existsById(id)) {
            SLARepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
