package com.charann.producttrackerapi.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegenerateOtpModel {
    @NotBlank(message = "Please enter email")
    private String email;

}
