package com.devsuperior.movieflix.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;

public class ReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long movieId;
	public ReviewUserDTO user;

	private String text;
	
	public ReviewDTO() {
	}

	public ReviewDTO(Long id, String text, Long movieId,  Long userId) {
		this.id = id;
		this.text = text;
		this.movieId = movieId;
		movieId = userId;
	}
	public ReviewDTO(Review entity)	{
		id = entity.getId();
		text = entity.getText();
		movieId = entity.getMovie().getId();
	}

	public ReviewDTO(Review entity, User user1) {
		
		this(entity);
		
		user = new ReviewUserDTO( );
		user.setId	 ( user1.getId()   );
		user.setEmail( user1.getEmail());
		user.setName ( user1.getName() );
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	
	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public void setText(String text) {
		this.text = text;
	}

}
