package com.example.demo.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Enums.DealStage;
import com.example.demo.dto.AddNoteDTO;
import com.example.demo.dto.CreateDealDTO;
import com.example.demo.dto.DealDTO;
import com.example.demo.service.DealService;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor

public class DealController {
    private final DealService dealService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<DealDTO> createDeal(@Valid @RequestBody CreateDealDTO createDealDTO) {
        DealDTO dealDTO = dealService.createDeal(createDealDTO);
        return ResponseEntity.ok(dealDTO);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<DealDTO>> getAllDeals(
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String dealType,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String stage) {
        List<DealDTO> deals = dealService.getAllDeals(clientName, dealType, sector, stage);
        return ResponseEntity.ok(deals);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<DealDTO> getDealById(@PathVariable Long id) {
        DealDTO dealDTO = dealService.getDealById(id);
        return ResponseEntity.ok(dealDTO);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<DealDTO> updateDeal(
            @PathVariable Long id,
            @Valid @RequestBody CreateDealDTO updateDealDTO) {
        DealDTO dealDTO = dealService.updateDeal(id, updateDealDTO);
        return ResponseEntity.ok(dealDTO);
    }
    
    @PatchMapping("/{id}/stage")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<DealDTO> updateDealStage(
            @PathVariable Long id,
            @RequestParam DealStage stage) {
        DealDTO dealDTO = dealService.updateDealStage(id, stage);
        return ResponseEntity.ok(dealDTO);
    }
    
    @PatchMapping("/{id}/value")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DealDTO> updateDealValue(
            @PathVariable Long id,
            @RequestParam Double dealValue) {
        DealDTO dealDTO = dealService.updateDealValue(id, dealValue);
        return ResponseEntity.ok(dealDTO);
    }
    
    @PostMapping("/{id}/notes")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<DealDTO> addNote(
            @PathVariable Long id,
            @Valid @RequestBody AddNoteDTO addNoteDTO) {
        DealDTO dealDTO = dealService.addNote(id, addNoteDTO);
        return ResponseEntity.ok(dealDTO);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        return ResponseEntity.noContent().build();
    }
}