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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", length = 100)
    private String title;

    @Column(name="meta_title", length = 100)
    private String metaTitle;

    @Column(name="slug", length = 50)
    private String slug;

    @Column(name="content",length = 10000)
    private String content;

    @ManyToOne
    private Post post;

}
