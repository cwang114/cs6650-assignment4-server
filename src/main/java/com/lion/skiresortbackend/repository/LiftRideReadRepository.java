package com.lion.skiresortbackend.repository;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.lion.skiresortbackend.entity.LiftRideRead;

@EnableScan
public interface LiftRideReadRepository extends CrudRepository<LiftRideRead, String>{
	
	LiftRideRead save(LiftRideRead liftRideRead);

    Optional<LiftRideRead> findById(String id);
    
    boolean existsById(String id);
	

}
