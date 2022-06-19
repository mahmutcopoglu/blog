package com.mahmutcopoglu.blog.api;

import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.dto.ServiceResponseData;
import com.mahmutcopoglu.blog.dto.TagDto;
import com.mahmutcopoglu.blog.enums.ProcessStatus;
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
    public ServiceResponseData getById(@PathVariable("id") Long id) {
        CategoryDto categoryDto = categoryServiceImpl.getById(id);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(categoryDto);
        return response;
    }

    @PutMapping("/{id}")
    public ServiceResponseData updatePostComment(@PathVariable("id") Long id,@RequestBody CategoryDto category){
        CategoryDto categoryModel = categoryServiceImpl.update(id,category);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(categoryModel);
        return response;
    }

    @DeleteMapping("/{id}")
    public ServiceResponseData deletePostComment(@PathVariable(value = "id", required = true) Long id) {
        categoryServiceImpl.delete(id);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        return response;
    }

    @PostMapping("/create")
    public ServiceResponseData create(@RequestBody CategoryDto categoryDto){
        CategoryDto categoryModel = categoryServiceImpl.create(categoryDto);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(categoryModel);
        return response;
    }
}
