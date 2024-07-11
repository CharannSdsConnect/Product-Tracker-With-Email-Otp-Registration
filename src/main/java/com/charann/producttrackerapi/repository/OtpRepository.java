package com.charann.producttrackerapi.repository;

import com.charann.producttrackerapi.entity.Otp;
import com.charann.producttrackerapi.entity.OtpModel;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmail(String email);

    Boolean existsByEmail(String email);


//     OtpModel getById(long id);
}
