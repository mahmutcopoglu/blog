package com.mahmutcopoglu.blog.api;

import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.service.impl.CategoryServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CategoryCtrl.CTRL)
public class CategoryController {
    private final CategoryServiceImpl categoryServiceImpl;
    public CategoryController(CategoryServiceImpl categoryServiceImpl){
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") Long id) {
        CategoryDto categoryDto = categoryServiceImpl.getById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createPostComment(@RequestBody CategoryDto category){
        return ResponseEntity.ok(categoryServiceImpl.save(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updatePostComment(@PathVariable("id") Long id,@RequestBody CategoryDto category){
        return ResponseEntity.ok(categoryServiceImpl.update(id,category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePostComment(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(categoryServiceImpl.delete(id));
    }
}
