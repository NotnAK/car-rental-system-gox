package com.gox.controller;
import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.user.User;
import com.gox.domain.entity.user.UserRole;
import com.gox.domain.service.BookingFacade;
import com.gox.domain.service.BookingFactory;
import com.gox.domain.service.PhotoFacade;
import com.gox.domain.vo.BookingEstimate;
import com.gox.mapper.BookingEstimateMapper;
import com.gox.mapper.BookingMapper;
import com.gox.mapper.PhotoMapper;
import com.gox.rest.api.BookingsApi;
import com.gox.rest.dto.*;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class BookingRestController implements BookingsApi {

    private final BookingFacade bookingFacade;
    private final BookingFactory bookingFactory;
    private final BookingMapper bookingMapper;
    private final CurrentUserDetailService currentUserService;
    private final BookingEstimateMapper estimateMapper;
    private final PhotoFacade photoFacade;
    private final PhotoMapper photoMapper;
    public BookingRestController(BookingFacade bookingFacade,
                                 BookingFactory bookingFactory,
                                 BookingMapper bookingMapper,
                                 BookingEstimateMapper estimateMapper,
                                 CurrentUserDetailService currentUserService,
                                 PhotoFacade photoFacade,
                                 PhotoMapper photoMapper  ) {
        this.bookingFacade      = bookingFacade;
        this.bookingFactory     = bookingFactory;
        this.bookingMapper             = bookingMapper;
        this.estimateMapper     = estimateMapper;
        this.currentUserService = currentUserService;
        this.photoFacade        = photoFacade;
        this.photoMapper        = photoMapper;
    }

    @Override
    public ResponseEntity<BookingSummaryDto> createBooking(BookingCreateRequestDto dto) {
        Booking b = bookingFactory.create(
                dto.getCarId(),
                dto.getPickupLocationId(),
                dto.getDropoffLocationId(),
                currentUserService.getFullCurrentUser(),
                dto.getStartDate(),
                dto.getEndDate()
        );
        return ResponseEntity.status(201).body(bookingMapper.toSummaryDto(b));
    }

    @Override
    public ResponseEntity<List<BookingSummaryDto>> getAllBookings() {
        List<BookingSummaryDto> list = bookingFacade.getAll().stream()
                .map(bookingMapper::toSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<BookingDetailDto> getBookingById(Long bookingId) {
        Booking b = bookingFacade.get(bookingId);
        User current = currentUserService.getFullCurrentUser();
        if (!current.getRole().equals(UserRole.ADMIN)
                && !b.getUser().getId().equals(current.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        BookingDetailDto detail = bookingMapper.toDetailDto(b);
        if(b.getCar()!=null) {
            var prev = photoFacade.getPreviewForCar(b.getCar().getId());
            detail.getCar().setPreview(photoMapper.toDto(prev));
        }
        return ResponseEntity.ok(detail);
    }


    @Override
    public ResponseEntity<BookingEstimateDto> estimateBooking(
            BookingCreateRequestDto dto) {
        User user = currentUserService.getOptionalFullUser().orElse(null);
        BookingEstimate vo = bookingFacade.estimate(
                dto.getCarId(),
                dto.getPickupLocationId(),
                dto.getDropoffLocationId(),
                user,
                dto.getStartDate(),
                dto.getEndDate()
        );
        BookingEstimateDto estimateDto = estimateMapper.toDto(vo);
        return ResponseEntity.ok(estimateDto);
    }
    @Override
    public ResponseEntity<Void> approveBooking(Integer bookingId) {
        bookingFacade.changeStatus(
                bookingId.longValue(),
                BookingStatus.APPROVED
        );
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelBooking(Integer bookingId) {
        Long id = bookingId.longValue();
        Booking b = bookingFacade.get(id);
        User current = currentUserService.getFullCurrentUser();
        if (!current.getRole().equals(UserRole.ADMIN)
                && !b.getUser().getId().equals(current.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        bookingFacade.changeStatus(id, BookingStatus.CANCELLED);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<BookingSummaryDto> completeBooking(
            Long bookingId,
            CompleteBookingRequestDto dto) {
        Booking b = bookingFacade.completeBooking(
                bookingId,
                dto.getActualReturnDate()
        );
        return ResponseEntity.ok(bookingMapper.toSummaryDto(b));
    }

    @Override
    public ResponseEntity<Void> deleteBooking(Long bookingId) {
        bookingFacade.delete(bookingId);
        return ResponseEntity.noContent().build();
    }
}