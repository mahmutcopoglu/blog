package com.mahmutcopoglu.blog.service.impl;

import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.entity.Category;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.entity.Role;
import com.mahmutcopoglu.blog.entity.User;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.RoleRepository;
import com.mahmutcopoglu.blog.repository.UserRepository;
import com.mahmutcopoglu.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,ModelMapper modelMapper, PasswordEncoder passwordEncoder ){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto user) {
        User userDb = modelMapper.map(user, User.class);
        userDb.setPassword(passwordEncoder.encode(userDb.getPassword()));
        User saveUser = userRepository.save(userDb);
        return modelMapper.map(saveUser, UserDto.class);
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
    public UserDto addRoleToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        Set<Role> role = new HashSet<>();
        if(!(CollectionUtils.isEmpty(user.getRoles()))){
            user.getRoles().forEach(element -> role.add(roleRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Role","RoleId",element.getId()))));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(role);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
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
