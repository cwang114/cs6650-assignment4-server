package com.lion.skiresortbackend.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Skier")
public class Skier {
	
	int skierId;
		
	public Skier() {
	}

	public Skier(int skierId) {
		super();
		this.skierId = skierId;
	}


	@DynamoDBHashKey
	public int getSkierId() {
		return skierId;
	}

	public void setSkierId(int skierId) {
		this.skierId = skierId;
	}
	
}
