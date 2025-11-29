package com.example.laba5.controller;

import com.example.laba5.model.SLA;
import com.example.laba5.repository.SLARepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sla")
public class SLAController {

    private final SLARepository slaRepository;

    public SLAController(SLARepository slaRepository) {
        this.slaRepository = slaRepository;
    }

    @GetMapping
    public List<SLA> getAllSLAs() {
        return slaRepository.findAll();
    }

    @PostMapping
    public SLA createSLA(@RequestBody SLA sla) {
        return slaRepository.save(sla);
    }

    @PutMapping("/{id}")
    public SLA updateSLA(@PathVariable Long id, @RequestBody SLA updatedSLA) {
        return slaRepository.findById(id)
                .map(sla -> {
                    sla.setLevel(updatedSLA.getLevel());
                    sla.setResponseHours(updatedSLA.getResponseHours());
                    sla.setResolveHours(updatedSLA.getResolveHours());
                    return slaRepository.save(sla);
                })
                .orElseThrow(() -> new RuntimeException("SLA not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteSLA(@PathVariable Long id) {
        slaRepository.deleteById(id);
    }
}
