package com.andreisoimu.cine_mille.service;

import com.andreisoimu.cine_mille.model.dao.Room;

import java.util.List;

public interface RoomService {

	/**
	 * Get all rooms
	 * @return List<Room>
	 */
	public List<Room> getAll();

	/**
	 * Insert room
	 * @param room
	 */
    public void insert(Room room) throws Exception;

}
