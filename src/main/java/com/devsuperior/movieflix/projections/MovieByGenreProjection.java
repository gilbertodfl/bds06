package com.devsuperior.movieflix.projections;

public interface MovieByGenreProjection {

	Long getId(); 
	String getTitle();
	String getSubTitle();
	Integer  getYear();
	Long getGenreId();
	String getImgUrl();
}
