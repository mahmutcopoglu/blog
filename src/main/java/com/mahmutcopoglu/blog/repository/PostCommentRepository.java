package com.mahmutcopoglu.blog.repository;

import com.mahmutcopoglu.blog.entity.PostComment;
import com.mahmutcopoglu.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByUser(User user);
}
