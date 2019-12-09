package com.lion.skiresortbackend.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.lion.skiresortbackend.entity.Season;


@EnableScan
public interface SeasonRepository extends CrudRepository<Season, String> {
	
	List<Season> findByResortId(String resortId);
}
