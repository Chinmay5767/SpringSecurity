package com.freelancing_platform.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.*;
import com.freelancing_platform.backend.entity.UserAuthEntity;



public interface UserRepository extends JpaRepository <UserAuthEntity , Long>{

	boolean existsByEmailID(String emailID);

	UserAuthEntity findByUsername(String username);

        

	
     

}
