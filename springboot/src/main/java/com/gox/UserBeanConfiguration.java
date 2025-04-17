package com.gox;

import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.repository.WishlistRepository;
import com.gox.domain.service.UserFacade;
import com.gox.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfiguration {
    @Bean
    public UserFacade userFacade(UserRepository userRepository) {
        return new UserService(userRepository);
    }

}
