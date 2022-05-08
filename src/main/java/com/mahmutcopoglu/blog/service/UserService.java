package com.mahmutcopoglu.blog.service;

import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.entity.User;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);
    UserDto update(Long id, UserDto user);
    UserDto getById(Long id);
    Boolean delete(Long id);
    List<UserDto> getAllUsers();
}
