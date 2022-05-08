package com.mahmutcopoglu.blog.service;

import com.mahmutcopoglu.blog.dto.TagDto;

public interface TagService {

    TagDto save(TagDto tag, Long postId);
    TagDto update(Long id, TagDto tag);
    TagDto getById(Long id);
    Boolean delete(Long id);

}
