package com.mahmutcopoglu.blog.service.impl;

import com.mahmutcopoglu.blog.dto.RoleDto;
import com.mahmutcopoglu.blog.entity.Role;
import com.mahmutcopoglu.blog.repository.RoleRepository;
import com.mahmutcopoglu.blog.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper){
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public RoleDto create(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        Role roleCreate = roleRepository.save(role);
        return modelMapper.map(roleCreate, RoleDto.class);
    }
}
