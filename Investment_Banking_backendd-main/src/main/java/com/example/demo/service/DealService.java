package com.example.demo.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Enums.DealStage;
import com.example.demo.dto.AddNoteDTO;
import com.example.demo.dto.CreateDealDTO;
import com.example.demo.dto.DealDTO;
import com.example.demo.dto.NoteDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Deal;
import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.DealRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DealService {

    private final DealRepository dealRepository;
    private final UserRepository userRepository;

   
    public DealDTO createDeal(CreateDealDTO createDealDTO) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User assignedTo = userRepository.findById(createDealDTO.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found"));

        Deal deal = new Deal();
        deal.setClientName(createDealDTO.getClientName());
        deal.setDealType(createDealDTO.getDealType());
        deal.setSector(createDealDTO.getSector());
        deal.setCurrentStage(createDealDTO.getCurrentStage());
        deal.setSummary(createDealDTO.getSummary());

      
        deal.setDealValue(createDealDTO.getDealValue());

        deal.setCreatedBy(currentUser);
        deal.setAssignedTo(assignedTo);

        Deal savedDeal = dealRepository.save(deal);
        return convertToDTO(savedDeal);
    }

    
    public List<DealDTO> getAllDeals(String clientName, String dealType, String sector, String stage) {
        return dealRepository.searchDeals(clientName, dealType, sector, stage)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

   
    public DealDTO getDealById(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));
        return convertToDTO(deal);
    }

   
    public DealDTO updateDeal(Long id, CreateDealDTO updateDealDTO) {

        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        User assignedTo = userRepository.findById(updateDealDTO.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found"));

        deal.setClientName(updateDealDTO.getClientName());
        deal.setDealType(updateDealDTO.getDealType());
        deal.setSector(updateDealDTO.getSector());
        deal.setCurrentStage(updateDealDTO.getCurrentStage());
        deal.setSummary(updateDealDTO.getSummary());
        deal.setDealValue(updateDealDTO.getDealValue());
        deal.setAssignedTo(assignedTo);

        Deal updatedDeal = dealRepository.save(deal);
        return convertToDTO(updatedDeal);
    }

   
    public DealDTO updateDealStage(Long id, DealStage stage) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setCurrentStage(stage);
        return convertToDTO(dealRepository.save(deal));
    }

 
    public DealDTO updateDealValue(Long id, Double dealValue) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setDealValue(dealValue);
        return convertToDTO(dealRepository.save(deal));
    }


    public DealDTO addNote(Long dealId, AddNoteDTO addNoteDTO) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        Note note = new Note();
        note.setNote(addNoteDTO.getNote());
        note.setUser(currentUser);
        note.setTimestamp(LocalDateTime.now());

        deal.getNotes().add(note);
        return convertToDTO(dealRepository.save(deal));
    }

   
    public void deleteDeal(Long id) {
        if (!dealRepository.existsById(id)) {
            throw new ResourceNotFoundException("Deal not found");
        }
        dealRepository.deleteById(id);
    }

  
    private DealDTO convertToDTO(Deal deal) {

        UserDTO createdByDTO = new UserDTO(
                deal.getCreatedBy().getId(),
                deal.getCreatedBy().getUsername(),
                deal.getCreatedBy().getEmail(),
                deal.getCreatedBy().getRole(),
                deal.getCreatedBy().getActive(),
                deal.getCreatedBy().getCreatedAt()
        );

        UserDTO assignedToDTO = new UserDTO(
                deal.getAssignedTo().getId(),
                deal.getAssignedTo().getUsername(),
                deal.getAssignedTo().getEmail(),
                deal.getAssignedTo().getRole(),
                deal.getAssignedTo().getActive(),
                deal.getAssignedTo().getCreatedAt()
        );

        List<NoteDTO> noteDTOs = deal.getNotes().stream()
                .map(note -> new NoteDTO(
                        note.getId(),
                        note.getNote(),
                        new UserDTO(
                                note.getUser().getId(),
                                note.getUser().getUsername(),
                                note.getUser().getEmail(),
                                note.getUser().getRole(),
                                note.getUser().getActive(),
                                note.getUser().getCreatedAt()
                        ),
                        note.getTimestamp()
                ))
                .collect(Collectors.toList());

        return new DealDTO(
                deal.getId(),
                deal.getClientName(),
                deal.getDealType(),
                deal.getSector(),
                deal.getDealValue(),
                deal.getCurrentStage(),
                deal.getSummary(),
                noteDTOs,
                createdByDTO,
                assignedToDTO,
                deal.getCreatedAt(),
                deal.getUpdatedAt()
        );
    }
}
