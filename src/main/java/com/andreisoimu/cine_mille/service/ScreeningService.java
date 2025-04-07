package com.andreisoimu.cine_mille.service;

import com.andreisoimu.cine_mille.model.dao.Screening;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ScreeningService {

	/**
	 * Get current screenings
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Screening> getCurrentScreenings(LocalDate startDate, LocalDate endDate, int page, int size);

	/**
	 * Get current screenings
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Screening> getPastScreenings(int page, int size);

	/**
	 * Insert screening
	 * @param screening
	 */
    public void insert(Screening screening) throws Exception;

}
