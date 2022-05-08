package com.mahmutcopoglu.blog.service.impl;

import com.mahmutcopoglu.blog.dto.TagDto;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.entity.Tag;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.PostRepository;
import com.mahmutcopoglu.blog.repository.TagRepository;
import com.mahmutcopoglu.blog.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, PostRepository postRepository, ModelMapper modelMapper){
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public TagDto save(TagDto tag, Long postId) {
        Post post =this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        Tag tagDb = modelMapper.map(tag, Tag.class);
        tagDb.setPost(post);
        tagDb = tagRepository.save(tagDb);
        return modelMapper.map(tagDb, TagDto.class);
    }

    @Override
    public TagDto update(Long id, TagDto tag) {
        Tag tagDb = tagRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Tag","TagId",id));
        tagDb.setTitle(tag.getTitle());
        tagDb.setContent(tag.getTitle());
        tagRepository.save(tagDb);
        return modelMapper.map(tagDb, TagDto.class);
    }

    @Override
    public TagDto getById(Long id) {
        Tag tagDb = tagRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Tag","TagId",id));;
        return modelMapper.map(tagDb, TagDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        tagRepository.deleteById(id);
        return true;
    }

}
