package com.andreisoimu.cine_mille.service.impl;

import com.andreisoimu.cine_mille.model.dao.Screening;
import com.andreisoimu.cine_mille.repository.jpa.ScreeningJpaRepository;
import com.andreisoimu.cine_mille.service.ScreeningService;
import com.andreisoimu.cine_mille.specs.ScreeningSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

	@Autowired
	public ScreeningServiceImpl(ScreeningJpaRepository screeningJpaRepository) {
		this.screeningJpaRepository = screeningJpaRepository;
	}
	private final ScreeningJpaRepository screeningJpaRepository;

	public List<Screening> get() throws UsernameNotFoundException {
		return screeningJpaRepository.findAll();
	}

	public Page<Screening> getCurrentScreenings(LocalDate startDate, LocalDate endDate, int page, int size) {
		Specification<Screening> spec = Specification.where(null);

		if (startDate == null)
			startDate = LocalDate.now();

		spec = spec.and(ScreeningSpecifications.withEndDateGreaterThanOrEqual(startDate));


		if (endDate != null) {
			spec = spec.and(ScreeningSpecifications.withStartDateLessThanOrEqual(endDate));
		}

		Pageable pageable = PageRequest.of(page, size,
				Sort.by(Sort.Direction.DESC, "startDate"));

		return screeningJpaRepository.findAll(spec, pageable);
	}

	public Page<Screening> getPastScreenings(int page, int size) {

		Specification<Screening> spec = Specification.where(null);

		spec = spec.and(ScreeningSpecifications.withEndDateLessThanOrEqual(LocalDate.now()));

		Pageable pageable = PageRequest.of(page, size,
				Sort.by(Sort.Direction.DESC, "startDate"));

		return screeningJpaRepository.findAll(spec, pageable);
	}

	@Transactional
	public void insert(Screening screening) throws Exception {
		if(screening.getMovie() != null && screening.getRoom() != null) {
			screeningJpaRepository.save(screening);
		} else
			throw new Exception("Movie and room are required!");
		
	}
}