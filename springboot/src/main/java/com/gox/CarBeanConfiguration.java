package com.gox;

import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.CarFactory;
import com.gox.domain.service.CarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarBeanConfiguration {

    @Bean
    public CarFacade carFacade(CarRepository carRepository) {
        return new CarService(carRepository);
    }
    @Bean
    public CarFactory carFactory(CarRepository carRepository, LocationRepository locationRepository){
        return new CarFactory(carRepository, locationRepository);
    }
}
