package com.andreisoimu.cine_mille.service;

import com.andreisoimu.cine_mille.model.dao.Movie;

import java.util.List;

public interface MovieService {

	/**
	 * Get all movies
	 * @return List<Movie>
	 */
	public List<Movie> getAll();

	/**
	 * Insert movie
	 * @param movie
	 */
    public void insert(Movie movie) throws Exception;

}
