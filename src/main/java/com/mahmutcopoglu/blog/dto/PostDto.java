package com.mahmutcopoglu.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mahmutcopoglu.blog.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class PostDto {

    private Long id;
    private String title;
    private String metaTitle;
    private String slug;
    private Boolean published;
    private String content;
    private CategoryDto category;
    private UserDto user;
    private Set<PostCommentDto> postComments = new HashSet<>();
    private Set<TagDto> tags = new HashSet<>();
    @JsonIgnoreProperties("children")
    private PostDto parent;
    @JsonIgnoreProperties("parent")
    private Set<PostDto> children;



}
