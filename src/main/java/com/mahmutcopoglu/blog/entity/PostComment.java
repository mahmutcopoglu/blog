package com.mahmutcopoglu.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="post_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostComment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", length = 100)
    private String title;

    @Column(name="published")
    private Boolean published;

    @Column(name="published_at")
    private Date publishedAt;

    @Column(name="content",length = 10000)
    private String content;

    @ManyToOne
    private Post post;

    @OneToOne
    @JoinColumn(name="parent_id")
    private PostComment parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent",cascade = CascadeType.ALL)
    private Set<PostComment> children;


}
