package com.mahmutcopoglu.blog.service;


import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.entity.Post;

import java.util.List;


public interface PostService {

    PostDto save(PostDto post);
    PostDto subPostSave(PostDto post, Long postId);
    PostDto update(Long id, PostDto post);
    PostDto getById(Long id);
    Boolean delete(Long id);

    List<PostDto> getAllPost();
    List<PostDto> getPostsByCategory(Long categoryId);
    List<PostDto> getPostsByUser(Long userId);

}
