    package com.gox;

    import com.gox.domain.repository.BookingRepository;
    import com.gox.domain.repository.CarRepository;
    import com.gox.domain.repository.LocationRepository;
    import com.gox.domain.repository.UserRepository;
    import com.gox.domain.service.BookingFacade;
    import com.gox.domain.service.BookingFactory;
    import com.gox.domain.service.BookingService;
    import com.gox.domain.service.booking.BookingBusyIntervalProvider;
    import com.gox.domain.service.booking.BookingCompletionHandler;
    import com.gox.domain.service.booking.BookingLoyaltyUpdater;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class BookingBeanConfiguration {
        @Bean
        public BookingFacade bookingFacade(BookingRepository bookingRepository,
                                           CarRepository carRepository,
                                           LocationRepository locationRepository,
                                           BookingBusyIntervalProvider bookingBusyIntervalProvider,
                                           BookingCompletionHandler bookingCompletionHandler,
                                           BookingLoyaltyUpdater bookingLoyaltyUpdater) {
            return new BookingService(bookingRepository,
                    carRepository,
                    locationRepository,
                    bookingBusyIntervalProvider,
                    bookingCompletionHandler,
                    bookingLoyaltyUpdater);
        }
        @Bean
        public BookingFactory bookingFactory(CarRepository carRepository,
                                             LocationRepository locationRepository,
                                             BookingRepository bookingRepository,
                                             BookingFacade bookingFacade) {
            return new BookingFactory(bookingFacade, carRepository, locationRepository, bookingRepository);
        }
        @Bean
        public BookingBusyIntervalProvider bookingBusyIntervalProvider(BookingRepository bookingRepository){
            return new BookingBusyIntervalProvider(bookingRepository);
        }
        @Bean
        public BookingCompletionHandler bookingCompletionHandler(BookingRepository bookingRepository){
            return new BookingCompletionHandler(bookingRepository);
        }
        @Bean
        public BookingLoyaltyUpdater bookingLoyaltyUpdater(BookingRepository bookingRepository, UserRepository userRepository){
            return new BookingLoyaltyUpdater(bookingRepository, userRepository);
        }
    }