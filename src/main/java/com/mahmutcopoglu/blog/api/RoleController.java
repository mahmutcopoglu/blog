package com.mahmutcopoglu.blog.api;

import com.mahmutcopoglu.blog.dto.RoleDto;
import com.mahmutcopoglu.blog.service.impl.RoleServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.RoleCtrl.CTRL)
public class RoleController {

    private final RoleServiceImpl roleServiceImpl;
    public RoleController(RoleServiceImpl roleServiceImpl)
    {
        this.roleServiceImpl = roleServiceImpl;
    }

    @PostMapping("create")
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok(roleServiceImpl.create(roleDto));
    }
}
