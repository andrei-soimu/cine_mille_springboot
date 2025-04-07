package com.andreisoimu.cine_mille.service.impl;

import com.andreisoimu.cine_mille.model.dao.Room;
import com.andreisoimu.cine_mille.repository.jpa.RoomJpaRepository;
import com.andreisoimu.cine_mille.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	public RoomServiceImpl(RoomJpaRepository roomJpaRepository) {
		this.roomJpaRepository = roomJpaRepository;
	}
	private final RoomJpaRepository roomJpaRepository;

	public List<Room> getAll() throws UsernameNotFoundException {
		return roomJpaRepository.findAll();
	}

	@Transactional
	public void insert(Room room) throws Exception {
		if(!room.getName().isEmpty()) {
			roomJpaRepository.save(room);
		} else
			throw new Exception("Name is required!");
		
	}
}