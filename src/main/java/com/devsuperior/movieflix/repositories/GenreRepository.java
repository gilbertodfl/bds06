package com.devsuperior.movieflix.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.movieflix.entities.Genre;



//repare que aqui é uma INTERFACE 
// e quem precisa importar o import org.springframework.data.jpa.repository.JpaRepository;
public interface GenreRepository extends JpaRepository <Genre, Long>{

	Genre findByName(String genre);
//	Genre findById2(Long id);
}
