package com.example.CoinApp.controllers;

import com.example.CoinApp.dto.UserDTO;
import com.example.CoinApp.models.UserRole;
import com.example.CoinApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        userDTO.setUserRole(UserRole.USER);
        userDTO.setRegistrationDate(LocalDate.now());
        userService.save(userDTO);
        model.addAttribute("username", userDTO.getUsername());
        return "redirect:/";
    }


}


