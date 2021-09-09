package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {
	// Já instância : org.springframework.beans.factory.annotation.Autowired;
	@Autowired
	private ReviewRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private AuthService authService;

	
  @Transactional(readOnly = true)
  public List<ReviewDTO> findAll(){
		List<Review> list = repository.findAll(Sort.by("name"));
		return list.stream().map( x -> new ReviewDTO(x)).collect(Collectors.toList());
	}
		

	@Transactional(readOnly = true)
	public ReviewDTO findById(Long id) {
		Optional<Review> obj = repository.findById(id);
		Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ReviewDTO(entity);
	}
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {

		Movie movie1  = new Movie();
		Review entity = new Review();
		User user = authService.authenticated();

		entity.setUser ( user)  ;

//		System.out.println( " id eh entity.  " + entity.getUser().getId() );
//		System.out.println( " email eh  entity. "  + entity.getUser().getEmail() );


		movie1 = movieRepository.getOne( dto.getMovieId() );
		entity.setMovie( movie1 );

//		System.out.println( " id movie entity.  " + entity.getMovie().getId() );
//		System.out.println( " email eh  entity. "  + entity.getMovie().getTitle() );

		// aqui vai a descrição do Review do filme
		entity.setText (dto.getText());
		
		entity = repository.save(entity);
		return new ReviewDTO(entity);
	}

	@Transactional
	public ReviewDTO update(Long id, ReviewDTO dto) {
		try {
			Review entity = repository.getOne(id);
			entity.setText(dto.getText());
			entity = repository.save(entity);
			return new ReviewDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}	
}
