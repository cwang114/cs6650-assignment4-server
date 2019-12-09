package com.lion.skiresortbackend.repository;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.lion.skiresortbackend.entity.Resort;

@EnableScan
public interface ResortRepository extends CrudRepository<Resort, String> {
	
	Optional<Resort> findByResortName(String resortName);
	
	List<Resort> findAll();
	
	boolean existsById(String resortId);

}
