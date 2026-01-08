package com.example.demo.SecurityConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Enums.DealStage;
import com.example.demo.Enums.DealType;
import com.example.demo.Enums.Role;
import com.example.demo.entity.Deal;
import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.repository.DealRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DealRepository dealRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing database with sample data...");
        createUsers();
        createDeals();
        System.out.println("Database initialization completed!");
    }
    
    private void createUsers() {

        if (!userRepository.existsByUsername("satwika")) {
            User satwika = new User();
            satwika.setUsername("satwika");
            satwika.setEmail("satwika@gmail.com");
            satwika.setPassword(passwordEncoder.encode("satwika123"));
            satwika.setRole(Role.ADMIN);
            satwika.setActive(true);
            userRepository.save(satwika);
        }
        
        if (!userRepository.existsByUsername("sweety")) {
            User sweety = new User();
            sweety.setUsername("sweety");
            sweety.setEmail("sweety@gmail.com");
            sweety.setPassword(passwordEncoder.encode("sweety123"));
            sweety.setRole(Role.USER);
            sweety.setActive(true);
            userRepository.save(sweety);
        }
        
        if (!userRepository.existsByUsername("keerthi")) {
            User keerthi = new User();
            keerthi.setUsername("keerthi");
            keerthi.setEmail("keerthi@gmail.com");
            keerthi.setPassword(passwordEncoder.encode("keerthi123"));
            keerthi.setRole(Role.USER);
            keerthi.setActive(true);
            userRepository.save(keerthi);
        }
        
        if (!userRepository.existsByUsername("aishu")) {
            User aishu = new User();
            aishu.setUsername("aishu");
            aishu.setEmail("aishu@gmail.com");
            aishu.setPassword(passwordEncoder.encode("aishu123"));
            aishu.setRole(Role.USER);
            aishu.setActive(true);
            userRepository.save(aishu);
        }
    }
    
    private void createDeals() {

        if (dealRepository.count() == 0) {

            User satwika = userRepository.findByUsername("satwika")
                .orElseThrow(() -> new RuntimeException("User not found"));
            User sweety = userRepository.findByUsername("sweety")
                .orElseThrow(() -> new RuntimeException("User not found"));
            User keerthi = userRepository.findByUsername("keerthi")
                .orElseThrow(() -> new RuntimeException("User not found"));
            User aishu = userRepository.findByUsername("aishu")
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            Deal deal1 = new Deal();
            deal1.setClientName("Satwika Enterprises");
            deal1.setDealType(DealType.M_A);
            deal1.setSector("IT");
            deal1.setDealValue(50000000.00);
            deal1.setCurrentStage(DealStage.PROSPECT);
            deal1.setSummary("Acquisition discussion for software firm");
            deal1.setCreatedBy(satwika);
            deal1.setAssignedTo(sweety);
            deal1.setCreatedAt(LocalDateTime.now().minusDays(10));
            
            Deal deal2 = new Deal();
            deal2.setClientName("Sweety Finance");
            deal2.setDealType(DealType.EQUITY_FINANCING);
            deal2.setSector("Finance");
            deal2.setDealValue(75000000.00);
            deal2.setCurrentStage(DealStage.UNDER_EVALUATION);
            deal2.setSummary("Equity funding for fintech expansion");
            deal2.setCreatedBy(sweety);
            deal2.setAssignedTo(keerthi);
            deal2.setCreatedAt(LocalDateTime.now().minusDays(7));
            
            Deal deal3 = new Deal();
            deal3.setClientName("Keerthi Energy");
            deal3.setDealType(DealType.IPO);
            deal3.setSector("Energy");
            deal3.setDealValue(150000000.00);
            deal3.setCurrentStage(DealStage.TERM_SHEET_SUBMITTED);
            deal3.setSummary("IPO preparation for renewable energy company");
            deal3.setCreatedBy(satwika);
            deal3.setAssignedTo(aishu);
            deal3.setCreatedAt(LocalDateTime.now().minusDays(5));
            
            Deal deal4 = new Deal();
            deal4.setClientName("Aishu Healthcare");
            deal4.setDealType(DealType.DEBT_OFFERING);
            deal4.setSector("Healthcare");
            deal4.setDealValue(25000000.00);
            deal4.setCurrentStage(DealStage.CLOSED);
            deal4.setSummary("Bond issuance for hospital expansion");
            deal4.setCreatedBy(keerthi);
            deal4.setAssignedTo(sweety);
            deal4.setCreatedAt(LocalDateTime.now().minusDays(30));
            
            dealRepository.saveAll(Arrays.asList(deal1, deal2, deal3, deal4));

            addSampleNotes(deal1, satwika, sweety);
            addSampleNotes(deal2, sweety, keerthi);
        }
    }
    
    private void addSampleNotes(Deal deal, User... users) {

        Note note1 = new Note();
        note1.setNote("Initial discussion completed.");
        note1.setUser(users[0]);
        note1.setTimestamp(LocalDateTime.now().minusDays(2));
        
        Note note2 = new Note();
        note2.setNote("Next meeting scheduled.");
        note2.setUser(users.length > 1 ? users[1] : users[0]);
        note2.setTimestamp(LocalDateTime.now().minusDays(1));
        
        Note note3 = new Note();
        note3.setNote("Internal review in progress.");
        note3.setUser(users[0]);
        note3.setTimestamp(LocalDateTime.now());
        
        deal.setNotes(Arrays.asList(note1, note2, note3));
        dealRepository.save(deal);
    }
}
