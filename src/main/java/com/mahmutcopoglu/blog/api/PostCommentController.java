package com.mahmutcopoglu.blog.api;


import com.mahmutcopoglu.blog.dto.CategoryDto;
import com.mahmutcopoglu.blog.dto.PostCommentDto;
import com.mahmutcopoglu.blog.dto.PostDto;
import com.mahmutcopoglu.blog.dto.ServiceResponseData;
import com.mahmutcopoglu.blog.enums.ProcessStatus;
import com.mahmutcopoglu.blog.service.impl.PostCommentServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PostCommentCtrl.CTRL)
public class PostCommentController {
    private final PostCommentServiceImpl postCommentServiceImpl;
    public PostCommentController(PostCommentServiceImpl postCommentServiceImpl){
        this.postCommentServiceImpl = postCommentServiceImpl;
    }

    @GetMapping("/{id}")
    public ServiceResponseData getById(@PathVariable("id") Long id) {
        PostCommentDto postCommentDto = postCommentServiceImpl.getById(id);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(postCommentDto);
        return response;
    }

    @PostMapping("/post/{postId}/comments")
    public ServiceResponseData createPostComment(@RequestBody PostCommentDto postComment,
                                                            @PathVariable Long postId){
        PostCommentDto postCommentDto = postCommentServiceImpl.save(postComment,postId);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(postCommentDto);
        return response;
    }

    @PostMapping("/post/{postId}/comments/{commentId}")
    public ServiceResponseData subComment(@RequestBody PostCommentDto postComment,
                                              @PathVariable Long postId,
                                              @PathVariable Long commentId){
        PostCommentDto postCommentDto = postCommentServiceImpl.subCommentSave(postComment,postId,commentId);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(postCommentDto);
        return response;
    }

    @PutMapping("/{id}")
    public ServiceResponseData updatePostComment(@PathVariable("id") Long id,@RequestBody PostCommentDto postComment){
        PostCommentDto postCommentDto = postCommentServiceImpl.update(id,postComment);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        response.setData(postCommentDto);
        return response;
    }

    @DeleteMapping("/{id}")
    public ServiceResponseData deletePostComment(@PathVariable(value = "id", required = true) Long id) {
        postCommentServiceImpl.delete(id);
        var response = new ServiceResponseData();
        response.setStatus(ProcessStatus.SUCCESS);
        return response;
    }

    @GetMapping("/allPostComments")
    public ResponseEntity<List<PostCommentDto>> getAllPostComment(){
        return ResponseEntity.ok(this.postCommentServiceImpl.getAllPostComments());
    }

    @GetMapping("/user/{userId}/postComments")
    public ResponseEntity<List<PostCommentDto>> getPostCommentsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(this.postCommentServiceImpl.getPostCommentsByUser(userId));
    }
}
