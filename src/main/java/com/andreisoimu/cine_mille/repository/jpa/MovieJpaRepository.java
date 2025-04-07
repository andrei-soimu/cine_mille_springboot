package com.andreisoimu.cine_mille.repository.jpa;

import com.andreisoimu.cine_mille.model.dao.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<Movie, Long>{

}
