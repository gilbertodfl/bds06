package com.devsuperior.movieflix.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;

public class ReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long movieId;
//	private ReviewUserDTO user = new ReviewUserDTO( );
	private ReviewUserDTO user ;

	@NotBlank(message = "Campo obrigatório")
	private String text;
	
	public ReviewDTO() {
	}

	public ReviewDTO(Long id, String text, Long movieId,  ReviewUserDTO user ) {
		this.id = id;
		this.text = text;
		this.movieId = movieId;
		this.user = user;
	}
	public ReviewDTO(Review entity)	{
		id = entity.getId();
		text = entity.getText();
		movieId = entity.getMovie().getId();
		ReviewUserDTO user1 = new ReviewUserDTO(entity.getUser().getId(), entity.getUser().getName(),  entity.getUser().getEmail() );
/*		MY DEBUG
		System.out.println( "....... id user entity.  " + user1.getId() );
		System.out.println( "........ email eh  entity. "  + user1.getEmail() );
		System.out.println( "........ name eh  entity. "  + user1.getName() );
*/
		this.user = user1;

		
	}

	public ReviewDTO(Review entity , User user )	{
		id = entity.getId();
		text = entity.getText();
		movieId = entity.getMovie().getId();
	
		ReviewUserDTO user1 = new ReviewUserDTO(user.getId(),user.getName(),  user.getEmail() );
		this.user = user1;
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

	public ReviewUserDTO getUser() {
		return user;
	}

	public void setUser(ReviewUserDTO user) {
		this.user = user;
	}
	

}
