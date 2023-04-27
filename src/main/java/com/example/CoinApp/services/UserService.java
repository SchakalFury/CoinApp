package com.example.CoinApp.services;

import com.example.CoinApp.dto.AssetDto;
import com.example.CoinApp.dto.UserDto;
import com.example.CoinApp.models.Asset;
import com.example.CoinApp.models.User;

import com.example.CoinApp.repositories.AssetRepository;
import com.example.CoinApp.repositories.UserAssetRepository;
import com.example.CoinApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final UserAssetRepository userAssetRepository;

    @Autowired
    public UserService(UserRepository userRepository, AssetRepository assetRepository, UserAssetRepository userAssetRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.userAssetRepository = userAssetRepository;
    }

    public UserDto createOrUpdateUser(UserDto userDto) {
        User user = userRepository.save(UserDto.to(userDto));
        return UserDto.from(user);
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.from(user);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> findUsersByUsername(String username) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByEmail(String email) {
        List<User> users = userRepository.findByEmailContainingIgnoreCase(email);
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByRegistrationDate(LocalDate date) {
        List<User> users = userRepository.findByRegistrationDate(date);
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByCountryOfResidence(String country) {
        List<User> users = userRepository.findByCountryOfResidenceContainingIgnoreCase(country);
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }


    public static void addAssetToUser(UserDto userDto, AssetDto assetDto) {
        User user = UserDto.to(userDto);
        Asset asset = AssetDto.to(assetDto);
        user.addAsset(asset);
        userDto.setAssets(user.getAssets().stream().map(AssetDto::from).collect(Collectors.toSet()));
    }

    public static void removeAssetFromUser(UserDto userDto, AssetDto assetDto) {
        User user = UserDto.to(userDto);
        Asset asset = AssetDto.to(assetDto);
        user.removeAsset(asset);
        userDto.setAssets(user.getAssets().stream().map(AssetDto::from).collect(Collectors.toSet()));
    }


}







