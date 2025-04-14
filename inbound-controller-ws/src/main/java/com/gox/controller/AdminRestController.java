package com.gox.controller;

import com.gox.domain.entity.user.User;
import com.gox.domain.service.UserFacade;
import com.gox.mapper.UserMapper;
import com.gox.rest.api.AdminApi;
import com.gox.rest.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class AdminRestController implements AdminApi {
    private final UserFacade userFacade;
    private final UserMapper userMapper;

    public AdminRestController(UserFacade userFacade, UserMapper userMapper) {
        this.userFacade = userFacade;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userFacade.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
}
