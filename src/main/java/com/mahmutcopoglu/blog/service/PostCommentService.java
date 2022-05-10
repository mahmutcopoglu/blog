package com.mahmutcopoglu.blog.service;

import com.mahmutcopoglu.blog.dto.PostCommentDto;
import com.mahmutcopoglu.blog.dto.PostDto;

import java.util.List;

public interface PostCommentService {

    PostCommentDto save(PostCommentDto postComment, Long postId);
    PostCommentDto subCommentSave(PostCommentDto postComment, Long postId, Long commentId);
    PostCommentDto update(Long id, PostCommentDto postComment);
    PostCommentDto getById(Long id);
    Boolean delete(Long id);

    List<PostCommentDto> getAllPostComments();
    List<PostCommentDto> getPostCommentsByUser(Long userId);
}
