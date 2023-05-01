package com.example.CoinApp.services;

import com.example.CoinApp.dto.UserDTO;
import com.example.CoinApp.models.Currency;
import com.example.CoinApp.models.User;
import com.example.CoinApp.models.UserRole;
import com.example.CoinApp.repositories.UserRepository;
import com.example.CoinApp.utils.UtilClasses.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserValidator userValidator;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserValidator userValidator) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;

    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserDTO::fromEntity);
    }

    public void save(UserDTO userDTO) {
        userValidator.validateUsername(userDTO.getUsername());
        userValidator.validateEmail(userDTO.getEmail());
        userValidator.validatePassword(userDTO.getPassword(), userDTO.getUsername());
        userValidator.validateCountryOfResidence(userDTO.getCountryOfResidence());

        if (userDTO.getCurrencies() == null) {
            userDTO.setCurrencies(new ArrayList<>());
        }

        User user = userDTO.toEntity();

        User savedUser = userRepository.save(user);
    }
    public UserDTO update(UserDTO userDTO) {
        userValidator.validateUsername(userDTO.getUsername());
        userValidator.validateEmail(userDTO.getEmail());
        userValidator.validatePassword(userDTO.getPassword(), userDTO.getUsername());
        userValidator.validateCountryOfResidence(userDTO.getCountryOfResidence());

        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userDTO.toEntity();
            User updatedUser = userRepository.save(user);

            return UserDTO.fromEntity(updatedUser);
        } else {
            logger.error("User with id={} not found, update failed", userDTO.getId());
            throw new RuntimeException("User not found");
        }

    }

        public void deleteById (Long id){

            userRepository.deleteById(id);
            logger.warn("User with ID {} has been deleted", id);
        }

        public List<UserDTO> findByUserRole (UserRole userRole){
            List<User> users = userRepository.findByUserRole(userRole);
            return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        public List<UserDTO> findByRegistrationDateBetween (LocalDate start, LocalDate end){
            List<User> users = userRepository.findByRegistrationDateBetween(start, end);
            return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        public List<UserDTO> findByEmailContainingIgnoreCase (String email){
            List<User> users = userRepository.findByEmailContainingIgnoreCase(email);
            return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        public List<UserDTO> findByCountryOfResidence (String country){
            List<User> users = userRepository.findByCountryOfResidence(country);
            return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        public List<UserDTO> findByUsernameContainingIgnoreCase (String username){
            List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
            return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        public List<UserDTO> findByUsernameOrEmailContainingIgnoreCase (String username, String email){
            List<User> users = userRepository.findByUsernameOrEmailContainingIgnoreCase(username, email);
            return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
        }
    }

