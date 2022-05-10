package com.mahmutcopoglu.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", length = 100)
    private String title;

    @Column(name="meta_title", length = 100)
    private String metaTitle;

    @Column(name="slug", length = 50)
    private String slug;

    @Column(name="content",length = 10000)
    private String content;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Post> posts = new HashSet<>();

}
