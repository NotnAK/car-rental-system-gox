package com.gox.controller;
import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.service.BookingFacade;
import com.gox.domain.service.BookingFactory;
import com.gox.mapper.BookingMapper;
import com.gox.rest.api.BookingsApi;
import com.gox.rest.dto.BookingCreateRequestDto;
import com.gox.rest.dto.BookingDto;
import com.gox.rest.dto.PatchBookingRequestDto;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class BookingRestController implements BookingsApi {

    private final BookingFacade bookingFacade;
    private final BookingFactory bookingFactory;
    private final BookingMapper mapper;
    private final CurrentUserDetailService currentUserService;

    public BookingRestController(BookingFacade bookingFacade,
                                 BookingFactory bookingFactory,
                                 BookingMapper mapper,
                                 CurrentUserDetailService currentUserService) {
        this.bookingFacade      = bookingFacade;
        this.bookingFactory     = bookingFactory;
        this.mapper             = mapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public ResponseEntity<BookingDto> createBooking(BookingCreateRequestDto dto) {
        Booking b = bookingFactory.createBooking(
                dto.getCarId(),
                dto.getPickupLocationId(),
                dto.getDropoffLocationId(),
                currentUserService.getFullCurrentUser(),
                dto.getStartDate(),
                dto.getEndDate()
        );
        return ResponseEntity.status(201).body(mapper.toDto(b));
    }

    @Override
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> list = bookingFacade.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<BookingDto> getBookingById(Long bookingId) {
        Booking b = bookingFacade.get(bookingId);  // BookingNotFoundException → 404
        return ResponseEntity.ok(mapper.toDto(b));
    }

    @Override
    public ResponseEntity<Void> patchBooking(Long bookingId, PatchBookingRequestDto dto) {
        bookingFacade.changeStatus(bookingId, BookingStatus.valueOf(dto.getStatus().name()));
        return ResponseEntity.ok().build();
    }
}