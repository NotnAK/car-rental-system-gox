package com.gox.beans;

import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.repository.WishlistRepository;
import com.gox.domain.service.WishlistFacade;
import com.gox.domain.service.WishlistService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishListBeanConfiguration {
    @Bean
    public WishlistFacade wishlistFacade(WishlistRepository wishlistRepository,
                                         UserRepository userRepository,
                                         CarRepository carRepository){
        return new WishlistService(wishlistRepository, userRepository, carRepository);
    }
}
