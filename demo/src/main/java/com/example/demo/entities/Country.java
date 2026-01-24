package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @Column(name = "country_name")
    private String country_name;

    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime last_update;

    @OneToMany(
            mappedBy = "country",
            cascade = CascadeType.ALL
    )
    private Set<Division> divisions = new HashSet<>();

}
