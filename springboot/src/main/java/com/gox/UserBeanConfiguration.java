package com.gox;

import com.gox.domain.repository.*;
import com.gox.domain.service.UserFacade;
import com.gox.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfiguration {
    @Bean
    public UserFacade userFacade(UserRepository userRepository, ReviewRepository reviewRepository,
                                 BookingRepository bookingRepository) {
        return new UserService(userRepository, reviewRepository, bookingRepository);
    }

}
