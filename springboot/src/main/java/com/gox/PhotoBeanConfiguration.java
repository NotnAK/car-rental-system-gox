package com.gox;

import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.PhotoRepository;
import com.gox.domain.service.PhotoFacade;
import com.gox.domain.service.PhotoFactory;
import com.gox.domain.service.PhotoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhotoBeanConfiguration {
    @Bean
    public PhotoFacade photoFacade(
            PhotoRepository photoRepository) {
        return new PhotoService(photoRepository);
    }

    @Bean
    public PhotoFactory photoFactory(PhotoRepository photoRepository, CarRepository carRepository){
        return new PhotoFactory(photoRepository, carRepository);
    }
}
