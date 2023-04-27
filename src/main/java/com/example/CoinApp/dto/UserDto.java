package com.example.CoinApp.dto;

import com.example.CoinApp.models.User;
import com.example.CoinApp.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean isEnabled;
    private LocalDate registrationDate;
    private String countryOfResidence;
    private UserRole userRole;
    private Set<AssetDto> assets;

    public static UserDto from(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setCountryOfResidence(user.getCountryOfResidence());
        dto.setUserRole(user.getRole());
        dto.setAssets(user.getAssets().stream()
                .map(AssetDto::from)
                .collect(Collectors.toSet()));
        return dto;
    }

    public static User to(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setEnabled(userDto.isEnabled());
        user.setRegistrationDate(userDto.getRegistrationDate());
        user.setCountryOfResidence(userDto.getCountryOfResidence());
        user.setRole(userDto.getUserRole());
        user.setAssets(userDto.getAssets().stream()
                .map(AssetDto::to)
                .collect(Collectors.toSet()));
        return user;
    }
}