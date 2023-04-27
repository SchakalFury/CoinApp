package com.example.CoinApp.repositories;

import com.example.CoinApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByEmailContainingIgnoreCase(String email);

    List<User> findByRegistrationDate(LocalDate date);

    List<User> findByCountryOfResidenceContainingIgnoreCase(String country);
}
