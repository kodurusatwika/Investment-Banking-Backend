package com.example.demo;



import com.example.demo.Enums.DealStage;
import com.example.demo.controller.DealController;
import com.example.demo.dto.AddNoteDTO;
import com.example.demo.dto.CreateDealDTO;
import com.example.demo.dto.DealDTO;
import com.example.demo.service.DealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealControllerTest {

    @Mock
    private DealService dealService;

    @InjectMocks
    private DealController dealController;

    private DealDTO dealDTO;

    @BeforeEach
    void setUp() {
        dealDTO = new DealDTO();
        dealDTO.setId(1L);
        dealDTO.setClientName("Client A");
        dealDTO.setDealValue(100000.0);
        
    }

    @Test
    void createDeal_success() {
        CreateDealDTO createDealDTO = new CreateDealDTO();

        when(dealService.createDeal(createDealDTO)).thenReturn(dealDTO);

        ResponseEntity<DealDTO> response =
                dealController.createDeal(createDealDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Client A", response.getBody().getClientName());
        verify(dealService).createDeal(createDealDTO);
    }

    @Test
    void getAllDeals_success() {
        when(dealService.getAllDeals(null, null, null, null))
                .thenReturn(List.of(dealDTO));

        ResponseEntity<List<DealDTO>> response =
                dealController.getAllDeals(null, null, null, null);

        assertEquals(1, response.getBody().size());
        verify(dealService).getAllDeals(null, null, null, null);
    }

    @Test
    void getDealById_success() {
        when(dealService.getDealById(1L)).thenReturn(dealDTO);

        ResponseEntity<DealDTO> response =
                dealController.getDealById(1L);

        assertEquals(1L, response.getBody().getId());
        verify(dealService).getDealById(1L);
    }

    @Test
    void updateDeal_success() {
        CreateDealDTO updateDealDTO = new CreateDealDTO();

        when(dealService.updateDeal(1L, updateDealDTO))
                .thenReturn(dealDTO);

        ResponseEntity<DealDTO> response =
                dealController.updateDeal(1L, updateDealDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dealService).updateDeal(1L, updateDealDTO);
    }

    @Test
    void updateDealStage_success() {
        when(dealService.updateDealStage(1L, DealStage.CLOSED))
                .thenReturn(dealDTO);

        ResponseEntity<DealDTO> response =
                dealController.updateDealStage(1L, DealStage.CLOSED);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dealService).updateDealStage(1L, DealStage.CLOSED);
    }

    @Test
    void updateDealValue_success() {
        when(dealService.updateDealValue(1L, 200000.0))
                .thenReturn(dealDTO);

        ResponseEntity<DealDTO> response =
                dealController.updateDealValue(1L, 200000.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dealService).updateDealValue(1L, 200000.0);
    }

    @Test
    void addNote_success() {
        AddNoteDTO noteDTO = new AddNoteDTO();

        when(dealService.addNote(1L, noteDTO))
                .thenReturn(dealDTO);

        ResponseEntity<DealDTO> response =
                dealController.addNote(1L, noteDTO);

        assertNotNull(response.getBody());
        verify(dealService).addNote(1L, noteDTO);
    }

    @Test
    void deleteDeal_success() {
        doNothing().when(dealService).deleteDeal(1L);

        ResponseEntity<Void> response =
                dealController.deleteDeal(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(dealService).deleteDeal(1L);
    }
}
