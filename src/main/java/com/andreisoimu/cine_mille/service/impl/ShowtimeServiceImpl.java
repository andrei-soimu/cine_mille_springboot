package com.andreisoimu.cine_mille.service.impl;

import com.andreisoimu.cine_mille.model.dao.Room;
import com.andreisoimu.cine_mille.model.dao.Showtime;
import com.andreisoimu.cine_mille.repository.jpa.RoomJpaRepository;
import com.andreisoimu.cine_mille.repository.jpa.ShowtimeJpaRepository;
import com.andreisoimu.cine_mille.service.RoomService;
import com.andreisoimu.cine_mille.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

	@Autowired
	public ShowtimeServiceImpl(ShowtimeJpaRepository showtimeJpaRepository) {
		this.showtimeJpaRepository = showtimeJpaRepository;
	}
	private final ShowtimeJpaRepository showtimeJpaRepository;

	public List<Showtime> getAll() throws UsernameNotFoundException {
		return showtimeJpaRepository.findAll();
	}

	@Transactional
	public void insert(Showtime showtime) throws Exception {
		if(showtime.getStart() != null) {
			showtimeJpaRepository.save(showtime);
		} else
			throw new Exception("Star date is required!");
		
	}
}