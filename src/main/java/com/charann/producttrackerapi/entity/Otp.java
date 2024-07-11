package com.charann.producttrackerapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_id")
    private String email;

    @Column(name = "otp")
    private String otp;

    @Column(name = "generated_at", updatable = false)
    private LocalDateTime generatedAt;

}
