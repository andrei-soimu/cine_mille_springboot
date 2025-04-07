package com.andreisoimu.cine_mille.repository.jpa;

import com.andreisoimu.cine_mille.model.dao.Movie;
import com.andreisoimu.cine_mille.model.dao.Screening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ScreeningJpaRepository extends JpaRepository<Screening, Long>, JpaSpecificationExecutor<Screening> {

}
