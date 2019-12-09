package com.lion.skiresortbackend.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Represents a single lift ride information.
 * @author lion
 *
 */

@DynamoDBTable(tableName = "LiftRide")
public class LiftRideRead {

	private String id;
	
	private int liftId;

	private int time;
	
	private int vertical;
	
	
	public LiftRideRead() {
		
	}


	public LiftRideRead(int resortId, int season, int dayId, int skierId, int liftId, int time) {
		super();
		this.id = formLiftRideReadStringId(resortId, season, dayId, skierId);
		this.liftId = liftId;
		this.time = time;
		this.vertical = liftId * 10;
	}
	
	public LiftRideRead(String id, int liftId, int time, int vertical) {
		this.id = id;
		this.liftId = liftId;
		this.time = time;
		this.vertical = vertical;
	}


	private String formLiftRideReadStringId(int resortId, int season, int dayId, int skierId) {
		return resortId + "-" + season + "-" + dayId + "-" + skierId;
	}

	@DynamoDBHashKey(attributeName="compId")
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute
	public int getLiftId() {
		return liftId;
	}

	@DynamoDBAttribute
	public int getTime() {
		return time;
	}

	@DynamoDBAttribute
	public int getVertical() {
		return vertical;
	}

	public void setLiftId(int liftId) {
		this.liftId = liftId;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public void setVertical(int vertical) {
		this.vertical = vertical;
	}
}
