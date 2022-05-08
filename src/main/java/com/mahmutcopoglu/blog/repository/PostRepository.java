package com.mahmutcopoglu.blog.repository;

import com.mahmutcopoglu.blog.entity.Category;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

}
