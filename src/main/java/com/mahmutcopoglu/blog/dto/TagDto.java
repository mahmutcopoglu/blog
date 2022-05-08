package com.mahmutcopoglu.blog.dto;

import lombok.Data;

@Data
public class TagDto {

    private Long id;
    private String title;
    private String metaTitle;
    private String slug;
    private String content;

}
