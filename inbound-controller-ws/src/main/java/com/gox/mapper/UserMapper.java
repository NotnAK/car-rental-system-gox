package com.gox.mapper;

import com.gox.domain.entity.user.User;
import com.gox.rest.dto.UserDto;
import com.gox.rest.dto.UserSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {WishlistMapper.class})
public interface UserMapper {
    User toEntity(UserDto dto);
    UserSummaryDto toSummaryDto(User user);
    UserDto toDto(User user);

}
