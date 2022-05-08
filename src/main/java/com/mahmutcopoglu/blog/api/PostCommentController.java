package com.mahmutcopoglu.blog.api;


import com.mahmutcopoglu.blog.dto.PostCommentDto;
import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.service.impl.PostCommentServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.PostCommentCtrl.CTRL)
public class PostCommentController {
    private final PostCommentServiceImpl postCommentServiceImpl;
    public PostCommentController(PostCommentServiceImpl postCommentServiceImpl){
        this.postCommentServiceImpl = postCommentServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCommentDto> getById(@PathVariable("id") Long id) {
        PostCommentDto postCommentDto = postCommentServiceImpl.getById(id);
        return ResponseEntity.ok(postCommentDto);
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<PostCommentDto> createPostComment(@RequestBody PostCommentDto postComment,
                                                            @PathVariable Long postId){
        return ResponseEntity.ok(postCommentServiceImpl.save(postComment,postId));
    }

    @PostMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<PostCommentDto> subComment(@RequestBody PostCommentDto postComment,
                                              @PathVariable Long postId,
                                              @PathVariable Long commentId){
        return ResponseEntity.ok(postCommentServiceImpl.subCommentSave(postComment,postId,commentId));
    }



    @PutMapping("/{id}")
    public ResponseEntity<PostCommentDto> updatePostComment(@PathVariable("id") Long id,@RequestBody PostCommentDto postComment){
        return ResponseEntity.ok(postCommentServiceImpl.update(id,postComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePostComment(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(postCommentServiceImpl.delete(id));
    }
}
