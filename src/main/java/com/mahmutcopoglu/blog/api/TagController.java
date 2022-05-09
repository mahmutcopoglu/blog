package com.mahmutcopoglu.blog.api;

import com.mahmutcopoglu.blog.dto.TagDto;
import com.mahmutcopoglu.blog.service.impl.TagServiceImpl;
import com.mahmutcopoglu.blog.util.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.TagCtrl.CTRL)
public class TagController {

    private final TagServiceImpl tagServiceImpl;
    public TagController(TagServiceImpl tagServiceImpl){
        this.tagServiceImpl = tagServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getById(@PathVariable("id") Long id) {
        TagDto tagDto = tagServiceImpl.getById(id);
        return ResponseEntity.ok(tagDto);
    }
    
    @PostMapping("create")
    public ResponseEntity<TagDto> create(@RequestBody TagDto tag){
        return ResponseEntity.ok(tagServiceImpl.create(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable("id") Long id,@RequestBody TagDto tag){
        return ResponseEntity.ok(tagServiceImpl.update(id,tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTag(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(tagServiceImpl.delete(id));
    }
}
