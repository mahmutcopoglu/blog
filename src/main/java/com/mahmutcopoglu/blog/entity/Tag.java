package com.mahmutcopoglu.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Tag extends BaseEntity{

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

}
