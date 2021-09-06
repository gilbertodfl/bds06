package com.devsuperior.movieflix.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.movieflix.entities.Review;



//repare que aqui é uma INTERFACE 
// e quem precisa importar o import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository <Review, Long>{

	List<Review> findAllByMovieId(Long movieId);
	
}
