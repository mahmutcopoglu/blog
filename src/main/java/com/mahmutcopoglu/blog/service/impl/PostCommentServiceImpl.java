package com.mahmutcopoglu.blog.service.impl;

import com.mahmutcopoglu.blog.dto.PostCommentDto;
import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.entity.Category;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.entity.PostComment;
import com.mahmutcopoglu.blog.entity.User;
import com.mahmutcopoglu.blog.exceptions.ResourceNotFoundException;
import com.mahmutcopoglu.blog.repository.PostCommentRepository;
import com.mahmutcopoglu.blog.repository.PostRepository;
import com.mahmutcopoglu.blog.service.PostCommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostRepository postRepository, ModelMapper modelMapper){
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostCommentDto save(PostCommentDto postComment, Long postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        PostComment postCommentDb = modelMapper.map(postComment, PostComment.class);
        postCommentDb.setPost(post);
        PostComment savedComment = postCommentRepository.save(postCommentDb);
        return modelMapper.map(savedComment, PostCommentDto.class);
    }

    @Override
    public PostCommentDto subCommentSave(PostCommentDto postComment, Long postId, Long commentId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        PostComment parentComment = this.postCommentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("PostComment","PostCommentId",commentId));
        PostComment postCommentDb = modelMapper.map(postComment, PostComment.class);
        postCommentDb.setPost(post);
        postCommentDb.setParent(parentComment);
        PostComment savedParentComment = postCommentRepository.save(postCommentDb);
        return modelMapper.map(savedParentComment, PostCommentDto.class);
    }

    @Override
    public PostCommentDto update(Long id, PostCommentDto postComment) {
        PostComment postCommentDb = postCommentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("PostComment","PostCommentId",id));
        postCommentDb.setContent(postComment.getContent());
        postCommentDb.setTitle(postComment.getTitle());
        postCommentRepository.save(postCommentDb);
        return modelMapper.map(postCommentDb, PostCommentDto.class);
    }

    @Override
    public PostCommentDto getById(Long id) {
        PostComment postCommentDb = postCommentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("PostComment","PostCommentId",id));;
        return modelMapper.map(postCommentDb, PostCommentDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        postCommentRepository.deleteById(id);
        return true;
    }
}
