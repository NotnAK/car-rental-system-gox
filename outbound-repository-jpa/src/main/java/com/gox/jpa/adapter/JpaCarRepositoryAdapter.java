package com.gox.jpa.adapter;

import com.gox.domain.entity.car.Car;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.vo.CarFilter;
import com.gox.jpa.repository.CarSpringDataRepository;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Car> findByFilter(CarFilter f) {
        Specification<Car> spec = Specification.where(null);

        if (f.getBrand()    != null) spec = spec.and((r, q, cb) ->
                cb.equal(r.get("brand"), f.getBrand()));
        if (f.getYear()     != null) spec = spec.and((r, q, cb) ->
                cb.equal(r.get("year"), f.getYear()));
        if (f.getPriceMin() != null) spec = spec.and((r, q, cb) ->
                cb.ge(r.get("pricePerDay"), f.getPriceMin()));
        if (f.getPriceMax() != null) spec = spec.and((r, q, cb) ->
                cb.le(r.get("pricePerDay"), f.getPriceMax()));
        if (f.getCategory() != null) spec = spec.and((r, q, cb) ->
                cb.equal(r.get("category"), f.getCategory()));
        if (f.getSeats()    != null) spec = spec.and((r, q, cb) ->
                cb.equal(r.get("seats"), f.getSeats()));
        if (f.getTransmission() != null) spec = spec.and((r, q, cb) ->
                cb.equal(r.get("transmission"), f.getTransmission()));
        if (f.getFuelType()     != null) spec = spec.and((r, q, cb) ->
                cb.equal(r.get("fuelType"), f.getFuelType()));

        return carSpringDataRepository.findAll(spec);
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
