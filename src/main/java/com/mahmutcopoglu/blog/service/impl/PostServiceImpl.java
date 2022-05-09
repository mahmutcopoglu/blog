package com.mahmutcopoglu.blog.service.impl;


import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.dto.UserDto;
import com.mahmutcopoglu.blog.entity.Category;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.entity.Tag;
import com.mahmutcopoglu.blog.entity.User;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.CategoryRepository;
import com.mahmutcopoglu.blog.repository.PostRepository;
import com.mahmutcopoglu.blog.repository.TagRepository;
import com.mahmutcopoglu.blog.repository.UserRepository;
import com.mahmutcopoglu.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,CategoryRepository categoryRepository,TagRepository tagRepository,ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDto save(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        User user = null;
        if(Objects.nonNull(postDto.getUser()))
        {
            user = userRepository.findById(postDto.getUser().getId()).orElseThrow(()->new ResourceNotFoundException("User","UserId",postDto.getUser().getId()));
        }
        post.setUser(user);
        Set<Category> category = new HashSet<>();
        if(!(CollectionUtils.isEmpty(postDto.getCategory()))){
            postDto.getCategory().forEach(element -> category.add(categoryRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",element.getId()))));
        }
        post.setCategory(category);
        Set<Tag> tag = new HashSet<>();
        if(!(CollectionUtils.isEmpty(postDto.getTags()))){
            postDto.getTags().forEach(element -> tag.add(tagRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Tag","TagId",element.getId()))));
        }
        post.setTags(tag);
        Post savePost = postRepository.save(post);
        return modelMapper.map(savePost, PostDto.class);
    }

    @Override
    public PostDto subPostSave(PostDto postDto,Long postId) {
        Post post = modelMapper.map(postDto, Post.class);
        User user = null;
        if(Objects.nonNull(postDto.getUser()))
        {
            user = userRepository.findById(postDto.getUser().getId()).orElseThrow(()->new ResourceNotFoundException("User","UserId",postDto.getUser().getId()));
        }
        post.setUser(user);
        Set<Category> category = new HashSet<>();
        if(!(CollectionUtils.isEmpty(postDto.getCategory()))){
            postDto.getCategory().forEach(element -> category.add(categoryRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",element.getId()))));
        }
        post.setCategory(category);
        Set<Tag> tag = new HashSet<>();
        if(!(CollectionUtils.isEmpty(postDto.getTags()))){
            postDto.getTags().forEach(element -> tag.add(tagRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Tag","TagId",element.getId()))));
        }
        post.setTags(tag);
        Post parentPost = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post",postId));;
        post.setParent(parentPost);
        Post parentPostSave = postRepository.save(post);
        return modelMapper.map(parentPostSave, PostDto.class);
    }

    @Override
    public PostDto update(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","PostId",id));
        post.setTitle(postDto.getTitle());
        post.setMetaTitle(postDto.getMetaTitle());
        post.setContent(postDto.getContent());
        Set<Category> category = new HashSet<>();
        if(!(CollectionUtils.isEmpty(postDto.getCategory()))){
            postDto.getCategory().forEach(element -> category.add(categoryRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",element.getId()))));
        }
        post.setCategory(category);
        Set<Tag> tag = new HashSet<>();
        if(!(CollectionUtils.isEmpty(postDto.getTags()))){
            postDto.getTags().forEach(element -> tag.add(tagRepository.findById(element.getId()).orElseThrow(()->new ResourceNotFoundException("Tag","TagId",element.getId()))));
        }
        post.setTags(tag);
        Post updatedPostSave = postRepository.save(post);
        return modelMapper.map(updatedPostSave, PostDto.class);
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
