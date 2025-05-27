package com.gox.controller;
import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.BookingFacade;
import com.gox.domain.service.BookingFactory;
import com.gox.domain.vo.BookingEstimate;
import com.gox.mapper.BookingEstimateMapper;
import com.gox.mapper.BookingMapper;
import com.gox.rest.api.BookingsApi;
import com.gox.rest.dto.BookingCreateRequestDto;
import com.gox.rest.dto.BookingDto;
import com.gox.rest.dto.BookingEstimateDto;
import com.gox.rest.dto.CompleteBookingRequestDto;
import com.gox.security.CurrentUserDetailService;
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
    public BookingRestController(BookingFacade bookingFacade,
                                 BookingFactory bookingFactory,
                                 BookingMapper bookingMapper,
                                 BookingEstimateMapper estimateMapper,
                                 CurrentUserDetailService currentUserService) {
        this.bookingFacade      = bookingFacade;
        this.bookingFactory     = bookingFactory;
        this.bookingMapper             = bookingMapper;
        this.estimateMapper     = estimateMapper;
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
        return ResponseEntity.status(201).body(bookingMapper.toDto(b));
    }

    @Override
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> list = bookingFacade.getAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<BookingDto> getBookingById(Long bookingId) {
        Booking b = bookingFacade.get(bookingId);  // BookingNotFoundException → 404
        return ResponseEntity.ok(bookingMapper.toDto(b));
    }


    @Override
    public ResponseEntity<BookingEstimateDto> estimateBooking(
            BookingCreateRequestDto dto) {
        User user = currentUserService.getOptionalFullUser().orElse(null);
        // 1) вызываем фасад, он возвращает VO
        BookingEstimate vo = bookingFacade.estimate(
                dto.getCarId(),
                dto.getPickupLocationId(),
                dto.getDropoffLocationId(),
                user,
                dto.getStartDate(),
                dto.getEndDate()
        );

        // 2) мапим VO → REST-DTO
        BookingEstimateDto estimateDto = estimateMapper.toDto(vo);

        return ResponseEntity.ok(estimateDto);
    }
    @Override
    public ResponseEntity<Void> approveBooking(Integer bookingId) {
        // Преобразуем в Long, устанавливаем статус APPROVED
        bookingFacade.changeStatus(
                bookingId.longValue(),
                BookingStatus.APPROVED
        );
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelBooking(Integer bookingId) {
        // То же для статуса CANCELLED
        bookingFacade.changeStatus(
                bookingId.longValue(),
                BookingStatus.CANCELLED
        );
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<BookingDto> completeBooking(
            Long bookingId,
            CompleteBookingRequestDto dto) {

        Booking b = bookingFacade.completeBooking(
                bookingId,
                dto.getActualReturnDate()
        );
        return ResponseEntity.ok(bookingMapper.toDto(b));
    }
}