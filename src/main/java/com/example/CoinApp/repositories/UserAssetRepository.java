package com.example.CoinApp.repositories;

import com.example.CoinApp.models.Asset;
import com.example.CoinApp.models.User;
import com.example.CoinApp.models.UserAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAssetRepository extends JpaRepository<UserAsset, Long> {

    List<UserAsset> findByUser(User user);

    List<UserAsset> findByAsset(Asset asset);

    void deleteByUserAndAsset(User user, Asset asset);

}
