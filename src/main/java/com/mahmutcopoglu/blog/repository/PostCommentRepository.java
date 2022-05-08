package com.mahmutcopoglu.blog.repository;

import com.mahmutcopoglu.blog.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {


}
