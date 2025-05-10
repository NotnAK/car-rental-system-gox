
package com.gox.mapper;

import com.gox.domain.entity.booking.Booking;
import com.gox.rest.dto.BookingCreateRequestDto;
import com.gox.rest.dto.BookingDto;
import com.gox.rest.dto.PatchBookingRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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
    Booking toEntity(BookingCreateRequestDto dto);

    @Mapping(source = "user.id",            target = "userId")
    @Mapping(source = "car.id",             target = "carId")
    @Mapping(source = "pickupLocation.id",  target = "pickupLocationId")
    @Mapping(source = "dropoffLocation.id", target = "dropoffLocationId")
    BookingDto toDto(Booking booking);

    // для PATCH только status
    @Mapping(target = "status", source = "status")
    void patchStatus(PatchBookingRequestDto dto, @MappingTarget Booking booking);
}
