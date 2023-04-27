package com.example.CoinApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @NotBlank
    @Column(name = "country_of_residence")
    private String countryOfResidence;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @ManyToMany
    @JoinTable(
            name = "user_assets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_symbol")
    )
    private Set<Asset> assets = new HashSet<>();


    public void addAsset(Asset asset) {
        assets.add(asset);
        asset.getUsers().add(this);
    }

    public void removeAsset(Asset asset) {
        assets.remove(asset);
        asset.getUsers().remove(this);
    }


}
