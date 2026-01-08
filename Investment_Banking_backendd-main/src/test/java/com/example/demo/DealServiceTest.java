package com.example.demo;

import com.example.demo.Enums.DealStage;
import com.example.demo.Enums.DealType;
import com.example.demo.dto.AddNoteDTO;
import com.example.demo.dto.CreateDealDTO;
import com.example.demo.dto.DealDTO;
import com.example.demo.entity.Deal;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.DealRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealServiceTest {

    @InjectMocks
    private DealService dealService;

    @Mock
    private DealRepository dealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private User currentUser;
    private User assignedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Security Context
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("currentUser");

        // Sample Users
        currentUser = new User(
                1L, "currentUser", "current@user.com",
                "password", null, true, LocalDateTime.now(),
                null, null
        );

        assignedUser = new User(
                2L, "assignedUser", "assigned@user.com",
                "password", null, true, LocalDateTime.now(),
                null, null
        );
    }

    @Test
    void testCreateDeal_Success() {
        CreateDealDTO dto = new CreateDealDTO(
                "Client A",
                DealType.M_A,
                "IT",
                DealStage.PROSPECT,
                "Summary",
                1_000_000.0,
                2L
        );

        when(userRepository.findByUsername("currentUser"))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(assignedUser));

        Deal savedDeal = new Deal();
        savedDeal.setId(1L);
        savedDeal.setClientName(dto.getClientName());
        savedDeal.setDealType(dto.getDealType());
        savedDeal.setSector(dto.getSector());
        savedDeal.setCurrentStage(dto.getCurrentStage());
        savedDeal.setSummary(dto.getSummary());
        savedDeal.setDealValue(dto.getDealValue());
        savedDeal.setCreatedBy(currentUser);
        savedDeal.setAssignedTo(assignedUser);
        savedDeal.setNotes(new ArrayList<>());

        when(dealRepository.save(any(Deal.class)))
                .thenReturn(savedDeal);

        DealDTO result = dealService.createDeal(dto);

        assertNotNull(result);
        assertEquals(dto.getClientName(), result.getClientName());
        assertEquals(dto.getDealType(), result.getDealType());
        assertEquals(dto.getSector(), result.getSector());
        assertEquals(dto.getCurrentStage(), result.getCurrentStage());
        assertEquals(dto.getSummary(), result.getSummary());
        assertEquals(currentUser.getUsername(), result.getCreatedBy().getUsername());
        assertEquals(assignedUser.getUsername(), result.getAssignedTo().getUsername());
    }

    @Test
    void testGetDealById_DealNotFound() {
        when(dealRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> dealService.getDealById(1L));
    }

    @Test
    void testUpdateDeal_Success() {
        CreateDealDTO dto = new CreateDealDTO(
                "Client B",
                DealType.EQUITY_FINANCING,
                "Finance",
                DealStage.PROSPECT,
                "Updated Summary",
                2_000_000.0,
                2L
        );

        Deal existingDeal = new Deal();
        existingDeal.setId(1L);
        existingDeal.setCreatedBy(currentUser);
        existingDeal.setAssignedTo(currentUser);
        existingDeal.setCurrentStage(DealStage.PROSPECT);
        existingDeal.setNotes(new ArrayList<>());

        when(dealRepository.findById(1L))
                .thenReturn(Optional.of(existingDeal));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(assignedUser));
        when(dealRepository.save(any(Deal.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DealDTO result = dealService.updateDeal(1L, dto);

        assertEquals("Client B", result.getClientName());
        assertEquals("Updated Summary", result.getSummary());
        assertEquals(assignedUser.getId(), result.getAssignedTo().getId());
    }

    @Test
    void testUpdateDealStage_Success() {
        Deal deal = new Deal();
        deal.setId(1L);
        deal.setCreatedBy(currentUser);
        deal.setAssignedTo(assignedUser);
        deal.setCurrentStage(DealStage.PROSPECT);
        deal.setNotes(new ArrayList<>());

        when(dealRepository.findById(1L))
                .thenReturn(Optional.of(deal));
        when(dealRepository.save(any(Deal.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DealDTO result = dealService.updateDealStage(1L, DealStage.CLOSED);

        assertEquals(DealStage.CLOSED, result.getCurrentStage());
    }

    @Test
    void testUpdateDealValue_Success() {
        Deal deal = new Deal();
        deal.setId(1L);
        deal.setCreatedBy(currentUser);
        deal.setAssignedTo(assignedUser);
        deal.setDealValue(1000.0);
        deal.setCurrentStage(DealStage.PROSPECT);
        deal.setNotes(new ArrayList<>());

        when(dealRepository.findById(1L))
                .thenReturn(Optional.of(deal));
        when(dealRepository.save(any(Deal.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DealDTO result = dealService.updateDealValue(1L, 5000.0);

        assertEquals(5000.0, result.getDealValue());
    }

    @Test
    void testAddNote_Success() {
        AddNoteDTO noteDTO = new AddNoteDTO("Important Note");

        Deal deal = new Deal();
        deal.setId(1L);
        deal.setCreatedBy(currentUser);
        deal.setAssignedTo(assignedUser);
        deal.setCurrentStage(DealStage.PROSPECT);
        deal.setNotes(new ArrayList<>());

        when(userRepository.findByUsername("currentUser"))
                .thenReturn(Optional.of(currentUser));
        when(dealRepository.findById(1L))
                .thenReturn(Optional.of(deal));
        when(dealRepository.save(any(Deal.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DealDTO result = dealService.addNote(1L, noteDTO);

        assertEquals(1, result.getNotes().size());
        assertEquals("Important Note", result.getNotes().get(0).getNote());
        assertEquals(
                currentUser.getUsername(),
                result.getNotes().get(0).getUser().getUsername()
        );
    }

    @Test
    void testDeleteDeal_Success() {
        when(dealRepository.existsById(1L)).thenReturn(true);
        doNothing().when(dealRepository).deleteById(1L);

        assertDoesNotThrow(() -> dealService.deleteDeal(1L));
        verify(dealRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDeal_NotFound() {
        when(dealRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> dealService.deleteDeal(1L));
    }
}
