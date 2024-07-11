package com.charann.producttrackerapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OtpModel {

    private Long id;

    private String email;

    private String otp;

    private LocalDateTime generatedAt;
}
