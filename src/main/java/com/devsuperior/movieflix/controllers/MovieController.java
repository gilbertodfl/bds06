package com.devsuperior.movieflix.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieGenreDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;


@RestController
@RequestMapping(value = "/movies")

public class MovieController {

		@Autowired
		private MovieService service;
		
		@GetMapping(value = "/{id}")
		public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
			MovieDTO dto = service.findById(id);
			return ResponseEntity.ok().body(dto);
		}

		@GetMapping(value = "/{id}/reviews")
		public ResponseEntity<List<ReviewDTO>> findAllByIdReview(@PathVariable Long id) {
			List<ReviewDTO> dto = service.findAllByIdReview(id);
			return ResponseEntity.ok().body(dto);
		}

		// pesquisa por genero e todos os filmes se não especificado o genero
		@GetMapping
		public ResponseEntity<Page<MovieGenreDTO>> findAllByGenre(
				@RequestParam( value = "genreId", defaultValue = "0") Long genreId, 
			    @RequestParam( name="name", defaultValue="" ) String name,
			Pageable pageable) 
		{
			Page<MovieGenreDTO> pages = service.findAllByGenre( genreId, name, pageable );
		    return new ResponseEntity<>(pages, HttpStatus.OK);
		}
		
		@PostMapping
		public ResponseEntity<MovieDTO> insert(@Valid @RequestBody MovieDTO dto) {
			dto = service.insert(dto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(dto.getId()).toUri();
			return ResponseEntity.created(uri).body(dto);
		}

		@PutMapping(value = "/{id}")
		public ResponseEntity<MovieDTO> update( @PathVariable Long id,  @Valid @RequestBody MovieDTO dto) {
			dto = service.update(id, dto);
			return ResponseEntity.ok().body(dto);
		}

		@DeleteMapping(value = "/{id}")
		public ResponseEntity<Void> delete(@PathVariable Long id) {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}	
}
