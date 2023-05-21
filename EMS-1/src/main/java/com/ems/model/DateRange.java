package com.ems.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRange {

    private LocalDate leaveFrom;
    private LocalDate leaveTo;
	
}
