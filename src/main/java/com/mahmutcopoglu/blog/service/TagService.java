package com.mahmutcopoglu.blog.service;

import com.mahmutcopoglu.blog.dto.TagDto;

public interface TagService {

    TagDto update(Long id, TagDto tag);
    TagDto getById(Long id);
    Boolean delete(Long id);
    TagDto create(TagDto tagDto);

}
