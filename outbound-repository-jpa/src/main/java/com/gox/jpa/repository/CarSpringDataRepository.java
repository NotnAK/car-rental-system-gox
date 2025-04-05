package com.gox.jpa.repository;

import com.gox.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSpringDataRepository extends JpaRepository<Car, Long> {
    // Дополнительные методы, если необходимы
}
