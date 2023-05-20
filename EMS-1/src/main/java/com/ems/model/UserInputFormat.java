package com.ems.model;

import com.ems.enums.Role;

import lombok.Data;

@Data
public class UserInputFormat {

	private String username;
    private String password;
    private Role role;
	
}
