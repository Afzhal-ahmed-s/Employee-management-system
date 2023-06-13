package com.ems.model;

import org.springframework.stereotype.Component;

@Component
public class NextNumberGenerator {

	private Integer number;
	
	public NextNumberGenerator() {
		this.number = 0;
	}
	
	public Integer getNextNumber() {
		return ++number;
	}
	
}
