package com.gox;

import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.repository.PhotoRepository;
import com.gox.domain.service.LocationFacade;
import com.gox.domain.service.LocationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationBeanConfiguration {
    @Bean
    public LocationFacade locationFacade(LocationRepository locationRepository,
                                         PhotoRepository photoRepository,
                                         CarRepository carRepository,
                                         BookingRepository bookingRepository){
        return new LocationService(locationRepository,photoRepository, carRepository,bookingRepository);
    }
}
