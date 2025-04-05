package com.gox.jpa.adapter;

import com.gox.domain.entity.Car;
import com.gox.domain.repository.CarRepository;
import com.gox.jpa.repository.CarSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaCarRepositoryAdapter implements CarRepository {

    private final CarSpringDataRepository carSpringDataRepository;

    public JpaCarRepositoryAdapter(CarSpringDataRepository carSpringDataRepository) {
        this.carSpringDataRepository = carSpringDataRepository;
    }

    @Override
    public Car read(Long id) {
        return carSpringDataRepository.findById(id).orElse(null);
    }

    @Override
    public List<Car> findAll() {
        return carSpringDataRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        return carSpringDataRepository.save(car);
    }

    @Override
    public void delete(Long id) {
        carSpringDataRepository.deleteById(id);
    }
}
