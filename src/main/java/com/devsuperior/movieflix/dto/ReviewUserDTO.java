package com.devsuperior.movieflix.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.devsuperior.movieflix.entities.User;

public class ReviewUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;

	private String email;
	
	
	public ReviewUserDTO() {
	}

	public ReviewUserDTO(Long id, String name,  String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public ReviewUserDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
	}

	public ReviewUserDTO(ReviewUserDTO dto ,User entity) {
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
