package com.lion.skiresortbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lion.skiresortbackend.entity.Resort;
import com.lion.skiresortbackend.entity.Season;
import com.lion.skiresortbackend.exception.InvalidSeasonException;
import com.lion.skiresortbackend.exception.ResortNotFoundException;
import com.lion.skiresortbackend.repository.ResortRepository;
import com.lion.skiresortbackend.repository.SeasonRepository;
import com.lion.skiresortbackend.util.SeasonListWrapper;


@RestController
@RequestMapping(path="/resorts")
public class ResortController {
	
	@Autowired
	private ResortRepository resortRepository;
	@Autowired
	private SeasonRepository seasonRepository;
	
	final static Logger logger = LoggerFactory.getLogger(ResortController.class);
	
	@GetMapping(path="")
	public List<Resort> getResorts() {
		logger.debug("Calling getResorts()");
		List<Resort> resorts = resortRepository.findAll();
		return resorts;
	}
	
	@PostMapping(path="")
	public ResponseEntity<?> addResort(@RequestBody Resort resort) {
		resortRepository.save(resort);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{resortId}/seasons")
    public SeasonListWrapper getResortSeasons(@PathVariable(value = "resortId") String resortId) {
        if (!resortRepository.existsById(resortId)) {
        	throw new ResortNotFoundException("No resort id: " + resortId);
        }
        List<Season> seasonList = seasonRepository.findByResortId(resortId);
        List<Integer> seasons = seasonList.stream().map(season -> season.getSeason()).collect(Collectors.toList());
        SeasonListWrapper seasonWrapper = new SeasonListWrapper(seasons);
        return seasonWrapper;
        
    }
	
	@PostMapping("/{resortId}/seasons")
    public ResponseEntity<?> addSeason(@PathVariable (value = "resortId") String resortId,
                          			   @RequestBody ObjectNode seasonFromRequest) {
        if (!resortRepository.existsById(resortId)) {
        	throw new ResortNotFoundException("No resort id: " + resortId);
        }
        if (seasonFromRequest.get("year") == null) {
        	throw new InvalidSeasonException("Invalid request of adding season.");
        }
        int year = seasonFromRequest.get("year").asInt();
        Season season = new Season(year, resortId);
        seasonRepository.save(season);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
     
    }
}
