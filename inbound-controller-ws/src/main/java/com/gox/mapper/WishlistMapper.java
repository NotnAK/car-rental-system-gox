package com.gox.mapper;

import com.gox.domain.entity.wishlist.Wishlist;
import com.gox.rest.dto.WishlistDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WishlistMapper {

    WishlistDto toDto(Wishlist wishlist);

    Wishlist toEntity(WishlistDto dto);
}