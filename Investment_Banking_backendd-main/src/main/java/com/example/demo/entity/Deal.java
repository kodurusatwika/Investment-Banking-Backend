package com.example.demo.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Enums.DealStage;
import com.example.demo.Enums.DealType;



@Entity
@Table(name = "deals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "client_name", nullable = false)
    private String clientName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false)
    private DealType dealType;
    
    @Column(nullable = false)
    private String sector;
    
    @Column(name = "deal_value")
    private Double dealValue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage", nullable = false)
    private DealStage currentStage;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deal_id")
    private List<Note> notes = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}