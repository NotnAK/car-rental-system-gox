package com.gox;

import com.gox.domain.repository.LocationRepository;
import com.gox.domain.service.LocationFacade;
import com.gox.domain.service.LocationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationBeanConfiguration {
    @Bean
    public LocationFacade locationFacade(LocationRepository locationRepository){
        return new LocationService(locationRepository);
    }
}
