package com.example.demo.dto;

import com.example.demo.Enums.DealStage;
import com.example.demo.Enums.DealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDealDTO {

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotNull(message = "Deal type is required")
    private DealType dealType;

    @NotBlank(message = "Sector is required")
    private String sector;

    @NotNull(message = "Deal stage is required")
    private DealStage currentStage;

    private String summary;

    private Double dealValue;  

    @NotNull(message = "Assigned user ID is required")
    private Long assignedToId;
}
