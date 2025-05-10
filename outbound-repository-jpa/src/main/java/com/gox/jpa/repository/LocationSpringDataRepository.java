package com.gox.jpa.repository;


import com.gox.domain.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationSpringDataRepository extends JpaRepository<Location, Long> {
}