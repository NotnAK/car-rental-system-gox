
package com.gox.mapper;

import com.gox.domain.entity.booking.Booking;
import com.gox.rest.dto.BookingCreateRequestDto;
import com.gox.rest.dto.BookingDetailDto;
import com.gox.rest.dto.BookingSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "transferFee", ignore = true)
    @Mapping(target = "urgent", ignore = true)
    @Mapping(target = "penalty", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "actualReturnDate", ignore = true)
    @Mapping(target = "basePrice",         ignore = true)
    @Mapping(target = "loyaltyDiscount",   ignore = true)
    @Mapping(target = "discountedPrice",   ignore = true)
    Booking toEntity(BookingCreateRequestDto dto);


    @Mapping(source = "car.brand",  target = "carBrand")
    @Mapping(source = "car.model",  target = "carModel")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "car.id", target = "carId")
    @Mapping(source = "user.id", target = "userId")
    BookingSummaryDto toSummaryDto(Booking booking);

    @Mapping(source = "user",           target = "user")
    @Mapping(source = "car",            target = "car")
    @Mapping(source = "pickupLocation", target = "pickupLocation")
    @Mapping(source = "dropoffLocation",target = "dropoffLocation")
    BookingDetailDto toDetailDto(Booking booking);
}
