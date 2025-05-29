package com.gox.jpa.repository;

import com.gox.domain.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface CarSpringDataRepository
        extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    @Query("select distinct c.brand from Car c order by c.brand")
    List<String> findDistinctBrands();

    @Query("select distinct c.year from Car c order by c.year")
    List<Integer> findDistinctYears();

    @Query("select min(c.pricePerDay) from Car c")
    BigDecimal findMinPrice();

    @Query("select max(c.pricePerDay) from Car c")
    BigDecimal findMaxPrice();

    @Query("select distinct c.seats from Car c order by c.seats")
    List<Integer> findDistinctSeats();
    @Transactional
    @Modifying
    @Query("UPDATE Car c SET c.location = NULL WHERE c.location.id = :locId")
    void nullifyLocation(@Param("locId") Long locId);
}
