package com.gox;

import com.gox.domain.repository.*;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.CarFactory;
import com.gox.domain.service.CarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarBeanConfiguration {

    @Bean
    public CarFacade carFacade(CarRepository carRepository,
                               CarFilterOptionsRepository carFilterOptionsRepository,
                               LocationRepository locationRepository,
                               WishlistRepository wishlistRepository,
                               PhotoRepository photoRepository,
                               BookingRepository bookingRepository,
                               ReviewRepository reviewRepository) {
        return new CarService(carRepository,
                carFilterOptionsRepository,
                locationRepository,
                wishlistRepository,
                photoRepository,
                bookingRepository,
                reviewRepository);
    }
    @Bean
    public CarFactory carFactory(CarRepository carRepository, LocationRepository locationRepository){
        return new CarFactory(carRepository, locationRepository);
    }
}
