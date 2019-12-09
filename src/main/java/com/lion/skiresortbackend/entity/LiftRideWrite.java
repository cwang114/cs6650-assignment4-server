package com.lion.skiresortbackend.entity;

public class LiftRideWrite {
	
	Integer id;
	
	int resortId;
	
	int season; 
	
	int dayId; 
	
	int skierId;
	
	private int liftId;

	private int time;

	private int vertical;
	

	public LiftRideWrite() {
		super();
	}

	public LiftRideWrite(int resortId, int season, int dayId, int skierId, int liftId, int time) {
		super();
		this.resortId = resortId;
		this.season = season;
		this.dayId = dayId;
		this.skierId = skierId;
		this.liftId = liftId;
		this.time = time;
		this.vertical = liftId * 10;
	}

	public int getResortId() {
		return resortId;
	}

	public int getSeason() {
		return season;
	}

	public int getDayId() {
		return dayId;
	}

	public int getSkierId() {
		return skierId;
	}

	public int getLiftId() {
		return liftId;
	}

	public int getTime() {
		return time;
	}

	public int getVertical() {
		return vertical;
	}

	public void setResortId(int resortId) {
		this.resortId = resortId;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public void setDayId(int dayId) {
		this.dayId = dayId;
	}

	public void setSkierId(int skierId) {
		this.skierId = skierId;
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
