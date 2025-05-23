package com.gox.jpa.repository;

import com.gox.domain.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarSpringDataRepository
        extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
