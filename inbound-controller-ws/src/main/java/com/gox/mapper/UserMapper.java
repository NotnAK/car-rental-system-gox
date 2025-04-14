package com.gox.mapper;

import com.gox.domain.entity.user.User;
import com.gox.rest.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {WishlistMapper.class})
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);

}
