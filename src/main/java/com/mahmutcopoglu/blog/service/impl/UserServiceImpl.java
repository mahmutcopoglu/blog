package com.mahmutcopoglu.blog.service.impl;

import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.entity.User;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.UserRepository;
import com.mahmutcopoglu.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto save(UserDto user) {
        User userDb = modelMapper.map(user, User.class);
        userDb = userRepository.save(userDb);
        return modelMapper.map(userDb, UserDto.class);
    }

    @Override
    public UserDto update(Long id, UserDto user) {
        User userDb = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","UserId",id));
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());
        userDb.setPassword(user.getPassword());
        userDb.setUsername(user.getUsername());
        userRepository.save(userDb);
        return modelMapper.map(userDb, UserDto.class);
    }

    @Override
    public UserDto getById(Long id) {
        User userDb = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","UserId",id));
        return modelMapper.map(userDb, UserDto.class);
    }

    @Override
    public Boolean delete(Long id) {
       userRepository.deleteById(id);
       return true;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}
