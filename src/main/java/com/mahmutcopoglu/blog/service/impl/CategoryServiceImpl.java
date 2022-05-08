package com.mahmutcopoglu.blog.service.impl;

import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.entity.Category;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.CategoryRepository;
import com.mahmutcopoglu.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CategoryDto save(CategoryDto category) {
        Category categoryDb = modelMapper.map(category, Category.class);
        categoryDb = categoryRepository.save(categoryDb);
        return modelMapper.map(categoryDb, CategoryDto.class);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto category) {
        Category categoryDb = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",id));
        categoryDb.setContent(category.getContent());
        categoryDb.setTitle(category.getTitle());
        categoryRepository.save(categoryDb);
        return modelMapper.map(categoryDb, CategoryDto.class);
    }

    @Override
    public CategoryDto getById(Long id) {
        Category categoryDb = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",id));
        return modelMapper.map(categoryDb, CategoryDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
