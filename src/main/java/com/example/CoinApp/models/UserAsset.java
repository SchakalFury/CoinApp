package com.example.CoinApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_assets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_symbol", nullable = false)
    private Asset asset;

    public UserAsset(User user, Asset asset) {
        this.user = user;
        this.asset = asset;
    }
}
