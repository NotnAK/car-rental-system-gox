
package com.gox.mapper;

import com.gox.domain.entity.booking.Booking;
import com.gox.rest.dto.BookingCreateRequestDto;
import com.gox.rest.dto.BookingDto;
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
    @Mapping(target = "actualReturnDate", ignore = true) // при создании new Booking оно ещё пустое
    @Mapping(target = "basePrice",         ignore = true)
    @Mapping(target = "loyaltyDiscount",   ignore = true)
    @Mapping(target = "discountedPrice",   ignore = true)
    Booking toEntity(BookingCreateRequestDto dto);

    @Mapping(source = "user.id",            target = "userId")
    @Mapping(source = "car.id",             target = "carId")
    @Mapping(source = "pickupLocation.id",  target = "pickupLocationId")
    @Mapping(source = "dropoffLocation.id", target = "dropoffLocationId")
    @Mapping(source = "actualReturnDate",   target = "actualReturnDate")
    @Mapping(source = "penalty",           target = "penalty")

    @Mapping(source = "basePrice",          target = "basePrice")
    @Mapping(source = "loyaltyDiscount",    target = "loyaltyDiscount")
    @Mapping(source = "discountedPrice",    target = "discountedPrice")
    BookingDto toDto(Booking booking);

}
