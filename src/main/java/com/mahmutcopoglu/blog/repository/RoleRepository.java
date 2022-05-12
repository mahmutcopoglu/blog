package com.mahmutcopoglu.blog.repository;

import com.mahmutcopoglu.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
