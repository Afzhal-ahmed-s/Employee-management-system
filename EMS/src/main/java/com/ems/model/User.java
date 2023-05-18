package com.ems.model;

import com.ems.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
