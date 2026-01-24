package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "excursions")
@Getter
@Setter
public class Excursion {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "excursion_id")
   private Long id;

   @Column(name = "create_date")
   @CreationTimestamp
   private LocalDateTime create_date;

   @Column(name = "excursion_price")
   private BigDecimal excursion_price;

   @Column(name = "excursion_title")
   private String excursion_title;

   @Column(name = "image_url")
   private String image_URL;

   @Column(name = "last_update")
   @UpdateTimestamp
   private LocalDateTime last_update;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "vacation_id", nullable = false)
   private Vacation vacation;

   @ManyToMany(mappedBy = "excursions")
   private Set<CartItem> cartItems = new HashSet<>();



}
