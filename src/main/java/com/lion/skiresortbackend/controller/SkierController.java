package com.lion.skiresortbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lion.skiresortbackend.entity.LiftRideRead;
import com.lion.skiresortbackend.entity.LiftRideWrite;
import com.lion.skiresortbackend.entity.Resort;
import com.lion.skiresortbackend.entity.Skier;
import com.lion.skiresortbackend.exception.InvalidResortNameException;
import com.lion.skiresortbackend.exception.ResortNotFoundException;
import com.lion.skiresortbackend.repository.LiftRideReadRepository;
import com.lion.skiresortbackend.repository.LiftRideWriteRepository;
import com.lion.skiresortbackend.repository.ResortRepository;
import com.lion.skiresortbackend.repository.SkierRepository;
import com.lion.skiresortbackend.stat.MetricService;
import com.lion.skiresortbackend.util.SeasonAndVertical;
import com.lion.skiresortbackend.util.SeasonAndVerticalWrapper;


@RestController
@RequestMapping(path="/skiers")
public class SkierController {
	
	final static Logger logger = LoggerFactory.getLogger(SkierController.class);
	@Autowired
	private ResortRepository resortRepository;
	@Autowired
	private SkierRepository skierRepository;
	@Autowired
	private LiftRideReadRepository liftRideReadRepository;

	@GetMapping(path="")
	public List<Skier> findAllSkiers() {
		return skierRepository.findAll();
	}

	@PostMapping(path="")
	public ResponseEntity<?> addSkier(@RequestBody Skier skier) {
		skierRepository.save(skier);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping(path="/liftrides")
	public List<LiftRideRead> findAllLiftRides() {
		return (List<LiftRideRead>) liftRideReadRepository.findAll();
	}
	
	@GetMapping(path="/{resortId}/seasons/{seasonId}/days/{daysId}/skiers/{skierId}")
	public int getTotalVerticalForSkier(@PathVariable(value = "resortId") int resortId,
										@PathVariable(value = "seasonId") int seasonId,
										@PathVariable(value = "daysId") int daysId,
										@PathVariable(value = "skierId") int skierId) {
		String composite = formLiftRideReadStringId(resortId, seasonId, daysId, skierId);
		Optional<LiftRideRead> liftRideRead = liftRideReadRepository.findById(composite);
		if (!liftRideRead.isPresent()) {
			return 0;
		} else {
			return liftRideRead.get().getVertical();
		}
	}
	
	/**
	 * Write a new lift ride under this skier. Stores new lift ride details in the data store.
	 * @param resortId ID of the resort the skier is at
	 * @param seasonId ID of the ski season
	 * @param dayId ID number of ski day in the ski season
	 * @param skierId ID of the skier riding the lift
	 * @param liftRide
	 */
	
	@PostMapping(path="/{resortId}/seasons/{seasonId}/days/{daysId}/skiers/{skierId}")
	public ResponseEntity<Void> writeNewLiftRide(@PathVariable(value = "resortId") int resortId,
									 		  @PathVariable(value = "seasonId") int seasonId,
									 		  @PathVariable(value = "daysId") @Size(min=1, max=366)int dayId,
									 		  @PathVariable(value = "skierId") int skierId,
									 		  @RequestBody ObjectNode liftRide) {

		int time = liftRide.get("time").asInt();
		int liftId = liftRide.get("liftID").asInt();
		
//		Validation:
//		if (!resortRepository.existsById(resortId)) {
//			throw new ResortNotFoundException("Resort not found");
//		}
//		if (!skierRepository.existsById(skierId)) {
//			throw new SkierNotFoundException("Skier not found");
//		}
		
		LiftRideRead newLiftRideRead = new LiftRideRead(resortId, seasonId, dayId, skierId, liftId, time);
		String compId = newLiftRideRead.getId();
		if (liftRideReadRepository.existsById(compId)) {
			int oldVertical = liftRideReadRepository.findById(compId).get().getVertical();
			newLiftRideRead.setVertical(oldVertical + newLiftRideRead.getVertical());
		}
		liftRideReadRepository.save(newLiftRideRead);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
		
	}
	
	
	/**
	 * Get the total vertical for the skier for specified seasons at the specified resort 
	 * @param skierId
	 * @param resortName
	 * @param season
	 * @return
	 */
	
	@GetMapping(path="/{skierId}/vertical")
	public SeasonAndVerticalWrapper getSkierDayVertical(@PathVariable(value = "skierId") int skierId,
														@RequestParam(name = "resort") String resortName,
														@RequestParam(name = "season", required = false) Integer season) {
		
		if (resortName == null) {
			throw new InvalidResortNameException("The resort name is required");
		}
		Optional<Resort> optionalResort = resortRepository.findByResortName(resortName);
		if (!optionalResort.isPresent()) {
			throw new ResortNotFoundException("Resort Not Found");
		}
		Resort resort = optionalResort.get();
		String resortId = resort.getResortId();
		List<LiftRideWrite> liftRideWrites = null;
//		if (season != null) {
//			liftRideWrites = liftRideWriteRepository.findBySkierIdAndResortIdAndSeason(skierId, resortId, season);
//		} else {
//			liftRideWrites = liftRideWriteRepository.findBySkierIdAndResortId(skierId, resortId);
//		}
//		Map<Integer, Integer> map = liftRideWrites.stream()
//			          						.collect(Collectors.groupingBy(liftRide -> liftRide.getSeason(),
//			          													   Collectors.summingInt(LiftRideWrite::getVertical)));
//		List<SeasonAndVertical> resorts = map.entrySet()
//			       							 .stream()
//			       							 .map(e -> new SeasonAndVertical(e.getKey(), e.getValue()))
//			       							 .collect(Collectors.toList());
//		SeasonAndVerticalWrapper wrapper = new SeasonAndVerticalWrapper(resorts);
//		return wrapper;
		return null;
	}
	
	private String formLiftRideReadStringId(int resortId, int season, int dayId, int skierId) {
		return resortId + "-" + season + "-" + dayId + "-" + skierId;
	}
	
	
}
