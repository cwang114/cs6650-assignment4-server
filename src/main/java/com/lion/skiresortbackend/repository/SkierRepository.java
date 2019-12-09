package com.lion.skiresortbackend.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.lion.skiresortbackend.entity.Skier;

@EnableScan
public interface SkierRepository extends CrudRepository<Skier, Integer> {
	
	List<Skier> findAll();
	
	boolean existsById(int skierId);
}
