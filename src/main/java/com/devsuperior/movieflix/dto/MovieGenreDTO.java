package com.devsuperior.movieflix.dto;

import java.io.Serializable;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieByGenreProjection;

public class MovieGenreDTO implements Serializable {
	
	/*
	 * Esse dto é usado em {{host}}/movies/genre?genreId=1
	 * que é a consulta de filmes por genero. Por isto não tem sets 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private String subTitle;
	
	private Integer year;
	private String imgUrl;
	private Long genreId;
		
	public MovieGenreDTO() {
	}

	
	public MovieGenreDTO(Long id, String title, String subTitle,  Integer year, Long genreId,  String imgUrl) {
		this.id = id;
		this.title = title;
		this.subTitle = subTitle;
		this.year = year;
		this.genreId = genreId;
		this.imgUrl = imgUrl;

	}


	public MovieGenreDTO(Movie entity) {
		id = entity.getId();
		title = entity.getTitle();
		subTitle = entity.getSubTitle();
		year = entity.getYear();
		genreId = entity.getGenre().getId();
		imgUrl = entity.getImgUrl();
	}

	public MovieGenreDTO( MovieByGenreProjection projection) {
		id = projection.getId();
		title = projection.getTitle();
		subTitle = projection.getSubTitle();
		year = projection.getYear();
		genreId = projection.getGenreId();
		imgUrl = projection.getImgUrl();
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}


	public Integer getYear() {
		return year;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public Long getGenreId() {
		return genreId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
