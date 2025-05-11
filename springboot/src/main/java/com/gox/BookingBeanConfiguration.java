package com.gox;

import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.LocationRepository;
import com.gox.domain.service.BookingFacade;
import com.gox.domain.service.BookingFactory;
import com.gox.domain.service.BookingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingBeanConfiguration {
    @Bean
    public BookingFacade bookingFacade(BookingRepository repo, CarRepository carRepo, LocationRepository locRepo) {
        return new BookingService(repo, carRepo, locRepo);
    }
    @Bean
    public BookingFactory bookingFactory(CarRepository carRepo,
                                         LocationRepository locRepo,
                                         BookingRepository bookingRepo) {
        return new BookingFactory(carRepo, locRepo, bookingRepo);
    }
}