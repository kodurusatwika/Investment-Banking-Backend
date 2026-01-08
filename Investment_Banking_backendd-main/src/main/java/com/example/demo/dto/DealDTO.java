package com.example.demo.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.Enums.DealStage;
import com.example.demo.Enums.DealType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {
    private Long id;
    private String clientName;
    private DealType dealType;
    private String sector;
    private Double dealValue;
    private DealStage currentStage;
    private String summary;
    private List<NoteDTO> notes;
    private UserDTO createdBy;
    private UserDTO assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}