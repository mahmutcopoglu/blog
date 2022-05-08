package com.mahmutcopoglu.blog.service.impl;


import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.entity.Category;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.entity.User;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.CategoryRepository;
import com.mahmutcopoglu.blog.repository.PostRepository;
import com.mahmutcopoglu.blog.repository.UserRepository;
import com.mahmutcopoglu.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,CategoryRepository categoryRepository,ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDto save(PostDto post, Long userId, Long categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        Post postDb = modelMapper.map(post, Post.class);
        postDb.setUser(user);
        postDb.setCategory(category);
        postDb = postRepository.save(postDb);
        return modelMapper.map(postDb, PostDto.class);
    }

    @Override
    public PostDto subPostSave(PostDto post, Long userId, Long categoryId,Long postId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        Post parentPost = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post",postId));
        Post postDb = modelMapper.map(post, Post.class);
        postDb.setUser(user);
        postDb.setCategory(category);
        postDb.setParent(parentPost);
        postDb = postRepository.save(postDb);
        return modelMapper.map(postDb, PostDto.class);
    }

    @Override
    public PostDto update(Long id, PostDto post) {
        Post postDb = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","PostId",id));
        postDb.setTitle(post.getTitle());
        postDb.setMetaTitle(post.getMetaTitle());
        postDb.setContent(post.getContent());
        postRepository.save(postDb);
        return modelMapper.map(postDb, PostDto.class);
    }

    @Override
    public PostDto getById(Long id) {
        Post postDb = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","PostId",id));
        return modelMapper.map(postDb, PostDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> allPosts = this.postRepository.findAll();
        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;

    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));;
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
