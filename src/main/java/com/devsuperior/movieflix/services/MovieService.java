package com.devsuperior.movieflix.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieGenreDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projections.MovieByGenreProjection;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {
	
	@Autowired
	private GenreRepository genreRepository;
	// Já instância : org.springframework.beans.factory.annotation.Autowired;
	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository repositoryUser;

	@Autowired
	private ReviewRepository repositoryReview;
	
	@Autowired
	private GenreRepository repositoryGenre;
	
	public User user;

	@Transactional(readOnly = true)
	public List<MovieDTO> findAll(){
			List<Movie> list = repository.findAll(Sort.by("title"));
			
			return list.stream().map( x -> new MovieDTO(x)).collect(Collectors.toList());
	}
	

	@Transactional(readOnly = true)
	public Page<MovieGenreDTO> findAllByGenre(Long genreId, String name, Pageable pageable) {
//		List<Genre> genres = (genreId == 0) ? null : Arrays.asList(genreRepository.getOne(genreId));
		// Aqui retorna uma projection que é o default do Repository para native query :nativeQuery = true
		List<MovieByGenreProjection> filmes = repository.search1(genreId);
		// Preciso converter a projection para uma Lista DTO
		List<MovieGenreDTO> filmesDTO2 = filmes.stream().map( x-> new MovieGenreDTO(x)).collect(Collectors.toList());
		// E finalmente retornar no formato PAGE com a instrução abaixo: 
	    Page<MovieGenreDTO> pages1 = new PageImpl<MovieGenreDTO>(filmesDTO2, pageable, filmesDTO2.size() );
		return  pages1 ;
	}
	@Transactional(readOnly = true)
	public Page<MovieDTO> findAll(Pageable pageable) {
		Page<Movie> list = repository.findAll(pageable);
		return list.map(x -> new MovieDTO(x));
	}

	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieDTO(entity);
	}

	@Transactional 
	public MovieDTO insert(MovieDTO dto) {
		Movie entity = new Movie();
		copyDtotoEntity( dto, entity ); 
		entity = repository.save(entity);
		return new MovieDTO(entity);
	}
	
	 
	public void copyDtotoEntity(MovieDTO dto, Movie entity) {
		
		entity.setTitle    ( dto.getTitle()		);
		entity.setSubTitle ( dto.getSubTitle()	);
		entity.setImgUrl   ( dto.getImgUrl()	);
		entity.setSynopsis ( dto.getSynopsis()	);
		entity.setYear     ( dto.getYear()		);
//		dto.getGenres().getId();
		Genre entityGenre = repositoryGenre.getOne(dto.getGenres().getId());
		entity.setGenre ( new Genre ( entityGenre ));
//		entity.setGenre ( new Genre ( dto.getGenreId(), null ));

	}

	@Transactional
	public MovieDTO update(Long id, MovieDTO dto) {
		try {
			// Instancia apenas. Ainda não vai ao banco
			Movie entity = repository.getOne(id);
			copyDtotoEntity( dto, entity );
			entity = repository.save(entity);
			return new MovieDTO(entity);

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
	
	// exemplo: {{host}}/movies/1/reviews
	@Transactional(readOnly = true)
	public List<ReviewDTO> findAllByIdReview(Long id) {
		List  <Review> list = repositoryReview.findAllByMovieId(id);
		if ( list.size()  < 1)
				new ResourceNotFoundException("Entity not found");
		// Preciso carregar os dados do usuário aqui.
		for (Review x : list) {
//			System.out.println( " list " + x.getText() );
			 user = repositoryUser.getOne( x.getUser().getId());
		}
	   return list.stream().map( x -> new ReviewDTO(x, user)).collect(Collectors.toList());
	}

		
	
}
