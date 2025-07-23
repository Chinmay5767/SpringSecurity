package com.freelancing_platform.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.freelancing_platform.backend.entity.UserAuthEntity;

public interface UserRepository extends JpaRepository <UserAuthEntity , Long>{

	boolean existsByEmailID(String emailID);

	UserAuthEntity findByUserName(String username);

        

	
     

}
