package com.andreisoimu.cine_mille.service.impl;

import com.andreisoimu.cine_mille.model.dao.Movie;
import com.andreisoimu.cine_mille.repository.jpa.MovieJpaRepository;
import com.andreisoimu.cine_mille.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	public MovieServiceImpl(MovieJpaRepository movieJpaRepository) {
		this.movieJpaRepository = movieJpaRepository;
	}
	private final MovieJpaRepository movieJpaRepository;

	public List<Movie> getAll() throws UsernameNotFoundException {
		return movieJpaRepository.findAll();
	}

	@Transactional
	public void insert(Movie movie) throws Exception {
		if(!movie.getName().isEmpty()) {
			movieJpaRepository.save(movie);
		} else
			throw new Exception("Name is required!");
		
	}
}