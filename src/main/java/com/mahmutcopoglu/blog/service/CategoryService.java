package com.mahmutcopoglu.blog.service;

import com.mahmutcopoglu.blog.dto.CategoryDto;

public interface CategoryService {

    CategoryDto save(CategoryDto category);
    CategoryDto update(Long id, CategoryDto category);
    CategoryDto getById(Long id);
    Boolean delete(Long id);

}
