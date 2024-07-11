package com.charann.producttrackerapi.repository;

import java.util.Optional;

import com.charann.producttrackerapi.entity.AuthModel;
import com.charann.producttrackerapi.entity.User;
import com.charann.producttrackerapi.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

//	void delete(User user);

//	void delete(UserModel user);

//	AuthModel findUser(String email);

//	Boolean checkEmailValid(String email);

}
