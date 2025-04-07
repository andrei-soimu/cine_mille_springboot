package com.andreisoimu.cine_mille.model.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "t_movie")
public class Movie extends DataSuperclass {

	@Column(name="name")
	private String name;

	public Movie(String name) {
		this.name = name;
	}

	public Movie() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Movie movie = (Movie) o;
		return Objects.equals(name, movie.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return "Movie{" +
				"name='" + name + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
