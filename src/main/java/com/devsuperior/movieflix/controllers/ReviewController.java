package com.devsuperior.movieflix.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.ReviewService;


@RestController
@RequestMapping(value = "/reviews")

public class ReviewController {

		@Autowired
		private ReviewService service;
	
		
		@GetMapping
		public ResponseEntity<List<ReviewDTO>> findAll() {
			List<ReviewDTO> list = service.findAll();		
			return ResponseEntity.ok().body(list);
		}
		
/*		
 * Embora paginado seja melhor, o exercício pede que seja uma lista simples. 
		@GetMapping
		public ResponseEntity<Page<ReviewDTO>> findAll(Pageable pageable) {
			PageRequest pageRequest = PageRequest.of( pageable.getPageNumber(), pageable.getPageSize() , Sort.by("name") );
			Page<ReviewDTO> list = service.findAllPaged(pageRequest);
			return ResponseEntity.ok().body(list); 
		}
*/		
		@GetMapping(value = "/{id}")
		public ResponseEntity<ReviewDTO> findById(@PathVariable Long id) {
			ReviewDTO dto = service.findById(id);
			return ResponseEntity.ok().body(dto);
		}
		
		@PostMapping
		public ResponseEntity<ReviewDTO> insert(@Valid @RequestBody ReviewDTO dto) {
			// o parametro @Valid é o que ativa as validações. Sem ele nada do que foi construído será executado. 
			dto = service.insert(dto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(dto.getId()).toUri();
			return ResponseEntity.created(uri).body(dto);
		}

		@PutMapping(value = "/{id}")
		public ResponseEntity<ReviewDTO> update(@Valid @PathVariable Long id, @RequestBody ReviewDTO dto) {
			dto = service.update(id, dto);
			return ResponseEntity.ok().body(dto);
		}

		@DeleteMapping(value = "/{id}")
		public ResponseEntity<Void> delete(@PathVariable Long id) {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}		
}
