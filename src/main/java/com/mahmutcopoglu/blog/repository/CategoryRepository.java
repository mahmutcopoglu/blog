package com.mahmutcopoglu.blog.repository;

import com.mahmutcopoglu.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
