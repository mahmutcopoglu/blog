package com.mahmutcopoglu.blog.api;


import com.mahmutcopoglu.blog.dto.PostCommentDto;
import com.mahmutcopoglu.blog.dto.ServiceResponseData;
import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.enums.ProcessStatus;
import com.mahmutcopoglu.blog.service.impl.UserServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController

public class UserController {

    private final UserServiceImpl userServiceImpl;
    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        UserDto userDto = userServiceImpl.getById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userServiceImpl.getAllUsers());
    }

    @PostMapping("/register")
    public ServiceResponseData createUser(@RequestBody UserDto user){
        UserDto userModel = userServiceImpl.save(user);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(userModel);
        return response;
    }

    @PostMapping("/addRoleUser")
    public ResponseEntity<UserDto> addRoleUser(@RequestBody UserDto user){
        return ResponseEntity.ok(userServiceImpl.addRoleToUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id,@RequestBody UserDto user){
        return ResponseEntity.ok(userServiceImpl.update(id,user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(userServiceImpl.delete(id));
    }


}
