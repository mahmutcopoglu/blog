package com.mahmutcopoglu.blog.api;

import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.entity.Post;
import com.mahmutcopoglu.blog.service.impl.PostServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PostCtrl.CTRL)
public class PostController {

    private final PostServiceImpl postServiceImpl;

    public PostController(PostServiceImpl postServiceImpl){
        this.postServiceImpl = postServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Long id) {
        PostDto postDto = postServiceImpl.getById(id);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post){
        return ResponseEntity.ok(postServiceImpl.save(post));
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<PostDto> subPost(@RequestBody PostDto post,
                                              @PathVariable Long postId){
        return ResponseEntity.ok(postServiceImpl.subPostSave(post,postId));
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(this.postServiceImpl.getPostsByUser(userId));
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(this.postServiceImpl.getPostsByCategory(categoryId));
    }

    @GetMapping("/allPosts")
    public ResponseEntity<List<PostDto>> getAllPost(){
        return ResponseEntity.ok(this.postServiceImpl.getAllPost());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long id,@RequestBody PostDto post){
        return ResponseEntity.ok(postServiceImpl.update(id,post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(postServiceImpl.delete(id));
    }


}
