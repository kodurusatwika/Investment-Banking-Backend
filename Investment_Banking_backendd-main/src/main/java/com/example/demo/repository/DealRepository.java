package com.example.demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Enums.DealStage;
import com.example.demo.entity.Deal;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByAssignedToId(Long userId);
    List<Deal> findByCurrentStage(DealStage stage);
    //custom query 
    @Query("SELECT d FROM Deal d WHERE " +
           "(:clientName IS NULL OR LOWER(d.clientName) LIKE LOWER(CONCAT('%', :clientName, '%'))) AND " +
           "(:dealType IS NULL OR d.dealType = :dealType) AND " +
           "(:sector IS NULL OR LOWER(d.sector) LIKE LOWER(CONCAT('%', :sector, '%'))) AND " +
           "(:stage IS NULL OR d.currentStage = :stage)")
    List<Deal> searchDeals(
        @Param("clientName") String clientName, 
        @Param("dealType") String dealType,
        @Param("sector") String sector,
        @Param("stage") String stage
    );
}