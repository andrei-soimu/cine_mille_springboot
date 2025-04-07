package com.andreisoimu.cine_mille.model.dao;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_screening")
public class Screening extends DataSuperclass {

	@JoinColumn(name="id_movie")
	@ManyToOne
	private Movie movie;

	@JoinColumn(name="id_room")
	@ManyToOne
	private Room room;

	@ManyToMany
	@JoinTable(
			name = "t_screening_showtime",
			joinColumns = @JoinColumn(name = "id_screening"),
			inverseJoinColumns = @JoinColumn(name = "id_showtime")
	)
	private List<Showtime> showtimes;

	@Column(name="startDate")
	private LocalDate startDate;

	@Column(name="endDate")
	private LocalDate endDate;

	public Screening(Movie movie, Room room, List<Showtime> showtimes, LocalDate startDate, LocalDate endDate) {
		this.movie = movie;
		this.room = room;
		this.showtimes = showtimes;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Screening() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Screening screening = (Screening) o;
		return Objects.equals(movie, screening.movie) && Objects.equals(room, screening.room) && Objects.equals(showtimes, screening.showtimes) && Objects.equals(startDate, screening.startDate) && Objects.equals(endDate, screening.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(movie, room, showtimes, startDate, endDate);
	}

	@Override
	public String toString() {
		return "Screening{" +
				"movie=" + movie +
				", room=" + room +
				", showtimes=" + showtimes +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Showtime> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(List<Showtime> showtimes) {
		this.showtimes = showtimes;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
}
