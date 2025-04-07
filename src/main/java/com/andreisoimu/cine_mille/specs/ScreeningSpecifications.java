package com.andreisoimu.cine_mille.specs;

import com.andreisoimu.cine_mille.model.dao.Screening;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ScreeningSpecifications {

    public static Specification<Screening> withStartDateLessThanOrEqual(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDate);
        };
    }

    public static Specification<Screening> withStartDateGreaterThanOrEqual(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
        };
    }

    public static Specification<Screening> withEndDateGreaterThanOrEqual(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDate);
        };
    }

    public static Specification<Screening> withEndDateLessThanOrEqual(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
        };
    }

}
