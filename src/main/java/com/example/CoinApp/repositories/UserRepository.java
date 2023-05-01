package com.example.CoinApp.repositories;

import com.example.CoinApp.models.User;
import com.example.CoinApp.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserRole(UserRole userRole);
    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);
    List<User> findByEmailContainingIgnoreCase(String email);
    List<User> findByCountryOfResidence(String country);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByUsernameOrEmailContainingIgnoreCase(String username, String email);
}
