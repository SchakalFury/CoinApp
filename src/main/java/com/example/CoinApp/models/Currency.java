package com.example.CoinApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "currencies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    private List<CurrencyPrice> prices;

    @ManyToMany(mappedBy = "currencies")
    private List<User> users;
}
