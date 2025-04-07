package com.andreisoimu.cine_mille.model.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_room")
public class Room extends DataSuperclass {

	@Column(name="name")
	private String name;

	@Column
	private Boolean enabled;

	public Room(String name, Boolean enabled) {
		this.name = name;
		this.enabled = enabled;
	}

	public Room() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Room room = (Room) o;
		return Objects.equals(name, room.name) && Objects.equals(enabled, room.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, enabled);
	}

	@Override
	public String toString() {
		return "Room{" +
				"name='" + name + '\'' +
				", enabled=" + enabled +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
