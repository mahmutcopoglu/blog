package com.mahmutcopoglu.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class PostCommentDto {

    private Long id;
    private String title;
    private Boolean published;
    private String content;
    @JsonIgnoreProperties("children")
    private PostCommentDto parent;
    @JsonIgnoreProperties("parent")
    private Set<PostCommentDto> children;

}
