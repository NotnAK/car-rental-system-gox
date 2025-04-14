package com.gox.jpa.repository;

import com.gox.domain.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSpringDataRepository extends JpaRepository<Car, Long> {
    // Дополнительные методы, если необходимы
}
