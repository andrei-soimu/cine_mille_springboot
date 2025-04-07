package com.andreisoimu.cine_mille.model.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "t_showtime")
public class Showtime extends DataSuperclass {

	@Column(name="start")
	private LocalTime start;

	public Showtime(LocalTime start) {
		this.start = start;
	}

	public Showtime() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Showtime showtime = (Showtime) o;
		return Objects.equals(start, showtime.start);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(start);
	}

	@Override
	public String toString() {
		return "Showtime{" +
				"start=" + start +
				'}';
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}
}
