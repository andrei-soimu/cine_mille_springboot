package com.andreisoimu.cine_mille.service;

import com.andreisoimu.cine_mille.model.dao.Showtime;

import java.util.List;

public interface ShowtimeService {

	/**
	 * Get all showtimes
	 * @return List<Showtime>
	 */
	public List<Showtime> getAll();

	/**
	 * Insert showtime
	 * @param showtime
	 */
    public void insert(Showtime showtime) throws Exception;

}
