package com.gox.beans;

import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.ReviewRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.service.ReviewFacade;
import com.gox.domain.service.ReviewFactory;
import com.gox.domain.service.ReviewService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewBeanConfiguration {
    @Bean
    public ReviewFacade reviewFacade(ReviewRepository reviewRepository, UserRepository userRepository){
        return new ReviewService(reviewRepository, userRepository);
    }
    @Bean
    public ReviewFactory reviewFactory(CarRepository carRepository, ReviewRepository reviewRepository){
        return new ReviewFactory(carRepository, reviewRepository);
    }
}
