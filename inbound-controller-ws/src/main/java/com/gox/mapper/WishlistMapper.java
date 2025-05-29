package com.gox.mapper;

import com.gox.domain.entity.wishlist.Wishlist;
import com.gox.rest.dto.WishlistDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {CarMapper.class})
public interface WishlistMapper {
    Wishlist toEntity(WishlistDto dto);
    WishlistDto toDto(Wishlist entity);
}
