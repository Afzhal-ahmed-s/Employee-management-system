package com.ems.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ems.enums.Role;

import lombok.Data;

@Entity
@Data
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int employeeId;
		
		@Column(unique = true)
	    private String username;
	    private String password;
	    private Role role;
	
}
