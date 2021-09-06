package com.devsuperior.movieflix.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieByGenreProjection;

//repare que aqui é uma INTERFACE 
// e quem precisa importar o import org.springframework.data.jpa.repository.JpaRepository;
public interface MovieRepository extends JpaRepository <Movie, Long>{
	
	
	@Query(nativeQuery = true, value = "SELECT id, title, sub_title as subTitle, year, img_url as imgUrl, genre_id as genreId  " 
			+ "FROM tb_movie "
			+ "WHERE :genreId = 0 OR  genre_id = :genreId order by title" )
	List<MovieByGenreProjection> search1(Long genreId);
	/*
	//Movie findById(Long id);
	@Query("SELECT DISTINCT obj FROM Movie obj INNER JOIN obj.genres cats WHERE "
			+ "(COALESCE(:genres) IS NULL OR cats IN :genres) AND "
			+ "(LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))) ")
	Page<Movie> find(List<Genre> genres, String name, Pageable pageable);
	
	@Query("SELECT obj FROM Movie obj JOIN FETCH obj.genres WHERE obj IN :movies")
	List<Movie> findMoviesWithGenres(List<Movie> movies);
*/	
}
